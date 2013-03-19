package de.deepamehta.plugins.webactivator.provider;

import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.template.ViewProcessor;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import de.deepamehta.core.util.JavaUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Logger;



@Provider
public class ThymeleafViewProcessor implements ViewProcessor<String> {

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private Logger logger = Logger.getLogger(getClass().getName());

    // -------------------------------------------------------------------------------------------------- Public Methods

    @Override
    public String resolve(String templateName) {
        return "/views/" + JavaUtils.getFilename(templateName) + ".html";
    }

    @Override
    public void writeTo(String templateName, Viewable viewable, OutputStream out) throws IOException {
        logger.info("Processing template \"" + templateName + "\"");
        //
        Context context = (Context) viewable.getModel();
        TemplateEngine templateEngine = (TemplateEngine) context.getVariables().get("_templateEngine");
        //
        Writer writer = new BufferedWriter(new OutputStreamWriter(out , "UTF-8"));
        templateEngine.process(templateName, context, writer);
        writer.flush();
    }
}
