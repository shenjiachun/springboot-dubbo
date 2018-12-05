package org.navychi.framework.order.test;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TestScanRegistrar.class)
public @interface EnableTest {

}
