#include "Led.h"

#define LUZ1 2

void leds();
void setColor(int,int,int);
void setup() {
  Serial.begin(115200);
  pinMode(LUZ1,OUTPUT);
  digitalWrite(LUZ1,LOW);
}

void loop() {
  if(Serial.available()){
    //String data = Serial.readString();
    //Serial.println(data);
    char value  = Serial.read();
    switch(value) {
      case 'a':digitalWrite(LUZ1,HIGH);break;
      case 'A':digitalWrite(LUZ1,LOW);break;
      default:break;
  }
}

void setColor(int red, int green, int blue){
  analogWrite(redPin,red);
  analogWrite(greenPin,green);
  analogWrite(bluePin,blue);
}
/*
void leds(){
    int estado=0;
    if(boton.estado() == HIGH || datoSerial == 'a'){
      miLed.encender();
      estado=1;
      notificarAlServidor();
    }
    if(boton.estado() == HIGH || datoSerial == 'b'){
      estado=2;
    }
    if(estado == 2){
      miLed.apagar();
      estado=1;
      notificarAlServidor();
    }
}*/
