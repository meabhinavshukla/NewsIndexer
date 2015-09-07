/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo
 * This factory class is responsible for instantiating "chained" {@link Analyzer} instances
 */
public class AnalyzerFactory {
	public static AnalyzerFactory object = new AnalyzerFactory();
	//private AnalyzerFactory(){}
	/**
	 * Static method to return an instance of the factory class.
	 * Usually factory classes are defined as singletons, i.e. 
	 * only one instance of the class exists at any instance.
	 * This is usually achieved by defining a private static instance
	 * that is initialized by the "private" constructor.
	 * On the method being called, you return the static instance.
	 * This allows you to reuse expensive objects that you may create
	 * during instantiation
	 * @return An instance of the factory
	 */
	public static AnalyzerFactory getInstance() {
		//TODO: YOU NEED TO IMPLEMENT THIS METHOD
		if(object==null)
		{
			object = new AnalyzerFactory();
		}
			
			return object;
		
	}
	
	/**
	 * Returns a fully constructed and chained {@link Analyzer} instance
	 * for a given {@link FieldNames} field
	 * Note again that the singleton factory instance allows you to reuse
	 * {@link TokenFilter} instances if need be
	 * @param name: The {@link FieldNames} for which the {@link Analyzer}
	 * is requested
	 * @param TokenStream : Stream for which the Analyzer is requested
	 * @return The built {@link Analyzer} instance for an indexable {@link FieldNames}
	 * null otherwise
	 */
	public Analyzer getAnalyzerForField(FieldNames name, TokenStream stream) {
		//TODO : YOU NEED TO IMPLEMENT THIS METHOD
		
		switch(name)
		{
		case AUTHOR:
			Analyzer aw = new AuthorAnalyzer(stream);
			return aw;
			
			//Analyzer az = new StopWords(stream);
		/**	TokenFilterFactory factorya = TokenFilterFactory.getInstance();
			TokenFilter filtera = factorya.getFilterByType(TokenFilterType.STOPWORD, stream);
			while (stream.hasNext())
			{
				try {
					filtera.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();**/
			
		case CONTENT :
			Analyzer c = new ContentAnalyzer(stream);
			return c;
			
		/**	TokenFilterFactory factory = TokenFilterFactory.getInstance();
			TokenFilter filter2 = factory.getFilterByType(TokenFilterType.CAPITALIZATION, stream);
			while (stream.hasNext())
			{
				try {
					filter2.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			TokenFilter filter1 = factory.getFilterByType(TokenFilterType.SYMBOL, stream);
			while (stream.hasNext())
			{
				try {
					filter1.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			
			TokenFilter filter3 = factory.getFilterByType(TokenFilterType.STOPWORD, stream);
			while (stream.hasNext())
			{
				try {
					filter3.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			
			
			TokenFilter filter4 = factory.getFilterByType(TokenFilterType.ACCENT, stream);
			while (stream.hasNext())
			{
				try {
					filter4.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			TokenFilter filter5 = factory.getFilterByType(TokenFilterType.STEMMER, stream);
			while (stream.hasNext())
			{
				try {
					filter5.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			TokenFilter filter6 = factory.getFilterByType(TokenFilterType.SPECIALCHARS, stream);
			while (stream.hasNext())
			{
				try {
					filter6.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			TokenFilter filter7 = factory.getFilterByType(TokenFilterType.NUMERIC, stream);
			while (stream.hasNext())
			{
				try {
					filter7.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			**/
			
		case CATEGORY:
			return null;
		case PLACE:
			return null;
		case TITLE:
			
			Analyzer t = new TitleAnalyzer(stream);
			return t;
		/*	TokenFilterFactory factoryb = TokenFilterFactory.getInstance();
			TokenFilter filter = factoryb.getFilterByType(TokenFilterType.CAPITALIZATION, stream);
			while (stream.hasNext())
			{
				try {
					filter.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			TokenFilter filterb2 = factoryb.getFilterByType(TokenFilterType.SYMBOL, stream);
			while (stream.hasNext())
			{
				try {
					filterb2.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			TokenFilter filterb6 = factoryb.getFilterByType(TokenFilterType.SPECIALCHARS, stream);
			while (stream.hasNext())
			{
				try {
					filterb6.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			TokenFilter filterb3 = factoryb.getFilterByType(TokenFilterType.STOPWORD, stream);
			while (stream.hasNext())
			{
				try {
					filterb3.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			
			
			TokenFilter filterb4 = factoryb.getFilterByType(TokenFilterType.ACCENT, stream);
			while (stream.hasNext())
			{
				try {
					filterb4.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			TokenFilter filterb5 = factoryb.getFilterByType(TokenFilterType.STEMMER, stream);
			while (stream.hasNext())
			{
				try {
					filterb5.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();
			
			stream.reset();
			TokenFilter filterb7 = factoryb.getFilterByType(TokenFilterType.NUMERIC, stream);
			while (stream.hasNext())
			{
				try {
					filterb7.increment();
				} catch (TokenizerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stream.reset();*/
		}
		return null;
	}
}
