package io.ruin.api.utils;

import kilim.KilimClassLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PackageLoader {

    public static Class[] load(String packageName, Class<?> ext) throws Exception {
        return load(packageName, ext, true);
    }

    public static Class[] load(String packageName, Class<?> ext, boolean allowAnonymous) throws Exception {
        Class[] classes = load(packageName);
        ArrayList<Class> list = new ArrayList<>();
        for(Class c : classes) {
            if(ext != c && ext.isAssignableFrom(c))
                list.add(c);
        }
        if(!allowAnonymous)
            list.removeIf(Class::isAnonymousClass);
        return list.toArray(new Class[list.size()]);
    }

    public static Class[] load(String packageName) throws Exception {
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        KilimClassLoader classLoader = new KilimClassLoader();
        if(classLoader == null)
            throw new IOException("Could not access ClassLoader!");
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        ArrayList<File> dirs = new ArrayList<>();
        while(resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String fileName = resource.getFile().replace("%20", " ").replace("%5c", File.separator);
            dirs.add(new File(fileName));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for(File dir : dirs) {
            List<Class> loaded = findClasses(dir, packageName);
            if(loaded != null)
                classes.addAll(loaded);
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static ArrayList<Class> findClasses(File dir, String packageName) throws IOException {
        ArrayList<Class> classes = new ArrayList<>();
        if(dir.getPath().contains(".jar!")) {
            /**
             * Loaded from jar
             */
            String[] split = dir.getPath().split("!");
            String jarName = split[0].substring(5);
            ZipInputStream in = null;
            try {
                in = new ZipInputStream(new FileInputStream(jarName));
                ZipEntry entry;
                while((entry = in.getNextEntry()) != null) {
                    String name = codePath(entry.getName());
                    if(name.endsWith(".class") && name.startsWith(packageName)) {
                        try {
                            classes.add(Class.forName(name.substring(0, name.length() - 6)));
                        } catch(Throwable t) {
                            System.err.println("Failed to load class: " + name);
                        }
                    }
                }
            } finally {
                if(in != null)
                    in.close();
            }
        } else {
            /**
             * Loaded from directory
             */
            File[] files = dir.listFiles();
            if(files == null || files.length == 0)
                return null;
            for(File file : files) {
                String name = file.getName();
                if(file.isDirectory()) {
                    if(name.contains("."))
                        continue;
                    List<Class> loaded = findClasses(file, packageName + "." + name);
                    if(loaded != null)
                        classes.addAll(loaded);
                    continue;
                }
                if(name.endsWith(".class")) {
                    try {
                        classes.add(Class.forName(packageName + '.' + name.substring(0, name.length() - 6)));
                    } catch(Throwable t) {
                        ServerWrapper.logError("Package Failure: " + name, t);
                    }
                }
            }
        }
        return classes;
    }

    private static String codePath(String path) {
        return path.replace('\\', '.').replace('/', '.');
    }

}
