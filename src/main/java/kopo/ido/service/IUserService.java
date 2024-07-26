package kopo.ido.service;

import kopo.ido.dto.MsgDTO;
import kopo.ido.dto.UserInfoDTO;

public interface IUserService {

    // 회원 가입하기(회원정보 등록하기)
    int joinProc(UserInfoDTO pDTO) throws Exception;
    // 아이디 중복확인
     MsgDTO checkId(String id) throws Exception;
}
