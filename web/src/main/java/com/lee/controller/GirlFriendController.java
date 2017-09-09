package com.lee.controller;

import com.lee.entity.GirlFriend;
import com.lee.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Controller
@RequestMapping("/")
public class GirlFriendController {
    @Autowired
    private GirlService girlService;

//    @Autowired
//    private OrderStatusServiceInterface orderStatusService;

//    @RequestMapping("chooseGirl")
//    public String gfFac() {
//        return "welcome";
//    }

    @RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public String aaa(Model model, HttpServletRequest request) {
        System.out.println("-------hello world-----");
//        orderStatusService.statusChange("","","");
        model.addAttribute("mod", "mod1");
        request.setAttribute("req", "req1");
        return "welcome";
    }

    @RequestMapping(value = "/dak", method = RequestMethod.GET)
    public String hello(Model model) {
        System.out.println("-------hello world-----");
//        orderStatusService.statusChange("","","");
        return "/dak/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void serveGirl(GirlFriend girlFriend, HttpServletResponse response) throws Exception {
//        System.out.println(girlFriend.getName());
        girlService.serve(girlFriend);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print("{\"success\": \"true\"}");
        pw.flush();
        pw.close();
    }

}
