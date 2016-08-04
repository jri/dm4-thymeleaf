package de.deepamehta.thymeleaf;

import de.deepamehta.core.osgi.PluginActivator;
import de.deepamehta.core.service.event.ServiceRequestFilterListener;

import com.sun.jersey.api.view.Viewable;
// ### TODO: hide Jersey internals. Move to JAX-RS 2.0.
import com.sun.jersey.spi.container.ContainerRequest;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import org.osgi.framework.Bundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;



// Note: although no REST service is provided the plugin is annotated as a root resource class.
// Otherwise we can't receive JAX-RS context injections (HttpServletRequest).
@Path("/thymeleaf")
public class ThymeleafPlugin extends PluginActivator implements ServiceRequestFilterListener {

    // ------------------------------------------------------------------------------------------------------- Constants

    private static String ATTR_CONTEXT = "de.deepamehta.thymeleaf.Context";
    private static String TEMPLATES_FOLDER = "/views/";
    private static String TEMPLATES_ENDING = ".html";

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private TemplateEngine templateEngine;
    Set<Bundle> additionalTemplateResourceBundles = new HashSet<Bundle>();


    @Context private HttpServletRequest request;
    @Context private HttpServletResponse response;
    @Context private ServletContext servletContext;

    private Logger logger = Logger.getLogger(getClass().getName());

    // -------------------------------------------------------------------------------------------------- Public Methods



    // ********************************
    // *** Listener Implementations ***
    // ********************************



    @Override
    public void serviceRequestFilter(ContainerRequest containerRequest) {
        // Note: we don't operate on the passed ContainerRequest but on the injected HttpServletRequest. At this spot we
        // could use containerRequest.getProperties().put(..) instead of request.setAttribute(..) but at the other spots
        // (viewData() and view()) we could not inject a ContainerRequest but only a javax.ws.rs.core.Request and
        // Request does not provide a getProperties() method. And we neither can cast a Request into a ContainerRequest
        // as the injected Request is actually a proxy object (in order to deal with multi-threading).
        request.setAttribute(ATTR_CONTEXT, new WebContext(request, response, servletContext));
    }



    // ===

    public TemplateEngine getTemplateEngine() {
        if (templateEngine == null) {
            throw new RuntimeException("The template engine for " + this + " is not initialized. " +
                "Don't forget calling initTemplateEngine() from your plugin's init() hook.");
        }
        //
        return templateEngine;
    }

    // ----------------------------------------------------------------------------------------------- Protected Methods

    protected void addTemplateResourceBundle(Bundle templateBundleResource) {
        additionalTemplateResourceBundles.add(templateBundleResource);
    }

    protected void removeTemplateResourceBundle(Bundle templateBundleResource) {
        additionalTemplateResourceBundles.remove(templateBundleResource);
    }

    protected void initTemplateEngine() {
        templateEngine = new TemplateEngine();
        // 1) if configured set additional BundleResourceResolver we register them 1st, giving their resources priority
        if (additionalTemplateResourceBundles.size() > 0) {
            logger.info("Initializing Thymeleaf TemplateEngine with additional template resolver bundles...");
            for (Bundle otherTemplateResourceBundle : additionalTemplateResourceBundles) {
                TemplateResolver otherTemplateResolver = createBundleResourcesResolver(otherTemplateResourceBundle);
                templateEngine.addTemplateResolver(otherTemplateResolver);
                logger.info("Added template resolver bundle \"" + otherTemplateResourceBundle.getSymbolicName() + "\"");
            }
        } else {
            logger.info("Initializing Thymeleaf TemplateEngine without any additional template resolver bundles...");
        }
        // 2) initialize the "this" plugin (the one extending this class) as a BundleResourceResolver too
        templateEngine.addTemplateResolver(createBundleResourcesResolver(bundle));
    }

    protected void viewData(String name, Object value) {
        context().setVariable(name, value);
    }

    protected Viewable view(String templateName) {
        return new Viewable(templateName, context());
    }

    // ------------------------------------------------------------------------------------------------- Private Methods

    private TemplateResolver createBundleResourcesResolver(Bundle bundle) {
        TemplateResolver tr = new TemplateResolver();
        tr.setResourceResolver(new BundleResourcesResolver(bundle));
        tr.setPrefix(TEMPLATES_FOLDER);
        tr.setSuffix(TEMPLATES_ENDING);
        return tr;
    }

    private AbstractContext context() {
        return (AbstractContext) request.getAttribute(ATTR_CONTEXT);
    }

    // -------------------------------------------------------------------------------------------------- Nested Classes

    private class BundleResourcesResolver implements IResourceResolver {

        private Bundle bundle;

        private BundleResourcesResolver(Bundle bundle) {
            this.bundle = bundle;
        }

        @Override
        public String getName() {
            return bundle.getSymbolicName() + ".BundleResourcesResolver";
        }

        @Override
        public InputStream getResourceAsStream(TemplateProcessingParameters params, String resourceName) {
            try {
                return bundle.getResource(resourceName).openStream();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
