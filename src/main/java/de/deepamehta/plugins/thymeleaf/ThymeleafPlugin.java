package de.deepamehta.plugins.thymeleaf;

import de.deepamehta.plugins.thymeleaf.service.ThymeleafService;
import de.deepamehta.core.osgi.PluginActivator;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import org.osgi.framework.Bundle;

import java.io.InputStream;
import java.util.logging.Logger;



public class ThymeleafPlugin extends PluginActivator implements ThymeleafService {

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private Logger logger = Logger.getLogger(getClass().getName());

    // -------------------------------------------------------------------------------------------------- Public Methods



    // ***************************************
    // *** ThymeleafService Implementation ***
    // ***************************************



    @Override
    public TemplateEngine createTemplateEngine(Bundle bundle) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(new BundleTemplateResolver(bundle));
        return templateEngine;
    }



    // ----------------------------------------------------------------------------------------------------- Inner Class



    private class BundleTemplateResolver extends TemplateResolver {

        BundleTemplateResolver(Bundle bundle) {
            setPrefix("/templates/");
            setSuffix(".html");
            setResourceResolver(new BundleResourceResolver(bundle));
        }
    }

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
