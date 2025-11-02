package com.example.dacn.mapper;

import com.example.dacn.model.Destination;
import com.example.dacn.model.Source;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class MapperClass {

    @Mapping(target = "destinationId", source = "source.sourceId")
    @Mapping(target = "destinationName", source = "source.sourceName")
    public abstract Destination toDestination(Source source);
}
