package com.nikitinho.riskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table
@EqualsAndHashCode(of = {"id"})
@Data
public class BoardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties("items")
    private Board board;
}
