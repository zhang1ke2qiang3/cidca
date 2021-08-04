package com.cidca.system.controller;

import java.util.UUID;

public class Test {
    public static void main(String[] args){
        String uuid = UUID.randomUUID().toString().replace("-","");
        System.out.println(uuid);
    }

}
