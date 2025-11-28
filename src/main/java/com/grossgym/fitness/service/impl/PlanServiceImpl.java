package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.Plan;
import com.grossgym.fitness.repository.PlanRepository;
import com.grossgym.fitness.service.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    @SuppressWarnings("null")
	@Override
    @Transactional(readOnly = true)
    public Optional<Plan> findById(Integer id) {
        return planRepository.findById(id);
    }

    @SuppressWarnings("null")
	@Override
    public Plan save(Plan plan) {
        return planRepository.save(plan);
    }

    @SuppressWarnings("null")
	@Override
    public void deleteById(Integer id) {
        planRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plan> findByTipoPlan(String tipoPlan) {
        return planRepository.findByTipoPlan(tipoPlan);
    }
}

