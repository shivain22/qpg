package com.qpg.converter.internal.util;

import java.io.IOException;

public class PassThroughException extends RuntimeException {
    public static <T> T wrap(SupplierWithException<T, IOException> supplier) {
        try {
            return supplier.get();
        } catch (IOException exception) {
            throw new PassThroughException(exception);
        }
    }

    public static <T> T unwrap(SupplierWithException<T, IOException> supplier) throws IOException {
        try {
            return supplier.get();
        } catch (PassThroughException exception) {
            throw exception.exception;
        }
    }

    private final IOException exception;

    public PassThroughException(IOException exception) {
        super(exception);
        this.exception = exception;
    }
}
