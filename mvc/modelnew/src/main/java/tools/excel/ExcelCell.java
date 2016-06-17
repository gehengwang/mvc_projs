package tools.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.FIELD)
public @interface ExcelCell {
    String convert_fmt(); //java 对象格式化
    String excel_fmt() default "@";//excel的格式化
}
