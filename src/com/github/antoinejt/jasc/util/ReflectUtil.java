package com.github.antoinejt.jasc.util;

import java.lang.reflect.Field;

public final class ReflectUtil {
    public static Object getPrivateField(Object obj, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Class clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        
        field.setAccessible(true);
        return field.get(obj);
    }
}
