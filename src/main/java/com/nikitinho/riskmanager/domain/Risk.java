package com.nikitinho.riskmanager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@EqualsAndHashCode(of = {"id"})
@Data
public class Risk {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullRisk.class)
    private LocalDateTime creationDate;

    @JsonView(Views.FullRisk.class)
    private String description;

    @JsonView(Views.FullRisk.class)
    private String causes;

    @JsonView(Views.FullRisk.class)
    private String consequences;

    @JsonView(Views.FullRisk.class)
    @ManyToMany
    @JoinTable(name = "risk_user",
            joinColumns = {@JoinColumn(name = "risk_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> responsible;

    @JsonView(Views.FullRisk.class)
    private RiskCategoryType category;

    @JsonView(Views.FullRisk.class)
    private RiskStatusType status;

    @JsonView(Views.FullRisk.class)
    private RiskStageType stage;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("risks")
    @JsonView(Views.FullRisk.class)
    private Project project;

    @JsonView(Views.FullRisk.class)
    private Integer probability;

    @JsonView(Views.FullRisk.class)
    private Integer impact;

    @JsonView(Views.FullRisk.class)
    private Integer riskRate;

    @JsonView(Views.FullRisk.class)
    private RiskLevelType riskLevel;

    @JsonView(Views.FullRisk.class)
    private String countermeasure;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL}, mappedBy = "risk")
    @JsonIgnoreProperties("risk")
    @JsonView(Views.FullRisk.class)
    private List<RiskComment> comments;

    public void setCategory(String category) {
        this.category = RiskCategoryType.fromString(category);
    }

    public void setStatus(String status) {
        this.status = RiskStatusType.fromString(status);
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = RiskLevelType.fromString(riskLevel);
    }

    public void setStage(String stage) {
        this.stage = RiskStageType.fromString(stage);
    }

    public void setComments(List<RiskComment> comments) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
            this.comments.addAll(comments);
        } else {
            this.comments.clear();
            this.comments.addAll(comments);
        }
    }
}
