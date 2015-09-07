package edu.buffalo.cse.irf14.analysis;



public class SpecialCharRule extends TokenFilter{

	public SpecialCharRule(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		Token t = new Token();
		while(this.stream.hasNext())
		{
		t = this.stream.next();
		
		String token = t.getTermText();
		token = token.replaceAll("~","");
		token = token.replaceAll("#","");
		token = token.replaceAll("\\$","");
		token = token.replaceAll("%","");
		token = token.replaceAll("&","");
		token = token.replaceAll("\\(","");
		token = token.replaceAll("\\)","");
		token = token.replaceAll(":","");
		token = token.replaceAll("\"","");
		token = token.replaceAll(",","");
		token = token.replaceAll(";","");
		token = token.replaceAll("\\+","");
		token = token.replaceAll("\\<","");
		token = token.replaceAll("\\>","");
		token = token.replaceAll("_","");
		token = token.replaceAll("\\\\","");
		token = token.replaceAll("/","");
		token = token.replaceAll("=","");
		token = token.replaceAll("\\|","");
		if(!token.matches(".*\\d+"))
		{
		token = token.replaceAll("-","");
		}
		token = token.replaceAll("@","");
		token = token.replaceAll("\\*","");
		token = token.replaceAll("\\^","");
		
		token = token.replaceAll("( )+", " ");
		token = token.trim();
		t.setTermText(token);
		}
		return false;
		
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return this.stream;
	}

}
