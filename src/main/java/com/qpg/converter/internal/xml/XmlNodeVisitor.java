package com.qpg.converter.internal.xml;

public interface XmlNodeVisitor<T> {
    T visit(XmlElement element);
    T visit(XmlTextNode textNode);
}
