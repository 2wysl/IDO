package kopo.poly.service.impl;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mapper.IUserMapper;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserMapper userMapper; // 회원관련 SQL 사용하기 위한 Mapper 가져오기


    @Override
    public int joinProc(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".joinProc Start!");

        // 회원가입 성공 : 1, 기타 에러 발생 : 0
        int res = 0;

        // 회원가입
        res = userMapper.joinProc(pDTO);


        log.info(this.getClass().getName() + ".joinProc End!");

        return res;
    }

    @Override
    public UserInfoDTO getLogin(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName()+".getLogin Start!");

        // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 mapper 호출하기
        // userInfoMapper.getUserLoginCheck(pDTO) 함수 실행 결과가 NULL 발생하면, UserInfoDTO 메모리에 올리기
        UserInfoDTO rDTO=Optional.ofNullable(userMapper.getLogin(pDTO)).orElseGet(UserInfoDTO::new);

        // userInfoMapper로부터 SELECT 쿼리의 결과로 회원아이디를 받아왔다면, 로그인 성공
        // DTO의 변수에 값이 있는지 확인하기 처리속도 측면에서 가장 좋은 방법은 변수의 길이를 가져오는 것이다.
        // 따라서 .length() 함수를 통해 회원아이디의 글자수를 가져와 0보다 큰지 비교한다.
        // 0보다 크다면, 글자가 존재하는 것이기 때문에 값이 존재한다.

        if (CmmUtil.nvl(rDTO.getUser_id()).length()>0){
            log.info("로그인 성공");

        }
        log.info(this.getClass().getName()+".getLogin End!");

        return rDTO;
    }

//    @Override
//    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {
//        return null;
//    }


//    @Override
//    public MsgDTO checkId(String id) throws Exception {
//
//        MsgDTO rDTO = new MsgDTO();
//        String msg = "";
//        int result = userMapper.checkId(id);
//
//        if (result == 0) {
//            msg = "중복되지 않은 아이디입니다. 가입 가능합니다";
//        } else {
//            msg = "중복된 아이디 입니다.";
//        }
//
//        rDTO.setMsg(msg);
//
//        return rDTO;
//    }
}
