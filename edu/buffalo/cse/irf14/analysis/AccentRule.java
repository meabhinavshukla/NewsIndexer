package edu.buffalo.cse.irf14.analysis;

public class AccentRule extends TokenFilter {

	public AccentRule(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}
	//char[] desired = new char[256];
	
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		Token t = new Token();
		while(this.stream.hasNext())
		{
		t = this.stream.next();
				
		char[] buffer = t.getTermBuffer();
		int length = buffer.length;
		
		
		for(int i =0; i < length; i++)
		{	
			
			char c = buffer[i];
			if(c>='\u00c0' && c<='\u0178')
			{
				String set =accentRemove(buffer,length);
				
				
				t.setTermText(set);
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
	
	public String accentRemove(char[] remove,int length)
	{	char[] desired = new char[256];
		int sizemax = 2*length;
		int requiredsize = desired.length;
		int position = 0;
		int desiredpos = 0;
		while(requiredsize<sizemax)
			requiredsize*=requiredsize;
		if (requiredsize!=desired.length)
			desired  = new char[requiredsize];
		for(int i = 0; i <length; i++,position++)
		{
			char inputchar = remove[position];
			if(inputchar<'\u00c0'){
				
				desired[desiredpos++]=inputchar;
			}
			else{
				
				switch(inputchar)
				{
					case '\u00C0' :
					case '\u00C1' :
					case '\u00C2' :
					case '\u00C3' :
					case '\u00C4' :
					case '\u00C5' :
						desired[desiredpos++] = 'A';
						break;
					case '\u00C6' :
						desired[desiredpos++] = 'A';
						desired[desiredpos++] = 'E';
						break;
					case '\u00C7' :
						desired[desiredpos++] = 'C';
						break;
					case '\u00C8' :
					case '\u00C9' :
					case '\u00CA' :
					case '\u00CB' :
						desired[desiredpos++] = 'E';
						break;
					case '\u00CC' :
					case '\u00CD' :
					case '\u00CE' :
					case '\u00CF' :
						desired[desiredpos++] = 'I';
						break;
					case '\u00D0' :
						desired[desiredpos++] = 'D';
						break;
					case '\u00D1' :
						desired[desiredpos++] = 'N';
						break;
					case '\u00D2' :
					case '\u00D3' :
					case '\u00D4' :
					case '\u00D5' :
					case '\u00D6' :
					case '\u00D7' :
					case '\u00D8' :
						desired[desiredpos++] = 'O';
						break;
					case '\u0152' :
						desired[desiredpos++] = 'O';
						desired[desiredpos++] = 'E';
						break;
					case '\u00DE' :
						desired[desiredpos++] = 'T';
						desired[desiredpos++] = 'H';
						break;
					case '\u00D9' :
					case '\u00DA' :
					case '\u00DB' :
					case '\u00DC' :
						desired[desiredpos++] = 'U';
						break;
					case '\u00DD' :
					case '\u0178' :
						desired[desiredpos++] = 'Y';
						break;
					//SMALL CASE
					case '\u00E0' :
					case '\u00E1' :
					case '\u00E2' :
					case '\u00E3' :
					case '\u00E4' :
					case '\u00E5' :
						desired[desiredpos++] = 'a';
						break;
					case '\u00E6' :
						desired[desiredpos++] = 'a';
						desired[desiredpos++] = 'e';
						break;
					case '\u00E7' :
						desired[desiredpos++] = 'c';
						break;
					case '\u00E8' :
					case '\u00E9' :
					case '\u00EA' :
					case '\u00EB' :
						desired[desiredpos++] = 'e';
						break;
					case '\u00EC' :
					case '\u00ED' :
					case '\u00EE' :
					case '\u00EF' :
						desired[desiredpos++] = 'i';
						break;
					case '\u00F0' :
						desired[desiredpos++] = 'd';
						break;
					case '\u00F1' :
						desired[desiredpos++] = 'n';
						break;
					case '\u00F2' :
					case '\u00F3' :
					case '\u00F4' :
					case '\u00F5' :
					case '\u00F6' :
					case '\u00F7' :
					case '\u00F8' :
						desired[desiredpos++] = 'o';
						break;
					case '\u0153' :
						desired[desiredpos++] = 'o';
						desired[desiredpos++] = 'e';
						break;
					case '\u00DF' :
						desired[desiredpos++] = 's';
						desired[desiredpos++] = 's';
						break;
					case '\u00FE' :
						desired[desiredpos++] = 't';
						desired[desiredpos++] = 'h';
						break;
					case '\u00F9' :
					case '\u00FA' :
					case '\u00FB' :
					case '\u00FC' :
						desired[desiredpos++] = 'u';
						break;
					case '\u00FD' :
					case '\u00FF' :
						desired[desiredpos++] = 'y';
						break;
					default :
						desired[desiredpos++]=inputchar;
						break;
						
				
					
				}
				
			}
		}
		String desired1 = new String(desired);
		desired1 = desired1.trim();
		return desired1;
	}
}
