package kopo.ido.persistance.mapper;

import kopo.ido.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {


    int joinProc(UserInfoDTO pDTO) throws Exception;

    int checkId(String id) throws Exception; // 중복이면 1, 중복 아니면 0

}
