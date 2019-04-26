package com.github.antoinejt.jasc.util;

import java.lang.reflect.Field;

public final class ReflectUtil {
    public static Object getPrivateField(Object obj, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
