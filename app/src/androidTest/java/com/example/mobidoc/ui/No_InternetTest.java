package com.example.mobidoc.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.adapters.adapterPatientUpcoming;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.ui.dashboards.Doctor_Dashboard;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class No_InternetTest {
    @Rule
    public ActivityScenarioRule<No_Internet> activityScenarioRule = new ActivityScenarioRule<>(No_Internet.class);

    @Test
    public void testInUserAcceptanceCriteria(){
        activityScenarioRule.getScenario().onActivity(activity -> {

        });
    }

}