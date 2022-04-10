package com.qpg.converter.internal;

import com.qpg.converter.internal.conversion.DocumentToHtml;
import com.qpg.converter.internal.conversion.DocumentToHtmlOptions;
import com.qpg.converter.internal.documents.DocumentElement;
import com.qpg.converter.internal.documents.HasChildren;
import com.qpg.converter.internal.documents.Paragraph;
import com.qpg.converter.internal.documents.Text;
import com.qpg.converter.internal.archives.Archive;
import com.qpg.converter.internal.docx.EmbeddedStyleMap;
import com.qpg.converter.internal.archives.InMemoryArchive;
import com.qpg.converter.internal.archives.ZippedArchive;
import com.qpg.converter.internal.html.Html;
import com.qpg.converter.internal.results.InternalResult;
import com.qpg.converter.internal.styles.StyleMap;
import com.qpg.converter.internal.styles.parsing.StyleMapParser;
import com.qpg.converter.internal.util.PassThroughException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.qpg.converter.internal.docx.DocumentReader.readDocument;
import static com.qpg.converter.internal.util.Casts.tryCast;
import static com.qpg.converter.internal.util.Iterables.lazyMap;
import static com.qpg.converter.internal.util.Lists.list;

public class InternalDocumentConverter {
    private final DocumentToHtmlOptions options;

    public InternalDocumentConverter(DocumentToHtmlOptions options) {
        this.options = options;
    }

    public InternalResult<String> convertToHtml(InputStream stream) throws IOException {
        return PassThroughException.unwrap(() ->
            withDocxFile(stream, zipFile ->
                convertToHtml(Optional.empty(), zipFile)));
    }

    public InternalResult<String> convertToHtml(File file) throws IOException {
        return PassThroughException.unwrap(() ->
            withDocxFile(file, zipFile -> convertToHtml(Optional.of(file.toPath()), zipFile)));
    }

    private InternalResult<String> convertToHtml(Optional<Path> path, Archive zipFile) {
        Optional<StyleMap> styleMap = readEmbeddedStyleMap(zipFile).map(StyleMapParser::parse);
        DocumentToHtmlOptions conversionOptions = styleMap.map(options::addEmbeddedStyleMap).orElse(options);

        return readDocument(path, zipFile)
            .flatMap(nodes -> DocumentToHtml.convertToHtml(nodes, conversionOptions))
            .map(Html::stripEmpty)
            .map(Html::collapse)
            .map(Html::write);
    }

    private Optional<String> readEmbeddedStyleMap(Archive zipFile) {
        return PassThroughException.wrap(() -> EmbeddedStyleMap.readStyleMap(zipFile));
    }

    public InternalResult<String> extractRawText(InputStream stream) throws IOException {
        return PassThroughException.unwrap(() ->
            withDocxFile(stream, zipFile ->
                extractRawText(Optional.empty(), zipFile)));
    }

    public InternalResult<String> extractRawText(File file) throws IOException {
        return PassThroughException.unwrap(() ->
            withDocxFile(file, zipFile ->
                extractRawText(Optional.of(file.toPath()), zipFile)));
    }

    private InternalResult<String> extractRawText(Optional<Path> path, Archive zipFile) {
        return readDocument(path, zipFile)
            .map(InternalDocumentConverter::extractRawTextOfChildren);
    }

    private static <T> T withDocxFile(File file, Function<Archive, T> function) throws IOException {
        try (Archive zipFile = new ZippedArchive(file)) {
            return function.apply(zipFile);
        }
    }

    private static <T> T withDocxFile(InputStream stream, Function<Archive, T> function) throws IOException {
        try (Archive zipFile = InMemoryArchive.fromStream(stream)) {
            return function.apply(zipFile);
        }
    }

    private static String extractRawTextOfChildren(HasChildren parent) {
        return extractRawText(parent.getChildren());
    }

    private static String extractRawText(List<DocumentElement> nodes) {
        return String.join("", lazyMap(nodes, node -> extractRawText(node)));
    }

    private static String extractRawText(DocumentElement node) {
        return tryCast(Text.class, node)
            .map(Text::getValue)
            .orElseGet(() -> {
                List<DocumentElement> children = tryCast(HasChildren.class, node)
                    .map(HasChildren::getChildren)
                    .orElse(list());
                String suffix = tryCast(Paragraph.class, node).map(paragraph -> "\n\n").orElse("");
                return extractRawText(children) + suffix;
            });
    }
}
