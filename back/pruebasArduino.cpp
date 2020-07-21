#include<ESP8266WiFi.h>

const char* ssid = ""; //nombre wifi
const char* password = "";//Contra  wifi
const char* host = "";//Ip de la pc

void setup(){
    Serial.begin(115200);
    delay(10);

    //nos conectamos a wifi

    Serial.print("Conectando a ");
    Serial.println(ssid);

    WiFi.begin(ssid,password);

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
    Serial.println(host);

    //Se crea una instancia de WIFICLIENT
    WiFiClient cliente;

    const int puertoHTTP = 80;
    if(!cliente.connect(host,puertoHTTP)){
        Serial.println("Error de conexion.");
        return;
    }    

    String url = "http://**ip**/back/pruebasControl.php";       //Conexion al codigo php
    String datoAEnviar = "serie=LM35&temp=32.2";                //formato de envio de datos

    //Solicitud del tipo POST que se envia al servidor
    cliente.print(String("POST") + url + " HTTP/1.0\r\n" +
                 "Host : " + host + "\r\n" +
                 "Accept: *" + "/" + "*\r\n" +
                 "Content-Length: " + datoAEnviar.length() + "\r\n"
                 "Content-Type: application/x-www-form-urlencoded\r\n" +
                 "\r\n" + datoAEnviar);

    delay(10);

    //Leemos todas las lineas que nos responde el servidor y las imprimimos por pantalla
    Serial.println("Responde: ");
    while(cliente.available()){
        String line = cliente.readStringUntil('\r');
        Serial.println(line);
    }

    Serial.println("Cerrando conexion");
}