package com.ntq.projectmanagement.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 155)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Status status;

    @OneToMany(mappedBy = "project")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Projectemployee> projectemployees = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "projectlanguage",
            joinColumns = @JoinColumn(name = "pid"),
            inverseJoinColumns = @JoinColumn(name = "lid"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Language> languages = new LinkedHashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}