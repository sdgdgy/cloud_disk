package com.ljh.cloud_disk.controller;

import com.ljh.cloud_disk.until.CheckFile;
import com.ljh.cloud_disk.until.FileDelete;
import com.ljh.cloud_disk.until.FileTraversal;
import com.ljh.cloud_disk.until.NewFolder;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping("upload")
    public String  upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,String path,Model model) throws IOException {
        String path1 = request.getServletContext().getRealPath(path);
        CheckFile checkFile=new CheckFile();
        FileTraversal fileTraversal=new FileTraversal();
        File realPath = new File(path1);
        Long size= FileUtils.sizeOfDirectory(new File(request.getServletContext().getRealPath("/"+request.getSession().getAttribute("userId").toString()+"upload")));
        if(((file.getSize()+size)/1024/1024)>30){
            model.addAttribute("msg","空间不足");
        }else {
            if(checkFile.CheckFile(path1+"/"+file.getOriginalFilename())){
                model.addAttribute("msg","文件或文件夹不能重名,或文件已在当前目录存在");
            }else {
                file.transferTo(new File(realPath +"/"+ file.getOriginalFilename()));
            }
        }
        //文件路径model
        model.addAttribute("fileList",fileTraversal.FileTraversal(path1));
        model.addAttribute("path",path);
        //进度条model
        model.addAttribute("size",String.format("%.2f", size.doubleValue()/1024/1024));
        model.addAttribute("percentage",String.format("%.2f", size.doubleValue()/1024/1024/30*100));
        //路径导航model
        String navigation=path;
        String[] navigationArr = navigation.split("/");
        model.addAttribute("navigationArr",navigationArr);
        return "userMain";
    }
    @RequestMapping(value="/download")
    public void downloads(HttpServletResponse response , HttpServletRequest request,String filepath,String filename) throws Exception{
        String  path = request.getSession().getServletContext().getRealPath(filepath);
        String  fileName = filename;
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));
        File file = new File(path,fileName);
        InputStream input=new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        byte[] buff =new byte[1024];
        int index=0;
        while((index= input.read(buff))!= -1){
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
    }
    @RequestMapping("filepath")
    public String filepath(String path, Model model, HttpServletRequest request){
        String filepath=request.getServletContext().getRealPath(path);
        FileTraversal fileTraversal=new FileTraversal();
        //文件路径model
        model.addAttribute("fileList",fileTraversal.FileTraversal(filepath));
        model.addAttribute("path",path);
        //进度条model
        Long size= FileUtils.sizeOfDirectory(new File(request.getServletContext().getRealPath("/"+request.getSession().getAttribute("userId").toString()+"upload")));
        model.addAttribute("size",String.format("%.2f", size.doubleValue()/1024/1024));
        model.addAttribute("percentage",String.format("%.2f", size.doubleValue()/1024/1024/30*100));
        //路径导航model
        String navigation=path;
        String[] navigationArr = navigation.split("/");
        model.addAttribute("navigationArr",navigationArr);
        return "userMain";
    }
    @RequestMapping("NewFolder")
    public String NewFolder(HttpServletRequest request,String path,String newFolderName,Model model){
        String filepath=request.getServletContext().getRealPath(path+"/"+newFolderName);
        FileTraversal fileTraversal=new FileTraversal();
        CheckFile checkFile=new CheckFile();
        if(checkFile.CheckFile(filepath)){
            model.addAttribute("msg","文件或文件夹不能重名,或文件已在当前目录存在");
        }else {
            NewFolder newFolder=new NewFolder();
            newFolder.NewFolder(filepath);
        }
        //文件路径model
        model.addAttribute("fileList",fileTraversal.FileTraversal(request.getServletContext().getRealPath(path)));
        model.addAttribute("path",path);
        //进度条model
        Long size= FileUtils.sizeOfDirectory(new File(request.getServletContext().getRealPath("/"+request.getSession().getAttribute("userId").toString()+"upload")));
        model.addAttribute("size",String.format("%.2f", size.doubleValue()/1024/1024));
        model.addAttribute("percentage",String.format("%.2f", size.doubleValue()/1024/1024/30*100));
        //路径导航model
        String navigation=path;
        String[] navigationArr = navigation.split("/");
        model.addAttribute("navigationArr",navigationArr);
        return "userMain";
    }
    @RequestMapping("fileDelete")
    public String fileDelete(HttpServletRequest request,Model model,String path,String name){
        FileDelete fileDelete=new FileDelete();
        File file=new File(request.getServletContext().getRealPath(path+"/"+name));
        fileDelete.FileDelete(file);
        FileTraversal fileTraversal=new FileTraversal();
        model.addAttribute("fileList",fileTraversal.FileTraversal(request.getServletContext().getRealPath(path)));
        model.addAttribute("path",path);
        Long size= FileUtils.sizeOfDirectory(new File(request.getServletContext().getRealPath("/"+request.getSession().getAttribute("userId").toString()+"upload")));
        model.addAttribute("size",String.format("%.2f", size.doubleValue()/1024/1024));
        model.addAttribute("percentage",String.format("%.2f", size.doubleValue()/1024/1024/30*100));
        //路径导航model
        String navigation=path;
        String[] navigationArr = navigation.split("/");
        model.addAttribute("navigationArr",navigationArr);
        return "userMain";
    }
}
