package com.skillbox.searchengine;

import com.skillbox.searchengine.controller.StatisticController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StatisticController statisticController;

    @Test
    public void statisticsTest() throws Exception {
        this.mockMvc.perform(get("/api/statistics").header("Authorization", "Basic YWRtaW46UXdlcnR5MjI="))
                .andDo(print())
                .andExpect(content().string(containsString("\"result\":")));
    }
}
