package cn.orange.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author : kz
 * @date : 2019/7/20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private String id;

    @NotBlank
    private String username;

    private String password;

    private Date birthday;

}
