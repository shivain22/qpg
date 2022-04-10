package com.qpg.converter.internal.conversion;

import com.qpg.converter.images.Image;
import com.qpg.converter.images.ImageConverter;
import com.qpg.converter.internal.documents.*;
import com.qpg.converter.internal.documents.Tab;
import com.qpg.service.QuestionBankMasterService;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.wmf.tosvg.WMFTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.qpg.converter.internal.html.Html;
import com.qpg.converter.internal.html.HtmlNode;
import com.qpg.converter.internal.results.InternalResult;
import com.qpg.converter.internal.styles.HtmlPath;
import com.qpg.converter.internal.styles.StyleMap;
import com.qpg.converter.internal.util.Lists;
import com.qpg.converter.internal.util.Maps;

import java.io.*;
import java.util.*;
import java.util.function.Supplier;

import static com.qpg.converter.internal.util.Casts.tryCast;
import static com.qpg.converter.internal.util.Iterables.findIndex;
import static com.qpg.converter.internal.util.Lists.*;
import static com.qpg.converter.internal.util.Maps.mutableMap;
import static com.qpg.converter.internal.util.Maps.lookup;
import static com.qpg.converter.internal.util.Maps.map;

public class DocumentToHtml {
    @Autowired
    QuestionBankMasterService questionBankMasterService;
    public static InternalResult<List<HtmlNode>> convertToHtml(Document document, DocumentToHtmlOptions options) {
        DocumentToHtml documentConverter = new DocumentToHtml(options, document.getComments());
        return new InternalResult<>(
            documentConverter.convertToHtml(document, INITIAL_CONTEXT),
            documentConverter.warnings);
    }

    private static List<Note> findNotes(Document document, Iterable<NoteReference> noteReferences) {
        return eagerMap(
            noteReferences,
            // TODO: handle missing notes
            reference -> document.getNotes().findNote(reference.getNoteType(), reference.getNoteId()).get());
    }

    public static InternalResult<List<HtmlNode>> convertToHtml(DocumentElement element, DocumentToHtmlOptions options) {
        DocumentToHtml documentConverter = new DocumentToHtml(options, list());
        return new InternalResult<>(
            documentConverter.convertToHtml(element, INITIAL_CONTEXT),
            documentConverter.warnings);
    }

    private static class ReferencedComment {
        private final String label;
        private final Comment comment;

        private ReferencedComment(String label, Comment comment) {
            this.label = label;
            this.comment = comment;
        }
    }

    private final String idPrefix;
    private final boolean preserveEmptyParagraphs;
    private final StyleMap styleMap;
    private final ImageConverter.ImgElement imageConverter;
    private final Map<String, Comment> comments;
    private final List<NoteReference> noteReferences = new ArrayList<>();
    private final List<ReferencedComment> referencedComments = new ArrayList<>();
    private final Set<String> warnings = new HashSet<>();

    private static final Context INITIAL_CONTEXT = new Context(false);

    private static class Context {
        private final boolean isHeader;

        Context(boolean isHeader) {
            this.isHeader = isHeader;
        }

        Context isHeader(boolean isHeader) {
            return new Context(isHeader);
        }
    }

    private DocumentToHtml(DocumentToHtmlOptions options, List<Comment> comments) {
        this.idPrefix = options.idPrefix();
        this.preserveEmptyParagraphs = options.shouldPreserveEmptyParagraphs();
        this.styleMap = options.styleMap();
        this.imageConverter = options.imageConverter();
        this.comments = Maps.toMapWithKey(comments, Comment::getCommentId);
    }

    private List<HtmlNode> convertToHtml(Document document, Context context) {
        List<HtmlNode> mainBody = convertChildrenToHtml(document, context);
        // TODO: can you have note references inside a note?
        List<Note> notes = findNotes(document, noteReferences);

        List<HtmlNode> noteNodes = notes.isEmpty()
            ? list()
            : list(Html.element("ol", eagerMap(notes, note -> convertToHtml(note, context))));

        List<HtmlNode> commentNodes = referencedComments.isEmpty()
            ? list()
            : list(Html.element("dl", eagerFlatMap(referencedComments, comment -> convertToHtml(comment, context))));

        return eagerConcat(mainBody, noteNodes, commentNodes);
    }

