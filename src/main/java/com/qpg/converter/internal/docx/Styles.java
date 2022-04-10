package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.documents.NumberingStyle;
import com.qpg.converter.internal.documents.Style;
import com.qpg.converter.internal.util.Maps;

import java.util.Map;
import java.util.Optional;

import static com.qpg.converter.internal.util.Maps.map;

public class Styles {
    public static final Styles EMPTY = new Styles(Maps.map(), Maps.map(), Maps.map(), Maps.map());

    private final Map<String, Style> paragraphStyles;
    private final Map<String, Style> characterStyles;
    private final Map<String, Style> tableStyles;
    private final Map<String, NumberingStyle> numberingStyles;

    public Styles(
        Map<String, Style> paragraphStyles,
        Map<String, Style> characterStyles,
        Map<String, Style> tableStyles,
        Map<String, NumberingStyle> numberingStyles
    ) {
        this.paragraphStyles = paragraphStyles;
        this.characterStyles = characterStyles;
        this.tableStyles = tableStyles;
        this.numberingStyles = numberingStyles;
    }

    public Optional<Style> findParagraphStyleById(String id) {
        return Maps.lookup(paragraphStyles, id);
    }

    public Optional<Style> findCharacterStyleById(String id) {
        return Maps.lookup(characterStyles, id);
    }

    public Optional<Style> findTableStyleById(String id) {
        return Maps.lookup(tableStyles, id);
    }

    public Optional<NumberingStyle> findNumberingStyleById(String id) {
        return Maps.lookup(numberingStyles, id);
    }
}
