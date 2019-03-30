package com.nikitinho.riskmanager.controller;

import com.nikitinho.riskmanager.domain.RiskCategoryType;
import com.nikitinho.riskmanager.domain.RiskStatusType;
import com.nikitinho.riskmanager.domain.User;
import com.nikitinho.riskmanager.repo.RiskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

@Controller
@RequestMapping("/")
public class MainController {
    private final RiskRepo riskRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(RiskRepo riskRepo) {
        this.riskRepo = riskRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);
            data.put("categories", Stream.of(RiskCategoryType.values()).map(RiskCategoryType::toString).toArray());
            data.put("statuses", Stream.of(RiskStatusType.values()).map(RiskStatusType::toString).toArray());
            data.put("risks", riskRepo.findAll());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
