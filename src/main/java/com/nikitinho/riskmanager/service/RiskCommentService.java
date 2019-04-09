package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.RiskComment;
import com.nikitinho.riskmanager.domain.User;
import com.nikitinho.riskmanager.repo.RiskCommentRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RiskCommentService {

    private final RiskCommentRepo riskCommentRepo;

    @Autowired
    public RiskCommentService(RiskCommentRepo riskCommentRepo) {
        this.riskCommentRepo = riskCommentRepo;
    }

    public void delete(RiskComment riskComment) {
        riskCommentRepo.delete(riskComment);
    }

    public RiskComment update(RiskComment riskCommentFromDb, RiskComment riskComment) throws IOException {
        BeanUtils.copyProperties(riskComment, riskCommentFromDb, "id");

        RiskComment updatedRiskComment = riskCommentRepo.save(riskCommentFromDb);

        return updatedRiskComment;
    }

    public RiskComment create(RiskComment riskComment, User user) throws IOException {
        riskComment.setCreationDate(LocalDateTime.now());
        riskComment.setAuthor(user);
        RiskComment updatedRiskComment = riskCommentRepo.save(riskComment);

        return updatedRiskComment;
    }

    public List<RiskComment> findAll() {
        return riskCommentRepo.findAll();
    }
}
