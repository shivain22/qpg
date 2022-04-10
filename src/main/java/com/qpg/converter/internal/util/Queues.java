package com.qpg.converter.internal.util;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public class Queues {
    private Queues() {}

    public static <T> Queue<T> stack() {
        return Collections.asLifoQueue(new ArrayDeque<>());
    }
}
