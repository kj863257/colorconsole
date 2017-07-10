import com.znvns.colorconsole.jni.SetColor;
import org.junit.Test;

import static com.znvns.colorconsole.jni.SetColor.Color.BLACK;
import static com.znvns.colorconsole.jni.SetColor.Color.LIGHT_GREEN;

/**
 * Created by Administrator on 2017/7/10.
 */
public class ColorTest {



    @Test
    public void test(){


        SetColor.setColor(BLACK, LIGHT_GREEN);
        System.out.println("hello");


    }


}
