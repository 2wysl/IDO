package kopo.poly.persistance.mapper;

import kopo.poly.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {


    int joinProc(UserInfoDTO pDTO) throws Exception;

//    int checkId(String userid) throws Exception; // 중복이면 1, 중복 아니면 0
    int checkIdExists(String userId) throws Exception;
}
