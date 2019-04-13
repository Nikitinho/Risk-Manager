package com.nikitinho.riskmanager.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@EqualsAndHashCode(of = {"id"})
@Data
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullProject.class)
    private LocalDateTime creationDate;

    @JsonView(Views.FullProject.class)
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL}, mappedBy = "project")
    @JsonIgnoreProperties("project")
    @JsonView(Views.FullProject.class)
    private List<Risk> risks;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false)
    @JsonView(Views.FullProject.class)
    private User author;

    @JsonView(Views.FullProject.class)
    @ManyToMany
    @JoinTable(name = "project_manager",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> managers;

    @JsonView(Views.FullProject.class)
    @ManyToMany
    @JoinTable(name = "project_analytic",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> analytics;

    @JsonView(Views.FullProject.class)
    @ManyToMany
    @JoinTable(name = "project_developer",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> developers;

    @JsonView(Views.FullProject.class)
    @ManyToMany
    @JoinTable(name = "project_tester",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> testers;

    @JsonView(Views.FullProject.class)
    @ManyToMany
    @JoinTable(name = "project_others",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> others;
}
