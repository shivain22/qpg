package com.qpg.converter.internal.util;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

public class Optionals {
    public static <T> Optional<T> first(Optional<T> first, Optional<T> second) {
        if (first.isPresent()) {
            return first;
        } else {
            return second;
        }
    }

    public static <T1, T2, R> Optional<R> flatMap(
        Optional<T1> first,
        Optional<T2> second,
        BiFunction<T1, T2, Optional<R>> function)
    {
        if (first.isPresent() && second.isPresent()) {
            return function.apply(first.get(), second.get());
        } else {
            return Optional.empty();
        }
    }

    public static <T1, T2, R> Optional<R> map(
        Optional<T1> first,
        Optional<T2> second,
        BiFunction<T1, T2, R> function)
    {
        if (first.isPresent() && second.isPresent()) {
            return Optional.of(function.apply(first.get(), second.get()));
        } else {
            return Optional.empty();
        }
    }

    public static <R> Optional<R> map(
        OptionalInt first,
        IntFunction<R> function)
    {
        if (first.isPresent()) {
            return Optional.of(function.apply(first.getAsInt()));
        } else {
            return Optional.empty();
        }
    }
}
