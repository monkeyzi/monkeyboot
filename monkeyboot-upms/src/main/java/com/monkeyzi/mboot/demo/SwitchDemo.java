package com.monkeyzi.mboot.demo;

public class SwitchDemo {


    public static void main(String[] args) {
        method(null);
    }


    public static void method(String param){
        System.out.println(param);
        System.out.println(null==null);
        switch (param){
            case "sth":
                System.out.println("It's sth");
                break;
            case "null":
                System.out.println("It's null");
                break;
            default:
                System.out.println("It's default");
        }
    }



}
