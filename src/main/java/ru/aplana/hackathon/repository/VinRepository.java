package ru.aplana.hackathon.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aplana.hackathon.model.Vin;

import java.util.List;

public interface VinRepository extends CrudRepository<Vin, Long> {
    List<Vin> findByValue(String value);
}
