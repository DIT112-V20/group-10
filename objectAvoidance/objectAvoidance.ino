
#include <Smartcar.h>
#include <Wire.h>
#include <VL53L0X.h>



const int fSpeed   = 30;
const int bSpeed   = -70; 
const int lDegrees = -75; 
const int rDegrees = 75; 
const int obstacleAhead  = 500; 

BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

SimpleCar car(control);

VL53L0X sensor;



void setup() {

  Serial.begin(9600);
  Wire.begin();

  

  
}

void loop() {

  Serial.println(sensor.readRangeContinuousMillimeters());
  

  
    

}
