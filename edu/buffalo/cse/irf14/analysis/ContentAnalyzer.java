package edu.buffalo.cse.irf14.analysis;

public class ContentAnalyzer implements Analyzer {
	TokenStream ts = new TokenStream(); 
	ContentAnalyzer(TokenStream stream){
		ts=stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		CapitalizationRule cp = new CapitalizationRule(ts);
			while(cp.increment()){
			
			}
		
		ts=cp.getStream();
		ts.reset();
		SymbolRule sr = new SymbolRule(ts);
		while(sr.increment()){
		
		}
	
		ts=sr.getStream();
		ts.reset();
	StopWords sw = new StopWords(ts);
	while(sw.increment()){
	
	}

		ts=sw.getStream();
		ts.reset();
		AccentRule ar = new AccentRule(ts);
		while(ar.increment()){
			
		}
		
		ts=ar.getStream();
		ts.reset();
		StemmerRule ser = new StemmerRule(ts);
		while(ser.increment()){
		
		}
	
		ts=ser.getStream();
		ts.reset();
		
		SpecialCharRule scr = new SpecialCharRule(ts);
		while(scr.increment()){
		
		}
	
		ts=scr.getStream();
		ts.reset();
		NumberRule nr = new NumberRule(ts);
		while(nr.increment()){
		
		}
	
		ts=nr.getStream();
		ts.reset();
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}

}
