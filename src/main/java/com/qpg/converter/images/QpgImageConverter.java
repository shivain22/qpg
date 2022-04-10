package com.qpg.converter.images;

import com.qpg.converter.internal.html.Html;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.wmf.tosvg.WMFTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.qpg.converter.internal.util.Lists.list;

public class QpgImageConverter implements ImageConverter.ImgElement{

    String imgLocation;

    public QpgImageConverter(String imgLocation){
        this.imgLocation = imgLocation;
    }
    @Override
    public Map<String, String> convert(Image image) throws IOException {
        Map<String,String> imgElement = new HashMap<>();
        String imageName = image.getImageName().get();
            File file = new File(imgLocation+imageName.substring(imageName.lastIndexOf("/")));
            try (OutputStream output = new FileOutputStream(file, false)) {
                byte[] buffer = new byte[image.getInputStream().available()];
                image.getInputStream().read(buffer);
                OutputStream outStream = new FileOutputStream(file);
                outStream.write(buffer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(image.getContentType().contains("x-wmf")){
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
            imgElement.put("src",src);

        image.getAltText().ifPresent(altText -> imgElement.put("alt", altText));
        return imgElement;
    }
}
