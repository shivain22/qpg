package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.archives.Archive;
import com.qpg.converter.internal.xml.XmlElement;
import com.qpg.converter.internal.xml.XmlNode;

public class BodyXmlReader {
    private final Styles styles;
    private final Numbering numbering;
    private final Relationships relationships;
    private final ContentTypes contentTypes;
    private final Archive file;
    private final FileReader fileReader;

    public BodyXmlReader(
        Styles styles,
        Numbering numbering,
        Relationships relationships,
        ContentTypes contentTypes,
        Archive file,
        FileReader fileReader
    )
    {
        this.styles = styles;
        this.numbering = numbering;
        this.relationships = relationships;
        this.contentTypes = contentTypes;
        this.file = file;
        this.fileReader = fileReader;
    }

    ReadResult readElements(Iterable<XmlNode> nodes) {
        return new StatefulBodyXmlReader(
            styles,
            numbering,
            relationships,
            contentTypes,
            file,
            fileReader
        ).readElements(nodes);
    }

    public ReadResult readElement(XmlElement element) {
        return new StatefulBodyXmlReader(
            styles,
            numbering,
            relationships,
            contentTypes,
            file,
            fileReader
        ).readElement(element);
    }
}
