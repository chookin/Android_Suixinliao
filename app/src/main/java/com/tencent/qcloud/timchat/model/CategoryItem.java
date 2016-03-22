package com.tencent.qcloud.timchat.model;

/**
 * 分组数据
 */
public class CategoryItem {

    //分组名称
    private String name;
    //是否被选择
    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
