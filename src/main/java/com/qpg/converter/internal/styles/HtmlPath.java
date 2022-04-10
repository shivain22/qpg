package com.qpg.converter.internal.styles;

import com.qpg.converter.internal.html.HtmlNode;
import com.qpg.converter.internal.html.HtmlTag;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static com.qpg.converter.internal.util.Lists.list;
import static com.qpg.converter.internal.util.Maps.map;

public interface HtmlPath {
    HtmlPath EMPTY = new HtmlPathElements(list());
    HtmlPath IGNORE = Ignore.INSTANCE;

    static HtmlPath elements(HtmlPathElement... elements) {
        return new HtmlPathElements(asList(elements));
    }

    static HtmlPath element(String tagName) {
        return element(tagName, map());
    }

    static HtmlPath element(String tagName, Map<String, String> attributes) {
        HtmlTag tag = new HtmlTag(list(tagName), attributes, false, "");
        return new HtmlPathElements(list(new HtmlPathElement(tag)));
    }

    static HtmlPath collapsibleElement(String tagName) {
        return collapsibleElement(tagName, map());
    }

    static HtmlPath collapsibleElement(List<String> tagNames) {
        return collapsibleElement(tagNames, map());
    }

    static HtmlPath collapsibleElement(String tagName, Map<String, String> attributes) {
        return collapsibleElement(list(tagName), attributes);
    }

    static HtmlPath collapsibleElement(List<String> tagNames, Map<String, String> attributes) {
        HtmlTag tag = new HtmlTag(tagNames, attributes, true, "");
        return new HtmlPathElements(list(new HtmlPathElement(tag)));
    }

    Supplier<List<HtmlNode>> wrap(Supplier<List<HtmlNode>> generateNodes);
}
