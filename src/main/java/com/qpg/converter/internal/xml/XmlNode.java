package com.qpg.converter.internal.xml;

public interface XmlNode {
    String innerText();
    <T> T accept(XmlNodeVisitor<T> visitor);
}
