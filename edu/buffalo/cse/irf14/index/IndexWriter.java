/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.document.Parser;

/**
 * @author nikhillo
 * Class responsible for writing indexes to disk
 */
public class IndexWriter {
	int topk_counter=1;
	static int placecount = 0;
	static int authorcount = 0;
	static int docdickey = 0;
	static int category_counter = 1;
	static int place_counter=1;
	static int author_counter=1;
	static int term_counter=1;
	 int frequency_counter=1;
	
	static HashMap<String, Integer> topk =new HashMap<String,Integer>(); 
	static Map<String, Integer> mk = new HashMap<String, Integer>();
	// static HashMap
	 
	static TreeMap<Integer, String> docdic = new TreeMap<Integer, String>();
	
	static HashMap<String, Integer> categorydictionary = new HashMap<String, Integer>();
	static HashMap<Integer, List<Integer>> categoryindex = new HashMap<Integer, List<Integer>>();
	
	
	static HashMap<String, Integer> authordictionary = new HashMap<String, Integer>();
	static HashMap<Integer, List<Integer>> authorindex = new HashMap<Integer, List<Integer>>();
	
	static TreeMap<String, Integer> termdictionary = new TreeMap<String, Integer>();
	//HashMap<Integer, List<Postings>> termindex = new HashMap<Integer, List<Postings>>();
	static HashMap<Integer, LinkedHashMap<Integer, Integer>> termindex = new HashMap<Integer, LinkedHashMap<Integer, Integer>>();
	
	static HashMap<String, Integer> placedictionary = new HashMap<String, Integer>();
	static HashMap<Integer, List<Integer>> placeindex = new HashMap<Integer, List<Integer>>();
	
	
	
	
	/**
	 * Default constructor
	 * @param indexDir : The root directory to be sued for indexing
	 */
	public IndexWriter(String indexDir) {
		//TODO : YOU MUST IMPLEMENT THIS
	}
	
