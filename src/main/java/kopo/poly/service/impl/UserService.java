package kopo.poly.service.impl;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mapper.IUserMapper;
import kopo.poly.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


    public boolean isIdExists(String userId) {
        int count = userMapper.checkIdExists(userId);
        return count > 0;
    }

    @Override
    public MsgDTO checkId(String id) throws Exception {

        MsgDTO rDTO = new MsgDTO();
        String msg = "";
        int result = userMapper.checkId(id);

        if (result == 0) {
            msg = "중복되지 않은 아이디입니다. 가입 가능합니다";
        } else {
            msg = "중복된 아이디 입니다.";
        }

        rDTO.setMsg(msg);

        return rDTO;
    }
}
