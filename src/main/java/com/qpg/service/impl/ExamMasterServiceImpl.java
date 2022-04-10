package com.qpg.service.impl;

import com.qpg.domain.*;
import com.qpg.service.*;
import com.qpg.repository.ExamMasterRepository;
import org.apache.commons.io.FileUtils;
import org.docx4j.convert.in.xhtml.FormattingOption;
import org.docx4j.convert.in.xhtml.XHTMLImporter;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.RFonts;
import org.opendope.questions.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.DivToSdt;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.convert.out.html.SdtTagHandler;
import org.docx4j.convert.out.html.SdtWriter;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.SdtPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

/**
 * Service Implementation for managing {@link ExamMaster}.
 */
@Service
@Transactional
public class ExamMasterServiceImpl implements ExamMasterService {

    private final Logger log = LoggerFactory.getLogger(ExamMasterServiceImpl.class);

    private static int VSA= 7;
    private static int SA = 8;
    private static int LA = 9;
    private final ExamMasterRepository examMasterRepository;

    @Autowired
    ExamQuestionPaperMasterService examQuestionPaperMasterService;

    @Autowired
    ExamQuestionPaperDetailService examQuestionPaperDetailService;

    @Autowired
    QuestionMasterService questionMasterService;

    @Autowired
    QuestionBluePrintMasterService questionBluePrintMasterService;

    @Autowired
    ConfigMasterService configMasterService;

    public ExamMasterServiceImpl(ExamMasterRepository examMasterRepository) {
        this.examMasterRepository = examMasterRepository;
    }

