package util.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Шаблонизатор HTML-страниц
 */
public class PageGenerator {

    /** Имя папки с шаблонами страниц */
    private static final String TEMPLATES_DIR = "/templates/";

    /** Singleton-Шаблонизатор */
    private static PageGenerator pageGenerator;

    /** Конфигурация */
    private final Configuration config;

    private PageGenerator() {
        config = new Configuration(Configuration.VERSION_2_3_30);
        config.setClassForTemplateLoading(this.getClass(), TEMPLATES_DIR);
    }

    /**
     * Метод создания и получения единственного объекта {@link PageGenerator}
     *
     * @return    -    Singleton-объект {@link PageGenerator}
     */
    public static PageGenerator getInstance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String renderPage(String name, Map<String, Object> data) {
        Writer stringWriter = new StringWriter();
        try {
            Template template = config.getTemplate(name);
            template.process(data, stringWriter);
        }
        catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stringWriter.toString();
    }

}
