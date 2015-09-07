package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberRule extends TokenFilter{

	public NumberRule(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		String TIME = "([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
		String YEAR="^([0-9]{4})(0[1-9]|1[012])((0?[1-9])|[12][0-9]|3[01])";
		String TIMESTAMP = "([0-9]{4})(0[1-9]|1[012])((0?[1-9])|[12][0-9]|3[01])\\s([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
		String PATTERN = "^(\\d{1,})[^a-zA-Z]{0,}\\d{1,}";
		String PATTERN1 = "\\d{1,}\\,{0,}\\.{0,}";
		
		Token t = new Token();
		while(this.stream.hasNext())
		{
		t = this.stream.next();
		String token = t.getTermText();
		
			if (stream != null) 
			{
				
						
					
					if (!(token.matches(TIME)||token.matches(YEAR)||token.matches(TIMESTAMP)))
					{
						Pattern datePattern = Pattern.compile(PATTERN);
					    Matcher matchDatePattern = datePattern.matcher(token);
					    if (matchDatePattern.find())
					    {
					    	Pattern date1 = Pattern.compile(PATTERN1);
							Matcher matchDate1 = date1.matcher(token);
					    	if (matchDate1.find())
					    	{
					    		token=token.replaceAll(PATTERN1,"");
					    		
					    		if(token.isEmpty())
					    			this.stream.toke.remove(t);
					    		else
					    			t.setTermText(token);
					    		
					    	}
					    }
					}
				
			}
		}
		return false;
			
		}
	

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return this.stream;
	}

}
