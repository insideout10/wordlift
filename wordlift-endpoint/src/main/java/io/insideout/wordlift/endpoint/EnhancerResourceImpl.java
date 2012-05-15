package io.insideout.wordlift.endpoint;

import io.insideout.wordlift.domain.JobResponse;
import io.insideout.wordlift.domain.TextJobRequest;
import io.insideout.wordlift.domain.UrlJobRequest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.stanbol.commons.web.base.ContextHelper;
import org.apache.stanbol.commons.web.base.resource.BaseStanbolResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/wordlift/enhance")
// @Component(name = "simple-service", immediate = true)
public class EnhancerResourceImpl extends BaseStanbolResource implements
		EnhancerResource {

	private JobService jobService;

	public EnhancerResourceImpl(@Context ServletContext context) {

		jobService = ContextHelper.getServiceFromContext(JobService.class,
				context);
		logger.debug("Got the Job Service instance [{}].", jobService);
	}

	// @POST
	// @Consumes(MediaType.APPLICATION_JSON_VALUE)
	// @Produces(MediaType.APPLICATION_JSON_VALUE)
	// public Response createSynchronousJob(JobRequest jobRequest) {
	//
	// logger.debug("Received a Job Request [{}].", jobRequest);
	//
	// String url = jobRequest.getUrl();
	//
	// Collection<EntityImpl> entities = extractEntities(url);
	//
	// return Response.ok(entities, MediaType.APPLICATION_JSON_VALUE).build();
	// }

	@POST
	@Path("/url")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response analyzeUrl(UrlJobRequest jobRequest) {

		logger.debug("Received a Job Request [{}].", jobRequest);

		String id = jobService.runJob(jobRequest);

		JobResponse jobResponse = new JobResponse(200,
				"Job created successfully.", id);

		return Response.ok(jobResponse, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/text")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response analyzeText(TextJobRequest jobRequest) {

		logger.debug("Received a Job Request [{}].", jobRequest);

		String id = jobService.runJob(jobRequest);

		JobResponse jobResponse = new JobResponse(200,
				"Job created successfully.", id);

		return Response.ok(jobResponse, MediaType.APPLICATION_JSON).build();
	}

	// @OPTIONS
	// public Response handleCorsPreflight(@Context HttpHeaders headers) {
	// ResponseBuilder res = Response.ok();
	// enableCORS(servletContext, res, headers);
	// return res.build();
	// }

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
