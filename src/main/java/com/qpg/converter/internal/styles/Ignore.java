package com.qpg.converter.internal.styles;

import com.qpg.converter.internal.html.HtmlNode;

import java.util.List;
import java.util.function.Supplier;

import static com.qpg.converter.internal.util.Lists.list;

class Ignore implements HtmlPath {
    static final HtmlPath INSTANCE = new Ignore();

    private Ignore() {
    }

    @Override
    public Supplier<List<HtmlNode>> wrap(Supplier<List<HtmlNode>> generateNodes) {
        return () -> list();
    }
}
