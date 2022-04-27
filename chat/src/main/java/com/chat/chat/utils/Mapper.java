package com.chat.chat.utils;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Component;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    private final ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public <S, T> List<T> mapAll(Collection<? extends S> sourceCollection, Class<T> targetClass) {
        return sourceCollection.stream()
                .map(x -> map(x, targetClass))
                .collect(Collectors.toList());
    }

}
