package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.CheckListDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;


    // 컨트롤러 파트

    // 회원가입 띄우는 컨트롤러
    @GetMapping(value = "/user/join")
    public String join() {
        log.info(this.getClass().getName() + ".user/join");

        return "/IDOSignUp";
    }


    // 로그인 띄우는 컨트롤러
    @GetMapping("/user/login")
    public String login() throws Exception {
        log.info(this.getClass().getName() + ".user/login Start!");
        log.info(this.getClass().getName() + ".user/login End!");

        return "/IDOLogin";
    }


    // 마이페이지 띄우는 컨트롤러
    @GetMapping(value = "/user/myPage")
    public String myPage() {
        log.info(this.getClass().getName() + ".user/myPage");

        return "/IDOMyProfilePage";
    }


    // 아이 체크리스트 띄우는 컨트롤러
    @GetMapping(value = "/user/checklist")
    public String checklist() {
        log.info(this.getClass().getName() + ".user/checklist");

        return "/IDOChildInfoPage";
    }


    // 아이디 찾는 페이지 띄우는 컨트롤러
    @GetMapping(value = "/user/findid")
    public String findid() {
        log.info(this.getClass().getName() + ".user/findid");

        return "/user/findid";
    }


    // 패스워드 찾는 페이지 띄우는 컨트롤러
    @GetMapping(value = "/user/findpw")
    public String findpw() {
        log.info(this.getClass().getName() + ".user/findpw");

        return "/user/findpw";
    }


    // 소개 페이지 띄우는 컨트롤러
    @GetMapping(value = "/user/intro")
    public String intro() {
        log.info(this.getClass().getName() + ".user/intro");

        return "/IDOIntro";
    }





    // 로직 파트

    // 회원가입 로직
    @PostMapping(value = "/user/joinProc")
    public String joinProc(HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".joinProc start!");
        int res;
        String msg = ""; //회원가입 결과에 대한 메시지를 전달할 변수
        String url = ""; //회원가입 결과에 대한  URL을 전달할 변수

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try {

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */
            String user_id = CmmUtil.nvl(request.getParameter("user_id")); //아이디
            String password = CmmUtil.nvl(request.getParameter("password")); //비밀번호
            String email = CmmUtil.nvl(request.getParameter("email")); //이메일
            String post_code = CmmUtil.nvl(request.getParameter("post_code")); //우편번호
            String address = CmmUtil.nvl(request.getParameter("address")); //상세주소
            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 끝!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */

            /*
             * #######################################################
             * 	 반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
             * 						반드시 작성할 것
             * #######################################################
             * */
            log.info("user_id : " + user_id);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("post_code : " + post_code);
            log.info("address : " + address);

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            //민감 정보인 이메일은 AES128-CBC로 암호화함
            pDTO.setEmail(EncryptUtil.encAES128CBC(email));

            pDTO.setPost_code(post_code);
            pDTO.setAddress(address);

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            /*
             * 회원가입
             * */
            res = userService.joinProc(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다.";
                url = "/main";
                //추후 회원가입 입력화면에서 ajax를 활용해서 아이디 중복, 이메일 중복을 체크하길 바람
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
                url = "/user/join";
            }
        } catch (DuplicateKeyException e) { //PK인 USER_ID가 중복되어 에러가 발생했다면
            msg = "이미 가입된 아이디입니다. 다른 아이디로 변경 후 다시 시도해주세요.";
            url = "/user/join";
            log.info(e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "시스템 오류로 실패하였습니다. 다시 시도해주세요.";
            url = "/user/join";
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info("출력할 메세지 : " + msg);
            log.info("이동할 경로 : " + url);
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);

            log.info(this.getClass().getName() + ".joinProc End!");
        }

        return "/redirect";
    }


    // 아이디 중복확인 로직
