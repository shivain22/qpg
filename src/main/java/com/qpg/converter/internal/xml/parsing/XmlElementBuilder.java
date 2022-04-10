package com.qpg.converter.internal.xml.parsing;

import com.qpg.converter.internal.xml.XmlElement;
import com.qpg.converter.internal.xml.XmlNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class XmlElementBuilder {
    private final String name;
    private final Map<String, String> attributes;
    private final List<XmlNode> children;

    XmlElementBuilder(String name, Map<String, String> attributes) {
        this.name = name;
        this.attributes = attributes;
        this.children = new ArrayList<>();
    }

    XmlElement build() {
        return new XmlElement(name, attributes, children);
    }

    void addChild(XmlNode node) {
        children.add(node);
    }
}
