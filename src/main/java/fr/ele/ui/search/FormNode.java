package fr.ele.ui.search;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

public class FormNode extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = -4069150316210041397L;

    public void add(String path, Object child) {
        String[] split = StringUtils.split(path, ".", 2);
        if (split.length < 2) {
            put(path, child);
        } else {
            FormNode node = (FormNode) get(split[0]);
            if (node == null) {
                node = new FormNode();
                put(split[0], node);
            }
            node.add(split[1], child);
        }
    }
}
