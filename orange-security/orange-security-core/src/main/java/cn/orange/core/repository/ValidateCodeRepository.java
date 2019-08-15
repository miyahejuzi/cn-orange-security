package cn.orange.core.repository;

import cn.orange.core.enums.ValidateCodeType;
import cn.orange.core.pojo.ValidateCode;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这里应该声明一个借口, 然后实现它, 这样可以更好的针对各种不同的需求
 *
 * @author kz
 * @date 2019/8/11
 */
@Repository
public class ValidateCodeRepository {

    private static Map<ValidateCodeType, ValidateCode> threadSafeMap = new ConcurrentHashMap<>();

    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType codeType) {
        threadSafeMap.put(codeType, code);
    }

    public ValidateCode get(ServletWebRequest request, ValidateCodeType codeType) {
        return threadSafeMap.get(codeType);
    }

    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        threadSafeMap.remove(codeType);
    }

}
