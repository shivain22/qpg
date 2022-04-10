package com.qpg.converter.images;

import org.checkerframework.checker.nullness.Opt;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface Image {
    Optional<String> getAltText();
    String getContentType();
    InputStream getInputStream() throws IOException;
    Optional<String> getImageName();
}
