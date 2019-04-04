package com.nikitinho.riskmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ToString(of = {"id", "text"})
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "risk_id")
    private List<CRAMM> cramms;

    public void setCategory(String category) {
        this.category = RiskCategoryType.fromString(category);
    }

    public void setStatus(String status) {
        this.status = RiskStatusType.fromString(status);
    }

    public void setCramms(List<CRAMM> cramms) {
        if (this.cramms == null) {
            this.cramms = new ArrayList<>();
        } else {
            this.cramms.clear();
        }
        this.cramms.addAll(cramms);
    }
}
