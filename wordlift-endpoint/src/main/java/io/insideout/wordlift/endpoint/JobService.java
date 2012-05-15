package io.insideout.wordlift.endpoint;

import io.insideout.wordlift.domain.JobRequest;

public interface JobService {

    public String runJob(JobRequest jobRequest);

}