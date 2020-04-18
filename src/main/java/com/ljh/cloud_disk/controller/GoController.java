package com.ljh.cloud_disk.controller;

import com.ljh.cloud_disk.until.FileTraversal;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping("/go")
public class GoController {
    @RequestMapping("toIndex")
    public String toIndex(){
        return "index";
    }
    @RequestMapping("toRegister")
    public String toRegister(){
        return "register";
    }
    @RequestMapping("toUserMain")
    public String toUserMain(Model model, HttpServletRequest request){
        String path=request.getServletContext().getRealPath("/"+request.getSession().getAttribute("userId").toString()+"upload");
        Long size=FileUtils.sizeOfDirectory(new File(request.getServletContext().getRealPath("/"+request.getSession().getAttribute("userId").toString()+"upload")));
        FileTraversal fileTraversal=new FileTraversal();
        //文件路径model
        model.addAttribute("fileList",fileTraversal.FileTraversal(path));
        model.addAttribute("path","/"+request.getSession().getAttribute("userId").toString()+"upload");
        //进度条model
        model.addAttribute("size",String.format("%.2f", size.doubleValue()/1024/1024));
        model.addAttribute("percentage",String.format("%.2f", size.doubleValue()/1024/1024/30*100));
        //路径导航model
        String navigation="/"+request.getSession().getAttribute("userId").toString()+"upload";
        String[] navigationArr = navigation.split("/");
        model.addAttribute("navigationArr",navigationArr);
        return "userMain";
    }
    @RequestMapping("to404")
    public String to404(){
        return "404";
    }
}
