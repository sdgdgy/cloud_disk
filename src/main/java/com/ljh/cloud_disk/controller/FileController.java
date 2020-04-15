package com.ljh.cloud_disk.controller;

import com.ljh.cloud_disk.until.FileDelete;
import com.ljh.cloud_disk.until.FileTraversal;
import com.ljh.cloud_disk.until.NewFolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping("upload")
    public String  upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,String path,Model model) throws IOException {
        //上传路径保存设置
        String path1 = request.getServletContext().getRealPath(path);
        File realPath = new File(path1);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        //上传文件地址
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new File(realPath +"/"+ file.getOriginalFilename()));
        FileTraversal fileTraversal=new FileTraversal();
        model.addAttribute("fileList",fileTraversal.FileTraversal(path1));
        model.addAttribute("path",path);
        return "userMain";
    }
    @RequestMapping(value="/download")
    public void downloads(HttpServletResponse response , HttpServletRequest request,String filepath,String filename) throws Exception{
        //要下载的图片地址
        String  path = request.getSession().getServletContext().getRealPath(filepath);
        String  fileName = filename;

        //1、设置response 响应头
        response.reset(); //设置页面不缓存,清空buffer
        response.setCharacterEncoding("UTF-8"); //字符编码
        response.setContentType("multipart/form-data"); //二进制传输数据
        //设置响应头
        response.setHeader("Content-Disposition",
                "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));

        File file = new File(path,fileName);
        //2、 读取文件--输入流
        InputStream input=new FileInputStream(file);
        //3、 写出文件--输出流
        OutputStream out = response.getOutputStream();

        byte[] buff =new byte[1024];
        int index=0;
        //4、执行 写出操作
        while((index= input.read(buff))!= -1){
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
//        return "redirect:/go/toIndex";
    }
    @RequestMapping("filepath")
    public String filepath(HttpSession httpSession, String path, Model model, HttpServletRequest request){
//        httpSession.setAttribute("filepath",httpSession.getAttribute("filepath").toString()+"/"+path);
        String filepath=request.getServletContext().getRealPath(path);
        FileTraversal fileTraversal=new FileTraversal();
        model.addAttribute("fileList",fileTraversal.FileTraversal(filepath));
        model.addAttribute("path",path);
        return "userMain";
    }
    @RequestMapping("NewFolder")
    public String NewFolder(HttpServletRequest request,String path,String newFolderName,Model model){
        String filepath=request.getServletContext().getRealPath(path+"/"+newFolderName);
        NewFolder newFolder=new NewFolder();
        newFolder.NewFolder(filepath);
        FileTraversal fileTraversal=new FileTraversal();
        model.addAttribute("fileList",fileTraversal.FileTraversal(request.getServletContext().getRealPath(path)));
        model.addAttribute("path",path);
        return "userMain";
    }
    @RequestMapping("fileDelete")
    public String fileDelete(HttpServletRequest request,Model model,String path,String name){
        //调用文件删除方法
        FileDelete fileDelete=new FileDelete();
        File file=new File(request.getServletContext().getRealPath(path+"/"+name));
        fileDelete.FileDelete(file);
        FileTraversal fileTraversal=new FileTraversal();
        model.addAttribute("fileList",fileTraversal.FileTraversal(request.getServletContext().getRealPath(path)));
        model.addAttribute("path",path);
        return "userMain";
    }
}
