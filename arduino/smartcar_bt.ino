
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

BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

SimpleCar car(control);
VL53L0X sensor;
BluetoothSerial bluetooth;

void setup() {
  Serial.begin(9600);
  Wire.begin();
  bluetooth.begin("Smartcar");
  }

void loop() {
 handleInput();
  Serial.println(sensor.readRangeContinuousMillimeters());
   
}
void handleInput()
{
    if (bluetooth.available()) {
           char input;
           while (bluetooth.available()) {
             input = bluetooth.read();
           }
    if (millimeterLimit > sensor.readRangeContinuousMillimeters()) {
    car.setSpeed(brake);
    }
        switch (input)
        {
        case 'l': //  go left
            car.setSpeed(forwardSpeed);
            car.setAngle(lDegrees);
            break;
        case 'r': // go right
            car.setSpeed(forwardSpeed);
            car.setAngle(rDegrees);
            break;
        case 'f': // go forward
            car.setSpeed(forwardSpeed);
            car.setAngle(0);
            break;
        case 'b': // go backward
            car.setSpeed(backSpeed);
            car.setAngle(0);
            break;
        case 's' : //stop
            car.setSpeed(0);
            car.setAngle(0);
            break;
        default: // if you receive something that you don't know, just stop
            car.setSpeed(0);
            car.setAngle(0);
        }
    }
}
