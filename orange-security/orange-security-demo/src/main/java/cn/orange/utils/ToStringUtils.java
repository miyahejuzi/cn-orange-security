package cn.orange.utils;

import cn.orange.dto.User;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author : kz
 * @date : 2019/7/20
 */
public class ToStringUtils {

    public static String toString(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Map<String, String> describe = BeanUtils.describe(object);
            stringBuilder.append(describe.get("class") + " = [ ");
            describe.forEach((key, value) -> {
                if(!"class".equals(key)) {
                    stringBuilder.append(key + " = " + value + ", ");
                }
            });
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            stringBuilder.append("]");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        long l1 = System.currentTimeMillis();
        System.out.println(toString(new User()));
        long l = l1 - System.currentTimeMillis();
        System.out.println("l1 = " + l);
    }

}
