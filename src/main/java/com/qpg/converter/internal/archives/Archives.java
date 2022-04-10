package com.qpg.converter.internal.archives;

import java.io.IOException;
import java.io.InputStream;

public class Archives {
    public static InputStream getInputStream(Archive file, String name) throws IOException {
        return file.tryGetInputStream(name)
            .orElseThrow(() -> new IOException("Missing entry in file: " + name));
    }
}
