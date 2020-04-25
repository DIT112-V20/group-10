#include <Smartcar.h>
#include <Wire.h>
#include <VL53L0X.h>
#include <BluetoothSerial.h>

char input;
const int forwardSpeed = 30;
const int brake = 0;
const int millimeterLimit = 300;
const int backSpeed = -30;
const int lDegrees = -30; // degrees to turn left
const int rDegrees = 30;  // degrees to turn right
int accelerate = 30;
int decelerate = -30;
const unsigned long PRINT_INTERVAL = 100;
unsigned long previousPrintout = 0;
const auto pulsesPerMeter = 600;

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

void loop() {
   Serial.println(sensor.readRangeContinuousMillimeters());
    Serial.println();

 handleInput();
 }

 void handleInput()
 { // handle serial input if there is any
     if (bluetooth.available())
     {
         input = bluetooth.read(); // read everything that has been received so far and log down
         while(sensor.readRangeContinuousMillimeters()<= 200){
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
         default: // if you receive something that you don't know, just stop
             car.setSpeed(0);
             car.setAngle(0);
         }
 }