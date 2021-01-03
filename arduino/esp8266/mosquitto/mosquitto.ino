#include <ESP8266WiFi.h>
#include <PubSubClient.h>

// Update these with values suitable for your network.

const char* ssid = "TeleCentro-ef39";
const char* password = "1164440382";
const char* mqtt_server = "192.168.0.67";
const char* mqtt_user = "fer";
const char* mqtt_password = "42737740";

WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
char msg[50];
int value = 0;

void setup() {
  //pinMode(BUILTIN_LED, OUTPUT);     // Initialize the BUILTIN_LED pin as an output
  Serial.begin(115200);
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
  if(value.equals("led1on"))
      Serial.write('a');
  if(value.equals("led1off"))
      Serial.write('A');
  if(value.equals("led2on"))
      Serial.write('b');
  if(value.equals("led2off"))
      Serial.write('B');
  if(value.equals("led3on"))
      Serial.write('c');
  if(value.equals("led3off"))
      Serial.write('C');
  if(value.equals("led4on"))
      Serial.write('d');
  if(value.equals("led4off"))
      Serial.write('D');
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("ESP8266Client")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      //client.publish("luces/estado", "hello world init");
      // ... and resubscribe
      client.subscribe("luces/led");
      //client.subscribe("luces/rgb");
      
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
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
  //Serial.println(msg);
  /*long now = millis();
  if (now - lastMsg > 2000) {
    lastMsg = now;
    ++value;
    snprintf (msg, 75, "hello world #%ld", value);
    client.subscribe("LED");
    //Serial.print("Publish message: ");
    Serial.println(msg);
    //client.publish("outTopic", msg);
  }*/
}
