package ru.aplana.hackathon.model;

import lombok.Data;
import ru.aplana.hackathon.validation.WmiConstraint;
import ru.aplana.hackathon.validation.YearConstraint;

@Data
public class VinRequest {

    @WmiConstraint
    private String wmi;

    @YearConstraint
    private Integer year;

}
