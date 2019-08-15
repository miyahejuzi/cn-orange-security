package cn.orange.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ConstraintValidator<UserConstraint, String>
 * UserConstraint : 注解
 * String : 能被注解的类型
 *
 * 可以注入 service 的业务校验逻辑
 *
 * @author : kz
 * @date : 2019/7/21
 */
public class UserConstraintValidator implements ConstraintValidator<UserConstraint, String> {

    /**
     * 初始化时做的事情
     * @param constraint
     */
    @Override
    public void initialize(UserConstraint constraint) {
        // TODO 可以初始化
    }

    /**
     *
     * @param obj 被你校验的值
     * @param context 校验的上下文
     * @return pass ? true : false
     */
    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        //
        return false;
    }
}
