package com.nikitinho.riskmanager.controller;

import com.nikitinho.riskmanager.domain.RiskCategoryType;
import com.nikitinho.riskmanager.domain.RiskStatusType;
import com.nikitinho.riskmanager.domain.User;
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
    private final RiskRepo riskRepo;
    private final UserDetailsRepo userDetailsRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(RiskRepo riskRepo, UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
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
            data.put("users", userDetailsRepo.findAll());
//            data.put("users", userDetailsRepo.findAll().stream().map(User::getEmail).toArray());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
