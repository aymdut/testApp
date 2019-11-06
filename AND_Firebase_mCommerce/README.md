# AND_Firebase_mCommerce

A sample m-commerce app to implement the Firebase Stack

## 00 - TODO 
- Make User_id as an app/user scope global
- Make all tracking dynamic

## 0 - Project History

- May 15th 2019, 2pm - Update Firebase Librairies
    - Firebase librairies have been updated to their last version

- May 15th 2019, 11am - Content Square for app SDK
    - add CS for apps SDK

- May 3rd 2018, 2pm - Prepare a package for submission
    - Comment logs
    - Sign the app
    - Create build release in the app build.gradle

- April 22th 2018, 6pm - Continue Crashlytics setup 
    - Create a continueCrashlyticsSetup branch
    - Update Google Services Plugin to v3.2
    - Update all Firebase Libraries to v15.0.0
    - Update GTM library to v15.0.0
    - Update Google Play Services to v15.0.O

- April 20th 2018, 4pm - Crashlytics setup 
    - Create a crashlyticsSetup branch
    - Setup Crashlytics SDK in the app
    - Put a crashing instruction behind the "crash" button
    - Set instruction to log user in the crashlytics platform and colelct custom events for Crashlytics

- April 20th 2018, 3pm 
    - Change the app "build.gradle" file: remove compile by implementation

- April 13th 2018, 11pm - Static Tracking setup 
    - Put a static tracking for all hits

- April 12th 2018, 1am - Firebase Dynamic Links integration
    - Add Firebase Dynamic Links SDK 
    - Add a new intent filter to the "SplashScreen" activity that handles deep links for our app
    - Ahh the code to catch the Dynamic Link into the "SplasScreen" activity
    - Add the SHA-1 & SHA-256 of the Francesco's computer to the Android App (to debug dynamic links)
    - Create a Dynamic Link on the Firebase Dynamic Links console
    - The Dynamic Links works and opens the app
    - ==> TODO : check if Re-open event is collected on the Firebase Daynamic Links console

- April 8th 2018, 8am - Funnel finished in static mode
    - Create a new "addFunnel" branch
    - Update the "Shipment" screen with its menu bar
    - Create the "Payment" screen with its radio button selectors and its menu 
    - Create the "Confirm" screen with its menu and a "refund" button
    - Update the "Basket"
        - Add a static list view with static fake products
        - Add 2 required conditons (user login status and amount > 0) to begin checkout
        - Add a "empty" button for the remove from cart tag
    - ==> So, funnel is finished in static mode and is could be exploited for a good tracking :-)
      
- April 7th 2018, 8pm - Add funnel - Part 1
    - Create a new "addFunnel" branch
    - Create the "Shipment" screen wit its radio button selectors
    - Start to create "Payment" screen

- April 7th 2018, pm - ongoing Reinsert tracking
    - Create a new "trackingSetup" branch
    - SplashScreen activity
        - Insert tracking
        - Replace the hard-coded clientId value by the FirebaseInstance id of the app
        - Replace the hard-coded deviceId value by the Android device id of the phone


- April 7th 2018, 1pm - Master updating
    - The Android project now needs Graddle v3 to manage dependencies, ccompile & buid
    - Upgrading all activities to implement 'back' button compliance and improve memory management (must reduce crash issues for memory leak purpose)

- April 2nd 2018, 8am - Clean all branches

- January 31th 2018, 12pm - Merge branch 'removeTracking' into 'master'
    - THe branch "Master" has no tracking anymore 

- September 26th 2017, 6pm - Setup Firebase Push Notif
    - Add the Firebase Push notif dependency
    - Configure the Firebase Push Notif tool
    - Update GMS to v3.1.1
    - Create the dedicated classes to get token, send and recieve notifications
    - Send a notif when the appp is in background ==> It works!
    - ToDo : 
         - Implement the code to recieve notifcations when the app is not launched or in foreground 

- September 25th, 9:30am - Test GA EEC hit through GTMv5
    - 	Write an event to track products list
 	- Adding GTM library to the app
    - Configure GTM container
    - Adding GTM container to the project
    - Configure GA property for the app
    - It works :-)

- Septemebr 13th, 3:15pm - Revamp liste & derail activities
    - Revamp list & detail activities to pass only 1 parameter (product sku) betweeen them

- Septemeber 13th, 12pm - Fix issue in product list
    - Now we have brand, price and product miniature pic

- September 9th, 8:30am - Fill product list from the database
     - "List" screen is now more generic 
     - Remove statical data from the app
     - "Promo" class does not exist anymore

