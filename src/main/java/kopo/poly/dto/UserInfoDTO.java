package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private String user_id;
    private String password;
    private String email;
    private String post_code;
    private String address;
    private String dt_addr;
}
