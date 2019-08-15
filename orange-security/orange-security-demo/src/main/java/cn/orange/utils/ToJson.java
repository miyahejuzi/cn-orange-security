package cn.orange.utils;

import java.util.stream.Stream;

/**
 * @author : kz
 * @date : 2019/7/25
 */
public class ToJson {

    public static String of(String oldStr) {
        StringBuilder json = new StringBuilder("{ ");
        Stream.of(oldStr.split(",")).forEach((each) -> {
            String[] params = each.trim().split(" ");
            json.append("\"" + params[0] + "\" : \"" + params[1] + "\", ");
        });
        json.deleteCharAt(json.lastIndexOf(","));
        json.append("}");
        return json.toString();
    }

}
