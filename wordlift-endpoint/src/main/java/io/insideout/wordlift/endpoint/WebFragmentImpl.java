package io.insideout.wordlift.endpoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.commons.web.base.LinkResource;
import org.apache.stanbol.commons.web.base.NavigationLink;
import org.apache.stanbol.commons.web.base.ScriptResource;
import org.apache.stanbol.commons.web.base.WebFragment;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;

@Component(immediate = true, metatype = true)
@Service
public class WebFragmentImpl implements WebFragment {

    private final String NAME = "wordlift";

    private BundleContext bundleContext;

    private static final String STATIC_RESOURCE_PATH = "/io/insideout/wordlift/web/static";
    private static final String TEMPLATE_PATH = "/io/insideout/wordlift/web/templates";

    @Activate
    protected void activate(ComponentContext ctx) {
	this.bundleContext = ctx.getBundleContext();
    }

    @Override
    public String getName() {
	return NAME;
    }

    @Override
    public String getStaticResourceClassPath() {
	return STATIC_RESOURCE_PATH;
    }

    @Override
    public Set<Class<?>> getJaxrsResourceClasses() {
	Set<Class<?>> classes = new HashSet<Class<?>>();
	classes.add(EnhancerResourceImpl.class);
	// classes.add(BridgeDefinitionsResource.class);
	// classes.add(NotifyResource.class);
	// classes.add(ObjectTypesResource.class);
	// classes.add(RootResource.class);
	// classes.add(RDFMapperResource.class);
	// classes.add(ContenthubFeedResource.class);
	// classes.add(SessionResource.class);
	return classes;
    }

    @Override
    public Set<Object> getJaxrsResourceSingletons() {
	Set<Object> singletons = new HashSet<Object>();
	try {
	    singletons.add(new JacksonJaxbJsonProvider());
	} catch (Exception e) {
	    logger.warn("Error in creating JAXB provider, ", e);
	}
	return singletons;
    }

    @Override
    public TemplateLoader getTemplateLoader() {
	return new ClassTemplateLoader(getClass(), TEMPLATE_PATH);
    }

    @Override
    public List<LinkResource> getLinkResources() {
	return new ArrayList<LinkResource>();
    }

    @Override
    public List<ScriptResource> getScriptResources() {
	return new ArrayList<ScriptResource>();
    }

    @Override
    public List<NavigationLink> getNavigationLinks() {
	List<NavigationLink> links = new ArrayList<NavigationLink>();
	links.add(new NavigationLink("wordlift", "/wordlift", "/imports/wordliftDescription.ftl", 80));
	return links;
    }

    @Override
    public BundleContext getBundleContext() {
	return bundleContext;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
