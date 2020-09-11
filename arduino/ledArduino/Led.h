#ifndef Led_h
#define Led_h

#include "Arduino.h"

class Led{
    public:
        Led(int unPuertoSalida,String unNombre);
        void iniciar();
        void encender();
        void apagar();
        void leerEstado();
        void setPuerto(int unPuerto);
        String getNombre();
        int getPuerto();
    private:
        int puertoSalida;
        String nombre;
};

#endif