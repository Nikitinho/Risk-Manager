package com.nikitinho.riskmanager.controller;

import com.nikitinho.riskmanager.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("risk")
public class RiskController {
    private int counter = 4;

    private List<Map<String, String>> risks = new ArrayList<Map<String, String>>() {{
       add(new HashMap<String, String>(){{ put("id", "1");  put("text", "First Message"); }});
       add(new HashMap<String, String>(){{ put("id", "2");  put("text", "Second Message"); }});
       add(new HashMap<String, String>(){{ put("id", "3");  put("text", "Third Message"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return risks;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getRisk(id);
    }

    private Map<String, String> getRisk(@PathVariable String id) {
        return risks.stream()
                .filter(risk -> risk.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> risk) {
        risk.put("id", String.valueOf(counter++));

        risks.add(risk);

        return risk;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> risk) {
        Map<String, String> riskFromDb = getRisk(id);

        riskFromDb.putAll(risk);
        riskFromDb.put("id", id);

        return riskFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> risk = getRisk(id);

        risks.remove(risk);
    }
}
