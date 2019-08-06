package ru.aplana.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aplana.hackathon.model.Vin;
import ru.aplana.hackathon.repository.VinRepository;

import java.util.List;

@Service
public class VinUniqueService {

    @Autowired
    private VinGeneratorService generatorService;

    @Autowired
    private VinRepository repository;

    @Transactional
    public Vin generateVin(String wmi, Integer year) {
        // try to get unique vin value
        String value;
        List<Vin> vins;
        do {
            value = generatorService.generateVin(wmi, year);
            vins = repository.findByValue(value);
        } while (!vins.isEmpty());

        // create Vin object
        Vin vin = new Vin();
        vin.setValue(value);

        // save to DB
        repository.save(vin);

        return vin;
    }
}
