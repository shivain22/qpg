package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.util.Maps;
import com.qpg.converter.internal.util.Paths;

import java.util.Map;
import java.util.Optional;

import static com.qpg.converter.internal.util.Maps.map;

public class ContentTypes {
    public static final ContentTypes DEFAULT = new ContentTypes(Maps.map(), Maps.map());

    private static final Map<String, String> imageExtensions = Maps.<String, String>builder()
        .put("png", "png")
        .put("gif", "gif")
        .put("jpeg", "jpeg")
        .put("jpg", "jpeg")
        .put("bmp", "bmp")
        .put("tif", "tiff")
        .put("tiff", "tiff")
        .build();

    private final Map<String, String> extensionDefaults;
    private final Map<String, String> overrides;

    public ContentTypes(Map<String, String> extensionDefaults, Map<String, String> overrides) {
        this.extensionDefaults = extensionDefaults;
        this.overrides = overrides;
    }

    public Optional<String> findContentType(String path) {
        if (overrides.containsKey(path)) {
            return Maps.lookup(overrides, path);
        } else {
            String extension = Paths.getExtension(path);
            if (extensionDefaults.containsKey(extension)) {
                return Maps.lookup(extensionDefaults, extension);
            } else {
                return Maps.lookup(imageExtensions, extension.toLowerCase())
                    .map(imageType -> "image/" + imageType);
            }
        }
    }
}
