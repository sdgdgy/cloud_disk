package com.ljh.cloud_disk.until;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTraversal {
    public List<String> FileTraversal(String path) {
        List<String> filelist=new ArrayList<>();
//        String path = request.getServletContext().getRealPath("/upload");
        File file = new File(path);
        File[] fs = file.listFiles();
            for(File f:fs){
                filelist.add(f.getName());
            }
//        Gson gson=new Gson();
//        System.out.println(gson.toJson(filelist));
//        return gson.toJson(filelist);
        return filelist;
    }

    public FileTraversal() {
    }
}
