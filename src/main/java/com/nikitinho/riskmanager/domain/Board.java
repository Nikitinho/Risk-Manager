package com.nikitinho.riskmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonView(Views.FullBoard.class)
    private LocalDateTime creationDate;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL}, mappedBy = "board")
    @JsonView(Views.FullBoard.class)
    @JsonIgnoreProperties("board")
    private List<BoardItem> items;
}
