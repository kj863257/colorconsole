package com.znvns.colorconsole.jni;

import java.io.File;
import java.net.URISyntaxException;

import static com.znvns.colorconsole.jni.SetColor.Color.BLACK;
import static com.znvns.colorconsole.jni.SetColor.Color.LIGHT_GREEN;

public class SetColor {
	static {
		try {
			System.out.println(System.getProperty("java.library.path"));
			String absolutePath = new File(SetColor.class.getClassLoader().getResource("").toURI()).getAbsolutePath();
			absolutePath = absolutePath.replaceAll("test-classes", "classes");
			System.out.println(absolutePath);

			System.setProperty("java.library.path", absolutePath + ";" + System.getProperty("java.library.path"));
			System.out.println(System.getProperty("java.library.path"));
            System.out.println(System.getProperty("os.arch"));
            System.out.println(System.getenv("PROCESSOR_ARCHITECTURE"));
            if(System.getProperty("os.arch").contains("64")){
                System.load(absolutePath + File.separator + "SetColor_64.dll");
                //System.loadLibrary("SetColor_64");
            } else {
                System.load(absolutePath + File.separator + "SetColor.dll");
                //System.loadLibrary("SetColor");
            }
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	public static final Color defaultBackground = Color.BLACK;
	public static final Color defaultForeground = Color.WHITE;

    public static native void setColor(int fc, int bc);

	public enum Color {

		BLACK(0),
		BLUE(1),
        GREEN(2),
        RED(4),
        MAGENTA(1, 4),
        YELLOW(2, 4),
        CYAN(1, 2),
        WHITE(1, 2, 4),
        GREY(1, 2, 4),
        LIGHT_BLUE(1, 8),
        LIGHT_GREEN(2, 8),
        LIGHT_RED(4, 8),
        LIGHT_MAGENTA(1, 4, 8),
        LIGHT_YELLOW(2, 4, 8),
        LIGHT_CYAN(1, 2, 8);


		private int value;

		Color(int... value){
            for (int v : value) {
                this.value += v;
            }
        }

	}
	private static class WindowsColorConverter {
	    private static int convertBackground(int... c){
            int value = 0;
            for (int v : c) {
                value += v << 4;
            }
            return value;
        }
        private static int convertForeground(int... c){
            int value = 0;
            for (int v : c) {
                value += v << 4;
            }
            return value;
        }
    }

	public static void setColor(Color foreground, Color background){
	    setColor(foreground.value, background.value);
	}

	public static void revert(){
		setColor(defaultForeground, defaultBackground);
	}


    public static void main(String[] args) {
        SetColor.setColor(Color.RED, Color.BLACK);
        System.out.println("hello");

        SetColor.revert();
        System.out.println("default");

    }
}