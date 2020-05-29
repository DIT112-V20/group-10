**_What have we made?_**

The Magess Delivery car is a Smartcar that can help you send things between friends and family, it is simple to use and perfect for you who are too lazy to do it yourself, or now during the covid-19 (coronavirus) pandemic avoiding personal contact as much as possible! 

Whatever you need to pick up, whether it’s groceries, parcels or medicine, it can be done through the Magess delivery system.

The service features a Smartcar and an Android application, in which users can track where their parcel will be delivered.

At the moment, the product works best for personal use. We want to expand the use cases but due to time constraints and being restricted by the current pandemic it was not feasible, for now.

**_If you would like to view a demo of the video, please click [here](add link)!_**

***

**_How can this device help us?_**

Due to the spread of covid-19, people are recommended to stay at home and avoid physical contact. It is specifically made for people with a compromised immune system, elders or disabled, where going out could put them at high risk for contracting the virus.

***
**_To get started and use this product you will need the following:_**


**Hardware**

1x Smartcar platform

(link for setup here https://github.com/platisd/smartcar_shield)

8x AA batteries

1x micro-LIDAR sensor

1x MicroUSB cable

Android Phone

**Software**

Our android application ("Magess") to control the car.

Software that can upload the sketches to the car i.e. Arduino IDE.

***
**_How to use it_**

Note: you will need some prior technical knowlegde to use the car!

First you need to add your wifi credentials in this code snippet here:

![code snippet](https://cdn.discordapp.com/attachments/712637138469912576/715561574172983377/Screenshot_from_2020-05-25_11-10-59.png)

The "ssid" should be the name of your network and the password of it should be put where the "password" is stated.
The google API key is optional, but to use the map function of the app, simply add it to the "googleApiKey".
To generate your own go to this site and follow the steps there: https://developers.google.com/maps/documentation/android-sdk/get-api-key

When the code is uploaded to the car you can now turn on the app and it will automatically connect to the car as long as it is powered on!

When starting the app you will come to this screen:


![app home screen](https://media.discordapp.net/attachments/701790165643034734/715542183482097684/startscreen.jpg?width=471&height=668)

You can either go with automatic or manual control, but also access the car's current location!

If manual control is selected you, it will take you to this screen where you can then steer the car in different directions.


![app manual control](https://media.discordapp.net/attachments/701790165643034734/715542163596771398/manualcontrol.jpg?width=321&height=668)

If you press the "current location" button this screen will show up, displaying where the car is!


![location sceen](https://media.discordapp.net/attachments/691759757404536834/715928952165761044/Skarmavbild_2020-05-29_kl._16.05.56.png?width=463&height=668)

***

**_Dependencies_**

WifiLocation https://github.com/gmag11/WifiLocation

Smartcar https://github.com/platisd/smartcar_shield

okhttp https://square.github.io/okhttp/

ESPmDNS https://github.com/espressif/arduino-esp32

Wire https://www.arduino.cc/en/reference/wire

VL53L0X https://github.com/pololu/vl53l0x-arduino

WiFi https://www.arduino.cc/en/Reference/WiFi

WebServer https://www.arduino.cc/en/Tutorial/WebServer

Arduino_JSON https://github.com/arduino-libraries/Arduino_JSON



***


**The Group 10 team:**

**M**ary Olsson Radda                     gusolsmafb@student.gu.se

**A**riana Mededovic                      gusmedar@student.gu.se

**G**ustav Skallberg                      gusskagu@student.gu.se

**E**ffat Mahmud Enti                     gusentef@student.gu.se

**S**amar Saeed                           gussaesaa@student.gu.se

**S**hahrzad Sheikholeslami               gussheish@student.gu.se 





