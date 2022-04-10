package com.qpg.converter.internal.html;

import com.qpg.converter.internal.util.Lists;

import java.util.Map;

public class HtmlWriter {
    public static void write(HtmlNode node, StringBuilder builder) {
        node.accept(new HtmlNode.Visitor() {
            @Override
            public void visit(HtmlElement element) {
                if(element.getTagName().contains("img")){
                    System.out.println("image starts");
                }
                builder.append("<").append(element.getTagName());

                HtmlWriter.generateAttributes(element.getAttributes(), builder);

                if (element.isVoid()) {
                    if(element.getTagName().contains("img")){
                        System.out.println("image starts");
                    }
                    builder.append(" />");
                } else {
                    if(element.getTagName().contains("img")){
                        System.out.println("image starts");
                    }
                    builder.append(">");

                    element.getChildren().forEach(child -> write(child, builder));

                    builder
                        .append("</")
                        .append(element.getTagName())
                        .append(">");
                }
            }

            @Override
            public void visit(HtmlTextNode node) {
                builder.append(HtmlWriter.escapeText(node.getValue()));
            }

            @Override
            public void visit(HtmlForceWrite forceWrite) {
            }
        });
    }

    private static void generateAttributes(Map<String, String> attributes, StringBuilder builder) {
        for (Map.Entry<String, String> attribute : Lists.orderedBy(attributes.entrySet(), Map.Entry::getKey)) {
            builder
                .append(" ")
                .append(attribute.getKey())
                .append("=\"")
                .append(HtmlWriter.escapeAttributeValue(attribute.getValue()))
                .append("\"");
        }
    }

    private static String escapeText(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String escapeAttributeValue(String value) {
        return escapeText(value).replace("\"", "&quot;");
    }
}
