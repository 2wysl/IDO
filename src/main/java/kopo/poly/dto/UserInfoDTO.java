package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserInfoDTO {
    private String user_id;
    private String password;
    private String email;
    private String post_code;
    private String address;
    private String dt_addr;

    private String exists_yn;

    private int authNumber;
}
