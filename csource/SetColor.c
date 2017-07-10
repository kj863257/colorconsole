#include <windows.h>
#include "SetColor.h"
JNIEXPORT void JNICALL Java_com_znvns_colorconsole_jni_SetColor_setColor (JNIEnv *env, jobject obj, jint fc, jint bc) {
	HANDLE hCon = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hCon, fc + (bc << 4));
}
