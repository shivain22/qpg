package com.qpg.converter.internal.xml;

import java.util.Iterator;
import java.util.List;

import static com.qpg.converter.internal.util.Lists.eagerFlatMap;

public class XmlElementList implements Iterable<XmlElement> {
    private final List<XmlElement> elements;

    public XmlElementList(List<XmlElement> elements) {
        this.elements = elements;
    }

    @Override
    public Iterator<XmlElement> iterator() {
        return elements.iterator();
    }

    public XmlElementList findChildren(String name) {
        return new XmlElementList(eagerFlatMap(
            elements,
            element -> element.findChildren(name)));
    }
}
