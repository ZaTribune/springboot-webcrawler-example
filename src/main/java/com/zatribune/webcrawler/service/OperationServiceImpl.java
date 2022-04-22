package com.zatribune.webcrawler.service;

import com.zatribune.webcrawler.db.entities.Operation;
import com.zatribune.webcrawler.db.repository.OperationRepository;
import com.zatribune.webcrawler.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OperationServiceImpl implements OperationService{


    private final OperationRepository repository;


    @Autowired
    public OperationServiceImpl(OperationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Operation getById(Long id) {
        return repository.findById(id).orElseThrow(()->new NotFoundException(String.format("No Operation found for the give id [%s]",id)));
    }

    @Override
    public Operation saveOrUpdate(Operation o) {
        return repository.save(o);
    }

    @Override
    public List<Operation> getAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).toList();
    }
}
