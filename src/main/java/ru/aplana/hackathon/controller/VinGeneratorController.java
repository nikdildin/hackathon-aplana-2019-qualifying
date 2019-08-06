package ru.aplana.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.aplana.hackathon.model.Vin;
import ru.aplana.hackathon.model.VinRequest;
import ru.aplana.hackathon.service.VinUniqueService;

import javax.validation.Valid;

@RequestMapping("/create")
@RestController
public class VinGeneratorController {

    @Autowired
    private VinUniqueService uniqueService;

    @GetMapping
    public Vin create() {
        return uniqueService.generateVin(null, null);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Vin create(@Valid @RequestBody VinRequest request) {
        return uniqueService.generateVin(request.getWmi(), request.getYear());
    }

}