    private HtmlNode convertToHtml(Note note, Context context) {
        String id = generateNoteHtmlId(note.getNoteType(), note.getId());
        String referenceId = generateNoteRefHtmlId(note.getNoteType(), note.getId());
        List<HtmlNode> noteBody = convertToHtml(note.getBody(), context);
        // TODO: we probably want this to collapse more eagerly than other collapsible elements
        // -- for instance, any paragraph will probably do, regardless of attributes. (Possible other elements will do too.)
        HtmlNode backLink = Html.collapsibleElement("p", list(
            Html.text(" "),
            Html.element("a", map("href", "#" + referenceId), list(Html.text("↑")))));
        return Html.element("li", map("id", id), eagerConcat(noteBody, list(backLink)));
    }

    private List<HtmlNode> convertToHtml(ReferencedComment referencedComment, Context context) {
        // TODO: remove duplication with notes
        String commentId = referencedComment.comment.getCommentId();
        List<HtmlNode> body = convertToHtml(referencedComment.comment.getBody(), context);
        // TODO: we probably want this to collapse more eagerly than other collapsible elements
        // -- for instance, any paragraph will probably do, regardless of attributes. (Possible other elements will do too.)
        HtmlNode backLink = Html.collapsibleElement("p", list(
            Html.text(" "),
            Html.element("a", map("href", "#" + generateReferenceHtmlId("comment", commentId)), list(Html.text("↑")))));

        return list(
            Html.element(
                "dt",
                map("id", generateReferentHtmlId("comment", commentId)),
                list(Html.text("Comment " + referencedComment.label))),
            Html.element("dd",
                eagerConcat(body, list(backLink))));
    }

    private List<HtmlNode> convertToHtml(List<DocumentElement> elements, Context context) {
        return eagerFlatMap(elements,element -> convertToHtml(element, context) );
    }

    private List<HtmlNode> convertChildrenToHtml(HasChildren element, Context context) {
        return convertToHtml(element.getChildren(), context);
    }

    private class ElementConverterVisitor implements DocumentElementVisitor<List<HtmlNode>, Context> {
        @Override
        public List<HtmlNode> visit(Paragraph paragraph, Context context) {
            Supplier<List<HtmlNode>> children = () -> {
                List<HtmlNode> content = convertChildrenToHtml(paragraph, context);
                return preserveEmptyParagraphs ? cons(Html.FORCE_WRITE, content) : content;
            };
            HtmlPath mapping = styleMap.getParagraphHtmlPath(paragraph)
                .orElseGet(() -> {
                    if (paragraph.getStyle().isPresent()) {
                        warnings.add("Unrecognised paragraph style: " + paragraph.getStyle().get().describe());
                    }
                    return HtmlPath.element("p");
                });
            return mapping.wrap(children).get();
        }

        @Override
        public List<HtmlNode> visit(Run run, Context context) {
            Supplier<List<HtmlNode>> nodes = () -> convertChildrenToHtml(run, context);
            if (run.isSmallCaps()) {
                nodes = styleMap.getSmallCaps().orElse(HtmlPath.EMPTY).wrap(nodes);
            }
            if (run.isAllCaps()) {
                nodes = styleMap.getAllCaps().orElse(HtmlPath.EMPTY).wrap(nodes);
            }
            if (run.isStrikethrough()) {
                nodes = styleMap.getStrikethrough().orElse(HtmlPath.collapsibleElement("s")).wrap(nodes);
            }
            if (run.isUnderline()) {
                nodes = styleMap.getUnderline().orElse(HtmlPath.EMPTY).wrap(nodes);
            }
            if (run.getVerticalAlignment() == VerticalAlignment.SUBSCRIPT) {
                nodes = HtmlPath.collapsibleElement("sub").wrap(nodes);
            }
            if (run.getVerticalAlignment() == VerticalAlignment.SUPERSCRIPT) {
                nodes = HtmlPath.collapsibleElement("sup").wrap(nodes);
            }
            if (run.isItalic()) {
                nodes = styleMap.getItalic().orElse(HtmlPath.collapsibleElement("em")).wrap(nodes);
            }
            if (run.isBold()) {
                nodes = styleMap.getBold().orElse(HtmlPath.collapsibleElement("strong")).wrap(nodes);
            }
            HtmlPath mapping = styleMap.getRunHtmlPath(run)
                .orElseGet(() -> {
                    if (run.getStyle().isPresent()) {
                        warnings.add("Unrecognised run style: " + run.getStyle().get().describe());
                    }
                    return HtmlPath.EMPTY;
                });
            return mapping.wrap(nodes).get();
        }

