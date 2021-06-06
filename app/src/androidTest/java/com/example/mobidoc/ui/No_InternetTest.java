package com.example.mobidoc.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class No_InternetTest {
  @Rule
  public ActivityScenarioRule<No_Internet> activityScenarioRule = new ActivityScenarioRule<>(No_Internet.class);

  @Test
   public void testInUserAcceptanceCriteria(){
     activityScenarioRule.getScenario().onActivity(activity -> {

     });
  }

}
