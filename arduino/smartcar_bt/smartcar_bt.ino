#include <Smartcar.h>
#include <Wire.h>
#include <VL53L0X.h>
#include <WiFi.h>
#include <ESPmDNS.h>

#include <WebServer.h>
#include <Arduino_JSON.h>
#include <WifiLocation.h>

char input;
int forwardSpeed = 40;
int backSpeed = -30;
int turningSpeed= 35;
int brake = 0;
int millimeterLimit = 350;
int lDegrees = -60; // degrees to turn left
int rDegrees = 60;  // degrees to turn right
int straight = 0;  // going straight
int changeSpeed = 15;

const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const auto pulsesPerMeter = 600;

const char* ssid     =  "ssid";
const char* password = "password";
const char* googleApiKey = "noworrieswecreatedanewapikey";


WifiLocation location(googleApiKey);
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

void objectAvoid() {
    if(sensor.readRangeContinuousMillimeters()< millimeterLimit){ 
        car.setSpeed(brake);              // stop,
        delay(500);                       // wait 0,5 secs,
        car.setSpeed(backSpeed);          // go backwards
        delay(2000);                      // for 2 secs,
        car.setSpeed(brake);              // brake,
        delay(500);                       // wait 0,5 secs,
        car.setAngle(70);                 // turn right 70 degrees,
        car.setSpeed(forwardSpeed);       // go forward
        delay(500);
        car.setAngle(straight);
        }
  }

void setup() {
  Serial.begin(115200);
  Wire.begin();
  sensor.setTimeout(500);

    WiFi.mode(WIFI_MODE_STA);
    WiFi.begin(ssid, password);

    Serial.println("");
    Serial.print("Connecting to ");
    Serial.println(ssid);
    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print("Attempting to connect to WPA SSID: ");
        Serial.println(ssid);
         Serial.print("Status = ");
        Serial.println(WiFi.status());
    }

    location_t loc2 = location.getGeoFromWiFi();

    Serial.println("Location request data");
    Serial.println(location.getSurroundingWiFiJson());
    Serial.println("Latitude: " + String(loc2.lat, 7));
    Serial.println("Longitude: " + String(loc2.lon, 7));
    Serial.println("Accuracy: " + String(loc2.accuracy));

    Serial.println("");
    Serial.println("WiFi connected.");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());

    if (MDNS.begin("magess")) {

      Serial.println("magess.local is up");
    }

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

   objectAvoid();
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

            
          client.println("<!DOCTYPE HTML>");
          client.println("<html>");
          client.println("<body>");
          client.println("<center><h1>Magess</h1></center>");
          client.println("<center><h4>Group #10</h4></center>");
          client.println("<center><h5>Our delivery car</h5><br style = “line-height:8;”><br></center>");
          client.println("</body>");

         // add a meta refresh tag, so the browser pulls again every 5 seconds:
         client.println("<meta http-equiv=\"refresh\" content=\"5\">");
         client.println("</html>");
         
            // the content of the HTTP response follows the header:
            client.print("<center><button><a href=\"/F\">Forward</a></button> <br style = “line-height:2;”><br></center>");
            client.print("<center><button><a href=\"/S\">Stop</a></button> <br style = “line-height:2;”><br></center>");
            client.print("<center><button><a href=\"/L\">Left</a></button> <br style = “line-height:2;”><br></center>");
            client.print("<center><button><a href=\"/R\">Right</a></button> <br style = “line-height:2;”><br></center>");
            client.print("<center><button><a href=\"/B\">Backwards</a></button> <br style = “line-height:2;”><br></center>");
            client.print("<center><button><a href=\"/M\">Get the current location</a></button> <br style = “line-height:2;”><br></center>");
            client.print("<center><button><a href=\"/A\">Accelerate</a></button> <br style = “line-height:2;”><br></center>");
            client.print("<center><button><a href=\"/D\">Deccelerate</a></button> <br style = “line-height:2;”><br>");
            client.print("<center><button><a href=\"/G\">Automatic driving</a></button> <br style = “line-height:2;”><br></center>");

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
        // Check to see what the client request consist of:
        
        if (currentLine.endsWith("GET /F")) {
          car.setAngle(straight);
          car.setSpeed(forwardSpeed);                                         // GET /F makes the car run forward
          Serial.println(sensor.readRangeContinuousMillimeters());
        }
        if (currentLine.endsWith("GET /S")) {
          car.setAngle(straight);
          car.setSpeed(brake);                                                // GET /S makes the car stop
          Serial.println(sensor.readRangeContinuousMillimeters());
        }
        if (currentLine.endsWith("GET /B")) {
          car.setSpeed(brake);
          delay(500);
          car.setAngle(straight);
          car.setSpeed(backSpeed);                                             // GET /B makes the car go backward
        }
        if (currentLine.endsWith("GET /L")){
          car.setAngle(lDegrees);
          car.setSpeed(turningSpeed);
          delay(2500);
          car.setAngle(straight);
          car.setSpeed(forwardSpeed);
                                                                               // GET /L makes the car go to the left
        }
        if (currentLine.endsWith("GET /R")) {
          car.setAngle(rDegrees);
          car.setSpeed(turningSpeed);
          delay(2500);
          car.setAngle(straight);
          car.setSpeed(forwardSpeed);
                                                                               // GET /R makes the car go to the right
        }
        if (currentLine.endsWith("GET /M")){
        client.println("HTTP/1.1 200 OK");
        client.println("Content-type:text/html");
        client.println();

        location_t loc = location.getGeoFromWiFi();
        
        client.print("Lat: " + String(loc.lat, 7) + "\n");
        client.print("Lon: " + String(loc.lon, 7) + "\n");                     // GET /M prints the location of the car

        }
         if (currentLine.endsWith("GET /A")){
          car.setSpeed(forwardSpeed + changeSpeed);
                                                                               // GET /A makes the car accelerate
         }
         if (currentLine.endsWith("GET /D")){
          car.setSpeed(forwardSpeed - changeSpeed);
                                                                               // GET /D makes the car decelerate
         }
        if (currentLine.endsWith("GET /G")){
          
          while (sensor.readRangeContinuousMillimeters()> millimeterLimit) {
          car.setSpeed(forwardSpeed);                                          // GET /G makes the car run automatically
          car.setAngle(straight);
          }
    }
  }
  }
  
    // close the connection:
    client.stop();
    }
    }
