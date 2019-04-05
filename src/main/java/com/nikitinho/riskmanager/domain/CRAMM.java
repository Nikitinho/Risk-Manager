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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String asset;

    private Integer assetRate;

    private String threat;

    private String vulnerability;

    private Integer vulnerabilityRate;

    private Integer riskRate;

    private RiskLevelType riskLevel;

    private String countermeasure;

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = RiskLevelType.fromString(riskLevel);
    }
}
