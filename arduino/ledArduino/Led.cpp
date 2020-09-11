#include "Led.h"

/*Constructor*/
Led::Led(int unPuertoSalida,String unNombre){
    puertoSalida  = unPuertoSalida;
    nombre = unNombre;
}
/*Getters y Setters*/
void Led::setPuerto(int unPuerto){
    puertoSalida = unPuerto;
}
int Led::getPuerto(){
    return puertoSalida;
}

String Led::getNombre(){
    return nombre;
}
/*Metodos*/
void Led::iniciar(){
    pinMode(puertoSalida,OUTPUT);
    digitalWrite(puertoSalida,LOW);  
}
void Led::encender(){
    digitalWrite(puertoSalida,HIGH);
}

void Led::apagar(){
    digitalWrite(puertoSalida,LOW);
}

void Led::leerEstado(){

}
