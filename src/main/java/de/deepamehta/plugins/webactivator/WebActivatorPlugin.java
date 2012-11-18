package de.deepamehta.plugins.webactivator;

import de.deepamehta.core.osgi.PluginActivator;

import com.sun.jersey.api.view.Viewable;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.context.Context;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import org.osgi.framework.Bundle;

import java.io.InputStream;



public class WebActivatorPlugin extends PluginActivator {

    // ---------------------------------------------------------------------------------------------- Instance Variables

    protected Context context;

    private TemplateEngine templateEngine;

    // ----------------------------------------------------------------------------------------------- Protected Methods

    protected void setupRenderContext() {
        //
        TemplateResolver templateResolver = new TemplateResolver();
        templateResolver.setResourceResolver(new BundleResourceResolver(bundle));
        //
        this.templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        //
        this.context = new Context();
        context.setVariable("_templateEngine", templateEngine);
    }

    protected Viewable view(String templateName) {
        return new Viewable(templateName, context);
    }

    // --------------------------------------------------------------------------------------------------- Inner Classes

    private class BundleResourceResolver implements IResourceResolver {

        private Bundle bundle;

        private BundleResourceResolver(Bundle bundle) {
            this.bundle = bundle;
        }

        @Override
        public String getName() {
            return "BundleResourceResolver";
        }

        @Override
        public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters,
                                               String resourceName) {
            try {
                return bundle.getResource(resourceName).openStream();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
