**_What have we made?_**

The Magess Delivery car is a Smartcar that can help it's user to send things to friends and family. It is simple to use and perfect for those that are not able to deliver parcel themselves (e.g. now during the covid-19 (coronavirus) pandemic, avoiding personal contact as much as possible!).

Whatever one needs to pick up, whether it’s groceries, parcels, medicine (or candy around the office!), it can be done through the Magess delivery system.

The service features a Smartcar and an Android application, in which users can track where their parcel is currently located at.

At the moment, the product works best for personal use. We want to expand the use cases but due to time constraints and being restricted by the current pandemic it was not feasible, for the time being.

**_If you would like to view a demo of the video, please click [here!](add link)_**

***

**_How can this device help us?_**

Due to the spread of covid-19, people are recommended to stay at home and avoid physical contact. It is specifically made for people with a compromised immune system, elders or handicapped, where going out could put them at high risk for contracting the virus.

***
**_To get started and use this product you will need the following:_**


**Hardware**

1x Smartcar platform (Link for everything needed can be found [here!](https://github.com/platisd/smartcar_shield))

8x AA batteries

1x micro-LIDAR sensor

1x MicroUSB cable

Android Phone

**Software**

Our android application ("Magess") to control the car.

Software that can upload the sketches to the car i.e. [Arduino IDE.](https://www.arduino.cc/en/main/software)

***
**_How to use it_**

First the user needs to add their wifi credentials in this code snippet below here:

![code snippet](https://cdn.discordapp.com/attachments/712637138469912576/715943938850291853/Skarmavbild_2020-05-29_kl._17.04.51.png)

The "ssid" should be the name of the network being used, and the password of it should be simply put where "password" is stated in quotationmarks.
The google API key is optional, but to use the map function of the app, one has to simply add it to the "googleApiKey".
To generate one's own key, go to [this site](https://developers.google.com/maps/documentation/android-sdk/get-api-key) and follow the steps provided.

When the code is uploaded to the car the user can now turn on the app and it will automatically connect to the car as long as it is powered on!

When starting the app one will come to this screen:


![app home screen](https://media.discordapp.net/attachments/701790165643034734/715542183482097684/startscreen.jpg?width=471&height=668)

The user can either choose to go with automatic or manual control, but he can also decide to access the car's current location!

Once one selects "manual control", it will take them to this screen where one can steer the car in different directions themselves.


![app manual control](https://media.discordapp.net/attachments/701790165643034734/715542163596771398/manualcontrol.jpg?width=321&height=668)

If the user presses the "current location" button, this screen will show up, displaying where the car is!


![location sceen](https://media.discordapp.net/attachments/691759757404536834/715928952165761044/Skarmavbild_2020-05-29_kl._16.05.56.png?width=463&height=668)

***

**_Dependencies_**

[WifiLocation](https://github.com/gmag11/WifiLocation)

[Smartcar](https://github.com/platisd/smartcar_shield)

[OkHttp](https://square.github.io/okhttp/)

[ESPmDNS](https://github.com/espressif/arduino-esp32)

[Wire](https://www.arduino.cc/en/reference/wire)

[VL53L0X](https://github.com/pololu/vl53l0x-arduino)

[WiFi](https://www.arduino.cc/en/Reference/WiFi)

[WebServer](https://www.arduino.cc/en/Tutorial/WebServer)

[Arduino_JSON](https://github.com/arduino-libraries/Arduino_JSON)


***

**_Resources_**

*Used from Github* : [Map codes](https://gist.github.com/saxman/5347195)

[Some other codes used](https://stackoverflow.com/questions/7064857/making-an-android-map-menu-to-change-map-type)

*Codes from StackOverflow helped in fixing various bugs related to the app*


***


**The Group 10 team:**

**M**ary Olsson Radda                     gusolsmafb@student.gu.se

**A**riana Mededovic                      gusmedar@student.gu.se

**G**ustav Skallberg                      gusskagu@student.gu.se

**E**ffat Mahmud Enti                     gusentef@student.gu.se

**S**amar Saeed                           gussaesaa@student.gu.se

**S**hahrzad Sheikholeslami               gussheish@student.gu.se 





