package com.qpg.converter.internal.styles;

import com.qpg.converter.internal.html.HtmlElement;
import com.qpg.converter.internal.html.HtmlNode;
import com.qpg.converter.internal.html.HtmlTag;

import java.util.List;
import java.util.function.Supplier;

import static com.qpg.converter.internal.util.Lists.list;

public class HtmlPathElement {
    private final HtmlTag tag;

    public HtmlPathElement(HtmlTag tag) {
        this.tag = tag;
    }

    public Supplier<List<HtmlNode>> wrap(Supplier<List<HtmlNode>> generateNodes) {
        return () -> wrapNodes(generateNodes.get());
    }

    private List<HtmlNode> wrapNodes(List<HtmlNode> nodes) {
        return list(new HtmlElement(tag, nodes));
    }
}
