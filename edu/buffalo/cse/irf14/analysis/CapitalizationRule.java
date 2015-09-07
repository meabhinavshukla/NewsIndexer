package edu.buffalo.cse.irf14.analysis;

public class CapitalizationRule extends TokenFilter{

	public CapitalizationRule(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		Token t = new Token();
		Token n = new Token();
		Token p = new Token();
		while(this.stream.hasNext())
		{
		t = this.stream.next();
		
		
		String cap_term = t.getTermText();
		
		String prev_term = null;
		String next_term = null;
		
	/**	TokenStream pre = new TokenStream();
		pre=this.stream;
		Token tpre = new Token();
		//tpre = pre.next();
		
		
		TokenStream next = new TokenStream();
		next=this.stream;
		Token tnext = new Token();
		tnext =next.next();
		**/
		if(this.stream.hasNext())
		{	
			//this.stream.pointer = this.stream.pointer+1;
			n= this.stream.next();
			next_term = n.getTermText();
			this.stream.pointer = this.stream.pointer-1;
			
		}
		
		
		this.stream.pointer = this.stream.pointer-1;
		if(this.stream.pointer!=(-1))
		{	
			//p= this.stream.next();
			//this.stream.pointer = this.stream.pointer-1;
			
			p= this.stream.toke.get(this.stream.pointer);
			prev_term = p.getTermText();
			//this.stream.pointer = this.stream.pointer+1;
		}
		if(stream != null)
		{
				this.stream.pointer = this.stream.pointer+1;
				
				
				
				if(cap_term.length()>1){
				int x = cap_term.charAt(0);
				
				
				
				if(x >= 65 && x <= 90)
				{
					//System.out.println("Captial Term : " + cap_term);
					//System.out.println("Prev Term : " + prev_term);
					//System.out.println("NextTerm : " + next_term);
					
					int y = cap_term.charAt(1);
					if(y < 65 || y > 90)
					{
						
						if(next_term != null && next_term.length()>1)
						{
							y = next_term.charAt(0); 
							if(y >= 65 && y <= 90)
							{
								//System.out.println("San Framn");
								if(cap_term.contains("."))
								{
									t.setTermText(cap_term);
								}
								else{
								t.setTermText(cap_term +" " + next_term);
								
								this.stream.next();
								this.stream.remove();
								
								}
							}
							else if(prev_term!=null)
							{
								//System.out.println("Start of Line.");
								//cap_term=cap_term.toLowerCase();
								//cap_term = cap_term.replaceAll("california", "California");
								//cap_term = cap_term.replaceAll("apple's", "Apple's");
								//cap_term = cap_term.replaceAll("california", "California");
								t.setTermText(cap_term);
							
								
							}
							else
							{	
								cap_term=cap_term.toLowerCase();
								t.setTermText(cap_term);
							}
							
						}
							
					else if(prev_term != null)
						{		
							if(prev_term.endsWith(".") || prev_term.endsWith("?") || prev_term.endsWith("!"))
							{
								//System.out.println("Not New Line Character.");
									t.setTermText(cap_term.toLowerCase());
								
							}
							
							
						}	
						
					}
				}
				
				}
				else{
					t.setTermText(cap_term.toLowerCase());
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
