package com.qpg.converter.internal.results;

import com.qpg.converter.Result;
import com.qpg.converter.internal.documents.Style;
import com.qpg.domain.QuestionMaster;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.qpg.converter.internal.util.Iterables.lazyConcat;
import static com.qpg.converter.internal.util.Iterables.lazyFlatMap;
import static com.qpg.converter.internal.util.Lists.eagerMap;
import static com.qpg.converter.internal.util.Lists.list;
import static com.qpg.converter.internal.util.Sets.toSet;

public class InternalResult<T> {
    public static <T, R> InternalResult<List<R>> flatMap(Iterable<T> iterable, Function<T, InternalResult<R>> function) {
        List<InternalResult<R>> results = eagerMap(iterable, function);
        return new InternalResult<>(
            eagerMap(results, result -> result.value),
            lazyFlatMap(results, result -> result.warnings));
    }

    public static <T1, T2, R> InternalResult<R> flatMap(
        InternalResult<T1> first,
        InternalResult<T2> second,
        BiFunction<T1, T2, InternalResult<R>> function)
    {
        InternalResult<R> intermediateResult = function.apply(first.value, second.value);
        return new InternalResult<>(
            intermediateResult.value,
            lazyConcat(lazyConcat(first.warnings, second.warnings), intermediateResult.warnings));
    }

    public static <T1, T2, R> InternalResult<R> map(
        InternalResult<T1> first,
        InternalResult<T2> second,
        BiFunction<T1, T2, R> function)
    {
        return new InternalResult<>(
            function.apply(first.value, second.value),
            lazyConcat(first.warnings, second.warnings));
    }

    public static InternalResult<Optional<Style>> empty() {
        return new InternalResult<>(Optional.empty(), list());
    }

    public static <T> InternalResult<T> success(T value) {
        return new InternalResult<>(value, list());
    }

    private final T value;
    private final Iterable<String> warnings;

    public InternalResult(T value, Iterable<String> warnings) {
        this.value = value;
        this.warnings = warnings;
    }

    public T getValue() {
        return value;
    }

    public Iterable<String> getWarnings() {
        return warnings;
    }

    public <R> InternalResult<R> map(Function<T, R> function) {
        return new InternalResult<>(function.apply(value), warnings);
    }

    public <R> InternalResult<R> flatMap(Function<T, InternalResult<R>> function) {
        InternalResult<R> intermediateResult = function.apply(value);
        return new InternalResult<>(
            intermediateResult.value,
            lazyConcat(warnings, intermediateResult.warnings));
    }

    public Result<T> toResult() {
        Set<String> warnings = toSet(this.warnings);
        return new Result<T>() {
            @Override
            public T getValue() {
                return value;
            }

            @Override
            public Set<String> getWarnings() {
                return warnings;
            }

            @Override
            public Set<QuestionMaster> getQuestions(){return null;}
        };
    }
}
