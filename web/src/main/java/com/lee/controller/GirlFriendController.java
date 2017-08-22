package com.lee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.ServletWrappingController;


@Controller
@RequestMapping("/")
public class GirlFriendController {

//    @RequestMapping("chooseGirl")
//    public String gfFac() {
//        return "welcome";
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello(Model model) {
        System.out.println("-------hello world-----");
        return "welcome";
    }
}
