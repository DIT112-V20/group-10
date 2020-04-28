#include <Smartcar.h>
#include <Wire.h>
#include <VL53L0X.h>
#include <BluetoothSerial.h>
#include <WiFi.h>

char input;
int forwardSpeed = 30;
const int brake = 0;
const int millimeterLimit = 200;
const int backSpeed = -30;
const int lDegrees = -30; // degrees to turn left
const int rDegrees = 30;  // degrees to turn right
int accelerate = 30;
int decelerate = -30;
int diffSpeed = 5;
int currentSpeed;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const auto pulsesPerMeter = 600;
const char* ssid     = "yourssid";
const char* password = "yourpasswd";
WiFiServer server(80);

BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);
GY50 gyroscope(37);

DirectionlessOdometer leftOdometer(
    smartcarlib::pins::v2::leftOdometerPin,
    []() { leftOdometer.update(); },
    pulsesPerMeter);
DirectionlessOdometer rightOdometer(
    smartcarlib::pins::v2::rightOdometerPin,
    []() { rightOdometer.update(); },
    pulsesPerMeter);

SmartCar car(control, gyroscope, leftOdometer, rightOdometer);
VL53L0X sensor;
BluetoothSerial bluetooth;

void setup() {
  Serial.begin(9600);
  Wire.begin();
  bluetooth.begin("Group 10");
   sensor.setTimeout(500);
 Serial.println();
    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(ssid);
    WiFi.begin(ssid, password);
    
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("");
    Serial.println("WiFi connected.");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());
    
    server.begin();
   
  if (!sensor.init())
  {
    Serial.println("Failed to detect and initialize sensor!");
    while (1) {}
  }

  // Start continuous back-to-back mode (take readings as
  // fast as possible).  To use continuous timed mode
  // instead, provide a desired inter-measurement period in
  // ms (e.g. sensor.startContinuous(100)).
  sensor.startContinuous();
}

int value = 0;

void loop() {
   Serial.println(sensor.readRangeContinuousMillimeters());
    Serial.println();
    WiFiClient client = server.available();   // listen for incoming clients
    if (client) {                             // if you get a client,
    Serial.println("New Client.");           // print a message out the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    while (client.connected()) {            // loop while the client's connected
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        if (c == '\n') {                    // if the byte is a newline character

          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          if (currentLine.length() == 0) {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println();

            // the content of the HTTP response follows the header:
            client.print("Click <a href=\"/H\">here</a> to turn the LED on pin 5 on.<br>");
            client.print("Click <a href=\"/L\">here</a> to turn the LED on pin 5 off.<br>");

            // The HTTP response ends with another blank line:
            client.println();
            // break out of the while loop:
            break;
          } else {    // if you got a newline, then clear currentLine:
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }

        // Check to see if the client request was "GET /H" or "GET /L":
        if (currentLine.endsWith("GET /H")) {
          digitalWrite(5, HIGH);               // GET /H turns the LED on
        }
        if (currentLine.endsWith("GET /L")) {
          digitalWrite(5, LOW);                // GET /L turns the LED off
        }
      }
    }
    // close the connection:
    client.stop();
    Serial.println("Client Disconnected.");
  }

 handleInput();
 }

 void handleInput()
 { // handle serial input if there is any
     if (bluetooth.available())
     {
         input = bluetooth.read(); // read everything that has been received so far and log down
         while(sensor.readRangeContinuousMillimeters()<= millimeterLimit) {
              car.setSpeed(0);
             }
     }

         switch (input){
         case 'l' : // turn left
             car.setSpeed(forwardSpeed);
             car.setAngle(lDegrees);
         case 'r': // turn right
             car.setSpeed(forwardSpeed);
             car.setAngle(rDegrees);
             break;
         case 'f': // go ahead
             car.setSpeed(forwardSpeed);
             car.setAngle(0);
             break;
         case 'b': // go back
             car.setSpeed(backSpeed);
             car.setAngle(0);
             break;
         case 's': // stop the car
             car.setSpeed(brake);
             car.setAngle(0);
             break;
         case 'd': // decelerate
             currentSpeed = forwardSpeed - diffSpeed;
             car.setSpeed(currentSpeed);
             break;
         case 'a': // accelerate
             car.setSpeed(currentSpeed);
             break;
         default: // if you receive something that you don't know, just stop
             car.setSpeed(0);
             car.setAngle(0);
         }
 }
