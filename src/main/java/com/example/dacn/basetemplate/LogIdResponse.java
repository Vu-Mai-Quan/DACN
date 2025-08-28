package com.example.dacn.basetemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public abstract class LogIdResponse {
    @JsonIgnore
    private UUID idLog;

}
