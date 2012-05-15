package io.insideout.wordlift.endpoint;

import io.insideout.wordlift.domain.JobRequest;
import io.insideout.wordlift.endpoint.services.EntityService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.enhancer.servicesapi.ChainManager;
import org.apache.stanbol.enhancer.servicesapi.EnhancementJobManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name = "io.insideout.keywords.osgi.JobService", immediate = true)
@Service
public class JobServiceImpl implements JobService {

    @Reference
    private EnhancementJobManager enhancementJobManager;

    @Reference
    private ChainManager chainManager;

    @Reference
    private EntityService entityService;

    private Map<String, JobRequest> jobs = new HashMap<String, JobRequest>();
    private EnhancerHelper enhancerHelper;
    private ExecutorService executorService;

    private final int MAX_THREADS = 10;

    @Activate
    public void activate() {
	logger.info(String.format("Activating with [MAX_THREADS:%d][chainManager:%s][enhancementJobManager:%s][entityService:%s]...", MAX_THREADS, chainManager, enhancementJobManager, entityService));

	enhancerHelper = new EnhancerHelper(chainManager, enhancementJobManager, entityService);
	executorService = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public String runJob(final JobRequest jobRequest) {
	logger.debug("Received a Job Request [{}].", jobRequest);

	final String uuid = UUID.randomUUID().toString();

	jobs.put(uuid, jobRequest);

	logger.debug(String.format("A new job has been created with ID [%s]. There are currently %d job(s).", uuid, jobs.size()));

	executorService.execute(new EnhancerJobRunnable(enhancerHelper, uuid, jobRequest));

	return uuid;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

}
