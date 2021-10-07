package com.trungth14.io.entity;

import com.trungth14.io.schemaprovider.annotation.SchemaDefinition;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SchemaDefinition(headerNames = "ID")
    private Long id;

    @SchemaDefinition(headerNames = "Tên người dùng")
    @Column(name = "user_name")
    private String userName;

    @SchemaDefinition(headerNames = "Chức danh người dùng")
    @Column(name = "user_role")
    private String userRole;

    @SchemaDefinition(headerNames = "Tuổi")
    @Column(name = "age")
    private String age;


    @SchemaDefinition(headerNames = {"Tên người sở hữu", "Mã người sở hữu"}, isJoined = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private UserOwner owner;

//    @SchemaDefinition(headerNames = {"Tên tài khoản", "Mã người sở hữu"}, isJoined = true)
//    private Long accountId;

}
