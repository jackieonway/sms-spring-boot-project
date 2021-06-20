package com.example.demo.sms.service;

import com.example.demo.sms.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class SubMailServiceImplTest extends BaseTest {
    @Autowired
    private SubMailServiceImpl service;

    @Test
    public void testSendTemplateSms() {
        service.sendTemplateSms(PHONE_NO, "AWW2P3");
    }
}
