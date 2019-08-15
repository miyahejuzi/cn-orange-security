package cn.orange.browser.core;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : kz
 * @date : 2019/8/4
 */
@Data
@AllArgsConstructor
public class Result {

    private Object object;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
