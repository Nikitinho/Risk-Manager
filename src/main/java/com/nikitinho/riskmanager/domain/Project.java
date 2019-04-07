package com.nikitinho.riskmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@EqualsAndHashCode(of = {"id"})
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullRisk.class)
    private LocalDateTime creationDate;

    @JsonView(Views.FullRisk.class)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false)
    private User author;

    @JsonView(Views.FullRisk.class)
    @ManyToMany
    @JoinTable(name = "project_user",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> responsible;
}
