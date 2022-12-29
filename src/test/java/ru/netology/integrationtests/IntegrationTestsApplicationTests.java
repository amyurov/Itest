package ru.netology.integrationtests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTestsApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private GenericContainer<?> devapp = new GenericContainer<>("dev80")
            .withExposedPorts(8080);
    @Container
    private GenericContainer<?> prodapp = new GenericContainer<>("prod81")
            .withExposedPorts(8081);

//    @BeforeEach
//    void setUp() {
//        devapp.start();
//        prodapp.start();
//    }

    @Test
    void contextLoads() {
        Integer firstPort = devapp.getMappedPort(8080);
        Integer secondPort = prodapp.getMappedPort(8081);

        // т.к. Теперь мы знаем на какие порты обращаться, обратимся с гет запросом
        ResponseEntity<String> devAppEntity = restTemplate.getForEntity("http://192.168.99.100:" + firstPort + "/profile", String.class);
        ResponseEntity<String> prodAppEntity = restTemplate.getForEntity("http://192.168.99.100:" + secondPort +"/profile", String.class);

        System.out.println(devAppEntity.getBody());
        System.out.println(prodAppEntity.getBody());
    }

}