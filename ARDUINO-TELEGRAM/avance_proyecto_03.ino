#include <SoftwareSerial.h>
SoftwareSerial blue(18,19); 
 
int Vresistor = A0; 
float Vdata = 0; 
 
void setup(){ 
   pinMode(Vresistor, INPUT); 
   Serial.begin(9600); 
   blue.begin(9600); 
   pinMode(2, INPUT);
   pinMode(3, OUTPUT);
} 
void loop(){ 
   Vdata = analogRead(Vresistor)/169.0 + 36.0; 
   
   if(digitalRead(2) == 0){
      digitalWrite(3,HIGH);
      blue.print(Vdata);
      delay(500);
      digitalWrite(3,LOW);
   }
}