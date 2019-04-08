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
    private String name;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullProject.class)
    private LocalDateTime creationDate;

    @JsonView(Views.FullProject.class)
    private String description;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL}, mappedBy = "project")
    @JsonView(Views.FullProject.class)
    private List<Risk> risks;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false)
    @JsonView(Views.FullProject.class)
    private User author;

    @JsonView(Views.FullProject.class)
    @ManyToMany
    @JoinTable(name = "project_user",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> responsible;
}
