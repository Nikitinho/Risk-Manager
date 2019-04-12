package com.nikitinho.riskmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Entity
@Table
@EqualsAndHashCode(of = {"id"})
@Data
public class BoardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false)
    private User author;

    private String messageText;

    @Lob
    @Column(length=100000)
    private byte[] image;

    private BoardItemType type;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties("items")
    private Board board;

    public void setType(BoardItemType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = BoardItemType.fromString(type);
    }
}
