package com.qpg.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;

public class TestConvert {

    public static void main(String[] args) throws IOException {
        DocumentConverter converter = new DocumentConverter();
        Result<String> result = converter.convertToHtml(new File("E:\\qpg\\testqb.docx"));
        String html = result.getValue();
        Path path = Paths.get("E:\\qpg\\test.html");
        Files.write(path, Arrays.asList(html));
        Set<String> warnings = result.getWarnings();
        Document doc = Jsoup.parse(new File("E:\\qpg\\test.html"), "utf-8");
        Elements tables = doc.select("table");
        Elements paragraphs = doc.select("p");

        int i=0;
        int j =0;
        for(Element p:paragraphs){
            if(j==2){
                System.out.println("Subject Code "+ p.html());
            }
            if(j==3){
                System.out.println("Subject Name "+ p.html());
            }
            if(j==4){
                System.out.println("Professor's Name "+ p.html());
            }
            if(j==7){
                System.out.println("Department Name "+ p.html());
            }
            if(j==8){
                System.out.println("College Name "+ p.html());
            }
            j++;
        }
        j=0;
        int subTopic=0;
        //System.out.println(tables.size());
        for(Element table :tables) {
            if(i==1){
                Elements rows = table.select("> tbody > tr ");
                for (Element row : rows) {
                    if(j>0) {
                        Elements columns = row.select("td");
                        for (Element column : columns) {
                            if(columns.size()==1){
                                System.out.print("Topic : "+column.html());
                            }
                            if(columns.size()>1){
                                System.out.print("Sub Topic : "+column.html());
                            }

                            subTopic++;
                        }
                    }
                    j++;
                    System.out.println("");
                }
            }
            if(i<4){
                table.remove();
            }
            else {
                Elements rows = table.select("> tbody > tr ");
                int rowCount = 0;
                for (Element row : rows) {
                    int colCount = 0;
                    Elements columns = row.select("td");
                    for (Element column : columns) {
                        if (column.text().trim().length() == 0) {
                            colCount++;
                        }
                    }
                    if (colCount == columns.size()) {
                        row.remove();
                    }
                }
            }
            i++;
        }

        tables = doc.select("table");
       // System.out.println(tables.size());

        i=0;
        for(Element table :tables) {
            //System.out.println("value of i"+ i);
            if(table.parents().size()==2) {
                if (i % 7 == 0) {
                    System.out.println("#############################  Choose the Best answer :" + i + "start  ##########################");
                    Elements rows = table.select("> tbody > tr ");
                    int rowCount = 0;
                    for (Element row : rows) {
                        int colCount = 0;
                        Elements columns = row.select("td");
                        for (Element column : columns) {
                            if(rowCount==6){
                                System.out.println(column.text());
                            }
                            if(rowCount>6) {
                                if(rowCount>6 && (rowCount-7)%4==0) {
                                    if (column.html() != null && column.html().trim().length() > 0) {
                                        if (colCount == 0) {
                                            System.out.print("Question No: " + column.html() + "      ");
                                        }
                                        if (colCount == 1) {
                                            System.out.print("Question Text: " + column.html() + "      ");
                                        }
                                        if (colCount == 2) {
                                            System.out.print("Answer Option: " + column.html() + "      ");
                                        }
                                    }
                                }
                                if(rowCount>7 && (rowCount-8)%4==0) {
                                    if (column.html() != null && column.html().trim().length() > 0) {
                                        if (colCount == 2) {
                                            System.out.print("Option a: " + column.html() + "      ");
                                        }
                                        if (colCount == 4) {
                                            System.out.print("Option b: " + column.html() + "      ");
                                        }
                                    }
                                }
                                if(rowCount>8 && (rowCount-9)%4==0) {
                                    if (column.html() != null && column.html().trim().length() > 0) {
                                        if (colCount == 2) {
                                            System.out.print("Option c: " + column.html() + "      ");
                                        }
                                        if (colCount == 4) {
                                            System.out.print("Option d: " + column.html() + "      ");
                                        }
                                    }
                                }
                            }
                            colCount++;
                        }
                        rowCount++;
                        if(rowCount>7) {
                            System.out.println("");
                        }
                    }
                    System.out.println("#############################  Choose the Best answer :" + i + "end  ##########################");
                }else if(i==1 || i % 7 ==1){
                    System.out.println("#############################  Fill in the blanks :" + i + "start  ##########################");
                    Elements rows = table.select("> tbody > tr ");
                    int rowCount = 0;
                    for (Element row : rows) {
                        int colCount = 0;
                        Elements columns = row.select("td");
                        for (Element column : columns) {
                            if(rowCount==1){
                                System.out.println(column.text());
                            }
                            if(rowCount>1 && rowCount%2==0){
                                if (colCount == 0) {
                                    System.out.print("Question No: " + column.html() + "      ");
                                }
                                if (colCount == 1) {
                                    System.out.print("Question Text: " + column.html() + "      ");
                                }
                                if (colCount == 2) {
                                    System.out.print("Answer Option: " + column.html() + "      ");
                                }
                            }
                            colCount++;
                        }
                        rowCount++;
                        System.out.println("");
                    }
                    System.out.println("#############################  Fill in the blanks :" + i + "end  ##########################");
                }else if(i==2 || i % 7 ==2){
                    System.out.println("#############################  Abbreviation and Formulae :" + i + "start  ##########################");
                    Elements rows = table.select("> tbody > tr ");
                    int rowCount = 0;
                    for (Element row : rows) {
                        int colCount = 0;
                        Elements columns = row.select("td");
                        for (Element column : columns) {
                            if(rowCount==1){
                                System.out.println(column.text());
                            }
                            if(rowCount>1 && rowCount%2==0){
                                if (colCount == 0) {
                                    System.out.print("Question No: " + column.html() + "      ");
                                }
                                if (colCount == 1) {
                                    System.out.print("Question Text: " + column.html() + "      ");
                                }
                                if (colCount == 2) {
                                    System.out.print("Answer Option: " + column.html() + "      ");
                                }
                            }
                            colCount++;
                        }
                        rowCount++;
                        System.out.println("");
                    }
                    System.out.println("#############################  Abbreviation and Formulae :" + i + "end  ##########################");
                }else if(i==3||i % 7 ==3){
                    System.out.println("#############################  Match the following :" + i + "start  ##########################");
                    Elements rows = table.select("> tbody > tr ");
                    int rowCount = 0;
                    for (Element row : rows) {
                        int colCount = 0;
                        Elements columns = row.select("td");
                        for (Element column : columns) {
                            if(rowCount==2){
                                System.out.println(column.text());
                            }
                            if(rowCount>=3 && rowCount%2==1){
                                if (colCount == 0) {
                                    System.out.print("Question No: " + column.html() + "      ");
                                }
                                if (colCount == 1) {
                                    System.out.print("Question Text: " + column.html() + "      ");
                                }
                                if (colCount == 2) {
                                    System.out.print("Answer Option: " + column.html() + "      ");
                                }
                            }
                            colCount++;
                        }
                        rowCount++;
                        System.out.println("");
                    }
                    System.out.println("#############################  Match the following :" + i + "end  ##########################");
                }else if(i==4||i % 7 ==4){
                    System.out.println("#############################  Short answer 3 marks :" + i + "start  ##########################");
                    Elements rows = table.select("> tbody > tr ");
                    int rowCount = 0;
                    for (Element row : rows) {
                        int colCount = 0;
                        Elements columns = row.select(":root > td");
                        for (Element column : columns) {
                            if(rowCount==2){
                                System.out.println(column.text());
                            }
                            if (rowCount>=3 && rowCount%2 == 1) {
                                if (colCount == 0) {
                                    System.out.print("Question No: " + column.html() + "      ");
                                }
                                if (colCount == 1) {
                                    System.out.print("Question Text: " + column.html() + "      ");
                                }
                                if (colCount == 2) {
                                    System.out.print("Answer Text: " + column.html() + "      ");
                                }
                            }
                            colCount++;
                        }
                        rowCount++;
                        System.out.println("");
                    }
                    System.out.println("#############################  Short answer 3 marks :" + i + "end  ##########################");
                }else if(i==5||i % 7 ==5){
                    System.out.println("#############################  Short answer 6 marks :" + i + "start  ##########################");
                    Elements rows = table.select("> tbody > tr ");
                    int rowCount = 0;
                    for (Element row : rows) {
                        int colCount = 0;
                        Elements columns = row.select("td");
                        for (Element column : columns) {
                            if(rowCount==1){
                                System.out.println(column.text());
                            }
                            if (rowCount>=2 && rowCount%2 == 0) {
                                if (colCount == 0) {
                                    System.out.print("Question No: " + column.html() + "      ");
                                }
                                if (colCount == 1) {
                                    System.out.print("Question Text: " + column.html() + "      ");
                                }
                                if (colCount == 2) {
                                    System.out.print("Answer Text: " + column.html() + "      ");
                                }
                            }
                            colCount++;
                        }
                        rowCount++;
                        System.out.println("");
                    }
                    System.out.println("#############################  Short answer 6 marks :" + i + "end  ##########################");
                }else if(i==6|| i % 7 ==6){
                    System.out.println("#############################  Long answer 12 marks :" + i + "start  ##########################");
                    Elements rows = table.select("> tbody > tr ");
                    int rowCount = 0;
                    for (Element row : rows) {
                        int colCount = 0;
                        Elements columns = row.select("td");
                        for (Element column : columns) {
                            if(rowCount==1){
                                System.out.println(column.text());
                            }
                            if (rowCount>=2 && rowCount%2 == 0) {
                                if (colCount == 0) {
                                    System.out.print("Question No: " + column.html() + "      ");
                                }
                                if (colCount == 1) {
                                    System.out.print("Question Text: " + column.html() + "      ");
                                }
                                if (colCount == 2) {
                                    System.out.print("Answer Text: " + column.html() + "      ");
                                }
                            }
                            colCount++;
                        }
                        rowCount++;
                        System.out.println("");
                    }
                    System.out.println("#############################  Long answer 12 marks :" + i + "end  ##########################");
                }
                i++;
            }
        }
        System.out.println("Total tables of questions - "+i);
    }
}
