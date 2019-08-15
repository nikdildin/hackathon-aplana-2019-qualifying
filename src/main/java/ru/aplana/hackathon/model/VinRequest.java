package ru.aplana.hackathon.model;

import lombok.*;
import ru.aplana.hackathon.validation.WmiConstraint;

import javax.validation.constraints.Min;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VinRequest {

    @WmiConstraint
    private String wmi;

    @Min(1900)
    private Integer year;

}
