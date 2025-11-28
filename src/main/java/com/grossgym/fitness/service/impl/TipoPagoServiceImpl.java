package com.grossgym.fitness.service.impl;

import com.grossgym.fitness.model.TipoPago;
import com.grossgym.fitness.repository.TipoPagoRepository;
import com.grossgym.fitness.service.TipoPagoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoPagoServiceImpl implements TipoPagoService {

    private final TipoPagoRepository tipoPagoRepository;

    public TipoPagoServiceImpl(TipoPagoRepository tipoPagoRepository) {
        this.tipoPagoRepository = tipoPagoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoPago> findAll() {
        return tipoPagoRepository.findAll();
    }

    @SuppressWarnings("null")
	@Override
    @Transactional(readOnly = true)
    public Optional<TipoPago> findById(Integer id) {
        return tipoPagoRepository.findById(id);
    }

    @SuppressWarnings("null")
	@Override
    public TipoPago save(TipoPago tipoPago) {
        return tipoPagoRepository.save(tipoPago);
    }

    @SuppressWarnings("null")
	@Override
    public void deleteById(Integer id) {
        tipoPagoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoPago> findByEstado(Boolean estado) {
        return tipoPagoRepository.findByEstado(estado);
    }
}

