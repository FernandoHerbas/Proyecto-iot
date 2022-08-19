#include <ESP8266WiFi.h>
#include <PubSubClient.h>

// Update these with values suitable for your network.

const char* ssid = "TeleCentro-ef39";
const char* password = "";
const char* mqtt_server = "";
const char* mqtt_user = "fer";                                                                            
const char* mqtt_password = "";
#define MAX_CANT_CHAR 32

/**** Global variables ******/
WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
char msg[50];

/**** Topics to subscribe definition *************/
const char *LIVING_LEDS = "living/leds";
const char *LIVING_RGB  = "living/rgb";
const char *LIVING_RGB_CONTROL  = "living/control";

/**** Topics to publish definition *************/
const char *LIVING_BOTONES = "living/botones";

/**** Function declarations *********/
void suscripciones();
void readArduinoNanoAndPublish();
char decodificationsOfmessages(); 

void setup() {
  //Serial.begin(115200);
  Serial.begin(9600);
  Serial.setDebugOutput(true);
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void setup_wifi() {

  delay(10);
  // We start by connecting to a WiFi network 
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  String value;
  for (int i = 0; i < length; i++) {
      value += (char)payload[i];
  }
  String queue = getValue(topic, '/', 1);
  Serial.print(value + queue + "*");
  /*char value[length];
  int i;
  for (i = 0; i < length; i++) {
      value[i] = (char)payload[i];
      value[i+1] = '*'; // Append a null
      //Serial.print((char)payload[i]);
  } 
  Serial.print(value);
  //int result = strcmp(value, "oscar");
  //Serial.println(result);*/
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    // Attempt to connect
    if (client.connect("ESP8266Client")) {
      Serial.print("OK");
      suscripciones();
      
    } else {
      Serial.print("fail, wait 5 seg");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}
void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
  readArduinoNanoAndPublish();
}

void suscripciones() {
  client.subscribe(LIVING_LEDS);
  client.subscribe(LIVING_RGB);
  client.subscribe(LIVING_RGB_CONTROL);
}

void readArduinoNanoAndPublish() {
  String valueAux = "*";
  char values[MAX_CANT_CHAR];
  if(Serial.available()>0) {
    valueAux = Serial.readStringUntil('*');
    valueAux.toCharArray(values,MAX_CANT_CHAR);
    client.publish(LIVING_BOTONES,values);
  } 
}

String getValue(String data, char separator, int index)
{
    int found = 0;
    int strIndex[] = { 0, -1 };
    int maxIndex = data.length() - 1;

    for (int i = 0; i <= maxIndex && found <= index; i++) {
        if (data.charAt(i) == separator || i == maxIndex) {
            found++;
            strIndex[0] = strIndex[1] + 1;
            strIndex[1] = (i == maxIndex) ? i+1 : i;
        }
    }
    return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}

char decodificationsOfmessages(String value) {
  if(value.equals("00"))
      return 'a';
  if(value.equals("01"))
      return 'A';
  if(value.equals("10"))
      return 'b';
  if(value.equals("11"))
      return 'B';
  if(value.equals("20"))
      return 'c';
  if(value.equals("21"))
      return 'C';
  if(value.equals("30"))
      return 'd';
  if(value.equals("31"))
      return 'D';
}
