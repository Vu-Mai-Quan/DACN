package com.example.dacn.mapper;

import com.example.dacn.model.Destination;
import com.example.dacn.model.Source;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class MapperClass {

    @Mapping(target = "destinationId", source = "source.sourceId")
    @Mapping(target = "destinationName", expression = "java(fullName(source.getSourceFName(),source.getSourceLName()))")
    public abstract Destination toDestination(Source source);

    protected String fullName(String fName, String lName) {
        return "%s %s".formatted(fName, lName);
    }
}
