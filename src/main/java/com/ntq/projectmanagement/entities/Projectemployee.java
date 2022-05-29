package com.ntq.projectmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "projectemployee")
public class Projectemployee {
    @EmbeddedId
    private ProjectemployeeId id;

    @MapsId("projectId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Project project;

    @MapsId("employeeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;

    @Column(name = "date_join", nullable = false)
    private LocalDate dateJoin;

    @Column(name = "date_out")
    private LocalDate dateOut;

    @Column(name = "is_rejected", nullable = false)
    private Boolean isRejected = false;
}