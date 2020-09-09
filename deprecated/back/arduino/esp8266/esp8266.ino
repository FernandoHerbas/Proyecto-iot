#include<ESP8266WiFi.h>
#include <WiFiClient.h>

/***************VARIABLES Y CONSTANTES GLOBALES************************************/
const char* SSID_I   = "TeleCentro-ef39"; //nombre wifi
const char* PASSWORD = "1164440382";//Contra  wifi
const char* HOST_I   = "192.168.0.250";//Ip de la pc
/***************DECLARACION DE FUNCIONES*******************************************/
//String recibirDatos();
String enviarDatos(WiFiClient cliente, String dato, String url);
void setup(){
    Serial.begin(115200);
    delay(10);

    //nos conectamos a wifi

    Serial.print("Conectando a ");
    Serial.println(SSID_I);

    WiFi.begin(SSID_I,PASSWORD);

    while(WiFi.status() != WL_CONNECTED){
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.println("Conectado al wifi");
    Serial.println("Direccion IP: ");
    Serial.println(WiFi.localIP());
}

void loop(){
    delay(2000);

    Serial.print("Conectando a ");
    Serial.println(HOST_I);

    //Se crea una instancia de WIFICLIENT
    WiFiClient cliente;

    const int puertoHTTP = 80;
    if(!cliente.connect(HOST_I,puertoHTTP)){
        Serial.println("Error de conexion.");
        return;
    }    

    String url = "http://192.168.0.250/proyecto-domotica/back/leds.php";       //Conexion al codigo php
    String datoAEnviar = "dato=LM35";                //formato de envio de datos

    String respuesta = enviarDatos(cliente,datoAEnviar,url);
    Serial.print(respuesta);
    Serial.println("Cerrando conexion");

    
}

String enviarDatos(WiFiClient cliente, String dato, String url){
  //Solicitud del tipo POST que se envia al servidor
    cliente.print(String("POST ") + url + " HTTP/1.0\r\n" +
                 "Host: " + HOST_I + "\r\n" +
                 "Accept: *" + "/" + "*\r\n" +
                 "Content-Length: " + dato.length() + "\r\n" +
                 "Content-Type: application/x-www-form-urlencoded\r\n" +
                 "\r\n" + dato);

    delay(10);
    
    //Leemos todas las lineas que nos responde el servidor y las imprimimos por pantalla
    //Serial.println("Responde: ");
    String linea;
    while(cliente.available()){
        linea = cliente.readStringUntil('\r');
        //Serial.println(linea);
    }
    return linea;
}
/*
String recibirDatos(WiFiClient client){
    String linea = "error";
    if(!client.connect(HOST_I, 80)){
        Serial.println("Fallo de conexion");
        return linea;
    }
    while(client.available()){
        linea = client.readStringUntil('\r');
    }
    Serial.println(linea);
    return linea;
    //int indice = respuesta.lastIndexOf('8'); obtengo el indice del ultimo 8
    //String dato = respuesta.substring(indice); obtengo el string a partir de ese indice.

    //Serial.println(dato);
}*/
