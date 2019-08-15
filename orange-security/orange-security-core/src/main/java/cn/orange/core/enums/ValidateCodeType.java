package cn.orange.core.enums;

import lombok.Getter;

/**
 * @author kz
 * @date 2019/8/11
 */
public enum ValidateCodeType {
    //
    IMAGE("image"),
    SMS("sms");

    private String type;

    ValidateCodeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
