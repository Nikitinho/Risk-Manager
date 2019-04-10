package com.nikitinho.riskmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nikitinho.riskmanager.domain.*;
import com.nikitinho.riskmanager.repo.BoardRepo;
import com.nikitinho.riskmanager.repo.ProjectRepo;
import com.nikitinho.riskmanager.repo.RiskRepo;
import com.nikitinho.riskmanager.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/")
public class MainController {
    private final BoardRepo boardRepo;
    private final ProjectRepo projectRepo;
    private final UserDetailsRepo userDetailsRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(BoardRepo boardRepo, UserDetailsRepo userDetailsRepo, ProjectRepo projectRepo) {
        this.userDetailsRepo = userDetailsRepo;
        this.boardRepo = boardRepo;
        this.projectRepo = projectRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);
            data.put("categories", Stream.of(RiskCategoryType.values()).map(RiskCategoryType::toString).toArray());
            data.put("statuses", Stream.of(RiskStatusType.values()).map(RiskStatusType::toString).toArray());
            data.put("strategies", Stream.of(RiskActionStrategyType.values()).map(RiskActionStrategyType::toString)
                    .toArray());
            data.put("projects", projectRepo.findAll().stream().filter(project -> project.getResponsible().stream()
                    .anyMatch(usr -> usr.getId().equals(user.getId()))).toArray());

            // Only load risks where the user is one of responsible people
//            Arrays.stream(riskRepo.findAll().stream().filter(risk -> risk.getResponsible().stream().anyMatch(usr ->
//                    usr.getId().equals(user.getId()))).toArray()).forEach(x->System.out.print(x));
//            data.put("risks", riskRepo.findAll());
            // Filer for risks is not needed anymore
//            data.put("risks", riskRepo.findAll().stream()
//                    .filter(risk -> risk.getResponsible().stream()
//                            .anyMatch(usr -> usr.getId().equals(user.getId()))).toArray());
            data.put("boards", boardRepo.findAll());
            data.put("users", userDetailsRepo.findAll());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
