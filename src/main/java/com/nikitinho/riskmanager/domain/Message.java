package com.nikitinho.riskmanager.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue(value = "Message")
@EqualsAndHashCode(callSuper=true)
@Data
public class Message extends BoardItem {

    private String text;
}
