package edu.buffalo.cse.irf14.analysis;

public class AuthorAnalyzer implements Analyzer {
	TokenStream ts = new TokenStream(); 
	AuthorAnalyzer(TokenStream stream){
		ts=stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		/*SymbolRule sr = new SymbolRule(ts);
		while(sr.increment()){
		
		}

		ts=sr.getStream();
		ts.reset();*/
		
		StopWords sw = new StopWords(ts);
		while(sw.increment()){

		}

			ts=sw.getStream();
			ts.reset();
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}

}
