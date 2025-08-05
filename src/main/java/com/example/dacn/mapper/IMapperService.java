/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.util.function.Consumer;

/**
 * @author ADMIN
 */
public interface IMapperService {

    <S, D> D mapperObject(S source, Class<D> out, Consumer<ModelMapper> consumer);

    <S, D> D mapperObject(S source, Class<D> out);
}
