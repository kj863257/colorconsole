package com.znvns.colorconsole.log4j.layout;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/7/11.
 */
public class ColorPatternLayout extends PatternLayout {
    private String pattern;
    private StringBuffer sbuf = new StringBuffer(256);
    private PatternConverter head;
    private boolean begining = false;
    public void begin(){
        begining = true;
        c = head;
    }
    public void end(){
        this.begining = false;
    }

    public ColorPatternLayout(){
        head = new PatternParser(DEFAULT_CONVERSION_PATTERN).parse();
    }
    public ColorPatternLayout(String pattern) {
        this.pattern = pattern;
        head = createPatternParser((pattern == null) ? DEFAULT_CONVERSION_PATTERN :
                pattern).parse();
    }
    public
    void setConversionPattern(String conversionPattern) {
        pattern = conversionPattern;
        head = createPatternParser(conversionPattern).parse();
    }


    private PatternConverter c = null;
    private int type;
    public int getType(){
        return type;
    }
    @Override
    public String format(LoggingEvent event) {

        if(begining){

            if(sbuf.capacity() > 1024) {
                sbuf = new StringBuffer(256);
            } else {
                sbuf.setLength(0);
            }

            if(c != null) {
                c.format(sbuf, event);
                type = getType(c);
                c = c.next;
                return sbuf.toString();
            }
            return null;
        } else {
            return super.format(event);
        }
    }

    private int getType(PatternConverter c) {
        if(c != null){
            return getType(c, "org.apache.log4j.helpers.PatternParser$BasicPatternConverter");
        }
        return -3;
    }

    private int getType(PatternConverter c, String... className) {
        Class<? extends PatternConverter> clazz = c.getClass();
        for (String classN : className) {
            if(clazz.getName().equals(classN)){
                try {
                    Field field = clazz.getDeclaredField("type");
                    field.setAccessible(true);
                    return field.getInt(c);
                } catch (Exception e) {
                    return -1;
                }
            }
        }
        return -2;
    }

}
