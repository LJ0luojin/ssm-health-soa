package com.lh.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.*;
import java.util.Map;

public class GenerateStaticHtmlUtils {

    public static void baseGenerateStaticHtml(FreeMarkerConfigurer freemarkerConfigurer,String templateName, Map<String,Object> dataMap,String outPutPath,String outputName){
        Configuration configuration = freemarkerConfigurer.getConfiguration();
        Writer out = null;
        try{
            //拿模板
            Template template = configuration.getTemplate(templateName);
            //取数据--dateMap
            //生成文本
            File file = new File(outPutPath+outputName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            template.process(dataMap,out);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(out!=null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
