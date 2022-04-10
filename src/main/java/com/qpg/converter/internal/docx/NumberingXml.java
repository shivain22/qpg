package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.util.Maps;
import com.qpg.converter.internal.xml.XmlElement;
import com.qpg.converter.internal.xml.XmlElementList;

import java.util.Map;
import java.util.Optional;

public class NumberingXml {
    public static Numbering readNumberingXmlElement(XmlElement element, Styles styles) {
        Map<String, Numbering.AbstractNum> abstractNums = readAbstractNums(element.findChildren("w:abstractNum"));
        Map<String, Numbering.Num> nums = readNums(element.findChildren("w:num"));
        return new Numbering(abstractNums, nums, styles);
    }

    private static Map<String, Numbering.AbstractNum> readAbstractNums(XmlElementList children) {
        return Maps.toMap(children, NumberingXml::readAbstractNum);
    }

    private static Map.Entry<String, Numbering.AbstractNum> readAbstractNum(XmlElement element) {
        // TODO: in python-mammoth, we allow None here. Check whether that's actually possible or not
        String abstractNumId = element.getAttribute("w:abstractNumId");
        Numbering.AbstractNum abstractNum = new Numbering.AbstractNum(
            readAbstractNumLevels(element),
            element.findChildOrEmpty("w:numStyleLink").getAttributeOrNone("w:val")
        );
        return Maps.entry(abstractNumId, abstractNum);
    }

    private static Map<String, Numbering.AbstractNumLevel> readAbstractNumLevels(XmlElement element) {
        return Maps.toMap(element.findChildren("w:lvl"), NumberingXml::readAbstractNumLevel);
    }

    private static Map.Entry<String, Numbering.AbstractNumLevel> readAbstractNumLevel(XmlElement element) {
        String levelIndex = element.getAttribute("w:ilvl");
        Optional<String> numFmt = element.findChildOrEmpty("w:numFmt").getAttributeOrNone("w:val");
        boolean isOrdered = !numFmt.equals(Optional.of("bullet"));
        Optional<String> paragraphStyleId = element.findChildOrEmpty("w:pStyle").getAttributeOrNone("w:val");
        return Maps.entry(levelIndex, new Numbering.AbstractNumLevel(levelIndex, isOrdered, paragraphStyleId));
    }

    private static Map<String, Numbering.Num> readNums(XmlElementList numElements) {
        return Maps.toMap(numElements, NumberingXml::readNum);
    }

    private static Map.Entry<String, Numbering.Num> readNum(XmlElement numElement) {
        // TODO: in python-mammoth, we allow None here. Check whether that's actually possible or not
        String numId = numElement.getAttribute("w:numId");
        Optional<String> abstractNumId = numElement.findChildOrEmpty("w:abstractNumId").getAttributeOrNone("w:val");
        return Maps.entry(numId, new Numbering.Num(abstractNumId));
    }
}
