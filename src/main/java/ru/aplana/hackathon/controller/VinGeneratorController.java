package ru.aplana.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.aplana.hackathon.model.Vin;
import ru.aplana.hackathon.repository.VinRepository;
import ru.aplana.hackathon.service.VinGeneratorService;

import java.util.List;

@RequestMapping("/")
@RestController
public class VinGeneratorController {

    @Autowired
    private VinGeneratorService service;

    @Autowired
    private VinRepository repository;

    @Transactional
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public Vin create(
            @PathVariable(value = "wmi", required = false) String wmi,
            @PathVariable(value = "year", required = false) Integer year
    ) {
        // try to get unique vin value
        String value;
        List<Vin> vins;
        do {
            value = service.generateVin(wmi, year);
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
