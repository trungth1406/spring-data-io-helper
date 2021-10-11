package com.trungth14.io;

import com.trungth14.io.schemaprovider.annotation.SchemaDefinition;

import java.io.Serializable;



public class User implements Serializable {

    @SchemaDefinition(headerNames = "ID")
    private Long id;

    @SchemaDefinition(headerNames = "Tên người dùng")
    private String userName;

    @SchemaDefinition(headerNames = "Chức danh người dùng")
    private String userRole;

    @SchemaDefinition(headerNames = "Tuổi")
    private String age;


//    @SchemaDefinition(headerNames = {"Tên người sở hữu", "Mã người sở hữu"}, isJoined = true)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn
//    private UserOwner owner;

//    @SchemaDefinition(headerNames = {"Tên tài khoản", "Mã người sở hữu"}, isJoined = true)
//    private Long accountId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
