package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.Estado;
import com.grossgym.fitness.repository.EstadoRepository;
import com.grossgym.fitness.service.EstadoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoServiceImpl(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    @SuppressWarnings("null")
	@Override
    @Transactional(readOnly = true)
    public Optional<Estado> findById(Integer id) {
        return estadoRepository.findById(id);
    }

    @SuppressWarnings("null")
	@Override
    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    @SuppressWarnings("null")
	@Override
    public void deleteById(Integer id) {
        estadoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estado> findByHabilitado(Boolean habilitado) {
        return estadoRepository.findByHabilitado(habilitado);
    }
}

