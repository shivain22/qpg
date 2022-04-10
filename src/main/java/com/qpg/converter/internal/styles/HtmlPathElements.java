package com.qpg.converter.internal.styles;

import com.qpg.converter.internal.html.HtmlNode;

import java.util.List;
import java.util.function.Supplier;

import static com.qpg.converter.internal.util.Lists.reversed;

public class HtmlPathElements implements HtmlPath {
    private final List<HtmlPathElement> elements;

    public HtmlPathElements(List<HtmlPathElement> elements) {
        this.elements = elements;
    }

    @Override
    public Supplier<List<HtmlNode>> wrap(Supplier<List<HtmlNode>> generateNodes) {
        for (HtmlPathElement element : reversed(elements)) {
            generateNodes = element.wrap(generateNodes);
        }
        return generateNodes;
    }
}
