package de.deepamehta.plugins.thymeleaf.service;

import de.deepamehta.core.service.PluginService;

import org.thymeleaf.TemplateEngine;

import org.osgi.framework.Bundle;



public interface ThymeleafService extends PluginService {

    TemplateEngine createTemplateEngine(Bundle bundle);
}