        @Override
        public List<HtmlNode> visit(Text text, Context context) {
            if (text.getValue().isEmpty()) {
                return list();
            } else {
                return list(Html.text(text.getValue()));
            }
        }

        @Override
        public List<HtmlNode> visit(Tab tab, Context context) {
            return list(Html.text("\t"));
        }

        @Override
        public List<HtmlNode> visit(Break breakElement, Context context) {
            HtmlPath mapping = styleMap.getBreakHtmlPath(breakElement)
                .orElseGet(() -> {
                    if (breakElement.getType() == Break.Type.LINE) {
                        return HtmlPath.element("br");
                    } else {
                        return HtmlPath.EMPTY;
                    }
                });
            return mapping.wrap(() -> list()).get();
        }

        @Override
        public List<HtmlNode> visit(Table table, Context context) {
            HtmlPath mapping = styleMap.getTableHtmlPath(table)
                .orElse(HtmlPath.element("table"));
            return mapping.wrap(() -> generateTableChildren(table, context)).get();
        }

        private List<HtmlNode> generateTableChildren(Table table, Context context) {
            int bodyIndex = findIndex(table.getChildren(), child -> !isHeader(child))
                .orElse(table.getChildren().size());
            if (bodyIndex == 0) {
                return convertToHtml(table.getChildren(), context.isHeader(false));
            } else {
                List<HtmlNode> headRows = convertToHtml(
                    table.getChildren().subList(0, bodyIndex),
                    context.isHeader(true)
                );
                List<HtmlNode> bodyRows = convertToHtml(
                    table.getChildren().subList(bodyIndex, table.getChildren().size()),
                    context.isHeader(false)
                );
                return list(
                    Html.element("thead", headRows),
                    Html.element("tbody", bodyRows)
                );
            }
        }

        private boolean isHeader(DocumentElement child) {
            return tryCast(TableRow.class, child)
                .map(TableRow::isHeader)
                .orElse(false);
        }

        @Override
        public List<HtmlNode> visit(TableRow tableRow, Context context) {
            return list(Html.element("tr", Lists.cons(Html.FORCE_WRITE, convertChildrenToHtml(tableRow, context))));
        }

        @Override
        public List<HtmlNode> visit(TableCell tableCell, Context context) {
            String tagName = context.isHeader ? "th" : "td";
            Map<String, String> attributes = new HashMap<>();
            if (tableCell.getColspan() != 1) {
                attributes.put("colspan", Integer.toString(tableCell.getColspan()));
            }
            if (tableCell.getRowspan() != 1) {
                attributes.put("rowspan", Integer.toString(tableCell.getRowspan()));
            }
            return list(Html.element(tagName, attributes,
                Lists.cons(Html.FORCE_WRITE, convertChildrenToHtml(tableCell, context))));
        }

        @Override
        public List<HtmlNode> visit(Hyperlink hyperlink, Context context) {
            Map<String, String> attributes = mutableMap("href", generateHref(hyperlink));
            hyperlink.getTargetFrame().ifPresent(targetFrame ->
                attributes.put("target", targetFrame)
            );

            return list(Html.collapsibleElement("a", attributes, convertChildrenToHtml(hyperlink, context)));
        }

        private String generateHref(Hyperlink hyperlink) {
            if (hyperlink.getHref().isPresent()) {
                return hyperlink.getHref().get();
            } else if (hyperlink.getAnchor().isPresent()) {
                return "#" + generateId(hyperlink.getAnchor().get());
            } else {
                return "";
            }
        }

        @Override
        public List<HtmlNode> visit(Bookmark bookmark, Context context) {
            return list(Html.element("a", map("id", generateId(bookmark.getName())), list(Html.FORCE_WRITE)));
        }

        @Override
        public List<HtmlNode> visit(NoteReference noteReference, Context context) {
            noteReferences.add(noteReference);
            String noteAnchor = generateNoteHtmlId(noteReference.getNoteType(), noteReference.getNoteId());
            String noteReferenceAnchor = generateNoteRefHtmlId(noteReference.getNoteType(), noteReference.getNoteId());
            return list(Html.element("sup", list(
                Html.element("a", map("href", "#" + noteAnchor, "id", noteReferenceAnchor), list(
                    Html.text("[" + noteReferences.size() + "]"))))));
        }

