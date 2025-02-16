package com.bankpay.membership.adapter.in.web;

import com.bankpay.membership.domain.Membership;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterMembershipControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterMembership() throws Exception {
        RegisterMembershipRequest request = new RegisterMembershipRequest("name","address","email",false);

        Membership expect=Membership.generateMember(
                new Membership.MembershipId("1"),
                new Membership.MembershipName("name"),
                new Membership.MembershipEmail("email"),
                new Membership.MembershipAddress("address"),
                new Membership.MembershipIsValid(true),
                new Membership.MembershipIsCorp(false),
                new Membership.MembershipRefreshToken("")
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/membership/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expect)));
    }



}