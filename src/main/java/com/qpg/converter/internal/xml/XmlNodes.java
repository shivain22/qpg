package com.qpg.converter.internal.xml;

import java.util.List;
import java.util.Map;

import static com.qpg.converter.internal.util.Lists.list;
import static com.qpg.converter.internal.util.Maps.map;

public class XmlNodes {
    public static XmlElement element(String name) {
        return element(name, list());
    }

    public static XmlElement element(String name, Map<String, String> attributes) {
        return element(name, attributes, list());
    }

    public static XmlElement element(String name, List<XmlNode> children) {
        return element(name, map(), children);
    }

    public static XmlElement element(String name, Map<String, String> attributes, List<XmlNode> children) {
        return new XmlElement(name, attributes, children);
    }

    public static XmlTextNode text(String value) {
        return new XmlTextNode(value);
    }
}
