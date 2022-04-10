package com.qpg.converter.internal.documents;

import com.qpg.converter.internal.util.InputStreamSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class Image implements DocumentElement {

    private final Optional<String> altText;
    private final Optional<String> contentType;
    private final InputStreamSupplier open;
    private final Optional<String> imageName;
    private final Optional<String> imageLocation;

    public Image(Optional<String> altText, Optional<String> contentType, InputStreamSupplier open,Optional<String> imageName,Optional<String> imageLocation) {
        this.altText = altText;
        this.contentType = contentType;
        this.open = open;
        this.imageName = imageName;
        this.imageLocation = imageLocation;
    }

    public Optional<String> getAltText() {
        return altText;
    }

    public Optional<String> getContentType() {
        return contentType;
    }

    public InputStream open() throws IOException {
        return open.open();
    }

    public Optional<String> getImageName(){ return imageName; }

    public Optional<String> getImageLocation(){return imageLocation;}
    @Override
    public <T, U> T accept(DocumentElementVisitor<T, U> visitor, U context) {
        return visitor.visit(this, context);
    }
}
