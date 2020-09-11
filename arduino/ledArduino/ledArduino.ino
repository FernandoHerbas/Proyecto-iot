#include "Led.h"

Led miLed(2,"miLed");
void leds();
void setup() {
    Serial.begin(9600);
    miLed.iniciar();
}

void loop() {
  if (Serial.available() > 0) {
    char variable = Serial.read();
    Serial.println(variable);
    if(variable == 'a')
      miLed.encender();
    if(variable=='b')
      miLed.apagar();
  }
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
