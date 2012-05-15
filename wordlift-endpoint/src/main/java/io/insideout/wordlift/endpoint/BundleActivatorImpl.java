package io.insideout.wordlift.endpoint;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleActivatorImpl implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
	logger.info("Start.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
	logger.info("Stop.");
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
