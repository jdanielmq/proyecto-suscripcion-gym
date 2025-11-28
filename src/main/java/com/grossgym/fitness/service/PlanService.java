package com.grossgym.fitness.service;

import com.grossgym.fitness.model.Plan;
import java.util.List;
import java.util.Optional;

public interface PlanService {
    List<Plan> findAll();
    Optional<Plan> findById(Integer id);
    Plan save(Plan plan);
    void deleteById(Integer id);
    List<Plan> findByTipoPlan(String tipoPlan);
}

