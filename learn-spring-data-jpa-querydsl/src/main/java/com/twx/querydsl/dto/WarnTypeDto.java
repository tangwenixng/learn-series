package com.twx.querydsl.dto;

public class WarnTypeDto {
    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String typeCode;

    /**
     * 类型
     */
    private String dicName;

    public WarnTypeDto() {
    }

    public WarnTypeDto(String name, String typeCode, String dicName) {
        this.name = name;
        this.typeCode = typeCode;
        this.dicName = dicName;
    }

    @Override
    public String toString() {
        return "WarnTypeDto{" +
                "name='" + name + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", dicName='" + dicName + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }
}
