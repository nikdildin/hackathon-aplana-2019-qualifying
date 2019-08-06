package ru.aplana.hackathon.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VinGeneratorService {

    public String generateVin(String wmi, Integer year) {
        // TODO
        return UUID.randomUUID().toString();
    }

}
