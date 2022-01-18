package com.echo.reflect;

public  class Boy extends Person {

    public int age;
    private String home;

    public Boy(){}

    private Boy(String name){
        System.out.println("含参构造");
        this.name = name;
    }


    private void play(){
        System.out.println("i am playing");
    }

}
