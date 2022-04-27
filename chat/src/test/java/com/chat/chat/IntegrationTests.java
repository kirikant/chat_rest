package com.chat.chat;


import com.chat.chat.entity.Role;
import com.chat.chat.entity.Status;
import com.chat.chat.entity.UserEntity;
import com.chat.chat.repositories.UserRepository;
import com.chat.chat.security.JwtTokenProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        UserEntity user = new UserEntity();
        user.setLogin("userTest");
        user.setName("userTest");
        user.setPassword(passwordEncoder.encode("userTest"));
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }

    @After
    public void resetDb() {
        UserEntity user = userRepository.findByLogin("userTest").get();
        userRepository.delete(user);
    }

    @Test
    public void testSetJwtTokenProviderUser() throws Exception {
        UserEntity user = userRepository.findByLogin("userTest").get();
        String token = jwtTokenProvider.createToken(user.getLogin(), user.getRole().name());
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all?page=0").header("Authorization", token))
                .andExpect(status().is2xxSuccessful());
    }

//    @Test
//    public void testNotForUser() throws Exception {
//        UserEntity user = userRepository.findByLogin("userTest").get();
//        String token = jwtTokenProvider.createToken(user.getLogin(), user.getRole().name());
//        Authentication authentication = jwtTokenProvider.getAuthentication(token);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        mockMvc.perform(MockMvcRequestBuilders.put("/message/admin/"+user.getId()+"/update")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .header("Authorization", token))
//                .andExpect(status().isForbidden());
//    }

}
