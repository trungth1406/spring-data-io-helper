package com.trungth14.io.entity;


import com.trungth14.io.schemaprovider.annotation.SchemaDefinition;

import javax.persistence.*;

@Table(name = "user_owner")
@Entity
public class UserOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @SchemaDefinition(headerNames = "Tên người sở hữu")
    @Column(name = "owner_name")
    private String ownerFullName;

    @SchemaDefinition(headerNames = "Mã người sở hữu")
    @Column(name = "owner_code")
    private String ownerCode;

}