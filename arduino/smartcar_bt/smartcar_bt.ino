#include <Smartcar.h>
#include <Wire.h>
#include <VL53L0X.h>
#include <BluetoothSerial.h>

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
}

void loop() {
 Serial.println(sensor.readRangeContinuousMillimeters());
 if(sensor.readRangeContinuousMillimeters()< millimeterLimit) {
  car.setSpeed(brake);
 }
 handleInput();
 }

 void handleInput()
 { // handle serial input if there is any
     if (bluetooth.available())
     {
         char input = bluetooth.read(); // read everything that has been received so far and log down
                                     // the last entry
         switch (input)
         {
         case 'l': // rotate counter-clockwise going forward
             car.setSpeed(forwardSpeed);
             car.setAngle(lDegrees);
             break;
         case 'r': // turn clock-wise
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
 }