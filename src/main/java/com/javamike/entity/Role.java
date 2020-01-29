package com.javamike.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

public class Role {

    @Id
    private String id;

    /**
     * 自动为该字段建立索引
     */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
