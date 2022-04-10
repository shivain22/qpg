package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.documents.Note;
import com.qpg.converter.internal.documents.NoteType;
import com.qpg.converter.internal.results.InternalResult;
import com.qpg.converter.internal.util.Iterables;
import com.qpg.converter.internal.xml.XmlElement;

import java.util.List;

import static com.qpg.converter.internal.util.Iterables.lazyFilter;

public class NotesXmlReader {
    public static NotesXmlReader footnote(BodyXmlReader bodyReader) {
        return new NotesXmlReader(bodyReader, "footnote", NoteType.FOOTNOTE);
    }

    public static NotesXmlReader endnote(BodyXmlReader bodyReader) {
        return new NotesXmlReader(bodyReader, "endnote", NoteType.ENDNOTE);
    }

    private final BodyXmlReader bodyReader;
    private final String tagName;
    private final NoteType noteType;

    private NotesXmlReader(BodyXmlReader bodyReader, String tagName, NoteType noteType) {
        this.bodyReader = bodyReader;
        this.tagName = tagName;
        this.noteType = noteType;
    }

    public InternalResult<List<Note>> readElement(XmlElement element) {
        Iterable<XmlElement> elements = Iterables.lazyFilter(element.findChildren("w:" + tagName), this::isNoteElement);
        return InternalResult.flatMap(elements, this::readNoteElement);
    }

    private boolean isNoteElement(XmlElement element) {
        return element.getAttributeOrNone("w:type")
            .map(type -> !isSeparatorType(type))
            .orElse(true);
    }

    private boolean isSeparatorType(String type) {
        return type.equals("continuationSeparator") || type.equals("separator");
    }

    private InternalResult<Note> readNoteElement(XmlElement element) {
        return bodyReader.readElements(element.getChildren())
            .toResult()
            .map(children -> new Note(
                noteType,
                element.getAttribute("w:id"),
                children));
    }
}
