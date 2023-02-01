package com.movie.tickets.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.tickets.ServiceApplication;
import com.movie.tickets.model.TicketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketsControllerITest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper= new ObjectMapper();

    @Test
    public void shouldCreateTicket1() throws Exception {
        String requestJson="{" +
                "    \"transactionId\": 1," +
                "    \"customers\": [" +
                "        {" +
                "            \"name\": \"John Smith\"," +
                "            \"age\": 70" +
                "        }," +
                "        {" +
                "            \"name\": \"Jane Doe\"," +
                "            \"age\": 5" +
                "        }," +
                "        {" +
                "            \"name\": \"Bob Doe\"," +
                "            \"age\": 6" +
                "        }" +
                "    ]" +
                "}";
        String responseJson = "{\"id\":1,\"totalCost\":27.50,\"tickets\":[{\"ticketType\":\"Children\",\"quantity\":2,\"totalCost\":10.00},{\"ticketType\":\"Senior\",\"quantity\":1,\"totalCost\":17.50}]}";
        createTicket(requestJson, responseJson);
    }

    @Test
    public void shouldCreateTicket2() throws Exception {
        String requestJson="{\n" +
                "    \"transactionId\": 2,\n" +
                "    \"customers\": [\n" +
                "        {\n" +
                "            \"name\": \"Billy Kidd\",\n" +
                "            \"age\": 36\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Zoe Daniels\",\n" +
                "            \"age\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"Name\": \"George White\",\n" +
                "            \"Age\": 8\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Tommy Anderson\",\n" +
                "            \"age\": 9\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Joe Smith\",\n" +
                "            \"age\": 17\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String responseJson = "{\"id\":2,\"totalCost\":48.25,\"tickets\":[{\"ticketType\":\"Adult\",\"quantity\":1,\"totalCost\":25.00},{\"ticketType\":\"Children\",\"quantity\":3,\"totalCost\":11.25},{\"ticketType\":\"Teen\",\"quantity\":1,\"totalCost\":12.00}]}";
        createTicket(requestJson, responseJson);
    }

    @Test
    public void shouldCreateTicket3() throws Exception {
        String requestJson="{\n" +
                "    \"transactionId\": 3,\n" +
                "    \"customers\": [\n" +
                "        {\n" +
                "            \"name\": \"Jesse James\",\n" +
                "            \"age\": 36\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Daniel Anderson\",\n" +
                "            \"age\": 95\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Mary Jones\",\n" +
                "            \"age\": 15\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Michelle Parker\",\n" +
                "            \"age\": 10\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String responseJson = "{\"id\":3,\"totalCost\":59.50,\"tickets\":[{\"ticketType\":\"Senior\",\"quantity\":1,\"totalCost\":17.50},{\"ticketType\":\"Adult\",\"quantity\":1,\"totalCost\":25.00},{\"ticketType\":\"Teen\",\"quantity\":1,\"totalCost\":12.00},{\"ticketType\":\"Children\",\"quantity\":1,\"totalCost\":5.00}]}";
        createTicket(requestJson, responseJson);
    }
    private void createTicket(String requestJson, String responseJson) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/ticket/create")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultDOW = result.getResponse().getContentAsString();
        assertNotNull(resultDOW);
        TicketResponse response = objectMapper.readValue(resultDOW, TicketResponse.class);
        TicketResponse expectedResponse = objectMapper.readValue(responseJson, TicketResponse.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}