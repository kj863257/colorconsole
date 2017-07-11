package com.znvns.colorconsole.log4j.appender;

import com.znvns.colorconsole.jni.SetColor;
import com.znvns.colorconsole.log4j.layout.ColorPatternLayout;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

import java.io.*;

import static org.apache.log4j.EnhancedPatternLayout.DEFAULT_CONVERSION_PATTERN;

/**
 * Created by Administrator on 2017/7/11.
 */
public class ColorPrintAppender extends ConsoleAppender {
    @Override
    public synchronized void doAppend(LoggingEvent event) {
        Layout layout = ColorPrintAppender.this.getLayout();
        if(layout instanceof ColorPatternLayout){
            ColorPatternLayout cl = (ColorPatternLayout) layout;
            cl.begin();
            String msg;
            while((msg = cl.format(event)) != null){

                set(cl, cl.getType(), msg);
                this.qw.write(msg);
                this.qw.flush();
                revert(cl, cl.getType());
            }
            cl.end();
        } else {
            this.qw.write(this.layout.format(event));
        }
        if(shouldFlush(event)) {
            this.qw.flush();
        }

    }



    private void set(ColorPatternLayout layout, int type, String msg){
        if(layout != null && type > 0){
            switch (type) {
                case 2004:
                    SetColor.setColor(SetColor.Color.LIGHT_GREEN, SetColor.Color.BLACK);
                    break;
                case 2002:
                    switch (msg) {
                        case "DEBUG":
                            SetColor.setColor(SetColor.Color.YELLOW, SetColor.Color.BLACK);
                            break;
                        case "WARN":
                            SetColor.setColor(SetColor.Color.MAGENTA, SetColor.Color.BLACK);
                            break;
                        case "INFO":
                            SetColor.setColor(SetColor.Color.CYAN, SetColor.Color.BLACK);
                            break;
                        case "ERROR":
                            SetColor.setColor(SetColor.Color.RED, SetColor.Color.BLACK);
                            break;
                    }
                    break;
                default:break;
            }
        }
    }
    private void revert(ColorPatternLayout layout, int type){
        if(layout != null && type > 0){
            SetColor.revert();
        }
    }
}
