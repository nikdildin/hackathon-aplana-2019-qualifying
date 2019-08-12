package ru.aplana.hackathon.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

@Service
public class VinGeneratorService {

    private final String WMI = "WBZ";
    private final int YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private Random random = new Random();

    public String generateVin(String wmi, Integer year) {
        String[] parts = {
                Objects.nonNull(wmi) ? wmi.toUpperCase() : WMI,
                "0",
                converYear(Objects.nonNull(year) ? year : YEAR)
        };
        long r = System.currentTimeMillis();
        int part = 0;
        int pos = 0;
        for (int i = 0; i < 12; i++) {
            pos++;
            if (i == 5) {
                r = random.nextInt(Integer.MAX_VALUE);
                part = 2;
                pos = 0;
            }
            int j = (int) (r % Math.pow(ConstantsProvider.SYMBOLS.length, pos + 1));
            r -= j;
            j = (int) (j / Math.pow(ConstantsProvider.SYMBOLS.length, pos));
            parts[part] += ConstantsProvider.SYMBOLS[j];
        }
        String vin = String.join("", parts);
        int result = 0;
        int index = 0;
        for (char c : vin.toCharArray()) {
            result += ConstantsProvider.DIGITS[indexOf(ConstantsProvider.SYMBOLS, c)] * ConstantsProvider.MASKS[index++];
        }
        result %= 11;
        parts[1] = result > 9 ? "X" : String.valueOf(ConstantsProvider.SYMBOLS[result]);
        return String.join("", parts);
    }

    private String converYear(int year) {
        return String.valueOf(ConstantsProvider.YEARS[year % 30]);
    }

    private int indexOf(char[] a, char c) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == c) {
                return i;
            }
        }
        return 0;
    }

}
