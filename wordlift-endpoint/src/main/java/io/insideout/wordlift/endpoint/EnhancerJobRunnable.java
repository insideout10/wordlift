package io.insideout.wordlift.endpoint;

import io.insideout.wordlift.domain.EntityImpl;
import io.insideout.wordlift.domain.JobRequest;
import io.insideout.wordlift.domain.JobResult;
import io.insideout.wordlift.domain.TextJobRequest;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class EnhancerJobRunnable implements Runnable {

	private String id;
	private EnhancerHelper enhancerHelper;
	private JobRequest jobRequest;

	public EnhancerJobRunnable(EnhancerHelper enhancerHelper, String id,
			JobRequest jobRequest) {
		this.enhancerHelper = enhancerHelper;
		this.id = id;
		this.jobRequest = jobRequest;
	}

	@Override
	public void run() {
		logger.info("Job [{}] starting...", id);

		Collection<EntityImpl> entities = null;

		// if (true == jobRequest instanceof UrlJobRequest) {
		// entities = analyzeUrl(((UrlJobRequest) jobRequest).getUrl());
		// }

		if (true == jobRequest instanceof TextJobRequest) {
			entities = analyzeText(((TextJobRequest) jobRequest).getText());
		}

		String onCompleteUrl = jobRequest.getOnCompleteUrl();

		logger.info("Job [{}] completed, posting results to [{}]...", id,
				onCompleteUrl);

		URI uri = URI.create(onCompleteUrl);
		sendResults(uri, entities);
	}

	// private Collection<EntityImpl> analyzeUrl(final String urlString) {
	// URL url;
	// try {
	// url = new URL(urlString);
	// } catch (MalformedURLException e) {
	// logger.error("The url [{}] is not a valid url.", urlString, e);
	// throw new
	// RuntimeException(String.format("The url [%s] is not a valid url.",
	// urlString));
	// }
	// return enhancerHelper.extractEntities(url);
	// }

	private Collection<EntityImpl> analyzeText(final String text) {
		return enhancerHelper.extractEntities(text);
	}

	private void sendResults(final URI uri,
			final Collection<EntityImpl> entities) {
		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);

		Client client = Client.create(config);

		WebResource service = client.resource(uri);

		JobResult result = new JobResult(id, entities);

		service.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).post(result);
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

}
