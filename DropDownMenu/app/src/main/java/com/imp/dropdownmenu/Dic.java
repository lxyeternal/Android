package com.imp.dropdownmenu;

/**
 * Created by import on 2018-01-31.
 */

public class Dic {
    private String code;
    private String name;

    public Dic(){
        this.code="";
        this.name="";
    }
    public Dic(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public Dic(String code, String name, String type) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
