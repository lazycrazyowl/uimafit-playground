package fr.univnantes.atal.uima.playground.resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.fit.component.ExternalResourceAware;
import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

public final class WordCounterImpl implements SharedResourceObject, ExternalResourceAware {
	@ConfigurationParameter(name=ExternalResourceFactory.PARAM_RESOURCE_NAME)
	private String resourceName;

	Map<String, Integer> counts = new HashMap<>();

	public void incrementCount(String word) {
		if (counts.containsKey(word)) {
			counts.put(word, counts.get(word) + 1);
		} else {
			counts.put(word, 1);
		}
	}

	public Integer getCount(String word) {
		return counts.get(word);
	}
	
	public Map<String, Integer> getCounts() {
		return Collections.unmodifiableMap(counts);
	}
	
	@Override
	public void load(DataResource data) throws ResourceInitializationException {
		ConfigurationParameterInitializer.initialize(this, data);
		// Nothing to do, simple stuff.
	}

	@Override
	public String getResourceName() {
		return resourceName;
	}

	@Override
	public void afterResourcesInitialized()
			throws ResourceInitializationException {
	}
}
