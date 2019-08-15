package ru.aplana.hackathon.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.aplana.hackathon.TestApplication;
import ru.aplana.hackathon.model.VinRequest;
import ru.aplana.hackathon.repository.VinRepository;
import ru.aplana.hackathon.service.VinGeneratorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class CreateTest {

    private static final String URL_TEMPLATE = "/create";
    private static final String VIN_EXAMPLE = "WBZFLDAF1K9XY6XK1";
    private static final Integer YEAR_EXAMPLE_OK = 2005;
    private static final Integer YEAR_EXAMPLE_ERROR = 1812;
    private static final String WMI_EXAMPLE_OK = "ABC";
    private static final String WMI_EXAMPLE_ERROR1 = "AA";
    private static final String WMI_EXAMPLE_ERROR2 = "AAAA";
    private static final String WMI_EXAMPLE_ERROR3 = "IOQ";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VinRepository vinRepository;

    @Autowired
    private VinGeneratorService vinGeneratorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        Mockito.reset(vinGeneratorService);
        vinRepository.deleteAll();
    }

    /**
     * Проверка GET-запроса. Позитивный сценарий
     *
     * @throws Exception
     */
    @Test
    public void createTestGetPositive() throws Exception {

        // Настройка моков
        when(vinGeneratorService.generateVin(any(), any())).thenReturn(VIN_EXAMPLE);

        // Запрос и проверки ответа
        mvc.perform(get(URL_TEMPLATE))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("value").value(VIN_EXAMPLE));

        // Проверка состояния БД
        Assert.assertEquals(1, vinRepository.count());
        Assert.assertEquals(1, vinRepository.findByValue(VIN_EXAMPLE).size());

        // Проверка обращения к сервису генерации с верными параметрами
        verify(vinGeneratorService, times(1)).generateVin(null, null);
    }

    private void createTestPostPositive(final VinRequest request) throws Exception {

        // Настройка моков
        when(vinGeneratorService.generateVin(any(), any())).thenReturn(VIN_EXAMPLE);

        mvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("value").value(VIN_EXAMPLE));

        // Проверка состояния БД
        Assert.assertEquals(1, vinRepository.count());
        Assert.assertEquals(1, vinRepository.findByValue(VIN_EXAMPLE).size());

        // Проверка обращения к сервису генерации с верными параметрами
        verify(vinGeneratorService, times(1)).generateVin(request.getWmi(), request.getYear());

    }

    /**
     * Проверка POST-запроса. Позитивный сценарий, верно указаны все параметры
     *
     * @throws Exception
     */
    @Test
    public void createTestPostPositive1() throws Exception {

        // Параметры запроса
        VinRequest request = VinRequest.builder()
                .wmi(WMI_EXAMPLE_OK)
                .year(YEAR_EXAMPLE_OK)
                .build();

        createTestPostPositive(request);

    }

    /**
     * Проверка POST-запроса. Позитивный сценарий, верно указан год, wmi пропущен
     *
     * @throws Exception
     */
    @Test
    public void createTestPostPositive2() throws Exception {

        // Параметры запроса
        VinRequest request = VinRequest.builder()
                .year(YEAR_EXAMPLE_OK)
                .build();

        createTestPostPositive(request);

    }

    /**
     * Проверка POST-запроса. Позитивный сценарий, верно указан wmi, год пропущен
     *
     * @throws Exception
     */
    @Test
    public void createTestPostPositive3() throws Exception {

        // Параметры запроса
        VinRequest request = VinRequest.builder()
                .wmi(WMI_EXAMPLE_OK)
                .build();

        createTestPostPositive(request);

    }

    private void createTestPostNegative(final VinRequest request) throws Exception {
        // Настройка моков
        when(vinGeneratorService.generateVin(any(), any())).thenReturn(VIN_EXAMPLE);

        mvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

        // Проверка состояния БД
        Assert.assertEquals(0, vinRepository.count());

        // Проверка отсутствия обращения к сервису генерации
        verify(vinGeneratorService, never()).generateVin(any(), any());
    }

    /**
     * Проверка POST-запроса. Негативный сценарий, недостаточно символов в wmi
     *
     * @throws Exception
     */
    @Test
    public void createTestPostNegative1() throws Exception {

        // Параметры запроса
        VinRequest request = VinRequest.builder()
                .wmi(WMI_EXAMPLE_ERROR1)
                .year(YEAR_EXAMPLE_OK)
                .build();

        createTestPostNegative(request);

    }

    /**
     * Проверка POST-запроса. Негативный сценарий, слишком много символов в wmi
     *
     * @throws Exception
     */
    @Test
    public void createTestPostNegative2() throws Exception {

        // Параметры запроса
        VinRequest request = VinRequest.builder()
                .wmi(WMI_EXAMPLE_ERROR2)
                .year(YEAR_EXAMPLE_OK)
                .build();

        createTestPostNegative(request);

    }

    /**
     * Проверка POST-запроса. Негативный сценарий, недопустимые символы в wmi
     *
     * @throws Exception
     */
    @Test
    public void createTestPostNegative3() throws Exception {

        // Параметры запроса
        VinRequest request = VinRequest.builder()
                .wmi(WMI_EXAMPLE_ERROR3)
                .year(YEAR_EXAMPLE_OK)
                .build();

        createTestPostNegative(request);

    }

    /**
     * Проверка POST-запроса. Негативный сценарий, некорректный год
     *
     * @throws Exception
     */
    @Test
    public void createTestPostNegative4() throws Exception {

        // Параметры запроса
        VinRequest request = VinRequest.builder()
                .wmi(WMI_EXAMPLE_OK)
                .year(YEAR_EXAMPLE_ERROR)
                .build();

        createTestPostNegative(request);

    }

}
