# Mobile Patient Tracker
- #### Product Scope
- #### Function
- #### Purpose

# Requirements
- #### User
  - The user needs to have and android device and am internet connection/access

- #### Deverloper
  - Android Studio 4.1.3 (recommended)
  - Built-in Android Studio emulator or an android device to run the app. The latteer needs to have the setting in developer mode with USV debuggin enabled USB cable.

# Programmimg languages
- Java

# Build and Run
- #### Tutorials for setting up Android Studio
  - Introduction to Android Studio: https://developer.android.com/studio
  - Guide on creating your first project:
https://developer.android.com/training/basics/firstapp/creating-project
  - Documents for Android Studio: https://developer.android.com/docs

- #### Instructions
  - ##### Step 1
    - Clone project **Runtime-Terror**
  - #####  Step 2
      - Assemble files on Android Studio
    - Select **Build -&gt; Make Project**
    - Additional libraries containing Android API references:
https://developer.android.com/reference
  - ##### Step 3
    - Instructions running the app
        - ##### Emulator
          - (recomended) At least 8GB free of RAM space and an Intel processor of Core i5
          - Select **Run -&gt; Run app**
          - The emulator will then pop up shortly
          - With version 4.1.3 of Android Studio, there is default emulator meaning the developer does not have to choose from a list of emulator devices
          - Learn more: https://developer.android.com/studio/run/emulator
        - ##### Android Device
          - Alternatively, one can run the app on an Android device.
          - To install, connect the device to the computer with a USB cable.
          - Select **Run-&gt;Run app** on Android Studio
          - If there are mulitple devices connected to Android Studio, select the specific device from which you would like to run the project.
          - **Learn more:** https://developer.android.com/studio/run;https://developer.android.com/studio/build

 # Testing
 - As this is a test-driven developent, we utilized TravisCI to build and test the ap on GitHub. Codecov was another testing tool used to measure the percentage of code that was tested and which parts that were not tested. Below are badges that show the testing progress of our project.



[![Build Status](https://travis-ci.com/freezy04/Runtime-Terror.svg?branch=master)](https://travis-ci.com/freezy04/Runtime-Terror)
[![codecov](https://codecov.io/gh/freezy04/Runtime-Terror/branch/master/graph/badge.svg?token=Y53OOEBQ4F)](https://codecov.io/gh/freezy04/Runtime-Terror)