- August 31st, 10:30pm - Anonymous auth linking
     - When anonymous user signes up with his email and password, his account and profile are linked with the new on (account and database)

- August 31st, 11:30am - Project structure
     - Create a models package with all data model classes

- August 29th, 5:30pm | Customize the behavior of 'connect' button regarding to the user status

- August 29th, 4:30pm | Create anonymous user freshly autenticated in the database
     - Create a "User.java" class which allow to create a user and write it to the Firebase database
     - Create a "Utils.java" class to init and use the Firebase database
     - Update the "splashscreen" screen to write the new user in the database after a n Anonymous authentication.

- August 29th, 12:15pm | Invert Database & 1st launch check
     - Invert get database and 1st launch check 
     - Add toast to display network, auth and database status on splashscreen

- August 29th, 11:30am | Add connectivity check at start
     - If connection is available, check if it's the first run to launch Anonymous auth. 
     - If not, display HP directly

- August 29th, 1:00am | Change remote database rules & Update AnonymousAuth
     - Change database rules to get categories and products if user is authenticated or not
     - Change databese rules to get users nodes only readable and writeable by authenticated users themselves only
     - Update AnonymousAuth to set it on the first launch only

- August 28th, 4:30pm | Test database
     - Database test is ok. Get the mail of user 'f.khouryat@gmail.com

- August 25th - 5:30pm | Add anomyous auth during the splashscreen to get default database content 

- August 24th, 2017 - 1:00 pm | Crash reporting (merge the 'crashreport' branch to 'master')
     - Setup the Firebase Crash Report SDK
     - Set a fatal error crash behind te "crash" button in the "Informations" class
     - Test and good tracking on the project crash dashboard

- August 23rd, 2017 - 6:00pm | Design database
     - Add the database model scheme on Github repo

- August 10th, 2017 - 4:00pm | Design database
     - Design the database
     - Transcript the database as "Firebase_mCommerce_originDatabase.json" file 
     - Fill the database
     - Import the json file to Firebase Real Time Database console
     
- August 10th, 2017 - 12:00pm | Update architectures scheme
     - Update scheme with "login", "signup" and "resetpassword" screens

- August 10th, 2017 - 11:30am | Implement authentication system
     - Add "firebase-auth" and "fuitebase-ui-auth" dependencies to the app gradle
     - Create "login", "signup" and "resetpassword" screens

- August 7th, 2017 - 3:00pm | Install Firebase SDK
     - Import "google-services.json" configuration file in the project 	
     - Add google play services dependencies to root gradle 	
     - Add Firebase core dependencies to the app gradle

- August 7th, 2017 -  10:45am | Firebase project creation
     - Create the "55 Firebase m-Commerce" project on Firebase console (project id: 
project-5176900217331074858)
     - Declare the "55 Firebase m-Commerce" android app in the project (app id: 1:596167132252:android:45a45979843f1dbf
and package name: fiftyfive.and_firebase_mcommerce)

- August 6th, 2017 - 8:15am | Add the "Cart" screen 
     - Create and design  the "Cart" screen with its listview adapter and menu
     - Update "Informations" screen to set the menu
     - Add an icon for the app

- August 4th, 2017 - 3:30pm | Improve intent between liste/promo and detail screen to pass products parameters 

- August 3rd, 2017 - 8:15pm | Add promotional banner in the home page of the app and create the promo screen with jewelery products

- August 3rd, 2017 - 6:45pm | Add the png file to illustrate the target architecture of the app

- August 3rd, 2017 - 3:00pm | Add the "Legal" screen which embeds a webview

- August 3rd, 2017 - 12:00am | Add the menu bar to detail screen

- July 28th, 2017 - 4:00am | Add a menu bar to homepage, list and informations screen

- July 28th, 2017 - 2:00am | Add a list view in the list screen

- July 27th, 2017 - 5:15pm | Duplicate the AND_SampleAppVierge repository in AND_Firebase_mCommerce and rename the project in Android Studio
------

## 1 - Application target architecture

![App target architecture](https://gitlab.55labs.com/factory/AND_Firebase_mCommerce/blob/master/Firebase%20R&D%C2%A0-%20mCommerce%20app%20Architecture%20cible.png "App target architecture")
------

## 2 - Application database model

![App database model](https://gitlab.55labs.com/factory/AND_Firebase_mCommerce/blob/master/55%20Firebase%20m-Commerce%20%7C%C2%A0Database%20Model.png "App database model")
