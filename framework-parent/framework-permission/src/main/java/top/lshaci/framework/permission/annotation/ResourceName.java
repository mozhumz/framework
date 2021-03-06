package top.lshaci.framework.permission.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used where the resource name needs to be generated
 * 
 * @author lshaci
 * @since 0.0.4
 */
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceName {

	/**
	 * The resource name
	 * 
	 * @return the resource name
	 */
	String value();
}
