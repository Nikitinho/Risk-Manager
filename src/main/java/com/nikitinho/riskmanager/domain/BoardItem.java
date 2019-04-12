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
    @JsonView(Views.FullBoardItem.class)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false)
    @JsonView(Views.FullBoardItem.class)
    private User author;

    @JsonView(Views.FullBoardItem.class)
    private String messageText;

    @Lob
    @Column(length=100000)
    @JsonView(Views.FullBoardItem.class)
    private byte[] image;

    @Lob
    @Column(length=100000)
    @JsonView(Views.FullBoardItem.class)
    private byte[] file;

    @JsonView(Views.FullBoardItem.class)
    private String fileName;

    @JsonView(Views.FullBoardItem.class)
    private BoardItemType type;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties("items")
    @JsonView(Views.FullBoardItem.class)
    private Board board;

    public void setType(BoardItemType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = BoardItemType.fromString(type);
    }
}
