package cn.orange.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以尝试写一些从数据库里查询的逻辑
 * 必须有这三个属性才能使用
 * @author : kz
 * @date : 2019/7/21
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserConstraintValidator.class)
public @interface UserConstraint {

    String message() ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
