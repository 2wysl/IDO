package kopo.ido.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private String userId;
    private String password;
    private String email;
    private String postCode;
    private String addr;

}
