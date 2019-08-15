package ru.aplana.hackathon.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.aplana.hackathon.TestApplication;
import ru.aplana.hackathon.model.Vin;
import ru.aplana.hackathon.repository.VinRepository;
import ru.aplana.hackathon.service.ConstantsProvider;
import ru.aplana.hackathon.service.VinGeneratorService;
import ru.aplana.hackathon.service.VinUniqueService;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class GeneratorTest {

    private static final Integer YEAR_EXAMPLE_OK = 2005;
    private static final String YEAR_EXAMPLE_LETTER = "5";
    private static final String WMI_EXAMPLE_OK = "ABC";
    private static final String VIN_EXAMPLE_1 = "WBZFLDAF1K9XY6XK1";
    private static final String VIN_EXAMPLE_2 = "WBZ49NFFXKB4AFR80";

    @Autowired
    private VinRepository vinRepository;

    @Autowired
    private VinGeneratorService vinGeneratorService;

    @Autowired
    private VinUniqueService vinUniqueService;

    @Before
    public void init() {
        Mockito.reset(vinGeneratorService);
        vinRepository.deleteAll();
    }

    /**
     * Проверка корректности генерируемого VIN
     *
     * @throws Exception
     */
    @Test
    public void generateTestCheckValidVin() throws Exception {

        // Генерируем VIN
        Vin vin = vinUniqueService.generateVin(null, null);

        // Проверим полученный VIN
        Assert.assertNotNull(vin);
        Assert.assertNotNull(vin.getValue());
        Assert.assertEquals("Wrong VIN length", 17, vin.getValue().length());
        Assert.assertTrue("Incorrect VIN symbols", ConstantsProvider.checkSymbols(vin.getValue()));

        // Проверка состояния БД
        Assert.assertEquals(1, vinRepository.count());
        Assert.assertEquals(1, vinRepository.findByValue(vin.getValue()).size());

        // Проверка обращения к сервису генерации с верными параметрами
        verify(vinGeneratorService, times(1)).generateVin(null, null);
    }

    /**
     * Проверка соответствия генерируемого VIN переданным параметрам
     *
     * @throws Exception
     */
    @Test
    public void generateTestCheckAcceptableVin() throws Exception {

        // Генерируем VIN
        Vin vin = vinUniqueService.generateVin(WMI_EXAMPLE_OK, YEAR_EXAMPLE_OK);

        // Проверим полученный VIN
        Assert.assertNotNull(vin);
        Assert.assertNotNull(vin.getValue());
        Assert.assertEquals("Wrong WMI", WMI_EXAMPLE_OK, vin.getValue().substring(0, 3));
        Assert.assertEquals("Wrong year", YEAR_EXAMPLE_LETTER, vin.getValue().substring(9, 10));

        // Проверка состояния БД
        Assert.assertEquals(1, vinRepository.count());
        Assert.assertEquals(1, vinRepository.findByValue(vin.getValue()).size());

        // Проверка обращения к сервису генерации с верными параметрами
        verify(vinGeneratorService, times(1)).generateVin(WMI_EXAMPLE_OK, YEAR_EXAMPLE_OK);
    }

    /**
     * Проверка уникальности генерируемого VIN
     *
     * @throws Exception
     */
    @Test
    public void generateTestCheckUniqueVin() throws Exception {

        // Добавим в БД VIN
        Vin vin = new Vin();
        vin.setValue(VIN_EXAMPLE_1);
        vinRepository.save(vin);

        // Настроим mock на выдачу этого VIN в первый раз и выдачу другого VIN во второй раз
        AtomicInteger ordinal = new AtomicInteger(0);
        when(vinGeneratorService.generateVin(any(), any())).thenAnswer(invocation ->
                {
                    switch (ordinal.getAndIncrement()) {
                        case 0:
                            return VIN_EXAMPLE_1;
                        case 1:
                            return VIN_EXAMPLE_2;
                        default:
                            return null;
                    }
                }
        );

        // Генерируем VIN
        Vin newVin = vinUniqueService.generateVin(null, null);

        // Проверка обращения к сервису генерации дважды
        verify(vinGeneratorService, times(2)).generateVin(null, null);

        // Проверка состояния БД
        Assert.assertEquals(2, vinRepository.count());
        Assert.assertEquals(1, vinRepository.findByValue(VIN_EXAMPLE_1).size());
        Assert.assertEquals(1, vinRepository.findByValue(VIN_EXAMPLE_2).size());

    }
}
