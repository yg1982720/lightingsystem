package com.zklink.lightingsystem.control;

import java.util.UUID;

/**
 * Created by Administrator on 2016/5/17.
 */
public class Bean {
    private String name;
    private String jieShao;
    private int picture1,picture2;
    private UUID id;

    public UUID getId() {
        return id;
    }



    public void setId(UUID id) {
        this.id = id;
    }



    public Bean(String name,String jieShao,int picture1,int picture2) {
        this.name=name;
        this.jieShao=jieShao;
        this.picture1=picture1;
        this.picture2=picture2;
    }
    public int getPicture1() {
        return  picture1;
    }
    public int getPicture2() {
        return picture2;
    }


    public void setPicture1(int picture1) {
        this.picture1 = picture1;
    }
    public void setPicture2(int picture2) {
        this.picture2 = picture2;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getJieShao() {
        return jieShao;
    }
    public void setJieShao(String jieShao) {
        this.jieShao = jieShao;
    }
}

