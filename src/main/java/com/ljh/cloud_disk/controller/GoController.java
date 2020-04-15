package com.ljh.cloud_disk.controller;

import com.ljh.cloud_disk.until.FileTraversal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
        FileTraversal fileTraversal=new FileTraversal();
        model.addAttribute("fileList",fileTraversal.FileTraversal(path));
        model.addAttribute("path","/"+request.getSession().getAttribute("userId").toString()+"upload");
        return "userMain";
    }
    @RequestMapping("toTest")
    public String toTest(){
        return "test";
    }
}
