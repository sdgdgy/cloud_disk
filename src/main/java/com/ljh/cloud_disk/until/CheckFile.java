package com.ljh.cloud_disk.until;


import java.io.File;

public class CheckFile {
    public boolean CheckFile(String pathFileName) {
        File file=new File(pathFileName);
        if (file.exists()) {
            return true;
        }else{
            return false;
        }
    }
    public CheckFile() {
    }
}
