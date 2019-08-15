package cn.orange.core.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kz
 * @date 2019/8/11
 */
@Data
public class ValidateCode {

    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public ValidateCode(String code, int expireTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public boolean isExprie() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}
