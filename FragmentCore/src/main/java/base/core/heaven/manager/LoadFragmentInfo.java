/**
 * Project Name:CommonStruct
 * File Name:LoadFragmentInfoManager.java
 * Package Name:com.heaven.commonstruct.core
 * Date:2016年3月29日下午7:43:34
 * Copyright (c) 2016
 */

package base.core.heaven.manager;

import java.lang.reflect.Field;
import java.util.Enumeration;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * ClassName:LoadFragmentInfoManager <br/>
 * Function: 载入工程中fragment的信息. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2016年3月29日 下午7:43:34 <br/>
 *
 * @author neusoft liu.hongtao
 * @since JDK 1.6
 */
public class LoadFragmentInfo {
    private static Field dexField;

    static {
        try {
            dexField = PathClassLoader.class.getDeclaredField("mDexs");
            dexField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scan() {
        try {
            PathClassLoader classLoader = (PathClassLoader) Thread.currentThread().getContextClassLoader();

            DexFile[] dexs = (DexFile[]) dexField.get(classLoader);
            for (DexFile dex : dexs) {
                Enumeration<String> entries = dex.entries();
                while (entries.hasMoreElements()) {
                    String entry = entries.nextElement();

                    Class<?> entryClass = dex.loadClass(entry, classLoader);
                    if (entryClass != null) {
                        String className = entryClass.getSuperclass().getSimpleName();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
 
