package com.qpg.service.impl;

import com.qpg.converter.images.QpgImageConverter;
import com.qpg.converter.internal.util.Base64Encoding;
import com.qpg.domain.*;
import com.qpg.service.*;
import com.qpg.repository.QuestionBankMasterRepository;
import org.apache.commons.codec.CharEncoding;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.qpg.converter.DocumentConverter;
import com.qpg.converter.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Service Implementation for managing {@link QuestionBankMaster}.
 */
@Service
@Transactional
public class QuestionBankMasterServiceImpl implements QuestionBankMasterService {

    private final Logger log = LoggerFactory.getLogger(QuestionBankMasterServiceImpl.class);

    private final QuestionBankMasterRepository questionBankMasterRepository;

    @Autowired
    SubTopicMasterService subTopicMasterService;

    @Autowired
    QuestionMasterService questionMasterService;

    @Autowired
    QuestionTypeMasterService questionTypeMasterService;

    @Autowired
    DifficultyTypeMasterService difficultyTypeMasterService;

    @Autowired
    ConfigMasterService configMasterService;


    public QuestionBankMasterServiceImpl(QuestionBankMasterRepository questionBankMasterRepository) {
        this.questionBankMasterRepository = questionBankMasterRepository;
    }

    @Override
    public QuestionBankMaster save(QuestionBankMaster questionBankMaster) {
        log.debug("Request to save QuestionBankMaster : {}", questionBankMaster);
        try {
            processQuestionBank(bytesToFile(questionBankMaster),questionBankMaster);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionBankMasterRepository.save(questionBankMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionBankMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionBankMasters");
        return questionBankMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionBankMaster> findOne(Long id) {
        log.debug("Request to get QuestionBankMaster : {}", id);
        return questionBankMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionBankMaster : {}", id);
        questionBankMasterRepository.deleteById(id);
    }

    private void processQuestionBank(File questionBank,QuestionBankMaster questionBankMaster) throws IOException {
            List allUnits = new LinkedList();
            List unit = null;
            List mcq = null;
            List fb = null;
            List fae = null;
            List mf = null;
            List vsa = null;
            List sa = null;
            List la = null;

        DocumentConverter converter = new DocumentConverter();//new DocumentConverter().imageConverter(new QpgImageConverter(String.valueOf(configMasterService.findOne(1l).get().getValue())));
            Result<String> result = converter.convertToHtml(questionBank);
            String html = result.getValue();
            Path path = Paths.get(String.valueOf(new File(questionBank.getAbsolutePath()+".html")));
            Files.write(path, Arrays.asList(html));
            Set<String> warnings = result.getWarnings();
            Document doc = Jsoup.parse(new File(questionBank.getAbsolutePath()+".html"), "utf-8");
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            //doc.outputSettings().escapeMode(Entities.EscapeMode.base);
            //doc.outputSettings().charset(CharEncoding.UTF_8);
            Elements tables = doc.select("table");
            Elements paragraphs = doc.select("p");
            CollegeMaster collegeMaster=new CollegeMaster();
            DepartmentMaster departmentMaster = new DepartmentMaster();
            tables = doc.select("body > table");
            tables.remove(0);
            System.out.println("Total tables of questions - "+tables.size());
            for(int i=0;i<tables.size();i++) {
                Element table = tables.get(i);
                if(table.parents().size()==2) {
                System.out.println(i);
                    QuestionMaster qm =null;
                    QuestionTypeMaster qtm =null;
                    DifficultyTypeMaster dtm = null;
                    double marks =0.0d;
                    Elements rows = table.select("> tbody > tr ");
                    Element headerRow = table.select("> tbody > tr ").get(0);
                    Element subTopicRow = table.select("> tbody > tr ").get(1);
                    String qt = headerRow.select("td").get(0).text();
                    qt = qt.substring(0, qt.indexOf(":")).trim();
                    System.out.println("Unit "+((i/7)+1) +" and Question Type "+qt);
                    String subTopic = subTopicRow.select("td").get(1).text();
                    qtm = questionTypeMasterService.findQuestionTypeMasterByShortName(qt).get();
                    dtm = difficultyTypeMasterService.findDifficultyTypeMasterByName("Medium").get();
                    marks = Double.parseDouble(headerRow.select("td").get(1).text().trim());
                    SubTopicMaster subTopicMaster =null;
                    if(subTopicMasterService.findSubTopicMasterByTopicMasterAndName(questionBankMaster.getTopicMaster(),subTopic.trim()).isPresent()) {
                        subTopicMaster = subTopicMasterService.findSubTopicMasterByTopicMasterAndName(questionBankMaster.getTopicMaster(), subTopic.trim()).get();
                    }else{
                        subTopicMaster = new SubTopicMaster();
                        subTopicMaster.setTopicMaster(questionBankMaster.getTopicMaster());
                        subTopicMaster.setName(subTopic.trim());
                        subTopicMasterService.save(subTopicMaster);
                    }
                    questionBankMaster.setSubTopicMaster(subTopicMaster);

                    if (i % 7 == 0) {
                        int qCount =0;
                        int rowCount = 0;
                        unit = new LinkedList();
                        mcq = new LinkedList();
                        fb = new LinkedList();
                        fae = new LinkedList();
                        mf = new LinkedList();
                        vsa = new LinkedList();
                        sa = new LinkedList();
                        la = new LinkedList();
                        unit.add(mcq);
                        unit.add(fb);
                        unit.add(fae);
                        unit.add(mf);
                        unit.add(vsa);
                        unit.add(sa);
                        unit.add(la);
                        allUnits.add(unit);
                        for(int mcqRowCount =2;mcqRowCount<rows.size();mcqRowCount+=3){
                            System.out.println(mcqRowCount);
                            Element row = rows.get(mcqRowCount);
                            qm = new QuestionMaster();
                            qm.setSubTopicMaster(questionBankMaster.getSubTopicMaster());
                            qm.setQuestionTypeMaster(qtm);
                            qm.setDifficultyTypeMaster(dtm);
                            qm.setWeightage(marks);
                            qm.setParentQuestionMaster(null);
                            mcq.add(qm);
                            qm.setText(row.select("td").get(1).html());
                            String answerOption = row.select("td").get(2).text();
                            answerOption = answerOption.substring(0,answerOption.indexOf(":")).trim();//row.select("td").get(2).text().substring(rows.select("td").get(2).text().indexOf(":")).trim().toLowerCase();
                            AnswerMaster a = new AnswerMaster();
                            AnswerMaster b = new AnswerMaster();
                            AnswerMaster c = new AnswerMaster();
                            AnswerMaster d = new AnswerMaster();
                            a.setText(rows.get(mcqRowCount+1).select("td").get(2).text());
                            b.setText(rows.get(mcqRowCount+1).select("td").get(4).text());
                            c.setText(rows.get(mcqRowCount+2).select("td").get(2).text());
                            d.setText(rows.get(mcqRowCount+2).select("td").get(4).text());
                            a.setCorrect(false);
                            b.setCorrect(false);
                            c.setCorrect(false);
                            d.setCorrect(false);
                            if(answerOption.equals("a")){
                                a.setCorrect(true);
                            }
                            if(answerOption.equals("b")){
                                b.setCorrect(true);
                            }
                            if(answerOption.equals("c")){
                                c.setCorrect(true);
                            }
                            if(answerOption.equals("d")){
                                d.setCorrect(true);
                            }
                            qm.addAnswerMaster(a);
                            qm.addAnswerMaster(b);
                            qm.addAnswerMaster(c);
                            qm.addAnswerMaster(d);
                        }
                    }
                    else {
                        for (int rowCount =2;rowCount<rows.size();rowCount++) {
                            qm = new QuestionMaster();
                            qm.setSubTopicMaster(questionBankMaster.getSubTopicMaster());
                            qm.setQuestionTypeMaster(qtm);
                            qm.setDifficultyTypeMaster(dtm);
                            qm.setWeightage(marks);
                            qm.setParentQuestionMaster(null);
                            fb.add(qm);
                            qm.setText(rows.get(rowCount).select("td").get(1).html());
                            AnswerMaster a = new AnswerMaster();
                            a.setText(rows.get(rowCount).select("td").get(2).html());
                            a.setCorrect(true);
                            qm.addAnswerMaster(a);
                        }
                    }
            }

    }
        int k=0;
        for(Object u:allUnits){
            System.out.println("Unit "+k);
            int l=0;
            LinkedList questionBanks = (LinkedList) u;
            for(Object qBank:questionBanks){
                System.out.println("Question type "+l);
                LinkedList questions = (LinkedList) qBank;
                int m =0;
                for(Object ques:questions){
                    QuestionMaster qm = (QuestionMaster) ques;
                    System.out.println("actual question "+m + qm);
                    questionMasterService.save(qm);
                    m++;
                }
                l++;
            }
            k++;
        }

        }
    private File bytesToFile(QuestionBankMaster questionBankMaster){
        byte[] demBytes = questionBankMaster.getQuestionBankFile(); //instead of null, specify your bytes here.
        UUID uuid=UUID.randomUUID();
        File outputFile = new File((new File(System.getProperty("java.io.tmpdir"))).getAbsolutePath()+"/"+uuid.toString());
        try (FileOutputStream outputStream = new FileOutputStream(outputFile); ) {
            outputStream.write(demBytes);  //write the bytes and your done.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
    }
    private Set test(int noOfQues) {
        final Random r = new Random();
        final Set<Integer> s = new HashSet<>();
        for(int i = 0; i < noOfQues; i++){
            while(true) {
                int num = r.nextInt(6) + 1;
                if (s.contains(num) == false) {
                    s.add(num);
                    break;
                }
            }
        }
        return s;
    }
}