    @Override
    public ExamMaster save(ExamMaster examMaster) {
        log.debug("Request to save ExamMaster : {}", examMaster);
        return examMasterRepository.save(examMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamMaster> findAll(Pageable pageable) {
        log.debug("Request to get all ExamMasters");
        return examMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExamMaster> findOne(Long id) {
        log.debug("Request to get ExamMaster : {}", id);
        return examMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamMaster : {}", id);
        examMasterRepository.deleteById(id);
    }

    public void createQuestionPaper(ExamMaster examMaster){
        log.debug("Request to create questions for ExamMaster : {}", examMaster.getId());
        QuestionBluePrintMaster qbpm = questionBluePrintMasterService.findOne(examMaster.getQuestionBluePrintMaster().getId()).get();
        ExamQuestionPaperMaster eqpm = new ExamQuestionPaperMaster();
        Long qpNo = examQuestionPaperMasterService.countByExamMaster(examMaster);
        eqpm.setExamMaster(examMaster);
        eqpm.setFileName(configMasterService.findOne(1l).get().getValue()+"/"+examMaster.getTitle()+"_"+examMaster.getId()+"_QpNo_"+String.valueOf(qpNo)+".htm");
        eqpm.setName(examMaster.getTitle()+" - ("+String.valueOf(qpNo)+")");
        String selectedQuestions = "";
        List<Long> selectedQuestionsList = new ArrayList<Long>();

        List<List<QuestionMaster>> laMostEasy = new ArrayList<>();
        List<List<QuestionMaster>> laEasy = new ArrayList<>();
        List<List<QuestionMaster>> laMedium = new ArrayList<>();
        List<List<QuestionMaster>> laDifficult = new ArrayList<>();
        List<List<QuestionMaster>> laMostDifficult = new ArrayList<>();
        for(QuestionBluePrintDetail qbpdtls:qbpm.getQuestionBluePrintDetails()){
            List<QuestionMaster> randomQuestions =new ArrayList<>();// questionMasterService.findRandomQuestions(qbpdtls.getQuestionTypeMaster().getId(),qbpdtls.getTotalQuestions());
            ExamQuestionPaperDetail eqpdtl=null;
            if(qbpdtls.getQuestionTypeMaster().getId()==LA ){
                if(qbpdtls.getQuestionChoiceMaster().getShortName().equals("CE")) {
                    for (int i = 0; i < qbpdtls.getTotalQuestions(); i++) {
                            eqpdtl = new ExamQuestionPaperDetail();
                            eqpdtl.setExamQuestionPaperMaster(eqpm);
                            eqpdtl.setChoice("CE");
                            eqpm.addExamQuestionPaperDetails(eqpdtl);
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Most Easy")) {
                                randomQuestions = questionMasterService.findRandomChoiceQuestionsH2(LA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 1,examMaster);
                                for (QuestionMaster q : randomQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                    eqpdtl.setQuestionMaster(q);
                                }
                                eqpdtl.setComboQuestions(randomQuestions);
                                List<QuestionMaster> randomChoiceQuestions = questionMasterService.findRandomChoiceQuestionsH2(LA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 1,examMaster);
                                for (QuestionMaster q : randomChoiceQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                }
                                eqpdtl.setComboChoiceQuestions(randomChoiceQuestions);
                            }
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Easy")) {
                                randomQuestions = questionMasterService.findRandomChoiceQuestionsH2(SA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 2,examMaster);
                                for (QuestionMaster q : randomQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                    eqpdtl.setQuestionMaster(q);
                                }
                                eqpdtl.setComboQuestions(randomQuestions);
                                List<QuestionMaster> randomChoiceQuestions = questionMasterService.findRandomChoiceQuestionsH2(SA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 2,examMaster);
                                eqpdtl.setComboChoiceQuestions(randomChoiceQuestions);
                            }
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Medium")) {
                                randomQuestions = questionMasterService.findRandomChoiceQuestionsH2(VSA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 4,examMaster);
                                for (QuestionMaster q : randomQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                    eqpdtl.setQuestionMaster(q);
                                }
                                eqpdtl.setComboQuestions(randomQuestions);
                                List<QuestionMaster> randomChoiceQuestions = questionMasterService.findRandomChoiceQuestionsH2(VSA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 4,examMaster);
                                eqpdtl.setComboChoiceQuestions(randomChoiceQuestions);
                            }
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Difficult")) {
                                randomQuestions = questionMasterService.findRandomChoiceQuestionsH2(SA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 1,examMaster);
                                for (QuestionMaster q : randomQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                    eqpdtl.setQuestionMaster(q);
                                }
                                eqpdtl.setComboQuestions(randomQuestions);
                                List<QuestionMaster>randomQuestions1 = questionMasterService.findRandomChoiceQuestionsH2(VSA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 2,examMaster);
                                for (QuestionMaster q : randomQuestions1) {
                                    selectedQuestionsList.add(q.getId());
                                }
                                eqpdtl.getComboQuestions().addAll(randomQuestions1);
                                List<QuestionMaster> randomChoiceQuestions = questionMasterService.findRandomChoiceQuestionsH2(SA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 1,examMaster);
                                eqpdtl.setComboChoiceQuestions(randomChoiceQuestions);
                                List<QuestionMaster> randomChoiceQuestions1 = questionMasterService.findRandomChoiceQuestionsH2(VSA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 2,examMaster);
                                eqpdtl.getComboChoiceQuestions().addAll(randomChoiceQuestions1);
                            }
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Most Difficult")) {

                            }
                            randomQuestions = new ArrayList<>();
                    }
                }else if(qbpdtls.getQuestionChoiceMaster().getShortName().equals("CB")){
                    for (int i = 0; i < qbpdtls.getTotalQuestions()+qbpdtls.getNumOfChoices(); i++) {
                        if (qbpdtls.getDifficultyTypeMaster().getName().equals("Most Easy")) {
                            randomQuestions = new ArrayList<>();
                            eqpdtl = new ExamQuestionPaperDetail();
                            eqpdtl.setExamQuestionPaperMaster(eqpm);
                            eqpm.addExamQuestionPaperDetails(eqpdtl);
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Most Easy")) {
                                List<QuestionMaster> randomChoiceQuestions = questionMasterService.findRandomChoiceQuestionsH2(LA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 1,examMaster);
                                for (QuestionMaster q : randomChoiceQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                    eqpdtl.setQuestionMaster(q);
                                }
                                eqpdtl.setComboQuestions(randomChoiceQuestions);
                                eqpdtl.setChoice("CB");
                            }
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Easy")) {
                                List<QuestionMaster> randomChoiceQuestions = questionMasterService.findRandomChoiceQuestionsH2(SA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 2,examMaster);
                                for (QuestionMaster q : randomChoiceQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                    eqpdtl.setQuestionMaster(q);
                                }
                                eqpdtl.setComboQuestions(randomChoiceQuestions);
                                eqpdtl.setChoice("CB");
                            }
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Medium")) {
                                List<QuestionMaster> randomChoiceQuestions = questionMasterService.findRandomChoiceQuestionsH2(VSA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 4,examMaster);
                                for (QuestionMaster q : randomChoiceQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                    eqpdtl.setQuestionMaster(q);
                                }
                                eqpdtl.setComboQuestions(randomChoiceQuestions);
                                eqpdtl.setChoice("CB");
                            }
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Difficult")) {
                                List<QuestionMaster> randomChoiceQuestions = questionMasterService.findRandomChoiceQuestionsH2(SA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 1,examMaster);
                                for (QuestionMaster q : randomChoiceQuestions) {
                                    selectedQuestionsList.add(q.getId());
                                    eqpdtl.setQuestionMaster(q);
                                }
                                eqpdtl.setComboQuestions(randomChoiceQuestions);
                                eqpdtl.setChoice("CB");
                                List<QuestionMaster> randomChoiceQuestions1 = questionMasterService.findRandomChoiceQuestionsH2(VSA, selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]), 2,examMaster);
                                eqpdtl.getComboQuestions().addAll(randomChoiceQuestions1);
                                eqpdtl.setChoice("CB");
                            }
                            if (qbpdtls.getDifficultyTypeMaster().getName().equals("Most Difficult")) {

                            }
                            randomQuestions = new ArrayList<>();
                        }
                    }
                }
            }else{
                randomQuestions = questionMasterService.findRandomChoiceQuestionsH2(qbpdtls.getQuestionTypeMaster().getId(),selectedQuestionsList.toArray(new Long[selectedQuestionsList.size()]),qbpdtls.getTotalQuestions(),examMaster);
                for(QuestionMaster qm: randomQuestions){
                    eqpdtl = new ExamQuestionPaperDetail();
                    eqpdtl.setExamQuestionPaperMaster(eqpm);
                    eqpdtl.setQuestionMaster(qm);
                    eqpdtl.setChoice("NC");
                    //selectedQuestions+=qm.getId().toString()+",";
                    //selectedQuestionsList.add(qm.getId());
                    eqpm.addExamQuestionPaperDetails(eqpdtl);
                }
            }



        }
        eqpm = examQuestionPaperMasterService.save(eqpm);
        eqpm = examQuestionPaperMasterService.findOne(eqpm.getId()).get();
        try {
            File f = new File(configMasterService.findOne(1l).get().getValue()+"/"+examMaster.getTitle()+"_"+examMaster.getId()+"_QpNo_"+String.valueOf(qpNo)+".htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));

            File f1 = new File(configMasterService.findOne(1l).get().getValue()+"/"+examMaster.getTitle()+"_"+examMaster.getId()+"_QpNo_"+String.valueOf(qpNo)+"_key"+".htm");
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(f1));


            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            String totalDuration=minuteToTime(examMaster.getQuestionBluePrintMaster().getPartaDuration()+ examMaster.getQuestionBluePrintMaster().getPartbDuration());
            String htmlStartTag = "<html>";
            String bodyStartTag = "<body>";
            String htmlEndTag = "</html>";
            String bodyEndTag = "</body>";
            String spacer= "<br />";


            String questionPaperHeader = "";
            questionPaperHeader+="<table style=\"width: 100%; border: 0px; text-align: center;\" >";
            questionPaperHeader +="<tr><td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold;\" colspan=\"3\">"+examMaster.getCollegeMaster().getName()+"</td></tr>";
            questionPaperHeader +="<tr><td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold;\" colspan=\"3\">"+examMaster.getDepartmentMaster().getName().replace("&","&nbsp;")+"</td></tr>";
            questionPaperHeader +="<tr><td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold;\" colspan=\"3\">"+examMaster.getTitle()+ " - "+examMaster.getCategoryMaster().getName()+""+examMaster.getSubCategoryMaster().getName()+" "+examMaster.getStartDate()+"</td></tr>";
            questionPaperHeader +="<tr><td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold;\" colspan=\"3\">"+examMaster.getCourseMaster().getName()+"</td></tr>";
            questionPaperHeader +="<tr><td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold;\" colspan=\"3\"><hr /></td></tr>";
            questionPaperHeader +="<tr><td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\">"+examMaster.getTopicMaster().getShortCode()+" - "+examMaster.getTopicMaster().getName()+"</td>" +
                      "<td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\">Marks: "+examMaster.getQuestionBluePrintMaster().getTotalMarks()+"</td>" +
                      "<td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\">Time : "+totalDuration+"</td></tr>";
            questionPaperHeader +="<tr><td align=\"center\" colspan=\"3\"><hr /></td></tr>";
            questionPaperHeader+="</table>";

            long partaTotalMarks=0l;
            long partbTotalMarks=0l;

            long partaTotalDuration=0l;
            long partbTotalDuration=0l;

            String partaDuration="";
            String partbDuration ="";

            String partaHead="";
            String partbHead="";
            for(QuestionBluePrintDetail qtm: examMaster.getQuestionBluePrintMaster().getQuestionBluePrintDetails()){
                if(qtm.getQuestionTypeMaster().getQuestionTypeCategoryMaster().getShortName().equals("OBJ")){
                    partaHead = qtm.getQuestionTypeMaster().getQuestionTypeCategoryMaster().getName();
                    partaTotalMarks+=qtm.getTotalQuestions()*qtm.getQuestionTypeMaster().getDefaultWeightage();
                    partaTotalDuration+=qtm.getTotalQuestions()*qtm.getQuestionTypeMaster().getDefaultDuration();
                }else if(qtm.getQuestionTypeMaster().getQuestionTypeCategoryMaster().getShortName().equals("SUB")){
                    partbTotalMarks+=qtm.getTotalQuestions()*qtm.getQuestionTypeMaster().getDefaultWeightage();
                    partbTotalDuration+=qtm.getTotalQuestions()*qtm.getQuestionTypeMaster().getDefaultDuration();
                    partbHead = qtm.getQuestionTypeMaster().getQuestionTypeCategoryMaster().getName();
                }
            }
            partaDuration = secondsToTime(partaTotalDuration);
            partbDuration = secondsToTime(partbTotalDuration);

            String parta="<table style=\"width: 100%; border: 0px; text-align: center;\" ><tr><td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>"+partaHead+"</b></td><td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Marks: "+partaTotalMarks+"</b></td><td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Time: "+partaDuration+"</b></td></tr></table>";
            String partb="<table style=\"width: 100%; border: 0px; text-align: center;\" ><tr><td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>"+partbHead+"</b></td><td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Marks: "+partbTotalMarks+"</b></td><td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Time: "+partbDuration+"</b></td></tr></table>";

            int slNo=0;
            String ansA="";
            String ansB ="";
            String ansC ="";
            String ansD = "";

            String ansAkey="";
            String ansBkey ="";
            String ansCkey ="";
            String ansDkey = "";

            String mcsa ="";
            String fib  ="";
            String mtf = "";

            String mcsakey ="";
            String fibkey  ="";
            String mtfkey = "";

            String vsa = "";
            String sa ="";
            String la = "";
            String vla ="";
            String sac ="";
            String lac ="";
            String vlac= "";

            List<String> mtfAns = new LinkedList<>();
            List<String> mtfq = new LinkedList<>();

            List<String> mtfAnskey = new LinkedList<>();
            List<String> mtfqkey = new LinkedList<>();

            int fibSlno = 1;
            int fibKeySlno = 1;
            int laSlno =1;
            int saSlno=1;
            int vsaSlno =1;
            int vlaSlno =1;
            int sacSlno =1;
            int lacSlno =1;
            int vlacSlno =1;
            int mtfkeySlno=1;
            int mcsakeyslNo=1;
            for(ExamQuestionPaperDetail eqpdtl: eqpm.getExamQuestionPaperDetails()){
                if(eqpdtl.getQuestionMaster().getQuestionTypeMaster().getShortName().equals("FIB")){
                    fib+="<tr><td valign=\"top\">"+fibSlno+"</td><td>"+eqpdtl.getQuestionMaster().getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","")+"</td></tr>";
                    fibSlno++;
                }
                else if(eqpdtl.getQuestionMaster().getQuestionTypeMaster().getShortName().equals("MCSA")){
                    mcsa+="<tr><td valign=\"top\">"+(slNo+1)+"</td><td colspan=\"4\">"+eqpdtl.getQuestionMaster().getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","")+"</td></tr>";
                    int j =0;
                    for(AnswerMaster a:eqpdtl.getQuestionMaster().getAnswerMasters()){
                        if(j==0){
                            ansA=a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        if(j==1){
                            ansB=a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        if(j==2){
                            ansC=a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        if(j==3){
                            ansD=a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        j++;
                    }
                    mcsa+="<tr><td valign=\"top\"></td><td valign=\"top\">(a)</td><td valign=\"top\">"+ansA+"</td><td valign=\"top\">(b)</td><td valign=\"top\">"+ansB+"</td></tr>";
                    mcsa+="<tr><td valign=\"top\"></td><td valign=\"top\">(c)</td><td valign=\"top\">"+ansC+"</td><td valign=\"top\">(d)</td><td valign=\"top\">"+ansD+"</td></tr>";
                    slNo++;
                }

                else if(eqpdtl.getQuestionMaster().getQuestionTypeMaster().getShortName().equals("MTF")){
                       mtfq.add(eqpdtl.getQuestionMaster().getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>",""));
                       for(AnswerMaster a :eqpdtl.getQuestionMaster().getAnswerMasters()){
                           mtfAns.add(a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>",""));
                       }
                }
            }

            for(ExamQuestionPaperDetail eqpdtl: eqpm.getExamQuestionPaperDetails()){
                if(eqpdtl.getQuestionMaster().getQuestionTypeMaster().getShortName().equals("FIB")){
                    String ans="";
                    for(AnswerMaster am : eqpdtl.getQuestionMaster().getAnswerMasters()){
                        ans+= am.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                    }
                    fibkey+="<tr><td valign=\"top\">"+fibKeySlno+"</td><td>"+eqpdtl.getQuestionMaster().getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","")+"</td><td><b>"+ans+"</b></td></tr>";
                    fibKeySlno++;
                }
                else if(eqpdtl.getQuestionMaster().getQuestionTypeMaster().getShortName().equals("MCSA")){
                    mcsakey+="<tr><td valign=\"top\">"+(mcsakeyslNo)+"</td><td colspan=\"4\">"+eqpdtl.getQuestionMaster().getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","")+"</td></tr>";
                    String ans="";
                    /*for(AnswerMaster am : eqpdtl.getQuestionMaster().getAnswerMasters()){
                        ans+= am.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                    }*/
                    int j =0;
                    for(AnswerMaster a:eqpdtl.getQuestionMaster().getAnswerMasters()){
                        if(a.isCorrect()){
                            ans = a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        System.out.println(a.isCorrect()+"==="+ a.getText());
                        if(j==0){
                            ansAkey=a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        if(j==1){
                            ansBkey=a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        if(j==2){
                            ansCkey=a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        if(j==3){
                            ansDkey=a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>","");
                        }
                        j++;
                    }
                    mcsakey+="<tr><td valign=\"top\"></td><td valign=\"top\">(a)</td><td valign=\"top\">"+ansAkey+"</td><td valign=\"top\">(b)</td><td valign=\"top\">"+ansBkey+"</td></tr>";
                    mcsakey+="<tr><td valign=\"top\"></td><td valign=\"top\">(c)</td><td valign=\"top\">"+ansCkey+"</td><td valign=\"top\">(d)</td><td valign=\"top\">"+ansDkey+"</td></tr>";
                    mcsakey+="<tr><td valign=\"top\"></td><td valign=\"top\"> </td><td valign=\"top\"><b>Correct Answer</b></td><td valign=\"top\">(d)</td><td valign=\"top\"><b>"+ans+"</b></td></tr>";
                    mcsakeyslNo++;
                }

                else if(eqpdtl.getQuestionMaster().getQuestionTypeMaster().getShortName().equals("MTF")){
                    mtfqkey.add(eqpdtl.getQuestionMaster().getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>",""));
                    for(AnswerMaster a :eqpdtl.getQuestionMaster().getAnswerMasters()){
                        mtfAnskey.add(a.getText().replaceAll("&"+"nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>","").replaceAll("</p>",""));
                    }
                }
            }
            for(int i=0;i<mtfq.size();i++){
                mtfkey+="<tr><td valign=\"top\">"+mtfkeySlno+"</td><td valign=\"top\">"+mtfqkey.get(i)+"</td><td><b>"+mtfAnskey.get(i)+"</b></td></tr>";
                mtfkeySlno++;
            }

            String [] subQuestions = new String[]{"a","b","c","d","e","f","g","h"};
            for(ExamQuestionPaperDetail eqpdtl: eqpm.getExamQuestionPaperDetails()) {
                if (eqpdtl.getComboQuestions()!=null && eqpdtl.getComboQuestions().size()>0 &&  (eqpdtl.getChoice().equals("NC") || eqpdtl.getChoice().equals("CE")) ) {
                    String laSlNoStr = String.valueOf(laSlno);
                    int subCount=0;
                    if(eqpdtl.getComboQuestions()!=null && eqpdtl.getComboQuestions().size()>0){
                        for(QuestionMaster qm:eqpdtl.getComboQuestions()) {
                            if (eqpdtl.getComboQuestions()!=null) {
                                laSlNoStr = String.valueOf(laSlno)+" ("+subQuestions[subCount]+")";
                            }
                            la += "<tr><td valign=\"top\" width=\"20%\">" + laSlNoStr + "</td><td valign=\"top\">" + qm.getText().replaceAll("&" + "nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>", "").replaceAll("</p>", "") + "</td><td>"+qm.getWeightage()+"</td></tr>";
                            subCount++;
                        }
                    }
                    la += "<tr><td valign=\"top\" width=\"20%\">OR</td><td valign=\"top\"></td></tr>";
                    if (eqpdtl.getComboChoiceQuestions()!=null) {
                        laSlno++;
                        laSlNoStr = String.valueOf(laSlno);
                        //subCount=0;
                        if(eqpdtl.getComboChoiceQuestions()!=null && eqpdtl.getComboChoiceQuestions().size()>0){
                            for(QuestionMaster qm:eqpdtl.getComboChoiceQuestions()) {
                                if (eqpdtl.getChoice().equals("CE") && eqpdtl.getComboChoiceQuestions()!=null) {
                                    laSlNoStr = String.valueOf(laSlno)+" ("+subQuestions[subCount]+")";
                                }
                                la += "<tr><td valign=\"top\" width=\"20%\">" + laSlNoStr + "</td><td valign=\"top\">" + qm.getText().replaceAll("&" + "nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>", "").replaceAll("</p>", "") + "</td><td>"+qm.getWeightage()+"</td></tr>";
                                subCount++;
                            }
                        }
                    }
                    la += "<tr><td valign=\"top\" width=\"20%\">    </td><td valign=\"top\"></td></tr>";
                    laSlno++;
                }
            }

            for(ExamQuestionPaperDetail eqpdtl: eqpm.getExamQuestionPaperDetails()) {
                if (eqpdtl.getComboQuestions()!=null && eqpdtl.getComboQuestions().size()>0 &&  eqpdtl.getChoice().equals("CB") ) {
                    String laSlNoStr = String.valueOf(laSlno);
                    int subCount=0;
                    if(eqpdtl.getComboQuestions()!=null && eqpdtl.getComboQuestions().size()>0){
                        for(QuestionMaster qm:eqpdtl.getComboQuestions()) {
                            if (eqpdtl.getChoice().equals("CE") && eqpdtl.getComboQuestions()!=null) {
                                laSlNoStr = String.valueOf(laSlno)+" ("+subQuestions[subCount]+")";
                            }
                            la += "<tr><td valign=\"top\" width=\"20%\">" + laSlNoStr + "</td><td valign=\"top\">" + qm.getText().replaceAll("&" + "nbsp;", " ").replaceAll(String.valueOf((char) 160), " ").replaceAll("<p>", "").replaceAll("</p>", "") + "</td><td>\"+qm.getWeightage()+\"</td></tr>";
                        }
                    }
                }
            }



            Collections.shuffle(mtfq);
            Collections.shuffle(mtfAns);
            int mtfSlno =1;
            for(int i=0;i<mtfq.size();i++){
                mtf+="<tr><td valign=\"top\">"+mtfSlno+"</td><td valign=\"top\">"+mtfq.get(i)+"</td><td>"+mtfAns.get(i)+"</td></tr>";
                mtfSlno++;
            }
            String finalMcsa = "<table style=\"width: 100%; border: 0px; text-align: center;\">" +
                "<tr>" +
                "<td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>II</b> </td>" +
                "<td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Underline the correct answer</b></td>" +
                "<td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>30 X 0.5 = 15 M</b></td>" +
                "</tr>" +
                "</table>" +
                spacer+
                "<table style=\"width: 100%; border: 0px;\" >"+
                mcsa+
                "</table>";
            String finalFib =  "<table style=\"width: 100%; border: 0px; text-align: center;\">" +
                "<tr>" +
                "<td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>I</b></td>" +
                "<td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Fill in the blanks with suitable words.</b></td>" +
                "<td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>20 X 1 = 20 M</b></td>" +
                "</tr>" +
                "</table>" +
                spacer+
                "<table style=\"width: 100%; border: 0px;\" >"+
                fib+
                "</table>";
            String finalMtf = "<table style=\"width: 100%; border: 0px; text-align: center;\">" +
                "<tr>" +
                "<td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>III</b></td>" +
                "<td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Match the following</b></td>" +
                "<td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>10 X 0.5 = 5 M</b></td>" +
                "</tr>" +
                "</table> " +
                spacer+
                "<table>"+
                mtf+
                "</table>";
            String finalLa = "<table style=\"width: 100%; border: 0px; text-align: center;\">" +
                "<tr>" +
                "<td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Answer ANY FIVE Questions.</b></td>" +
                "<td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b></b></td>" +
                "<td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>5 X 12 = 60 M</b></td>" +
                "</tr>" +
                "</table> " +
                spacer+
                "<table style=\"width: 100%; border: 0px;\" >"+
                la+
                "</table>";

            String finalMcsakey = "<table style=\"width: 100%; border: 0px; text-align: center;\">" +
                "<tr>" +
                "<td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>II</b> </td>" +
                "<td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Underline the correct answer</b></td>" +
                "<td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>30 X 0.5 = 15 M</b></td>" +
                "</tr>" +
                "</table>" +
                spacer+
                "<table style=\"width: 100%; border: 0px;\" >"+
                mcsakey+
                "</table>";
            String finalFibkey =  "<table style=\"width: 100%; border: 0px; text-align: center;\">" +
                "<tr>" +
                "<td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>I</b></td>" +
                "<td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Fill in the blanks with suitable words.</b></td>" +
                "<td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>20 X 1 = 20 M</b></td>" +
                "</tr>" +
                "</table>" +
                spacer+
                "<table style=\"width: 100%; border: 0px;\" >"+
                fibkey+
                "</table>";
            String finalMtfkey = "<table style=\"width: 100%; border: 0px; text-align: center;\">" +
                "<tr>" +
                "<td style=\"border: 0px; text-align: left; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>III</b></td>" +
                "<td style=\"border: 0px; text-align: center; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>Match the following</b></td>" +
                "<td style=\"border: 0px; text-align: right; font-family: Arial, Helvetica, sans-serif;font-weight: bold; width:33%\"><b>10 X 0.5 = 5 M</b></td>" +
                "</tr>" +
                "</table> " +
                spacer+
                "<table>"+
                mtfkey+
                "</table>";

            bw.write(htmlStartTag);
                bw.write(bodyStartTag);
                    bw.write(questionPaperHeader);
                    bw.write(spacer);
                    bw.write(parta);
                    bw.write(spacer);
                    bw.write(finalFib);
                    bw.write(spacer);
                    bw.write(finalMcsa);
                    bw.write(spacer);
                    bw.write(finalMtf);
                    bw.write(spacer);
                    bw.write(finalLa);
                bw.write(bodyEndTag);
            bw.write(htmlEndTag);
            bw.close();

            bw1.write(htmlStartTag);
            bw1.write(bodyStartTag);
            bw1.write(questionPaperHeader);
            bw1.write(spacer);
            bw1.write(parta);
            bw1.write(spacer);
            bw1.write(finalFibkey);
            bw1.write(spacer);
            bw1.write(finalMcsakey);
            bw1.write(spacer);
            bw1.write(finalMtfkey);
            bw1.write(spacer);
            bw1.write(bodyEndTag);
            bw1.write(htmlEndTag);
            bw1.close();


            String baseURL = configMasterService.findOne(1l).get().getValue();//"file:///E:\\qpg\\images";
            String stringFromFile = FileUtils.readFileToString(new File(configMasterService.findOne(1l).get().getValue()+"/"+examMaster.getTitle()+"_"+examMaster.getId()+"_QpNo_"+String.valueOf(qpNo)+".htm"), "UTF-8");
            String unescaped = stringFromFile;
            RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
            rfonts.setAscii("Century Gothic");
            XHTMLImporterImpl.addFontMapping("Century Gothic", rfonts);
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
            NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
            wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
            ndp.unmarshalDefaultNumbering();
            XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
            XHTMLImporter.setHyperlinkStyle("Hyperlink");
            wordMLPackage.getMainDocumentPart().getContent().addAll(XHTMLImporter.convert(unescaped, baseURL) );
            wordMLPackage.save(new java.io.File(configMasterService.findOne(1l).get().getValue()+"/"+examMaster.getTitle()+"_"+examMaster.getId()+"_QpNo_"+String.valueOf(qpNo)+".docx") );

            String baseURL1 = configMasterService.findOne(1l).get().getValue();//"file:///E:\\qpg\\images";
            String stringFromFile1 = FileUtils.readFileToString(new File(configMasterService.findOne(1l).get().getValue()+"/"+examMaster.getTitle()+"_"+examMaster.getId()+"_QpNo_"+String.valueOf(qpNo)+"_key"+".htm"), "UTF-8");
            String unescaped1 = stringFromFile1;
            RFonts rfonts1 = Context.getWmlObjectFactory().createRFonts();
            rfonts1.setAscii("Century Gothic");
            XHTMLImporterImpl.addFontMapping("Century Gothic", rfonts1);
            WordprocessingMLPackage wordMLPackage1 = WordprocessingMLPackage.createPackage();
            NumberingDefinitionsPart ndp1 = new NumberingDefinitionsPart();
            wordMLPackage1.getMainDocumentPart().addTargetPart(ndp1);
            ndp1.unmarshalDefaultNumbering();
            XHTMLImporterImpl XHTMLImporter1 = new XHTMLImporterImpl(wordMLPackage1);
            XHTMLImporter1.setHyperlinkStyle("Hyperlink");
            wordMLPackage1.getMainDocumentPart().getContent().addAll(XHTMLImporter1.convert(unescaped1, baseURL1) );
            wordMLPackage1.save(new java.io.File(configMasterService.findOne(1l).get().getValue()+"/"+examMaster.getTitle()+"_"+examMaster.getId()+"_QpNo_"+String.valueOf(qpNo)+"_key"+".docx") );


        }catch(FileNotFoundException ffe){
            ffe.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


    }

    public static String minuteToTime(long minute) {
        long hour = minute / 60;
        minute %= 60;
        String p = "AM";
        if (hour >= 12) {
            hour %= 12;
            p = "PM";
        }
        if (hour == 0) {
            hour = 12;
        }
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + " " + p;
    }

    public static String secondsToTime(long seconds) {
        long minute = seconds/60;
        long hour = minute / 60;
        minute %= 60;
        String p = "AM";
        if (hour >= 12) {
            hour %= 12;
            p = "PM";
        }
        if (hour == 0) {
            hour = 12;
        }
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) ;
    }
}
