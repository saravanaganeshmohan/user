package com.bank.user.util;


import com.bank.user.application.user.command.UserCommand;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.nio.file.Files;

public class CommonUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    public static String PDFPasswordProtect(String filePath,String password){
        log.info("Inside PDFPasswordProtect start");
        try {
            File file = new File(filePath);
            PDDocument pdd = PDDocument.load(Files.readAllBytes(file.toPath()));
            AccessPermission ap = new AccessPermission();
            StandardProtectionPolicy stpp = new StandardProtectionPolicy(password, password, ap);
            stpp.setEncryptionKeyLength(128);
            stpp.setPermissions(ap);
            pdd.protect(stpp);
            pdd.save(filePath);
            pdd.close();
            log.info("Inside PDFPasswordProtect end");
            return file.getAbsolutePath();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public static String getPDFPassword(String name,String dob){
        return (name.substring(0,4).toUpperCase()+dob.substring(0,4));
    }
}
