package com.hanhan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class PKey {

    @TableId(
            value = "id",
            type = IdType.NONE
    )
    private Long id;
    private String publicKey;
    private String encoding;
    private String extra;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "key{" +
                "id=" + id +
                ", publicKey='" + publicKey + '\'' +
                ", encoding='" + encoding + '\'' +
                ", extra='" + extra + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
