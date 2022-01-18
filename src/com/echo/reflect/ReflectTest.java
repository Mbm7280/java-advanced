package com.echo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ReflectTest {

    public static void main(String[] args) throws Exception {
        // 获取反射
        Class clz = getReflectObject("forName");
        // 获取 class 中的 基本信息
        getBasicInfo(clz);
        // 属性操作
        opeFields(clz);
        // 方法操作
        opeMethods(clz);
        // 构造器操作
        opeConstructor(clz);
    }

    /**
     * 构造器操作
     * @param clz
     * @throws Exception
     */
    public static void opeConstructor(Class clz)throws Exception{

        // 获取类中定义的所有的公有构造方法
        Constructor[] clzPublicConstructors = clz.getConstructors();
        System.out.println(clz + " public 修饰的 Constructor 有 "+clzPublicConstructors.length+" 个");

        // 获取类中定义的所有的构造方法
        Constructor[] clzDeclaredConstructors = clz.getDeclaredConstructors();
        System.out.println(clz + " private 修饰的 Constructor 有 "+clzDeclaredConstructors.length+" 个");

        // 获取无参的构造函数
        Constructor clzWithOutArgsConstructor = clz.getDeclaredConstructor();

        // 获取含参的构造方法
        Constructor clzWithArgsConstructor = clz.getDeclaredConstructor(String.class);

        // 获取构造函数的修饰符
        int clzDeclaredConstructorModifiers = clzWithOutArgsConstructor.getModifiers();
        System.out.println("Boy 的 clzDeclaredConstructor 修饰符 有: "+ clzDeclaredConstructorModifiers + "个");

        // 获取参数列表类型
        Parameter[] clzDeclaredConstructorParameters = clzWithOutArgsConstructor.getParameters();
        System.out.println("Boy 的 clzDeclaredConstructor 参数列表 有: "+ clzDeclaredConstructorParameters.length + "个");

        // 获取异常列表
        Class[] clzDeclaredConstructorExceptions = clzWithOutArgsConstructor.getExceptionTypes();
        System.out.println("Boy 的 clzDeclaredConstructor 异常列表 有: "+ clzDeclaredConstructorExceptions.length + "个");

        // 无参的构造器调用
        Boy boy = (Boy) clzWithOutArgsConstructor.newInstance();
        boy.eat("apple");

        // 含参构造器调用
        clzWithArgsConstructor.setAccessible(true);
        Boy boyArgs = (Boy) clzWithArgsConstructor.newInstance("Echo");
        System.out.println(boyArgs.name);
    }


    /**
     * 方法操作
     * @param clz
     * @throws Exception
     */
    public static void opeMethods(Class clz)throws Exception{
        Boy boy = (Boy) clz.newInstance();

        // 获取所有公有的方法,包含继承
        Method[] clzMethods = clz.getMethods();
        System.out.println(clz + " public 修饰的 method 有 "+clzMethods.length+" 个");

        // 获取类中定义的指定名称的公有方法
        Method clzEatMethod = clz.getMethod("eat",String.class);

        // 获取私有方法
        Method clzPlayDeclaredMethod = clz.getDeclaredMethod("play");
        String clzPlayDeclaredMethodName = clzPlayDeclaredMethod.getName();
        System.out.println("clzPlayDeclaredMethod 获取私有方法:" + clzPlayDeclaredMethodName);

        // 获取方法定义的修饰
        int clzEatMethodModifiers = clzEatMethod.getModifiers();
        System.out.println("clzEatMethod 的修饰符:" + clzEatMethodModifiers);

        // 获取方法的返回值
        Class clzEatMethodReturnType= clzEatMethod.getReturnType();
        System.out.println("clzEatMethod 返回值:" + clzEatMethodReturnType);

        // 获取方法的参数列表
        Class[] clzEatMethodParameters = clzEatMethod.getParameterTypes();
        System.out.println("clzEatMethod 参数列表有:" + clzEatMethodParameters.length + "个");

        // 获取方法抛出的异常列表
        Class<?>[] clzEatMethodExceptions = clzEatMethod.getExceptionTypes();
        System.out.println("clzEatMethod 方法抛出的异常列表有:" + clzEatMethodExceptions.length + "个");

        // 调用公有方法
        clzEatMethod.invoke(boy,"fish");

        // 调用私有方法
        clzPlayDeclaredMethod.setAccessible(true);
        clzPlayDeclaredMethod.invoke(boy);
    }


    /**
     * 属性操作
     * @param clz
     */
    public static void opeFields(Class clz) throws Exception{
        Boy boy = (Boy) clz.newInstance();
        // 获取所有公有(public)的属性,包含继承
        Field[] clzFields = clz.getFields();
        System.out.println(clz + " public 修饰的属性有 "+clzFields.length+" 个");

        // 获取公共的、受保护的、默认的（包）访问和私有字段，但不包括继承的字段。
        Field[] clzDeclaredFields = clz.getDeclaredFields();
        System.out.println(clz + " 属性字段一共有 "+clzDeclaredFields.length+" 个");

        // 获取指定的属性对象
        // 获取父类的公有字段
        Field clzNameField = clz.getField("name");
        // 获取父类的公有字段，并获取对应属性的字段值
        Object clzNameFieldVal = clzNameField.get(boy);
        System.out.println(clz + " 获取父类的公有字段 clzNameField: " + clzNameField.getName() + "-------clzNameFieldVal: " + clzNameFieldVal);

        // 获取私有字段
        Field clzHomeDeclaredField = clz.getDeclaredField("home");
        // setAccessible的作用是将Field对象上的指定字段访问值设置为public
        clzHomeDeclaredField.setAccessible(true);
        Object clzHomeDeclaredFieldVal = clzHomeDeclaredField.get(boy);
        System.out.println(clz + " 获取私有字段 clzHomeDeclaredField: " + clzHomeDeclaredField.getName() + "-------clzHomeDeclaredFieldVal: " + clzHomeDeclaredFieldVal);

        // 操作父类的公有字段
        clzNameField.set(boy,"echo");
        System.out.println(clz + " 操作父类的公有字段 clzAgeField: " + clzNameField.getName() + "-------clzAgeFieldVal: " + boy.name);

        // 操作私有字段
        clzHomeDeclaredField.set(boy,"China");
        Object clzHomeDeclaredFieldVal2 = clzHomeDeclaredField.get(boy);
        System.out.println(clz + " 操作私有字段 clzHomeDeclaredField: " + clzNameField.getName() + "-------clzHomeDeclaredFieldVal2: " + clzHomeDeclaredFieldVal2);
    }


    /**
     * 获取 class 中的 基本信息
     *      修饰符
     *      包
     *      类
     * @param  clz
     * @throws Exception
     */
    public static void getBasicInfo(Class clz) throws Exception{
        // 获取修饰符
        /**
         *      数值对应表:
         *       ABSTRACT 1024
         *       FINAL 16
         *       INTERFACE 512
         *       NATIVE 256
         *       PRIVATE 2
         *       PROTECTED 4
         *       PUBLIC 1
         *       STATIC 8
         *       STRICT 2048
         *       SYNCHRONIZED 32
         *       TRANSIENT 128
         *       VOLATILE 64
         */
        int modifiers = clz.getModifiers();
        System.out.println("class "+clz+" 的 修饰符: " + modifiers);

        // 获取包对象
        Package  clzPackage = clz.getPackage();
        System.out.println("class "+clz+" 的  Package: " +  clzPackage.getName());

        // 获取类名
        String  clzFullName =  clz.getName();
        String  clzSimpleName =  clz.getSimpleName();
        System.out.println("class "+ clz+" 的  fullClassName: " +  clzFullName);
        System.out.println("class "+ clz+" 的  simpleClassname: " +  clzSimpleName);

        // 获取类加载器
        ClassLoader  clzLoader =  clz.getClassLoader();

        // 获取当前类实现的接口列表
        Class[] cs =  clz.getInterfaces();
        System.out.println("class "+ clz+" 的  csLength: " + cs.length);

        // 获取父类对象
        Class fatherClzObj =  clz.getSuperclass();
        System.out.println("class "+ clz+" 的  Superclass: "+fatherClzObj.getSimpleName());
    }

    /**
     * 获取反射的四种方法
     */
    public static Class getReflectObject(String type) throws Exception {
        Class reflectObject = null;
        switch (type){
            case "forName":
                reflectObject = Class.forName("com.echo.reflect.Boy");
                break;
            case "className":
                reflectObject = Boy.class;
                break;
            case "getClass":
                Boy boy = new Boy();
                reflectObject = boy.getClass();
                break;
            case "classLoader":
                reflectObject = ReflectTest.class.getClassLoader().loadClass("com.echo.reflect.Boy");
                break;
            default:
                break;
        }
        return reflectObject;
    }
}
