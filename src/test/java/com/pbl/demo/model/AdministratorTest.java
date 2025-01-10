package com.pbl.demo.model;

import org.junit.jupiter.api.Test;

import com.pbl.demo.model.administrator.Administrator;

import static org.junit.jupiter.api.Assertions.*;

public class AdministratorTest {
    @Test
    void testDefaultConstructor(){
        Administrator admin = new Administrator();

        //Assert
        assertNotNull(admin);
        assertEquals(0, admin.getAdminID());
        assertNull(admin.getEmail());
        assertNull(admin.getUsername());
        assertNull(admin.getSurname());
        assertNull(admin.getUname());
        assertNull(admin.getUserPass());
    }

    @Test
    void testConstructorWithAllParameters(){
        int adminId = 19;
        String adminName = "admin";
        String adminSurname = "admin";
        String email = "admin@gmail.com";
        String username = "admin";
        String pass = "12345678";

        Administrator admin = new Administrator(adminId, adminName, adminSurname, username, email, pass);

        assertEquals(adminId, admin.getAdminID());
        assertEquals(adminName, admin.getUname());
        assertEquals(adminSurname, admin.getSurname());
        assertEquals(email, admin.getEmail());
        assertEquals(pass, admin.getUserPass());
        assertEquals(username, admin.getUsername());
    }

    @Test
    void testConstructorWithoutId(){
        String adminName = "admin";
        String adminSurname = "admin";
        String email = "admin@gmail.com";
        String username = "admin";
        String pass = "12345678";

        Administrator admin = new Administrator(adminName, adminSurname, username, email, pass);

        assertEquals(adminName, admin.getUname());
        assertEquals(adminSurname, admin.getSurname());
        assertEquals(email, admin.getEmail());
        assertEquals(pass, admin.getUserPass());
        assertEquals(username, admin.getUsername());
    }

    @Test
    void testGettersAndSetters(){
        Administrator admin = new Administrator();
        int adminId = 19;
        String adminName = "admin";
        String adminSurname = "admin";
        String email = "admin@gmail.com";
        String username = "admin";
        String pass = "12345678";

        admin.setAdminID(adminId);
        admin.setEmail(email);
        admin.setSurname(adminSurname);
        admin.setUname(adminName);
        admin.setUserPass(pass);
        admin.setUsername(username);

        assertEquals(adminId, admin.getAdminID());
        assertEquals(email, admin.getEmail());
        assertEquals(adminSurname, admin.getSurname());
        assertEquals(adminName, admin.getUname());
        assertEquals(pass, admin.getUserPass());
        assertEquals(username, admin.getUsername());
    }
}
