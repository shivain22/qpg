package com.qpg.converter.internal.styles;

import com.qpg.converter.internal.styles.parsing.StyleMapParser;

import static com.qpg.converter.internal.util.Lists.list;

public class DefaultStyles {
    public static final StyleMap DEFAULT_STYLE_MAP = StyleMapParser.parseStyleMappings(list(
        "p.Heading1 => h1:fresh",
        "p.Heading2 => h2:fresh",
        "p.Heading3 => h3:fresh",
        "p.Heading4 => h4:fresh",
        "p.Heading5 => h5:fresh",
        "p.Heading6 => h6:fresh",
        "p[style-name='Heading 1'] => h1:fresh",
        "p[style-name='Heading 2'] => h2:fresh",
        "p[style-name='Heading 3'] => h3:fresh",
        "p[style-name='Heading 4'] => h4:fresh",
        "p[style-name='Heading 5'] => h5:fresh",
        "p[style-name='Heading 6'] => h6:fresh",
        "p[style-name='heading 1'] => h1:fresh",
        "p[style-name='heading 2'] => h2:fresh",
        "p[style-name='heading 3'] => h3:fresh",
        "p[style-name='heading 4'] => h4:fresh",
        "p[style-name='heading 5'] => h5:fresh",
        "p[style-name='heading 6'] => h6:fresh",

        "r[style-name='Strong'] => strong",

        "p[style-name='footnote text'] => p:fresh",
        "r[style-name='footnote reference'] =>",
        "p[style-name='endnote text'] => p:fresh",
        "r[style-name='endnote reference'] =>",
        "p[style-name='annotation text'] => p:fresh",
        "r[style-name='annotation reference'] =>",

        // LibreOffice
        "p[style-name='Footnote'] => p:fresh",
        "r[style-name='Footnote anchor'] =>",
        "p[style-name='Endnote'] => p:fresh",
        "r[style-name='Endnote anchor'] =>",

        "p:unordered-list(1) => ul > li:fresh",
        "p:unordered-list(2) => ul|ol > li > ul > li:fresh",
        "p:unordered-list(3) => ul|ol > li > ul|ol > li > ul > li:fresh",
        "p:unordered-list(4) => ul|ol > li > ul|ol > li > ul|ol > li > ul > li:fresh",
        "p:unordered-list(5) => ul|ol > li > ul|ol > li > ul|ol > li > ul|ol > li > ul > li:fresh",
        "p:ordered-list(1) => ol > li:fresh",
        "p:ordered-list(2) => ul|ol > li > ol > li:fresh",
        "p:ordered-list(3) => ul|ol > li > ul|ol > li > ol > li:fresh",
        "p:ordered-list(4) => ul|ol > li > ul|ol > li > ul|ol > li > ol > li:fresh",
        "p:ordered-list(5) => ul|ol > li > ul|ol > li > ul|ol > li > ul|ol > li > ol > li:fresh",

        "r[style-name='Hyperlink'] =>",

        "p[style-name='Normal'] => p:fresh"));
}
