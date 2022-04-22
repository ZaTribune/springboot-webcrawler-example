package com.zatribune.webcrawler.service;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CrudService <T,I>{

    T getById(I id);

    T saveOrUpdate(T o);
    List<T> getAll(PageRequest pageRequest);
}
