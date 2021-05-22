package com.example.mobidoc.ui.Appointment;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class BookingTest {

        @Rule
        public ActivityScenarioRule<Booking> activityScenarioRule = new ActivityScenarioRule<>(Booking.class);

        @Test
        public void Diplaytime_Booking_BookingUnsucceful(){
                activityScenarioRule.getScenario().onActivity(activity -> activity.mDisplayTime.performClick());
        }

        @Test
        public void DiplayDate_Booking_BookingUnsucceful(){
                activityScenarioRule.getScenario().onActivity(activity -> activity.mDisplayDate.performClick());
        }

        @Test
        public void registerDoctor_DoctorNotRegistered_RegistrationSuccessful(){
                activityScenarioRule.getScenario().onActivity(activity ->{
                        activity.mDisplayDate.setText("06/01/2021");
                        activity.mDisplayTime.setText("13:32");
                        activity.Reason.setText("rgkgnkjgsn");
                        activity.mBook.performClick();
                });
        }

        @Test
        public void Booking_BookingUnsucceful(){
            activityScenarioRule.getScenario().onActivity(activity -> activity.mBook.performClick());
        }
}

