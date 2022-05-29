package com.ntq.projectmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @OneToMany(mappedBy = "status")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Project> projects = new LinkedHashSet<>();

    @OneToMany(mappedBy = "status")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Employee> employees = new LinkedHashSet<>();
}