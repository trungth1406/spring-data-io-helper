package com.example.demo.entity;

import com.example.demo.schemaprovider.annotation.SchemaDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;


@Data
@EqualsAndHashCode
@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SchemaDefinition(columnName = "ID")
    private Long id;

    @SchemaDefinition(columnName = "Tên người dùng")
    @Column(name = "user_name")
    private String userName;

    @SchemaDefinition(columnName = "Chức danh người dùng")
    @Column(name = "user_role")
    private String userRole;

    @SchemaDefinition(columnName = "Tuổi")
    @Column(name = "age")
    private String age;

}
