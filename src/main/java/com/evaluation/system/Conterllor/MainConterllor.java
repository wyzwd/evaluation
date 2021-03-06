package com.evaluation.system.Conterllor;

import com.evaluation.system.Service.fileService;
import com.evaluation.system.Service.loginService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainConterllor {

    @Autowired
    loginService loginService;

    @Autowired
    fileService fileService;

    @GetMapping("index")
    public String login(){return "login/Login";}

    @GetMapping("/add")
    public String add(){
        return "table/add";
    }

    @GetMapping("/form")
    public String form(){return "page/form";}

    @PostMapping("/Login")
    public String Login(Model model,HttpServletRequest request){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println(username+password);
        String str=loginService.login(username,password);
        if(!str.equals("登录成功")){
            model.addAttribute("msg","用户名或密码错误");
            return "login/Login";
        }
        return "index";
    }

    @PostMapping("/upfile")
    public String upfile(Model model,HttpServletRequest request) throws Exception{
        //每次上传某个excel前先将其在数据库中的对应的数据表的数据清空，此处没写，需要根据具体需要来写

        MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;


        MultipartFile file= multipartHttpServletRequest.getFile("filename");
        String filename=file.getOriginalFilename();
        String type=filename.substring(filename.indexOf(".")+1);
        if(!type.equals("xls")||type.equals("xlsx")){
            model.addAttribute("msg","文件类型只能是xls或者xlsx格式");
            return "";
        }
        if(file.isEmpty()){
            model.addAttribute("msg","文件不能为空");
            return "";
        }
        InputStream inputStream=file.getInputStream();
        List<List<Object>> list=fileService.getExcel(inputStream,file.getOriginalFilename());

        return "";
    }

}
