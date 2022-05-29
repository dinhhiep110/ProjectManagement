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
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @ManyToMany(mappedBy = "languages",fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Employee> employees = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "projectlanguage",
            joinColumns = @JoinColumn(name = "lid"),
            inverseJoinColumns = @JoinColumn(name = "pid"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Project> projects = new LinkedHashSet<>();


}