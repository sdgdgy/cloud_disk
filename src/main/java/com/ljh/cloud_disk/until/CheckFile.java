package com.ljh.cloud_disk.until;

import com.sun.deploy.net.HttpRequest;

import java.io.File;

public class CheckFile {
    public String CheckFile(HttpRequest httpRequest,String path,String filename) {
        File file=new File(path,filename);
        if (file.exists()) {
            return "false";
        }else{
            return "true";
        }
    }
}
