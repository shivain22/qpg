package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.util.Maps;
import com.qpg.converter.internal.util.Strings;
import com.qpg.converter.internal.xml.XmlElement;
import com.qpg.converter.internal.xml.XmlElementList;

import java.util.Map;

public class ContentTypesXml {
    public static ContentTypes readContentTypesXmlElement(XmlElement element) {
        return new ContentTypes(
            readDefaults(element.findChildren("content-types:Default")),
            readOverrides(element.findChildren("content-types:Override")));
    }

    private static Map<String, String> readDefaults(XmlElementList children) {
        return Maps.toMap(children, ContentTypesXml::readDefault);
    }

    private static Map.Entry<String, String> readDefault(XmlElement element) {
        return Maps.entry(
            element.getAttribute("Extension"),
            element.getAttribute("ContentType"));
    }

    private static Map<String, String> readOverrides(XmlElementList children) {
        return Maps.toMap(children, ContentTypesXml::readOverride);
    }

    private static Map.Entry<String, String> readOverride(XmlElement element) {
        return Maps.entry(
            Strings.trimLeft(element.getAttribute("PartName"), '/'),
            element.getAttribute("ContentType"));
    }
}
