package ru.aplana.hackathon.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@NoArgsConstructor
public class Vin {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NonNull
    private Long id;

    @Column
    @NonNull
    private String value;

}