	/**
	 * Method to add the given Document to the index
	 * This method should take care of reading the filed values, passing
	 * them through corresponding analyzers and then indexing the results
	 * for each indexable field within the document. 
	 * @param d : The Document to be added
	 * @throws IndexerException : In case any error occurs
	 */
	public void addDocument(Document d)  {
		//TODO : YOU MUST IMPLEMENT THIS
		try
		{
		//docid
		
		String[] x = d.getField(FieldNames.FILEID);				//Document dictionary to maintain docdickey-docid(fileid)
		//int docid = Integer.parseInt(x[0]);
		docdickey++;
		docdic.put(docdickey, x[0]);
		
		
		//category
		
		String[] c=d.getField(FieldNames.CATEGORY);				// get category name in c[0]
		if(c!=null)
		{
		if(categorydictionary.get(c[0])==null)					// check if category to categoryid map exists.
			{
					categorydictionary.put(c[0],category_counter);	// add a category id to categorydictionary.
					List<Integer> l = new ArrayList<Integer>();
					l.add(docdickey);								// add the doc id(posting) in the list
					categoryindex.put(category_counter,l );			//
					category_counter++;
			}
		else
			{
				int categoryid = categorydictionary.get(c[0]);
				List<Integer> l = new ArrayList<Integer>();
				l =categoryindex.get(categoryid);
				l.add(docdickey);
				categoryindex.put(categoryid,l );
				
			}
		}
		
		//place
		
		
		String[] city = d.getField(FieldNames.PLACE);			//city
		if(city!=null)
		{
		placecount++;
		String[] split=city[0].split(",");
		if(split.length>1)
			{
				split[0]=split[0].trim();
				split[1]=split[1].trim();
				
				if(placedictionary.get(split[0])==null)
					{
						placedictionary.put(split[0],place_counter);	// add a category id to categorydictionary.
						List<Integer> l = new ArrayList<Integer>();
						l.add(docdickey);								// add the doc id(posting) in the list
						placeindex.put(place_counter,l );			//
						place_counter++;
					}
				else
					{
						int placeid = placedictionary.get(split[0]);
						List<Integer> l = new ArrayList<Integer>();
						l =placeindex.get(placeid);
						l.add(docdickey);
						categoryindex.put(placeid,l );
					
					}
				if(placedictionary.get(split[1])==null)
					{
						placedictionary.put(split[1],place_counter);	// add a category id to categorydictionary.
						List<Integer> l = new ArrayList<Integer>();
						l.add(docdickey);								// add the doc id(posting) in the list
						placeindex.put(place_counter,l );			//
						place_counter++;
					}
				
				else
					{
						int placeid = placedictionary.get(split[1]);
						List<Integer> l = new ArrayList<Integer>();
						l =placeindex.get(placeid);
						l.add(docdickey);
						categoryindex.put(placeid,l );
					
					
					}
				
				
			}
		else 
			{
				if(placedictionary.get(split[0])==null)
					{
				placedictionary.put(split[0],place_counter);	// add a category id to categorydictionary.
				List<Integer> l = new ArrayList<Integer>();
				l.add(docdickey);								// add the doc id(posting) in the list
				placeindex.put(place_counter,l );			//
				place_counter++;
					}
				else
					{
				int placeid = placedictionary.get(split[0]);
				List<Integer> l = new ArrayList<Integer>();
				l =placeindex.get(placeid);
				l.add(docdickey);
				categoryindex.put(placeid,l );
			
			
					}
			}
		}
		
		//author
		
		String[] author = d.getField(FieldNames.AUTHOR);
		
		if(author!=null)
			{
			authorcount++;
			Tokenizer tk = new Tokenizer();
			
			try {
				TokenStream ts = tk.consume(author[0]);
				AnalyzerFactory af = AnalyzerFactory.getInstance();				
				Analyzer as=af.getAnalyzerForField(FieldNames.AUTHOR, ts);//broken
				while(as.increment()){}
				ts=as.getStream();
				ts.reset();
				while(ts.next()!=null)
				{
					Token t = new Token();
					t = ts.getCurrent();
					String s = t.getTermText();
					
					if(authordictionary.get(s)==null)
					{
						authordictionary.put(s,author_counter);	// add a category id to categorydictionary.
						List<Integer> l = new ArrayList<Integer>();
						l.add(docdickey);								// add the doc id(posting) in the list
						authorindex.put(author_counter,l );			//
						author_counter++;
					}
				else
					{
						int authorid = authordictionary.get(s);
						List<Integer> l = new ArrayList<Integer>();
						l =authorindex.get(authorid);
						l.add(docdickey);
						categoryindex.put(authorid,l );
					
			
					}
					
					
				}
				String[] authorg = d.getField(FieldNames.AUTHORORG);
				if(authorg!=null)
					{
					if(authordictionary.get(authorg[0])==null)
					{
						authordictionary.put(authorg[0],author_counter);	// add a category id to categorydictionary.
						List<Integer> l = new ArrayList<Integer>();
						l.add(docdickey);								// add the doc id(posting) in the list
						authorindex.put(author_counter,l );			//
						author_counter++;
					}
				else
					{
						int authorid = authordictionary.get(authorg[0]);
						List<Integer> l = new ArrayList<Integer>();
						l =authorindex.get(authorid);
						l.add(docdickey);
						categoryindex.put(authorid,l );
					
			
					}
					}
				
				
			} catch (TokenizerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
		
		//term
		
		String[] termc=d.getField(FieldNames.CONTENT);
		
		if(termc!=null)
		{
		Tokenizer tk = new Tokenizer();
		
		try {
			TokenStream ts = tk.consume(termc[0]);
			AnalyzerFactory af = AnalyzerFactory.getInstance();				
			//af.getAnalyzerForField(FieldNames.CONTENT, ts);//broken
			Analyzer as=af.getAnalyzerForField(FieldNames.CONTENT, ts);//broken
			while(as.increment()){}
			ts=as.getStream();
			ts.reset();
			
			while(ts.next()!=null)
			{
				Token t = new Token();
				t = ts.getCurrent();
				String s = t.getTermText();
				
				if(termdictionary.get(s)==null)
				{	
					topk.put(s, topk_counter);
					termdictionary.put(s,term_counter);	// add a category id to categorydictionary.
					//List<Postings> l = new ArrayList<Postings>();
					//Postings p = new Postings(frequency_counter,docdickey);
												// add the doc id(posting) in the list
					LinkedHashMap<Integer, Integer> hm = new LinkedHashMap<Integer, Integer>();
					hm.put(docdickey, frequency_counter);
					termindex.put(term_counter,hm );			//
					term_counter++;
					//frequency_counter++;
				}
			else
				{
					int a=topk.get(s);
					a++;
					topk.put(s, a);
					
					int termid = termdictionary.get(s);
					//List<Postings> l = new ArrayList<Postings>();
					HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
					hm =termindex.get(termid);
					if(hm.get(docdickey)==null)
					{
						hm.put(docdickey, frequency_counter);
						
					}
					else{
						int frequency = hm.get(docdickey);
						frequency++;
						hm.put(docdickey, frequency);
					}
					
					//Postings p = new Postings(frequency_counter,docdickey);
					//p=l.get(termid);
					//p.frequency++;
					//l.add(p);
					//termindex.put(termid,l );
				
		
				}
				
				
			}
			
			
			
		} catch (TokenizerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
String[] termt=d.getField(FieldNames.TITLE);
		
		if(termt!=null)
		{
		Tokenizer tk = new Tokenizer();
		
		try {
			TokenStream ts = tk.consume(termt[0]);
			AnalyzerFactory af = AnalyzerFactory.getInstance();				
			Analyzer as=af.getAnalyzerForField(FieldNames.TITLE, ts);//broken
			while(as.increment()){}
			ts=as.getStream();
			ts.reset();
			
			while(ts.next()!=null)
			{
				Token t = new Token();
				t = ts.getCurrent();
				String s = t.getTermText();
				
				if(termdictionary.get(s)==null)
				{

					topk.put(s, topk_counter);
					termdictionary.put(s,term_counter);
					termdictionary.put(s,term_counter);	// add a category id to categorydictionary.
					//List<Postings> l = new ArrayList<Postings>();
					//Postings p = new Postings(frequency_counter,docdickey);
												// add the doc id(posting) in the list
					LinkedHashMap<Integer, Integer> hm = new LinkedHashMap<Integer, Integer>();
					hm.put(docdickey, frequency_counter);
					termindex.put(term_counter,hm );			//
					term_counter++;
					//frequency_counter++;
				}
			else
				{
					int a=topk.get(s);
					a++;
					topk.put(s, a);
					int termid = termdictionary.get(s);
					//List<Postings> l = new ArrayList<Postings>();
					HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
					hm =termindex.get(termid);
					if(hm.get(docdickey)==null)
					{
						hm.put(docdickey, frequency_counter);
						
					}
					else{
						int frequency = hm.get(docdickey);
						frequency++;
						hm.put(docdickey, frequency);
					}
					
					//Postings p = new Postings(frequency_counter,docdickey);
					//p=l.get(termid);
					//p.frequency++;
					//l.add(p);
					//termindex.put(termid,l );
				
		
				}
				
				
			}
			
			
			
		} catch (TokenizerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	/**	
		if(category.get(c[0])==null){
			cat.add(docdickey);
		}
		cat = category.get(c[0]);		
		cat.add(docdickey);
		category.put(c[0], cat);
		cat.clear();
		
		
		String[] a=d.getField(FieldNames.AUTHOR);
		
		**/
		
		
		
		
		
		/**
		
		if(FieldNames.AUTHOR!=null)
		
			{	
				String[] a=d.getField(FieldNames.AUTHOR);
				String[] awe = a[0].split(",");
				int length = awe.length;
				if(awe.length>1)
					{
						String[] split = awe[length-1].split("and");
						for(int i = 0; i<(length-1) ; i++)
							{		awe[i]=awe[i].trim();
								if(author.get(awe[i])==null)
									{
										cat.add(docdickey);
										author.put(awe[i],cat);
					
									}
								else
									{
										cat=author.get(awe[i]);
										cat.add(docdickey);
										author.put(awe[i],cat);
									}
							}
					for(int i = 0 ; i < split.length; i++)
						{
							split[i]=split[i].trim();
							if(author.get(split[i])==null)
								{
									cat.add(docdickey);
									author.put(split[i],cat);
				
									
								}
							else
								{
									cat=author.get(split[i]);
									cat.add(docdickey);
									author.put(split[i],cat);
								}
						}
			}
		
		else
		{
			String[] awe1 = a[0].split("and");
			
			if(awe1.length>1)
				{	awe1[0]=awe1[0].trim();
					if(author.get(awe1[0])==null)
						{
							cat.add(docdickey);
							author.put(awe1[0],cat);
						}
					else
						{
						cat=author.get(awe1[0]);
						cat.add(docdickey);
						author.put(awe1[0],cat);
						}
					awe1[1]=awe1[1].trim();
					if(author.get(awe1[1])==null)
						{
							cat.add(docdickey);
							author.put(awe1[1],cat);
						}
					else
						{
						cat=author.get(awe1[1]);
						cat.add(docdickey);
						author.put(awe1[1],cat);
						}
				}
			if(awe1.length==1)
			{ awe1[0]=awe1[0].trim();
			if(author.get(awe1[0])==null)
			{
				cat.add(docdickey);
				author.put(awe1[0],cat);
			}
			
			else{
			cat=author.get(a[0]);
			cat.add(docdickey);
			author.put(a[0],cat);
			}
			}
		}
	}**/
		
		
		//Printinggggggg
		
		
		
		/**	try{
		    File fileTwo=new File("authordic.txt");
		    FileOutputStream fos=new FileOutputStream(fileTwo);
		       PrintWriter pw=new PrintWriter(fos);
		       // ObjectOutputStream obj = new ObjectOutputStream(fos);
		        //obj.writeObject(docdic);

		        for(Map.Entry<String, Integer> m :authordictionary.entrySet()){
		            pw.println(m.getKey()+"="+m.getValue());
		        }

		       pw.flush();
		      pw.close();
		      //  obj.close();
		       fos.close();
		    }catch(Exception e){}
		try{
		    File fileTwo=new File("catdic.txt");
		    FileOutputStream fos=new FileOutputStream(fileTwo);
		       PrintWriter pw=new PrintWriter(fos);
		       // ObjectOutputStream obj = new ObjectOutputStream(fos);
		        //obj.writeObject(docdic);

		        for(Map.Entry<String, Integer> m :categorydictionary.entrySet()){
		            pw.println(m.getKey()+"="+m.getValue());
		        }

		       pw.flush();
		      pw.close();
		      //  obj.close();
		       fos.close();
		    }catch(Exception e){}
		try{
		    File fileTwo=new File("placedic.txt");
		    FileOutputStream fos=new FileOutputStream(fileTwo);
		       PrintWriter pw=new PrintWriter(fos);
		       // ObjectOutputStream obj = new ObjectOutputStream(fos);
		        //obj.writeObject(docdic);

		        for(Map.Entry<String, Integer> m :placedictionary.entrySet()){
		            pw.println(m.getKey()+"="+m.getValue());
		        }

		       pw.flush();
		      pw.close();
		      //  obj.close();
		       fos.close();
		    }catch(Exception e){}
		try{
		    File fileTwo=new File("termdic.txt");
		    FileOutputStream fos=new FileOutputStream(fileTwo);
		       PrintWriter pw=new PrintWriter(fos);
		       // ObjectOutputStream obj = new ObjectOutputStream(fos);
		        //obj.writeObject(docdic);

		        for(Map.Entry<String, Integer> m :termdictionary.entrySet()){
		            pw.println(m.getKey()+"="+m.getValue());
		        }

		       pw.flush();
		      pw.close();
		      //  obj.close();
		       fos.close();
		    }catch(Exception e){}
		try{
		    File fileTwo=new File("authorindex.txt");
		    FileOutputStream fos=new FileOutputStream(fileTwo);
		       PrintWriter pw=new PrintWriter(fos);
		       // ObjectOutputStream obj = new ObjectOutputStream(fos);
		        //obj.writeObject(docdic);

		        for(Entry<Integer, List<Integer>> m :authorindex.entrySet()){
		            pw.println(m.getKey()+"="+m.getValue());
		        }

		       pw.flush();
		      pw.close();
		      //  obj.close();
		       fos.close();
		    }catch(Exception e){}
		try{
		    File fileTwo=new File("termindex.txt");
		    FileOutputStream fos=new FileOutputStream(fileTwo);
		       PrintWriter pw=new PrintWriter(fos);
		       // ObjectOutputStream obj = new ObjectOutputStream(fos);
		        //obj.writeObject(docdic);

		        for(Entry<Integer, LinkedHashMap<Integer, Integer>> m :termindex.entrySet()){
		            pw.println(m.getKey()+"="+m.getValue());
		        }

		       pw.flush();
		      pw.close();
		      //  obj.close();
		       fos.close();
		    }catch(Exception e){}
		
		
	///**	List<Entry<String,Integer>> sortedEntries = new ArrayList<Entry<String,Integer>>(topk.entrySet());
		Collections.sort(sortedEntries, 
	            new Comparator<Entry<String,Integer>>(){
			public int compare( Map.Entry<String,Integer> o1, Map.Entry<String, Integer> o2 )
					 {
						// TODO Auto-generated method stub
						return (o1.getValue()).compareTo( o2.getValue() );
						
					}
			
		});
		
		
		
		mk= sortByValue(topk);
		try{
		    File fileTwo=new File("mk.txt");
		    FileOutputStream fos=new FileOutputStream(fileTwo);
		       PrintWriter pw=new PrintWriter(fos);
		       // ObjectOutputStream obj = new ObjectOutputStream(fos);
		        //obj.writeObject(docdic);

		        for(Entry<String, Integer> m :mk.entrySet()){
		            pw.println(m.getKey()+"="+m.getValue());
		        }

		       pw.flush();
		      pw.close();
		      //  obj.close();
		       fos.close();
		    }catch(Exception e){}
		
		
		
		
		/**
		for (Integer n: docdic.keySet() ){

	        String key =n.toString();
	        String value = docdic.get(n).toString();  
	        System.out.println(key + " " + value); 
	}
	 **/
		}catch(Exception e)
		{e.printStackTrace();}

		
} 
	
	/**
	 * Method that indicates that all open resources must be closed
	 * and cleaned and that the entire indexing operation has been completed.
	 * @throws IndexerException : In case any error occurs
	 */
	public void close() throws IndexerException {
		//TODO
	/**	try{
		    File fileTwo=new File("docdic.txt");
		    FileOutputStream fos=new FileOutputStream(fileTwo);
		       PrintWriter pw=new PrintWriter(fos);
		       // ObjectOutputStream obj = new ObjectOutputStream(fos);
		        //obj.writeObject(docdic);

		        for(Map.Entry<Integer,String> m :docdic.entrySet()){
		            pw.println(m.getKey()+"="+m.getValue());
		        }

		       pw.flush();
		      pw.close();
		      //  obj.close();
		       fos.close();
		    }catch(Exception e){}**/
	}

static Map<String, Integer> sortByValue(Map<String, Integer> map) {
    List list = new LinkedList(map.entrySet());
    Collections.sort(list, new Comparator() {
         public int compare(Object o1, Object o2) {
              return ((Comparable) ((Map.Entry) (o2)).getValue())
             .compareTo(((Map.Entry) (o1)).getValue());
         }
    });

   Map result = new LinkedHashMap<String, Integer>();
   for (Iterator it = list.iterator(); it.hasNext();) {
       Map.Entry entry = (Map.Entry)it.next();
       result.put(entry.getKey(), entry.getValue());
   }
   return result;
}
}

