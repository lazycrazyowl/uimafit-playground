package fr.univnantes.atal.uima.playground;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.IOException;

import opennlp.uima.namefind.NameFinder;
import opennlp.uima.namefind.TokenNameFinderModelResourceImpl;
import opennlp.uima.sentdetect.SentenceDetector;
import opennlp.uima.sentdetect.SentenceModelResourceImpl;
import opennlp.uima.tokenize.Tokenizer;
import opennlp.uima.tokenize.TokenizerModelResourceImpl;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import fr.univnantes.atal.uima.playground.annotators.WordCountAnnotator;
import fr.univnantes.atal.uima.playground.resources.WordCounterImpl;

public class Pipeline {
	public static void main(String[] args) {
		try {
			SimplePipeline.runPipeline(
	                createReaderDescription(TextReader.class,
	                        TextReader.PARAM_SOURCE_LOCATION, "input/*",
	                        TextReader.PARAM_LANGUAGE, "en"),
					createEngineDescription(SentenceDetector.class,
							"opennlp.uima.ModelName", createExternalResourceDescription(
									SentenceModelResourceImpl.class,
									"file:models/en-sent.bin"),
							"opennlp.uima.SentenceType", "fr.univnantes.atal.uima.playground.types.Sentence"),
					createEngineDescription(Tokenizer.class,
							"opennlp.uima.ModelName", createExternalResourceDescription(
									TokenizerModelResourceImpl.class,
									"file:models/en-token.bin"),
							"opennlp.uima.SentenceType", "fr.univnantes.atal.uima.playground.types.Sentence",
							"opennlp.uima.TokenType", "fr.univnantes.atal.uima.playground.types.Token"),
					createEngineDescription(NameFinder.class,
							"opennlp.uima.ModelName", createExternalResourceDescription(
									TokenNameFinderModelResourceImpl.class,
									"file:models/en-ner-person.bin"),
							"opennlp.uima.NameType", "fr.univnantes.atal.uima.playground.types.Person",
							"opennlp.uima.SentenceType", "fr.univnantes.atal.uima.playground.types.Sentence",
							"opennlp.uima.TokenType", "fr.univnantes.atal.uima.playground.types.Token"),
					createEngineDescription(WordCountAnnotator.class,
							WordCountAnnotator.WORD_COUNTER_KEY,
							createExternalResourceDescription(
									WordCountAnnotator.WORD_COUNTER_KEY,
									WordCounterImpl.class, "")),
					createEngineDescription(
							XmiWriter.class,
							XmiWriter.PARAM_TARGET_LOCATION, "output"));
		} catch(IOException | UIMAException e) {
			System.err.println("Encountered an exception: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}
}
