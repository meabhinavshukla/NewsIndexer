/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nikhillo
 * Class that emulates reading data back from a written index
 */
public class IndexReader {
	
	IndexWriter iw=null;
	IndexType type1;
	 List<String> lf=new ArrayList<String>();
	/**
	 * Default constructor
	 * @param indexDir : The root directory from which the index is to be read.
	 * This will be exactly the same directory as passed on IndexWriter. In case 
	 * you make subdirectories etc., you will have to handle it accordingly.
	 * @param type The {@link IndexType} to read from
	 */
	public IndexReader(String indexDir, IndexType type) {
		//TODO
		iw = new IndexWriter(indexDir);
		type1=type;
	}
	
	/**
	 * Get total number of terms from the "key" dictionary associated with this 
	 * index. A postings list is always created against the "key" dictionary
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {
		//TODO : YOU MUST IMPLEMENT THIS
		int size = 0;
		switch(type1){
		case TERM :
			size = iw.termindex.size();
			return size;
			
		case AUTHOR:
			size = iw.authorindex.size();
			return size;
		case CATEGORY:
			size = iw.categoryindex.size();
			return size;
		case PLACE:
			size = IndexWriter.placeindex.size();
			return size;
			
		}
		return -1;
	}
	
	/**
	 * Get total number of terms from the "value" dictionary associated with this 
	 * index. A postings list is always created with the "value" dictionary
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		//TODO: YOU MUST IMPLEMENT THIS
		int size = 0;
		switch(type1){
		case AUTHOR :
			size = iw.authorcount;
			return size;
		case CATEGORY:
			size = iw.category_counter;
			return size;
		case PLACE:
			size = iw.placecount;
			return size;
		case TERM:
			size = iw.docdickey;
			return size;
		}
		return -1;
	}
	
	/**
	 * Method to get the postings for a given term. You can assume that
	 * the raw string that is used to query would be passed through the same
	 * Analyzer as the original field would have been.
	 * @param term : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the 
	 * number of occurrences as values if the given term was found, null otherwise.
	 */
	public Map<String, Integer> getPostings(String term) {
		//TODO:YOU MUST IMPLEMENT THIS
		if(!term.equals("null"))
		{
		Map<String,Integer> map=new HashMap<String,Integer>();
		switch(type1){
		case AUTHOR :
			int aone=iw.authordictionary.get(term);
			List<Integer> l1=iw.authorindex.get(aone);
			for(int x:l1)
			{
				int j=l1.get(x);
				String str=iw.docdic.get(j);
				//int s=l1.size();
				map.put(str, 1);
				
				
			}
			return map;
			
		case CATEGORY:
		
		case PLACE:
			
		case TERM:
			if(term!=null){
			int tone=iw.termdictionary.get(term);
			HashMap<Integer,Integer> thm=iw.termindex.get(tone);
			Set<Integer> s=thm.keySet();
			for (Integer x : s) {
				String str=iw.docdic.get(x);
				int a=thm.get(x);
				map.put(str, a);
		      }
			return map;
			}
			else{return null;}
		}
		}
		return null;
	}
	
	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * @param k : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values
	 * null for invalid k values
	 */
	public List<String> getTopK(int k) {
		//TODO YOU MUST IMPLEMENT THIS
		if(k<1)
			return null;
		List<String> lf=new ArrayList<String>();
		Set s=iw.mk.keySet();
		String[] str=(String[]) s.toArray(new String[s.size()]);
	//	String[] str = (String[]) iw.mk.values().toArray();
		for(int i=0;i<k;i++)
		{	
			lf.add(str[i]);
		}
		
		
		return lf;
	}
	
	/**
	 * Method to implement a simple boolean AND query on the given index
	 * @param terms The ordered set of terms to AND, similar to getPostings()
	 * the terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key 
	 * and number of occurrences as the value, the number of occurrences 
	 * would be the sum of occurrences for each participating term. return null
	 * if the given term list returns no results
	 * BONUS ONLY
	 */
	public Map<String, Integer> query(String...terms) {
		//TODO : BONUS ONLY
		Map<String,Integer> ret = new HashMap<String,Integer>();
		HashMap<Integer,Integer> hmt = new HashMap<Integer,Integer>();
		String[] t = terms;
		for(int i = 0; i<t.length; i++)
		{	//switch(i)
			if(i==0){
			int termid = iw.termdictionary.get(t[i]);
			hmt = iw.termindex.get(termid);
			Set<Integer> s=hmt.keySet();
			for(Integer y: s){
				String p = iw.docdic.get(y);
				int l = hmt.get(y);
				ret.put(p, l);
			}
			}
			else if(i>=1){
			//	Set<String> r = ret.keySet();
			
				int termid = iw.termdictionary.get(t[i]);
				HashMap<Integer,Integer> hmt1 = new HashMap<Integer,Integer>();
				hmt1 = iw.termindex.get(termid);
				Set<Integer> s=hmt1.keySet();
				for(Integer y: s){
					String p=iw.docdic.get(y);
					if(!hmt1.get(y).equals(null)){
					if(!ret.containsKey(p))
					{
						ret.remove(p);
					}
					else 
					{
						
						int a=hmt1.get(y);
						int d = ret.get(p);
						//int m = Math.min(d,a);
						int k = a+d;
						ret.put(p,k);
					}
					}
					else{
						ret.remove(p);
					}
				}
				}}
				
			
		if(ret.isEmpty()){
			return null;
		}
		return ret;
	}
}
