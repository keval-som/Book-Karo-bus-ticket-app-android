# Bus Ticket Booking Application
This is an Android application for booking bus tickets. It allows users to search for available buses, select seats, and book tickets. The application also includes additional features like QR code generation for booked tickets, integration with Firebase Realtime Database for data storage, and Google reCAPTCHA for bot detection.

# Table of Contents
Features\
Prerequisites\
Getting Started\
Firebase Setup\
Google reCAPTCHA Setup\

## Features
Bus search and booking functionality.\
QR code generation for booked tickets.\
Integration with Firebase Realtime Database for data storage.\
Google reCAPTCHA for bot detection during user interactions.\
## Prerequisites
Before you begin, ensure you have met the following requirements:

Android Studio installed on your development machine.\
A Firebase project set up for storing data. You can follow the Firebase documentation on how to set up a project and enable Firebase Realtime Database.\
A Google reCAPTCHA API key. You can obtain this key by visiting the Google reCAPTCHA website and following the setup instructions.
## Getting Started
To get started with the application, follow these steps:

Clone this repository to your local machine.

git clone https://github.com/your-username/your-repo.git

Open the project in Android Studio.

Configure Firebase and Google reCAPTCHA (details in the next sections).

Build and run the application on your Android device or emulator.

## Firebase Setup
Create a Firebase project on the Firebase Console.

Add an Android app to your project and follow the setup instructions to download the google-services.json file.

Place the google-services.json file in the app directory of your project.

Update the Firebase dependencies in your app's build.gradle file.

Initialize Firebase in your application by adding the following code to your Application class or the appropriate activity:

Your Firebase Realtime Database should be set up with the necessary data structure to store bus information, bookings, and user details. Make sure to update your Firebase rules to secure your data.

## Google reCAPTCHA Setup
Visit the Google reCAPTCHA website and create a new site.

Select the "reCAPTCHA v2" option and choose "I'm not a robot" Checkbox.

Add your application's domain(s) to the list of domains allowed to use reCAPTCHA.

Note down your reCAPTCHA site key and secret key.

Integrate reCAPTCHA in your Android app's booking and registration forms. You can refer to the reCAPTCHA Android documentation for implementation details.

## Usage
Launch the application on your Android device or emulator.

Users can search for available buses, select seats, and book tickets.

QR codes are generated for booked tickets, which can be used for validation during boarding.
