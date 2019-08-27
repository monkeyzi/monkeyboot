package com.monkeyzi.mboot.demo;

import lombok.Data;

@Data
public class StaticInnerClassDemo {

    private String a="44";

    private static final Integer age=10;


    @Data
    public static  class Inner{
        private String a;

        private static final Integer b=5;

        public void  print(){
            System.out.println(new StaticInnerClassDemo().a);
            System.out.println(b);
        }
    }


    public static void main(String[] args) {

        Inner inner=new Inner();
        inner.print();
    }
}
