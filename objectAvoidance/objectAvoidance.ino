
#include <Smartcar.h>
#include <Wire.h>
#include <VL53L0X.h>

const int forward = 30;
const int brake = 0;
const int millimeterLimit = 300;

BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

SimpleCar car(control);
VL53L0X sensor;

void setup() {
  Serial.begin(9600);
  Wire.begin();}

void loop() {
  Serial.println(sensor.readRangeContinuousMillimeters());
  while(sensor.readRangeContinuousMillimeters()> millimeterLimit ) {
    car.setSpeed(forward);
  }
  car.setSpeed(brake);
   
}
