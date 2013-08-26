package fr.ele.ui.rest;

import java.io.OutputStream;
import java.util.Map;

public interface TemplateEngine {
    void execute(OutputStream outputStream, String view,
            Map<String, Object> datas);
}
