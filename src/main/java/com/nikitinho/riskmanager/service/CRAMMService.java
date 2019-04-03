package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.CRAMM;
import com.nikitinho.riskmanager.repo.CRAMMRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CRAMMService {
    private final CRAMMRepo crammRepo;

    @Autowired
    public CRAMMService(CRAMMRepo crammRepo) {
        this.crammRepo = crammRepo;
    }

    public CRAMM create(CRAMM cramm) {
        crammRepo.save(cramm);

        return cramm;
    }
}
