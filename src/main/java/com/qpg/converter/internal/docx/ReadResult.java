package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.documents.DocumentElement;
import com.qpg.converter.internal.results.InternalResult;
import com.qpg.converter.internal.util.Iterables;
import com.qpg.converter.internal.util.Lists;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ReadResult {
    public static final ReadResult EMPTY_SUCCESS = success(Lists.list());

    public static <T> ReadResult flatMap(Iterable<T> iterable, Function<T, ReadResult> function) {
        List<ReadResult> results = Lists.eagerMap(iterable, function);
        return new ReadResult(
            Lists.eagerFlatMap(results, result -> result.elements),
            Lists.eagerFlatMap(results, result -> result.extra),
            Iterables.lazyFlatMap(results, result -> result.warnings));
    }

    public static <T> ReadResult map(
        InternalResult<T> first,
        ReadResult second,
        BiFunction<T, List<DocumentElement>, DocumentElement> function)
    {
        return new ReadResult(
            Lists.list(function.apply(first.getValue(), second.elements)),
            second.extra,
            Iterables.lazyConcat(first.getWarnings(), second.warnings));
    }

    public static ReadResult success(DocumentElement element) {
        return success(Lists.list(element));
    }

    public static ReadResult success(List<DocumentElement> elements) {
        return new ReadResult(elements, Lists.list(), Lists.list());
    }

    public static ReadResult emptyWithWarning(String warning) {
        return withWarning(Lists.list(), warning);
    }

    public static ReadResult withWarning(DocumentElement element, String warning) {
        return withWarning(Lists.list(element), warning);
    }

    public static ReadResult withWarning(List<DocumentElement> elements, String warning) {
        return new ReadResult(elements, Lists.list(), Lists.list(warning));
    }

    private final List<DocumentElement> elements;
    private final List<DocumentElement> extra;
    private final Iterable<String> warnings;

    public ReadResult(List<DocumentElement> elements, List<DocumentElement> extra, Iterable<String> warnings) {
        this.elements = elements;
        this.extra = extra;
        this.warnings = warnings;
    }

    public ReadResult map(Function<List<DocumentElement>, DocumentElement> function) {
        return new ReadResult(
            Lists.list(function.apply(elements)),
            extra,
            warnings);
    }

    public ReadResult flatMap(Function<List<DocumentElement>, ReadResult> function) {
        ReadResult result = function.apply(elements);
        return new ReadResult(
            result.elements,
            Lists.eagerConcat(extra, result.extra),
            Iterables.lazyConcat(warnings, result.warnings));
    }

    public ReadResult toExtra() {
        return new ReadResult(Lists.list(), Lists.eagerConcat(extra, elements), warnings);
    }

    public ReadResult appendExtra() {
        return new ReadResult(Lists.eagerConcat(elements, extra), Lists.list(), warnings);
    }

    public InternalResult<List<DocumentElement>> toResult() {
        return new InternalResult<>(elements, warnings);
    }
}
