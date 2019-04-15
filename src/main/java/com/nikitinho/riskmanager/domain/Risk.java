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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullRisk.class)
    private LocalDateTime creationDate;

    @JsonView(Views.FullRisk.class)
    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonView(Views.FullRisk.class)
    @Column(columnDefinition = "TEXT")
    private String causes;

    @JsonView(Views.FullRisk.class)
    @Column(columnDefinition = "TEXT")
    private String consequences;

    @JsonView(Views.FullRisk.class)
    @ManyToMany(fetch = FetchType.EAGER)
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
    private Double probability;

    @JsonView(Views.FullRisk.class)
    private Double impact;

    @JsonView(Views.FullRisk.class)
    private Double riskRate;

    @JsonView(Views.FullRisk.class)
    private RiskLevelType riskLevel;

    @JsonView(Views.FullRisk.class)
    private RiskActionStrategyType strategy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullRisk.class)
    private LocalDateTime actionStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullRisk.class)
    private LocalDateTime actionEndDate;

    @JsonView(Views.FullRisk.class)
    @Column(columnDefinition = "TEXT")
    private String strategyInfo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullRisk.class)
    private LocalDateTime actualActionEndDate;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL}, mappedBy = "risk")
    @JsonIgnoreProperties("risk")
    @JsonView(Views.FullRisk.class)
    private List<RiskComment> comments;

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = RiskLevelType.fromString(riskLevel);
    }

    public void setRiskLevel(RiskLevelType riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setStage(String stage) {
        this.stage = RiskStageType.fromString(stage);
    }

    public void setStage(RiskStageType stage) {
        this.stage = stage;
    }

    public void setActionStartDate(String date) {
        if (date == null || date.isEmpty()) {
            this.actionStartDate = null;
        } else {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            this.actionStartDate = localDate.atTime(23, 59, 59);
        }
    }

    public void setActionStartDate(LocalDateTime date) {
        this.actionStartDate = date;
    }

    public void setActionEndDate(String date) {
        if (date == null || date.isEmpty()) {
            this.actionEndDate = null;
        } else {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            this.actionEndDate = localDate.atTime(23, 59, 59);
        }
    }

    public void setActionEndDate(LocalDateTime date) {
        this.actionEndDate = date;
    }

    public void setActualActionEndDate(String date) {
        if (date == null || date.isEmpty()) {
            this.actualActionEndDate = null;
        } else {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            this.actualActionEndDate = localDate.atTime(23, 59, 59);
        }
    }

    public void setActualActionEndDate(LocalDateTime date) {
        this.actualActionEndDate = date;
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
