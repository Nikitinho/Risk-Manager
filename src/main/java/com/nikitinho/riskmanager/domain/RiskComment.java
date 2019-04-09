package com.nikitinho.riskmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@EqualsAndHashCode(of = {"id"})
@Data
public class RiskComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullRiskComment.class)
    private LocalDateTime creationDate;

    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false)
    @JsonView(Views.FullRiskComment.class)
    private User author;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonIgnoreProperties("comments")
    @JsonView(Views.FullRiskComment.class)
    private Risk risk;
}
