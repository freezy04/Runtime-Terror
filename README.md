  # Mobile Patient Tracker
- #### Product Scope 
      - develop a mobile application for viewing and managing patient data
      - data comprises of, patient name, disease history, medication administered, date of arrival, date of appointment, consultation and treatment cost, etc
      - once the data is saved onto the system it will be stored for the doctor's perusal
       
- #### Function
      - the doctors should be able to log in to the platform and enter relevant information about their patient
      - the doctors should be able to upgrade the system continually by entering the latest medical data of their patients when a patient consults with the doctor
      - the doctors are able to accept or reject a booking request made by a patient
      
- #### Purpose
      -  the purpose of this app is to help doctors see the medical history of their patients without having to search for a patients paper records
      -  the appication will also assist patients to book appointments with an array of doctors
      -  MobiDoc will also allow doctors to manage their appointment by accepting or rejecting a patient's booking request
      -  this means the doctors save more time and focus on the task at hand -- treating an ill person and saving lives
      -  when the patient arrives for a consultaion, the doctor can just quickly look up said patient's profile containing their medical history and write up the appropriate treatment

# Requirements
- #### User
  - The user needs to have an android device and internet connection

- #### Deverloper
  - Android Studio 4.1.3 (recommended)
  - Built-in Android Studio emulator or an android device to run the app. The android device needs to have the setting in developer mode with USB debugging enabled USB cable.

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
    - Clone the project **Runtime-Terror**
  - #####  Step 2
      - Assemble he files on Android Studio
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
 - As this is a test-driven developent, we utilized TravisCI to build and test the app on GitHub. Codecov was another testing tool used to measure the percentage of code that was tested and which parts that were not tested. The following are badges that show the testing progress of our project.



[![Build Status](https://travis-ci.com/freezy04/Runtime-Terror.svg?branch=master)](https://travis-ci.com/freezy04/Runtime-Terror)
[![codecov](https://codecov.io/gh/freezy04/Runtime-Terror/branch/master/graph/badge.svg?token=Y53OOEBQ4F)](https://codecov.io/gh/freezy04/Runtime-Terror)
