/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.io.*;
import java.util.regex.*;


/**
 * @author nikhillo
 * Class that parses a given file into a Document
 */
public class Parser {
	static String content="";
	public static boolean hasAuthor(String ln){
		final String auth = "<AUTHOR>";
		if(ln.contains(auth))
		return true;
		return false;
	}
	/*public static String regExtractor(String desired, String undesired){
		String finalstring;
		Pattern pat = Pattern.compile(desired);
		Matcher mat = pat.matcher(undesired);
		finalstring = mat.replaceAll("");
		finalstring.trim();
		return finalstring;
		
	}
	*/
	public static String clearAuthor(String undesired){
		String desired;
		//Pattern pat = Pattern.compile(undesired);
		//Matcher mat = pat.matcher("/");
		//desired = mat.replaceAll("");
		//Matcher mat1 = pat.matcher("(?i)(<Author>|</Author>)");
		//desired = mat1.replaceAll("");
		//Matcher mat2 = pat.matcher("(?i)(\bby\b)");
		//desired = mat2.replaceFirst("");
		//desired = undesired.replaceAll("/","");
		desired = undesired.replaceAll("(?i)(<Author>|</Author>)","");
		desired = desired.trim();
		desired = desired.replaceAll(",.*", "");
		desired = desired.trim();
		desired = desired.replaceFirst("(By|by|BY|bY)", "");
		desired = desired.trim();
		return desired;
	}
	public static String getAuthororg(String undesired){
		String desired;
		//Pattern pat = Pattern.compile(undesired);
		//Matcher mat = pat.matcher("[A-Z a-z]*,");
		//desired = mat.replaceAll("");
		desired = undesired.replaceAll("[A-Z a-z]*,", "");
		desired = desired.trim();
		desired = desired.replaceFirst("(By|by)", "");
		desired = desired.trim();
		desired = desired.replaceAll("(?i)(<AUTHOR>|</AUTHOR>)", "");
		desired=desired.trim();
		return desired;
	}
	public static String getPlace(String undesired){
		String desired;
		//Pattern pat = Pattern.compile(undesired);
		//Matcher mat = pat.m
		//undesired=mat.replaceAll("");
		String str = undesired.replaceAll("-.*"," ");
		String[] arr=str.split(",");
		
		//Pattern pat1 = Pattern.compile(desired);
		//Matcher mat1 = pat1.matcher("[A-Z.]*,");
		//desired = mat1.group();
		
		desired = arr[0];
		desired= desired.trim();
		//desired = desired.concat("ny");
		if (arr.length>=3){
			desired = desired.concat(",");
			desired= desired.concat(arr[1]);
		}
		//Pattern pat2 = Pattern.compile(arr[0]));
		//Matcher mat2 = pat2.matcher(",$");
		//desired = mat2.replaceAll("");
		return desired;
		}
	public static String getDate(String undesired){
		String desired;
		String str = undesired.replaceAll("-.*"," ");
		desired = str.replaceAll(".*,", "");
		//Pattern pat = Pattern.compile(undesired);
		//Matcher mat = pat.matcher("-.*");
		//desired = mat.replaceAll("");
		//Pattern pat1 = Pattern.compile(desired);
		//Matcher mat1 = pat1.matcher("[A-Z.]*,");
		//desired = mat1.replaceAll("");
		desired= desired.trim();
		return desired;
	}
	public static String getContent1(String undesired){
		String desired;
		//Pattern pat = Pattern.compile(undesired);
		//Matcher mat = pat.matcher(".*-");
		//desired = mat.replaceAll("");
		desired =undesired.replaceAll(".*-", "");
		desired=desired.trim();
		return desired;
	}
	public static void addToContent(String concate){
		
	    
		content = content.concat(concate);
	}
	/**
	 * Static method to parse the given file into the Document object
	 * @param filename : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException In case any error occurs during parsing
	 */
	public static Document parse(String filename) throws ParserException {
		// TODO YOU MUST IMPLEMENT THIS
		Document doc1=new Document();
		try{
			
		if (filename==""|filename == null){
			throw new ParserException();
		}
		
		
		File file = new File(filename);
		String fname=file.getName();
		//System.out.println(filename);
	/**	boolean b= fname.matches("[A-Za-z0-9]*");
		if(!b){
			System.out.println(fname);
			throw new ParserException();
		}**/
		
		String catname=file.getParentFile().getName();
		
		//System.out.println(fname);
		
		doc1.setField(FieldNames.FILEID, fname);
		doc1.setField(FieldNames.CATEGORY, catname);
	
		
		
		FileReader fRead = new FileReader(file);
		BufferedReader br = new BufferedReader(fRead);
		
		Boolean isTitle=true,isMetadata = true;
		String line,line1,line2,line3;
		//line = br.readLine();
		while((line = br.readLine())!=null)
		{
			if(line.length()>0)
			{
			if(isTitle)
			{
				doc1.setField(FieldNames.TITLE,line);
				//System.out.println(line);
				isTitle=false;
			}
			else if(hasAuthor(line))
			{
				line1 = clearAuthor(line);
				doc1.setField(FieldNames.AUTHOR, line1);
				//System.out.println(line1);
				line2 = getAuthororg(line);
				doc1.setField(FieldNames.AUTHORORG, line2);
				//System.out.println(line2);
				
			}
			else if(isMetadata)
			{
				line1 = getPlace(line);
				doc1.setField(FieldNames.PLACE, line1);
				//System.out.println(line1);
				line2 = getDate(line);
				doc1.setField(FieldNames.NEWSDATE, line2);
				//System.out.println(line2);
				line3 = getContent1(line);
				addToContent(line3);
				content = content.concat(" ");
				isMetadata = false;
			}
			else{
				addToContent(line);
				doc1.setField(FieldNames.CONTENT, content);
				
			}
			
			
			}
			
			
		}
	//	System.out.println(content);
		content = "";
		
		//br.close();
	}
		
		catch(Exception e)
	{
			throw new ParserException();
		//e.printStackTrace();
	}	
		
		return doc1;
	
	}

}
