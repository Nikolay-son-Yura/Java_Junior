package ru.gb.HomeWork.Lesson2;

import java.lang.reflect.Constructor;

public class ObjectCreator {
    public static <T> T createObj(Class<T> tClass) {
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor();
            T obj = constructor.newInstance();
            RandomDateAnnotationProcessor.processAnnotation(obj);
            return obj;
        } catch (Exception e) {
            System.err.println("ниче не получилось: " + e.getMessage());
            return null;
        }
    }
}
