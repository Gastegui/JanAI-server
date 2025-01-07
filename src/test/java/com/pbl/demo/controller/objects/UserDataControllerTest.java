package com.pbl.demo.controller.objects;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pbl.demo.model.administrator.Administrator;
import com.pbl.demo.model.administrator.AdministratorRepository;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;



@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserDataRepository userRepo;

    @Mock
    private AdministratorRepository adminRepo;

    @Test
    void testGetAdminByName_AdminFound() throws Exception {
        Administrator admin = new Administrator();
        admin.setUsername("testAdmin");

        when(adminRepo.findByUsername("testAdmin")).thenReturn(Optional.of(admin));
        
        mockMvc.perform(get("/user/adminByName")
                .param("username", "testAdmin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAdminByName_AdminNotFound() throws Exception {
        when(adminRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/adminByName")
                .param("username", "unknown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUsers_UsersFound() throws Exception {
        List<UserData> users = List.of(new UserData(), new UserData());
        when(userRepo.findAll()).thenReturn(users);

        mockMvc.perform(get("/user/show")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUsers_NoUsersFound() throws Exception {
        when(userRepo.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/user/show")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserDataById_UserFound() throws Exception {
        UserData user = new UserData();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserDataById_UserNotFound() throws Exception {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    

    @Test
    void testGetUsersByName_UserFound() throws Exception {
        UserData user = new UserData();
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/usersByUsername")
                .param("username", "username")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUsersByName_UserNotFound() throws Exception {
        when(userRepo.findByUsername("username")).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/usersByUsername")
                .param("username", "username")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddUser_UserAlreadyExists() throws Exception {
        UserData user = new UserData();
        user.setUsername("existingUser");
        when(userRepo.findByUsername("existingUser")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "existingUser"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddUser_UserAdded() throws Exception {
        UserData user = new UserData();
        user.setUsername("newUser");
        user.setUname("user");
        user.setSecondName("user");
        user.setGender("male");
        user.setAge(12);
        user.setHeight(100);
        user.setEmail("usermail@gmail.com");
        user.setUserPass("abcdfghijk1234.H");
        user.setActivityLevel("high");
        user.setPremium(false);
        user.setObjective("lose weight");
        user.setWaist(10);
        when(userRepo.findByUsername("newUser")).thenReturn(Optional.empty());
        when(userRepo.save(any(UserData.class))).thenReturn(user);

        mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "userID" : 1,
                            "uname" : "user",
                            "secondName" : "user",
                            "gender" : "male",
                            "age" : 12,
                            "height" : 100,
                            "username" : "newUser",
                            "email" : "usermail@gmail.com",
                            "userPass" : "abcdfghijk1234.H",
                            "activityLevel" : "high",
                            "premium" : false,
                            "objective" : "lose weight",
                            "waist" : 10

                        }
                        """))
                .andExpect(status().isCreated());
    }

    @Test
    void testPutUsers_UserFound() throws Exception {
        UserData user = new UserData();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(put("/user/modify/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "updatedUser"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void testPutUsers_UserNotFound() throws Exception {
        when(userRepo.findById(16)).thenReturn(Optional.empty());

        mockMvc.perform(put("/user/modify/16")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "updatedUser"
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser_UserFound() throws Exception {
        UserData user = new UserData();
        when(userRepo.findById(17)).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/user/delete/17")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser_UserNotFound() throws Exception {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/user/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
