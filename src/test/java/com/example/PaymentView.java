package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentView {

    @LocalServerPort
    int port;


    @Before
    public void setUp() throws Exception {
        Configuration.browser = WebDriverRunner.FIREFOX;
        Configuration.baseUrl = "http://localhost:" + port;
    }


    @Test
    public void userPaymentTest() {

    }
}