        @Override
        public List<HtmlNode> visit(CommentReference commentReference, Context context) {
            return styleMap.getCommentReference().orElse(HtmlPath.IGNORE).wrap(() -> {
                String commentId = commentReference.getCommentId();
                Comment comment = lookup(comments, commentId)
                    .orElseThrow(() -> new RuntimeException("Referenced comment could not be found, id: " + commentId));
                String label = "[" + comment.getAuthorInitials().orElse("") + (referencedComments.size() + 1) + "]";
                referencedComments.add(new ReferencedComment(label, comment));

                // TODO: Remove duplication with note references
                return list(Html.element(
                    "a",
                    map(
                        "href", "#" + generateReferentHtmlId("comment", commentId),
                        "id", generateReferenceHtmlId("comment", commentId)),
                    list(Html.text(label))));
            }).get();
        }

        @Override
        public List<HtmlNode> visit(com.qpg.converter.internal.documents.Image image, Context context) {
            // TODO: custom image handlers
            // TODO: handle empty content type
            return image.getContentType()
                .map(contentType -> {
                    try {
                        Map<String, String> attributes = new HashMap<>(imageConverter.convert(new Image() {
                            @Override
                            public Optional<String> getAltText() {
                                return image.getAltText();
                            }

                            @Override
                            public String getContentType() {
                                return contentType;
                            }

                            @Override
                            public InputStream getInputStream() throws IOException {
                                return image.open();
                            }
                            @Override
                            public Optional<String> getImageName(){
                                return image.getImageName();
                            }
                        }));
                        image.getImageName().ifPresent(
                                imageName-> {/*
                                    File file = new File("images/" + imageName.substring(image.getImageName().get().lastIndexOf("/")));
                                    try (OutputStream output = new FileOutputStream(file, false)) {
                                        image.open().transferTo(output);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    if(image.getContentType().get().contains("x-wmf")){
                                        try {
                                            WMFTranscoder transcoder = new WMFTranscoder();
                                            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)));
                                            ByteArrayOutputStream svg = new ByteArrayOutputStream();
                                            TranscoderOutput output = new TranscoderOutput(svg);
                                            transcoder.transcode(input, output);
                                            String svgFile = StringUtils.replace(file.getAbsolutePath(), "wmf", "svg");
                                            FileOutputStream fileOut = new FileOutputStream(svgFile);
                                            fileOut.write(svg.toByteArray());
                                            fileOut.flush();
                                            JPEGTranscoder it = new JPEGTranscoder();
                                            ByteArrayOutputStream jpg = new ByteArrayOutputStream();
                                            it.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1f);
                                            it.transcode(new TranscoderInput(new ByteArrayInputStream(svg.toByteArray())), new TranscoderOutput(jpg));
                                            String jpgFile = StringUtils.replace(file.getAbsolutePath(), "wmf", "jpg");
                                            FileOutputStream jpgOut = new FileOutputStream(jpgFile);
                                            jpgOut.write(jpg.toByteArray());
                                            jpgOut.flush();
                                            File svgFileObj = new File(svgFile);
                                            svgFileObj.delete();
                                            file = new File(jpgFile);
                                        }catch(Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    String src = file.getAbsolutePath();
                                    attributes.put("src",src);
                                */});
                        image.getAltText().ifPresent(altText -> attributes.put("alt", altText));
                        return list(Html.element("img", attributes));
                    } catch (IOException exception) {
                        warnings.add(exception.getMessage());
                        return Lists.<HtmlNode>list();
                    }
                })
                .orElse(list());
        }
    }

    private List<HtmlNode> convertToHtml(DocumentElement element, Context context) {
        return element.accept(new ElementConverterVisitor(), context);
    }

    private String generateNoteHtmlId(NoteType noteType, String noteId) {
        return generateReferentHtmlId(noteTypeToIdFragment(noteType), noteId);
    }

    private String generateNoteRefHtmlId(NoteType noteType, String noteId) {
        return generateReferenceHtmlId(noteTypeToIdFragment(noteType), noteId);
    }

    private String generateReferentHtmlId(String referenceType, String referenceId) {
        return generateId(referenceType + "-" + referenceId);
    }

    private String generateReferenceHtmlId(String referenceType, String referenceId) {
        return generateId(referenceType + "-ref-" + referenceId);
    }

    private String noteTypeToIdFragment(NoteType noteType) {
        switch (noteType) {
            case FOOTNOTE:
                return "footnote";
            case ENDNOTE:
                return "endnote";
            default:
                throw new UnsupportedOperationException();
        }
    }

    private String generateId(String bookmarkName) {
        return idPrefix + bookmarkName;
    }
}
