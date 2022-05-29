package com.ntq.projectmanagement.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//khong nen dung annotaton cua lombook @Data trong entities cua jpa vi no bao gom ca equal and hashcode
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    private Boolean gender = false;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "address", length = 100)
    private String address;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Role role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_join_company", nullable = false)
    private LocalDate dateJoinCompany;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Status status;


    @ManyToMany
    @JoinTable(name = "employeelanguage",
            joinColumns = @JoinColumn(name = "eid"),
            inverseJoinColumns = @JoinColumn(name = "lid"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Language> languages = new LinkedHashSet<>();


    @OneToMany(mappedBy = "employee")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Projectemployee> projectemployees = new LinkedHashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}