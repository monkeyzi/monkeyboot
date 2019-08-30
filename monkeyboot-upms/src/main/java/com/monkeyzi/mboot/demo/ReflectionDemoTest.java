package com.monkeyzi.mboot.demo;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionDemoTest {






    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clazz=Class.forName("com.monkeyzi.mboot.demo.ReflectDemo");
        Method[] methods=clazz.getDeclaredMethods();
        for (Method method:methods){
            System.out.println("获取方法"+method.toString());
        }

        Field[] fields=clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(a->{
            System.out.println("属性："+a.toString());
        });

        Constructor[] constructors=clazz.getDeclaredConstructors();
        Arrays.stream(constructors).forEach(a->{
            System.out.println("构造函数："+a.toString());
        });

        //ReflectDemo reflectDemo= (ReflectDemo) clazz.newInstance();
        Constructor c=clazz.getDeclaredConstructor(String.class);
        c.setAccessible(true);
        ReflectDemo p= (ReflectDemo) c.newInstance("xiaogao");
        System.out.println("结果："+p.getName()+"----"+p.getPass());
    }


}
