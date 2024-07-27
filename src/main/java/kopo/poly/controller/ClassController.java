package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import kopo.poly.service.IClassService;
import org.springframework.web.bind.annotation.GetMapping;
@Slf4j
@Controller
public class ClassController {

//    private final IClassService classService;


    // 컨트롤러 파트

    //동화선택창1 띄우는 컨트롤러
    @GetMapping(value = "/selectone")
    public String selectone() {
        log.info(this.getClass().getName() + ".selectone");

        return "/IDOLessonSelection1.html";
    }


    //동화선택창2 띄우는 컨트롤러
    @GetMapping(value = "/selecttwo")
    public String selecttwo() {
        log.info(this.getClass().getName() + ".selecttwo");

        return "/IDOLessonSelection2.html";
    }


    //동화선택창3 띄우는 컨트롤러
    @GetMapping(value = "/selectthr")
    public String selectthr() {
        log.info(this.getClass().getName() + ".selectthr");

        return "/IDOLessonSelection3.html";
    }

}
