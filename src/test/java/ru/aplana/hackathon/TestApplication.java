package ru.aplana.hackathon;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.aplana.hackathon.service.VinGeneratorService;
import ru.aplana.hackathon.service.VinUniqueService;

import static org.mockito.Mockito.spy;

@SpringBootApplication
public class TestApplication {

    /* Mock Beans */

    @Bean
    public VinGeneratorService vinGeneratorService() {
        return spy(VinGeneratorService.class);
    }

    @Bean
    public VinUniqueService vinUniqueService() {
        return spy(VinUniqueService.class);
    }

}
