package edu.buffalo.cse.irf14.analysis;





public class StopWords extends TokenFilter{

	//final ArrayList<String> sw = new ArrayList<String>(25);
	public StopWords(TokenStream stream) {
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
		String stopWords[]={"a","A","an","An","and","And","are","Are","as","As","at","At","be","Be","by","By","for","For","from","From","do","Do","not","Not","this","This",
				"has","Has","he","He","in","In","is","Is","it","It","its","Its","of","on","I","i","that","the","to","was","were","will","with"};
			
			if (this.stream != null) {
				
				
						
					
					String compare = t.getTermText();
					for(int i=0;i<stopWords.length;i++)
					{
						if (compare.equalsIgnoreCase(stopWords[i]))
						{
							this.stream.toke.remove(t);
							this.stream.reset();
							break;
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
/**	public StopWords(){
		super(null);
	 
	sw.add("a");
	sw.add("an");
	sw.add("and");
	sw.add("are");
	sw.add("as");
	sw.add("at");
	sw.add("be");
	sw.add("by");
	sw.add("for");
	sw.add("from");
	sw.add("has");
	sw.add("he");
	sw.add("in");
	sw.add("is");
	sw.add("it");
	sw.add("its");
	sw.add("of");
	sw.add("on");
	sw.add("that");
	sw.add("the");
	sw.add("to");
	sw.add("was");
	sw.add("were");
	sw.add("will");
	sw.add("with");
	
	}**/
	/**public String[] sw = new String[25];{
	sw[0]="a";
	sw[1]="an";
	sw[2]="and";
	sw[3]="are";
	sw[4]="as";
	sw[5]="at";
	sw[6]="be";
	sw[7]="by";
	sw[8]="for";
	sw[9]="from";
	sw[10]="has";
	sw[11]="he";
	sw[12]="in";
}**/
/**	public String[] STOPWORD(String s){
		String[] split = s.split(" ");
		List<String> p = Arrays.<String>asList(split);
		ArrayList<String> input = new ArrayList<String>(p);
		for(String x : input ){
		if (sw.contains(x))
			input.remove(x);
		}
		String[] output = new String[input.size()];
		output = input.toArray(output);
		return output;
	}
	**/
}
