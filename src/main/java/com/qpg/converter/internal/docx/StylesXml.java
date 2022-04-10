package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.documents.NumberingStyle;
import com.qpg.converter.internal.documents.Style;
import com.qpg.converter.internal.util.Iterables;
import com.qpg.converter.internal.util.Maps;
import com.qpg.converter.internal.xml.XmlElement;
import com.qpg.converter.internal.xml.XmlElementList;

import java.util.Map;
import java.util.Optional;

import static com.qpg.converter.internal.util.Iterables.lazyFilter;

public class StylesXml {
    public static Styles readStylesXmlElement(XmlElement element) {
        XmlElementList styleElements = element.findChildren("w:style");
        return new Styles(
            readStyles(styleElements, "paragraph"),
            readStyles(styleElements, "character"),
            readStyles(styleElements, "table"),
            readNumberingStyles(styleElements)
        );
    }

    private static Map<String, Style> readStyles(XmlElementList styleElements, String styleType) {
        return Maps.toMap(
            styleElementsOfType(styleElements, styleType),
            StylesXml::readStyle
        );
    }

    private static Map.Entry<String, Style> readStyle(XmlElement element) {
        String styleId = readStyleId(element);
        Optional<String> styleName = element.findChildOrEmpty("w:name").getAttributeOrNone("w:val");
        return Maps.entry(styleId, new Style(styleId, styleName));
    }

    private static Map<String, NumberingStyle> readNumberingStyles(XmlElementList styleElements) {
        return Maps.toMap(
            styleElementsOfType(styleElements, "numbering"),
            StylesXml::readNumberingStyle
        );
    }

    private static Map.Entry<String, NumberingStyle> readNumberingStyle(XmlElement element) {
        String styleId = readStyleId(element);
        Optional<String> numId = element
            .findChildOrEmpty("w:pPr")
            .findChildOrEmpty("w:numPr")
            .findChildOrEmpty("w:numId")
            .getAttributeOrNone("w:val");
        return Maps.entry(styleId, new NumberingStyle(numId));
    }

    private static String readStyleId(XmlElement element) {
        return element.getAttribute("w:styleId");
    }

    private static Iterable<XmlElement> styleElementsOfType(XmlElementList styleElements, String styleType) {
        return Iterables.lazyFilter(styleElements, styleElement -> isStyleType(styleElement, styleType));
    }

    private static boolean isStyleType(XmlElement styleElement, String styleType) {
        return styleElement.getAttribute("w:type").equals(styleType);
    }
}
