package com.example.demo.entity;

import com.example.demo.schemaprovider.annotation.JoinType;
import com.example.demo.schemaprovider.annotation.SchemaDefinition;
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


    @SchemaDefinition(
            headerNames = {"Tên người sở hữu"}
    )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private UserOwner owner;

}
