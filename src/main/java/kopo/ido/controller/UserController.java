package kopo.ido.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.ido.dto.CheckListDTO;
import kopo.ido.dto.MsgDTO;
import kopo.ido.dto.UserInfoDTO;
import kopo.ido.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;

//    * @GetMapping
//     *  Return값이 "url"인 컨트롤러
//     *  ex) return "/user/userRegForm"

    //1. 메인페이지 띄우는 컨트롤러 Main
//    public class MainController {
//
//        @GetMapping("/main")
//        public String main() throws Exception{
//            log.info(this.getClass().getName()+".main 페이지 보여주는 함수 실행");
//            return "/main";
//        }
//    }
//

    //2. 로그인창 띄우는 컨트롤러 Login
    public class LoginController{

        @GetMapping("/user/login")
        public String login() throws Exception{
            log.info(this.getClass().getName()+".login 페이지 보여주는 함수 실행");
            return "/login";
        }
    }

    //3. 회원가입창 띄우는 컨트롤러  Join
    @GetMapping(value = "/user/join")
    public String join() {
        log.info(this.getClass().getName() + ".user/join");

        return "/IDOSignUp";
    }

    //4. 마이페이지 띄우는 컨트롤러 MyPage
    @GetMapping(value = "/user/myPage")
    public String myPage() {
        log.info(this.getClass().getName() + ".user/myPage");

        return "/user/mypage";
    }

    //5. 회원정보 수정페이지 띄우는 컨트롤러 Modify
    @GetMapping(value = "/user/modify")
    public String modify() {
        log.info(this.getClass().getName() + ".user/modify");

        return "/user/modify";
    }


    //6. 아이 체크리스트 띄우는 컨트롤러 Checklist
    @GetMapping(value = "/user/checklist")
    public String checklist() {
        log.info(this.getClass().getName() + ".user/checklist");

        return "/user/checklist";
    }


    //7. 아이디 찾는 페이지 띄우는 컨트롤러 FindId
    @GetMapping(value = "/user/findid")
    public String findid() {
        log.info(this.getClass().getName() + ".user/findid");

        return "/user/findid";
    }


    //8. 패스워드 찾는 페이지 띄우는 컨트롤러 FindPw
    @GetMapping(value = "/user/findpw")
    public String findpw() {
        log.info(this.getClass().getName() + ".user/findpw");

        return "/user/findpw";
    }




//      * @PostMapping
//     *  Return값이 int, DTO인 컨트롤러
//     *  return rDTO;
//     *  이 컨트롤러들은 ServiceInterface를 거쳐감
//     *  DB와 통신을 하는 로직을 작성하는 컨트롤러
//     *  클라이언트에서 값을 받아옴
//     *  1. 아이디 중복확인 CheckId

//     *  2. 이메일 인증   CheckEmail



//     *  3. 회원가입을 하는 로직  JoinProc
    /**
     * 회원가입 로직 처리
     */
    @PostMapping(value = "/user/joinProc")
    public String joinProc(HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".joinProc start!");
        int res;
        String msg = ""; //회원가입 결과에 대한 메시지를 전달할 변수
        String url = ""; //회원가입 결과에 대한  URL을 전달할 변수

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
//        UserInfoDTO pDTO = null;
        UserInfoDTO pDTO = new UserInfoDTO();

        try {

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */
            String user_id = kopo.poly.util.CmmUtil.nvl(request.getParameter("user_id")); //아이디
            String password = kopo.poly.util.CmmUtil.nvl(request.getParameter("password")); //비밀번호
            String email = kopo.poly.util.CmmUtil.nvl(request.getParameter("email")); //이메일
            String post_code = kopo.poly.util.CmmUtil.nvl(request.getParameter("post_code")); //우편번호
            String addr = kopo.poly.util.CmmUtil.nvl(request.getParameter("addr2")); //상세주소
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
            log.info("addr : " + addr);

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUserId(user_id);
            pDTO.setPassword(password);
            pDTO.setEmail(email);
            pDTO.setPostCode(post_code);
            pDTO.setAddr(addr);

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
        }
        catch (DuplicateKeyException e){ //PK인 USER_ID가 중복되어 에러가 발생했다면
            msg = "이미 가입된 아이디입니다. 다른 아이디로 변경 후 다시 시도해주세요.";
            url = "/user/join";
            log.info(e.toString());
            e.printStackTrace();
        }
        catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "시스템 오류로 실패하였습니다. 다시 시도해주세요.";
            url = "/user/join";
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info("출력할 메세지 : "+msg);
            log.info("이동할 경로 : "+url);
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);

            log.info(this.getClass().getName() + ".joinProc End!");
        }

        return "/redirect";
    }


//     *  4. 회원정보 수정하는 로직 UserModifyProc

//     *  5. 아이정보 수정하는 로직 ChildModifyProc

//     *  6. 회원탈퇴하는 로직    GoodByeUserProc

//     *  7. 체크리스트 저장하는 로직    ChecklistProc

//     *  8. 히스토리 가져오는 로직 HistoryProc





    /**
     * @GetMapping
     *  Return값이 "url"인 컨트롤러
     *  ex) return "/user/userRegForm"
     *  1. 로그인창 띄우는 컨트롤러    Login
     *  2. 회원가입창 띄우는 컨트롤러  Join
     *  3. 마이페이지 띄우는 컨트롤러   MyPage
     *  4. 회원정보 수정페이지 띄우는 컨트롤러  Modify
     *  5. 아이 체크리스트 띄우는 컨트롤러    Checklist
     *  6. 아이디/패스워드 찾는 페이지 띄우는 컨트롤러 FindId
     *      FindPw
     *  7. 메인페이지 띄우는 컨트롤러   Main
     *
     * @PostMapping
     *  Return값이 int, DTO인 컨트롤러
     *  return rDTO;
     *  이 컨트롤러들은 ServiceInterface를 거쳐감
     *  DB와 통신을 하는 로직을 작성하는 컨트롤러
     *  클라이언트에서 값을 받아옴
     *  1. 아이디 중복확인 CheckId
     *  2. 이메일 인증   CheckEmail
     *  3. 회원가입을 하는 로직  JoinProc
     *  4. 회원정보 수정하는 로직 UserModifyProc
     *  5. 아이정보 수정하는 로직 ChildModifyProc
     *  6. 회원탈퇴하는 로직    GoodByeUserProc
     *  7. 체크리스트 저장하는 로직    ChecklistProc
     *  8. 히스토리 가져오는 로직 HistoryProc
     *
     *  Model, Session
     *
     *  DTO를 Model에 .setAttribute()
     */

    public MsgDTO checkId(Model model, String id) throws Exception {

        MsgDTO rDTO = new MsgDTO();
        CheckListDTO cDTO = new CheckListDTO();

        rDTO = userService.checkId(id);

        return rDTO;
    }

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
