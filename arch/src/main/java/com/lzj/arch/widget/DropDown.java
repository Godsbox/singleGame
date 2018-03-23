package com.lzj.arch.widget;

import java.io.Serializable;

/**
 * 下拉式列表项数据类型
 */
public class DropDown implements Serializable {
    int id;
    String name;
    /**
     * 所属类型
     * 0排序，1状态，2标签
     * */
    int type;
    /**
     * 当前标签是否被选中*/
    boolean isCheck = false;

    public DropDown(String name) {
        this.name = name;
    }

    public DropDown(int id, String name) {
        this.id = id;
        this.name = name;
        this.type = 2;
    }

    public DropDown(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
