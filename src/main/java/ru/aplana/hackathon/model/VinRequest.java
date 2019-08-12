package ru.aplana.hackathon.model;

import lombok.Data;
import ru.aplana.hackathon.validation.WmiConstraint;

import javax.validation.constraints.Min;

@Data
public class VinRequest {

    @WmiConstraint
    private String wmi;

    @Min(1900)
    private Integer year;

}
