package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.documents.Comment;
import com.qpg.converter.internal.documents.Document;
import com.qpg.converter.internal.documents.Notes;
import com.qpg.converter.internal.results.InternalResult;
import com.qpg.converter.internal.xml.XmlElement;
import com.qpg.converter.internal.xml.XmlElementLike;

import java.util.List;

public class DocumentXmlReader {
    private final BodyXmlReader bodyReader;
    private final Notes notes;
    private final List<Comment> comments;

    public DocumentXmlReader(BodyXmlReader bodyReader, Notes notes, List<Comment> comments) {
        this.bodyReader = bodyReader;
        this.notes = notes;
        this.comments = comments;
    }

    public InternalResult<Document> readElement(XmlElement element) {
        XmlElementLike body = element.findChildOrEmpty("w:body");
        return bodyReader.readElements(body.getChildren())
            .toResult()
            .map(children -> new Document(children, notes, comments));
    }
}
