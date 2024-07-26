package kopo.ido.service.impl;

import kopo.ido.dto.MsgDTO;
import kopo.ido.dto.UserInfoDTO;
import kopo.ido.persistance.mapper.IUserMapper;
import kopo.ido.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserMapper userMapper;

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
