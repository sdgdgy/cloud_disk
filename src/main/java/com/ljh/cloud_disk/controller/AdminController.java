package com.ljh.cloud_disk.controller;

import com.ljh.cloud_disk.pojo.User;
import com.ljh.cloud_disk.service.UserService;
import com.ljh.cloud_disk.service.UserServiceImpl;
import com.ljh.cloud_disk.until.FileTraversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @PostMapping("login")
    public String login(String id, String password, HttpSession httpSession, HttpServletRequest httpServletRequest, Model model){
        User user=new User(id,password,0);
        try {
            List<User> list=userService.verifyUser(user);
            httpSession.setAttribute("accountNumber",list.get(0).getAccountNumber());
            if(list==null){
                return "redirect:/go/toIndex";
            }else {
                if(list.get(0).getIdentityType()==1){
                    String path=httpServletRequest.getServletContext().getRealPath("/");
                    FileTraversal fileTraversal=new FileTraversal();
                    model.addAttribute("fileList",fileTraversal.FileTraversal(path));
                    model.addAttribute("path","");
                    return "userMain";
                }else{
                    httpSession.setAttribute("userId",list.get(0).getAccountNumber());
                    String path = httpServletRequest.getServletContext().getRealPath("/"+httpSession.getAttribute("userId").toString()+"upload");
                    File realPath = new File(path);
                    if (!realPath.exists()){
                        realPath.mkdir();
                    }
                    return "redirect:/go/toUserMain";
                }
            }
        } catch (Exception e) {
            return "redirect:/go/toIndex";
        }
    }
    @PostMapping("register")
    public String register(String id, String password){
        User user=new User(id,password,0);
        userService.addUser(user);
        return "redirect:/go/toIndex";
    }
    @RequestMapping(value = "ReId",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ReId(String id){
        if(userService.queryUserById(id).size()==0){
            return "true";
        }else{
            return "false";
        }
    }
}
