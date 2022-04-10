package com.qpg.converter.internal.util;

public interface SupplierWithException<T, E extends Throwable> {
    T get() throws E;
}
