package kopo.poly.persistance.mapper;

import kopo.poly.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {


    // 회원 가입하기(회원정보 등록하기)
    int joinProc(UserInfoDTO pDTO) throws Exception;

    //    int checkId(String userid) throws Exception; // 중복이면 1, 중복 아니면 0
    int checkIdExists(String userId) throws Exception;

    // 로그인을 위해 아이디와 비밀번호가 일치하는 지 확인하기
    UserInfoDTO getLogin(UserInfoDTO pDTO) throws Exception;
}