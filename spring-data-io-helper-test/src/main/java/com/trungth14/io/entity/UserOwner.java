package com.trungth14.io.entity;


import com.trungth14.io.schemaprovider.annotation.SchemaDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Table(name = "user_owner")
@Entity
@AllArgsConstructor
@NoArgsConstructor
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