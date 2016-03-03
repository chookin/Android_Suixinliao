package com.tencent.qcloud.timchat.model;

/**
 * 回话数据
 */
public class Conversation {

    //回话对象id
    private String id;
    //回话类型
    private Type type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        if (!id.equals(that.id)) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    public enum Type{
        /**
         * 非法值
         */
        Invalid,

        /**
         * 单聊会话
         */
        C2C,

        /**
         * 群组会话
         */
        Group,

        /**
         * 系统会话
         */
        System
    }


}
