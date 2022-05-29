package com.ntq.projectmanagement.settings;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "settings")
public class Setting {
    @Id
    @Column(name = "`key`", nullable = false, length = 45)
    private String key;

    @Column(name = "value", nullable = false, length = 45)
    private String value;

}
