package fr.univnantes.atal.uima.playground.annotators;

import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;

import fr.univnantes.atal.uima.playground.resources.WordCounterImpl;
import fr.univnantes.atal.uima.playground.types.Token;

public class WordCountAnnotator extends JCasAnnotator_ImplBase {
	
	public static final String WORD_COUNTER_KEY = "wordCounter";
	
	@ExternalResource(key = WORD_COUNTER_KEY)
	private WordCounterImpl counts;
	
	@Override
	public void process(JCas aJCas)
			throws AnalysisEngineProcessException {
		for (Token token : select(aJCas, Token.class)) {
			counts.incrementCount(token.getCoveredText());
		}
	}
	
	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		System.out.println("Finished processing the collection. Results are:");
		for (String token : counts.getCounts().keySet()) {
			System.out.println(token + ":" + counts.getCount(token));
		}
	}
}
