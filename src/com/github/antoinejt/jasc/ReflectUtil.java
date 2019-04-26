package com.github.antoinejt.jasc;

import java.lang.reflect.Field;

public class ReflectUtil {
    public static Object getPrivateField(Object obj, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
