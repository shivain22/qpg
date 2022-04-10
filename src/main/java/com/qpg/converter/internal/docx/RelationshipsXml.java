package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.util.Lists;
import com.qpg.converter.internal.xml.XmlElement;

public class RelationshipsXml {
    public static Relationships readRelationshipsXmlElement(XmlElement element) {
        return new Relationships(Lists.eagerMap(
            element.findChildren("relationships:Relationship"),
            RelationshipsXml::readRelationship
        ));
    }

    private static Relationship readRelationship(XmlElement element) {
        String relationshipId = element.getAttribute("Id");
        String target = element.getAttribute("Target");
        String type = element.getAttribute("Type");
        return new Relationship(relationshipId, target, type);
    }
}
