package edu.buffalo.cse.irf14.analysis;

import java.util.Arrays;

public class SymbolRule extends TokenFilter{
	static String desired="";
	public SymbolRule(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		Token t = new Token();
		while(this.stream.hasNext())
	{
		t = stream.next();
		//String desired;
		
		
		String s = t.getTermText();
		
			desired= s.replaceAll("(\\.||\\?||\\!||')*$", "");
			desired = desired.replaceAll("won't", "will not");
			desired = desired.replaceAll("shan't", "shall not");
	if(desired.contains("'"))
	{
		
		if(desired.contains("''"))
		{
			String[] s2 = desired.split("''");
			desired = s2[0]+s2[1];
		}
		String[] splitted = desired.split("'");
		
		
		if(splitted[0]=="")
		{
			if(splitted[1]=="em")
			{
				desired = "them";
			}
			else{
			desired = splitted[1];
			}
		}
		char[] ye = splitted[0].toCharArray();
		int length = ye.length;
		
		int previous = length -1;
		if(splitted.length>1)
		{
			switch(splitted[1])
			{
			case "t" :
				
							
				ye[previous]=' ';
				desired = new String(ye);
				desired=desired.concat("not");
				
				break;
			case "d" :
				desired = splitted[0].concat(" "+"would"); 
				break;
			case "ll" :
				desired = splitted[0].concat(" "+"will");
				break;
			case "s" :
				desired = splitted[0];
				break;
			case "m" :
				desired = splitted[0].concat(" "+"am");
				break;
			case "ve" :
				desired = splitted[0].concat(" "+"have");
				break;
			case "re" :
				desired = splitted[0].concat(" "+"are");
				break;
			case "em" :
				desired = "them";
				break;
			default :
				desired = splitted[0].concat(splitted[1]);
			}
		}
	}
	if(desired.contains("-"))
	{
		if(desired.matches("\\-+"))
		{
			this.stream.toke.remove(t);
			
		}
		else if(!desired.matches(".*\\d.*")||desired.matches("\\-+")){
			String[] k = desired.split("-");
			String desiredcopy=k[0];
			for(int i=1;i<k.length;i++)
			{
				desiredcopy = desiredcopy.concat(" "+k[i]);
			}
			desiredcopy = desiredcopy.trim();
			desired = desiredcopy;
			
		}
	}
	t.setTermText(desired);
	}
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return this.stream;
	}

}
