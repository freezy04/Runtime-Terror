package com.example.mobidoc;

import org.junit.Test;
import static org.junit.Assert.*;

//following this guide - https://developer.android.com/training/testing/unit-testing/local-unit-tests#run

public class Registration_Testing {
    @Test
    public void validateLname_ReturnsTrue() {
        Register r = new Register();
        assertTrue(r.validateFname("name", false));
    }
    @Test
    public void validateLname_ReturnsTFalse() {
        Register r = new Register();
        assertFalse(r.validateFname("", false));
    }
}
