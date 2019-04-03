package com.nikitinho.riskmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@EqualsAndHashCode(of = { "id" })
@Data
public class CRAMM {
    @Id
    @GeneratedValue
    private Long id;

    private String asset;

    private Integer assetRate;

    private String threat;

    private String vulnerability;

    private Integer vulnerabilityRate;

    @ManyToOne
    @JoinColumn(name = "risk_id")
    private Risk risk;
}
