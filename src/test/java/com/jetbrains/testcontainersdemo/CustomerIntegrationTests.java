package com.jetbrains.testcontainersdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CustomerIntegrationTests {


    private static MySQLContainer container = new MySQLContainer("mysql:latest");
/*

    @Container
    private static RabbitMQContainer rabbit=new RabbitMQContainer("rabbitmq:latest");
*/




    @Autowired
    private CustomerDao customerDao;

    @BeforeAll
    static void setup(){
        container.start();
    }

    @DynamicPropertySource
    public static void overRideProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.username",container::getUsername);
        registry.add("spring.datasource.password",container::getPassword);
    }

    @Test
    void when_using_a_clean_db_this_should_be_empty() {
        container.withClasspathResourceMapping("application.properties","/tmp/application.properties", BindMode.READ_ONLY);
        //Integer port=container.getMappedPort(3306);
        //System.out.println(port);
        List<Customer> customers = customerDao.findAll();
        assertThat(customers).hasSize(2);
    }

    @Test
    void when_using_a_clean_db_this_should_be_empty2() {
        List<Customer> customers = customerDao.findAll();
        assertThat(customers).hasSize(2);
    }
}
