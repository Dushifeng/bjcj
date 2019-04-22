package cn.lovezsm.bjcj.dispacher;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface UDPService {
    String value();
}
