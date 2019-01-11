package com.amadeus.ist.springrest.member;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerUnitTest {
    private final Logger logger = Logger.getLogger(MemberControllerUnitTest.class.getName());

    private List<MemberDTO> memberDTOS;

    @Autowired
    private MockMvc mockMvc; // powerful way of testing MVC controllers without starting full HTTP server

    @MockBean
    private MemberService memberService; // providing mock implementations for required dependencies

    @Before
    public void initMocks() {
        MemberDTO memberDTO = new MemberDTO(new ObjectId(), "1078546324578", "ekin","yucel",500,"AC");
        MemberDTO memberDTO2 = new MemberDTO(new ObjectId(), "1097042349412", "john","doe",1500,"AC");
        memberDTOS = new CopyOnWriteArrayList<>();
        memberDTOS.add(memberDTO);
        memberDTOS.add(memberDTO2);
    }

    @Test
    public void returningListOfMembers_whenCallingRetrieveMembers() {
        Mockito.when(memberService.retrieveMembers()).thenReturn(memberDTOS.stream().map(MemberDTO::toConvertMember).collect(Collectors.toList()));
        try {
            mockMvc.perform(get("/retrieveMembers")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}
