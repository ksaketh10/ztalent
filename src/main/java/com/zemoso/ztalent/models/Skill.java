package com.zemoso.ztalent.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Skill")
@Getter
@Setter
public class Skill extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(nullable = false, name="TAG", unique = true)
    private String tag;

    @ManyToMany(mappedBy = "skills")
    private Set<Employee> employees = new HashSet<>();
}
