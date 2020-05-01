#include <Smartcar.h>
#include <Wire.h>
#include <VL53L0X.h>
#include <WiFi.h>

char input;
int forwardSpeed = 10;
int backSpeed = -10;
int brake = 0;
int millimeterLimit = 200;
int lDegrees = -10; // degrees to turn left
int rDegrees = 10;  // degrees to turn right
int accelerate = 10;
int decelerate = -10;
int diffSpeed = 5;
int currentSpeed;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const auto pulsesPerMeter = 600;
const char* ssid     =  "ssid";
const char* password = "password";
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

void setup() {
  Serial.begin(9600);
  Wire.begin();
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
            client.print("Click <a href=\"/F\">here</a> to turn on the car.<br>");
            client.print("Click <a href=\"/S\">here</a> to turn off the car.<br>");
            client.print("Click <a href=\"/L\">here</a> to turn left with the car.<br>");
            client.print("Click <a href=\"/R\">here</a> to turn right with the car.<br>");
            client.print("Click <a href=\"/B\">here</a> to go backwards with the car.<br>");
            client.print("Click <a href=\"/A\">here</a> to increase car-speed.<br>");
            client.print("Click <a href=\"/D\">here</a> to decrease car-speed.<br>");

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
        // Check to see if the client request was "GET /F" or "GET /S":
        if (currentLine.endsWith("GET /F")) {
          car.setSpeed(forwardSpeed);         // GET /F makes the car run forward
        }
        if (currentLine.endsWith("GET /S")) {
          car.setSpeed(brake);                // GET /S makes the car stop
        }
        if (currentLine.endsWith("GET /B")) {
          car.setSpeed(backSpeed);            // GET /B makes the car go backward
        }
        if (currentLine.endsWith("GET /L")) {
          car.setSpeed(lDegrees);             // GET /L makes the car go to the left
        }
        if (currentLine.endsWith("GET /R")) {
          car.setSpeed(rDegrees);             // GET /R makes the car go to the right
        }
        if (currentLine.endsWith("GET /A")) {
          car.setSpeed(accelerate);           // GET /A makes the car accelerate
        }
        if (currentLine.endsWith("GET /D")) {
          car.setSpeed(decelerate);           // GET /D makes the car decelerate
        }
         if(sensor.readRangeContinuousMillimeters()< 250){
        car.setSpeed(0); }
      }
    }
     
      }
    // close the connection:
    client.stop();
    Serial.println("Client Disconnected.");
    }
    
