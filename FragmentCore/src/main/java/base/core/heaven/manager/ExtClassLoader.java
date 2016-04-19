package base.core.heaven.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Field;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * Created by neusoft on 2016/4/1.
 */
public class ExtClassLoader extends ClassLoader {
    private static Field dexField;
    private DexFile[] mDexs;
    private final ClassLoader mLoader;
    private static boolean ICE_OR_ABOVE = Build.VERSION.SDK_INT >= 14;

    static {
        if (!ICE_OR_ABOVE) {
            Class<PathClassLoader> classLoader = PathClassLoader.class;
            try {
                dexField = classLoader.getDeclaredField("mDexs");
                dexField.setAccessible(true);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public ExtClassLoader(Context base, PackageInfo pi, String extPkg,
                          ClassLoader mainClassLoader) {
        super(mainClassLoader);
        Context extContext = null;
        try {
            extContext = base.createPackageContext(
                    extPkg,
                    Context.CONTEXT_INCLUDE_CODE
                            | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        if (ICE_OR_ABOVE) {
            String codePath = extContext.getPackageCodePath();
            String dir = pi.applicationInfo.nativeLibraryDir;
            mLoader = new PathClassLoader(codePath, dir, mainClassLoader);
        } else {
            mLoader = extContext.getClassLoader();
        }
    }

    @Override
    protected Class<?> loadClass(String className, boolean arg1)
            throws ClassNotFoundException {
        if (ICE_OR_ABOVE)
            return super.loadClass(className, arg1);
        Class<?> c = findLoadedClass(className);
        if (c != null)
            return c;
        try {
            c = getParent().loadClass(className);
            if (c == null)
                c = findClass(className);
        } catch (ClassNotFoundException e) {
            c = findClass(className);
        }
        return c;
    }

    @Override
    protected Class<?> findClass(String className)
            throws ClassNotFoundException {
        if (mLoader == null)
            return null;
        if (ICE_OR_ABOVE)
            return mLoader.loadClass(className);
        Class<?> c = null;
        if (mLoader instanceof PathClassLoader)
            c = loadClassFromDexFile(className);
        else
            c = mLoader.loadClass(className);
        return c;
    }

    private Class<?> loadClassFromDexFile(String className) {
        DexFile[] dfs = getDexFiles();
        if (dfs != null) {
            for (int i = 0; i < dfs.length; i++) {
                if (dfs[i] != null) {
                    Class<?> c = dfs[i].loadClass(className, this);
                    if (c != null)
                        return c;
                }
            }
        }
        return null;
    }

    private DexFile[] getDexFiles() {
        if (mDexs != null)
            return mDexs;
        try {
            mLoader.loadClass("all.money.go.my.home");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mDexs = (DexFile[]) dexField.get(mLoader);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return mDexs;
    }
}
