package com.monkeyzi.mboot.demo;

public class ReflectDemo {
    private String name;

    private String pass;

    /*public ReflectDemo(){

    }*/

    public ReflectDemo(String name,String pass){
        this.name=name;
        this.pass=pass;
    }

    private ReflectDemo(String   name){
        this.name=name;
    }
    private String read(){
        return this.name+""+this.pass;
    }

    public String read1(){
        return this.pass;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
