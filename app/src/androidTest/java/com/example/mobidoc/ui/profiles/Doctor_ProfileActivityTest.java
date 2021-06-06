package com.example.mobidoc.ui.profiles;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.R;

import org.junit.Rule;
import org.junit.Test;

//work please :(
public class Doctor_ProfileActivityTest {

   @Rule
   public ActivityScenarioRule<Doctor_ProfileActivity> activityScenarioRule = new ActivityScenarioRule<>(Doctor_ProfileActivity.class);

   @Test
   public void getDoctorDetails() {
       activityScenarioRule.getScenario().onActivity(activity -> {
          activity.getDoctorDetails("JAKLyVL37YV3PM0HZoUa8EhFWth2");
       });
   }
    @Test
    public void clickHomeNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.nav_home2);
        });
    }

    @Test
    public void clickPatientRecordsNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar( R.id.nav_pateintrecords);
        });

    }

    @Test
    public void clickProfilenNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.nav_profile2);
        });

    }

    @Test
    public void clickPendingTest() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.nav_pendingappointments2);
        });
    }
    @Test
    public void clickAcceptanceTestNavBarItem2() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar( R.id.nav_acceptedappointments2);
        });
    }





 @Test
       public void onBackPressed() {

               activityScenarioRule.getScenario().onActivity(activity -> {

                       activity.onSupportNavigateUp();
                       activity.onBackPressed();

               });
       }

//    @Test
//    public void clickOnNavBar_consultation() {
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            activity.ClickOnNavBar(R.id.menu_consultation);
//        });
//    }

//    @Test
//    public void clickOnNavBar_appointment() {
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            activity.ClickOnNavBar(R.id.menu_appointments);
//        });
//    }

//    @Test
//    public void clickOnNavBar_profle() {
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            activity.ClickOnNavBar(R.id.menu_profile);
//        });
//    }

//    @Test
//    public void clickOnNavBar_home() {
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            activity.ClickOnNavBar(R.id.menu_home);
//        });
//    }
}