//    @PostMapping(value = "/user/checkIdExists")
//    @ResponseBody
//    public boolean checkIdExists(@RequestParam("userId") String userId) {
//        return userService.isIdExists(userId);
//    }
//    @ResponseBody
//    @PostMapping(value = "getUserIdExists")
//    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {
//
//        log.info(this.getClass().getName() + ".getUserIdExists Start!");
//
//        String user_id = CmmUtil.nvl(request.getParameter("user_id")); // 회원아이디
//
//        log.info("userId : " + user_id);
//
//        UserInfoDTO pDTO = new UserInfoDTO();
//        pDTO.setUser_id(user_id);
//
//        // 회원아이디를 통해 중복된 아이디인지 조회
//        UserInfoDTO rDTO = Optional.ofNullable(userService.getUserIdExists(pDTO)).orElseGet(UserInfoDTO::new);
//
//        log.info(this.getClass().getName() + ".getUserIdExists End!");
//
//        return rDTO;
//    }

    // 로그인 로직
    @PostMapping(value = "/user/loginProc")
    public String loginProc(HttpServletRequest request, ModelMap model, HttpSession session){
        log.info(this.getClass().getName()+".loginProc Start!");

        String msg=""; // 로그인 결과에 대한 메시지를 전달할 변수
        String url="";
        // 웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO=null;

        try{
            String user_id=CmmUtil.nvl(request.getParameter("user_id"));
            String password=CmmUtil.nvl(request.getParameter("password"));

            log.info("user_id : " + user_id);
            log.info("password : " + password);

            // 입력 변수 메모리에 올리기
            pDTO=new UserInfoDTO();

            pDTO.setUser_id(user_id);

            // 비번 알고리즘 복호화
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));
            // 일치하는 지 확인하기 위한 서비스호출
            UserInfoDTO rDTO=userService.getLogin(pDTO);

            // 로그인 성공시, 회원아이디 정보를 세션에 저장
            // 세션은 톰켓의 메모리에 존재, 웹사이트에 접속한 사람마다 메모리에 값 올림

            if (CmmUtil.nvl(rDTO.getUser_id()).length()>0) { //로그인 성공
                session.setAttribute("SS_USER_ID", user_id);

                msg = "로그인이 성공했습니다. \n" + rDTO.getUser_id() + "님 환영합니다.";
                url = "/main";

            } else {
                msg = "로그인에 실패하였습니다.";
                url = "/user/login";
            }



        } catch (Exception e){
            msg="시스템 문제로 로그인이 실패했습니다.";
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);

            log.info(this.getClass().getName()+".loginProc End!");
        }

        return "/redirect";
    }







//     *  2. 이메일 인증   CheckEmail


//     *  4. 회원정보 수정하는 로직 UserModifyProc

//     *  5. 아이정보 수정하는 로직 ChildModifyProc

//     *  6. 회원탈퇴하는 로직    GoodByeUserProc

//     *  7. 체크리스트 저장하는 로직    ChecklistProc

//     *  8. 히스토리 가져오는 로직 HistoryProc


    /**
     * @GetMapping Return값이 "url"인 컨트롤러
     * ex) return "/user/userRegForm"
     * 1. 로그인창 띄우는 컨트롤러    Login
     * 2. 회원가입창 띄우는 컨트롤러  Join
     * 3. 마이페이지 띄우는 컨트롤러   MyPage
     * 4. 회원정보 수정페이지 띄우는 컨트롤러  Modify
     * 5. 아이 체크리스트 띄우는 컨트롤러    Checklist
     * 6. 아이디/패스워드 찾는 페이지 띄우는 컨트롤러 FindId
     * FindPw
     * 7. 메인페이지 띄우는 컨트롤러   Main
     *
     * @PostMapping Return값이 int, DTO인 컨트롤러
     * return rDTO;
     * 이 컨트롤러들은 ServiceInterface를 거쳐감
     * DB와 통신을 하는 로직을 작성하는 컨트롤러
     * 클라이언트에서 값을 받아옴
     * 1. 아이디 중복확인 CheckId
     * 2. 이메일 인증   CheckEmail
     * 3. 회원가입을 하는 로직  JoinProc
     * 4. 회원정보 수정하는 로직 UserModifyProc
     * 5. 아이정보 수정하는 로직 ChildModifyProc
     * 6. 회원탈퇴하는 로직    GoodByeUserProc
     * 7. 체크리스트 저장하는 로직    ChecklistProc
     * 8. 히스토리 가져오는 로직 HistoryProc
     * <p>
     * Model, Session
     * <p>
     * DTO를 Model에 .setAttribute()
     */

//    public MsgDTO checkId(Model model, String id) throws Exception {
//
//        MsgDTO rDTO = new MsgDTO();
//        CheckListDTO cDTO = new CheckListDTO();
//
//        rDTO = userService.check_id(id);
//
//        return rDTO;
//    }

    //      * @PostMapping
//     *  Return값이 int, DTO인 컨트롤러
//     *  return rDTO;
//     *  이 컨트롤러들은 ServiceInterface를 거쳐감
//     *  DB와 통신을 하는 로직을 작성하는 컨트롤러
//     *  클라이언트에서 값을 받아옴

    /*
        1. 화면에서 값을 받아온다.
        2. 받아온 값을 Controller의 파라미터 값으로 작성한다
        3. 로그를 찍고 데이터를 이동시키기 위한 DTO를 선언한다
        4. 컨트롤러의 파라미터를 DTO에 넣어 Service로 이동시킨다
        5. 서비스에서 Mapper를 불러와서 DB와 통신한다
        6. 결과값을 다시 컨트롤러로 반환하고 컨트롤러에서 화면으로 돌려준다
     */


    /*
        컨트롤러의 형식
        public 리턴값 함수명(파라미터) throws Exception {

            log.info( )

            XXDTO DTO명 = new XXDTO();
            DTO명.set(파라미터);

            DTO명 = XXService.함수명(파라미터);

            return 리턴값;
        }
     */

}
