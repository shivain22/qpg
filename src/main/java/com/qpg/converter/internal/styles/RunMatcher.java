package com.qpg.converter.internal.styles;

import com.qpg.converter.internal.documents.Run;

import java.util.Optional;

public class RunMatcher implements DocumentElementMatcher<Run> {
    public static final RunMatcher ANY = new RunMatcher(Optional.empty(), Optional.empty());

    public static RunMatcher styleId(String styleId) {
        return new RunMatcher(Optional.of(styleId), Optional.empty());
    }

    public static RunMatcher styleName(String styleName) {
        return new RunMatcher(Optional.empty(), Optional.of(new EqualToStringMatcher(styleName)));
    }

    private final Optional<String> styleId;
    private final Optional<StringMatcher> styleName;

    public RunMatcher(Optional<String> styleId, Optional<StringMatcher> styleName) {
        this.styleId = styleId;
        this.styleName = styleName;
    }

    @Override
    public boolean matches(Run run) {
        return DocumentElementMatching.matchesStyle(styleId, styleName, run.getStyle());
    }
}
