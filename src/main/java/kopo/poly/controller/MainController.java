package kopo.poly.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    // 메인창 띄우는 컨트롤러
    @GetMapping("/main")
    public String mainPage() throws Exception {
        log.info(this.getClass().getName() + ".main 페이지 보여주는 함수 실행");
        return "/IDOindex";
    }

    @GetMapping("/redirect")
    public String redirectPage(ModelMap modelMap) throws Exception {
        modelMap.addAttribute("msg", "Hi");
        modelMap.addAttribute("url", "/main");
        return "/redirect:/main";
    }
}