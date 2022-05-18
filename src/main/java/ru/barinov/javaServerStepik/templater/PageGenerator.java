package ru.barinov.javaServerStepik.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private final static String HTML_DIR = "src/main/resources/templates/";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance(){
        if(pageGenerator == null){
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    private PageGenerator() {
        cfg = new Configuration();
    }

    public String getPage(String filename, Map<String, Object> data){
        Writer stream = new StringWriter();

        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename); // Находим наш шаблон
            template.process(data, stream);//Передаем данные в шаблон
        }catch (IOException e){
            e.printStackTrace();
        }catch (TemplateException e){
            e.printStackTrace();
        }
        return stream.toString();
    }
}
