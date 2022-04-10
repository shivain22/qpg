package com.qpg.converter.internal.docx;

import com.qpg.converter.internal.util.Lists;
import com.qpg.converter.internal.util.Maps;

import java.util.List;
import java.util.Map;

import static com.qpg.converter.internal.util.Lists.list;

public class Relationships {
    public static final Relationships EMPTY = new Relationships(Lists.list());

    private final Map<String, String> targetsByRelationshipId;
    private final Map<String, List<String>> targetsByType;

    public Relationships(List<Relationship> relationships) {
        this.targetsByRelationshipId = Maps.toMap(relationships, relationship -> Maps.entry(
            relationship.getRelationshipId(),
            relationship.getTarget()
        ));
        this.targetsByType = Maps.toMultiMap(relationships, relationship -> Maps.entry(
            relationship.getType(),
            relationship.getTarget()
        ));
    }

    public String findTargetByRelationshipId(String relationshipId) {
        return Maps.lookup(targetsByRelationshipId, relationshipId)
            .orElseThrow(() -> new RuntimeException("Could not find relationship '" + relationshipId + "'"));
    }

    public List<String> findTargetsByType(String type) {
        return Maps.lookup(targetsByType, type).orElse(Lists.list());
    }
}
