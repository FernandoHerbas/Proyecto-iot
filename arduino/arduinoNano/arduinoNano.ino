#include <ezButton.h>
#include <stdio.h>
#include <string.h>

/// constants won't change
#define DEBOUNCE_TIME 50 //debounce time to 50 milliseconds
#define MAX_CANT_CHAR 32
const int BUTTON_PIN_01 = 7; // the number of the pushbutton pin
const int LED_PIN_01   =  4; // the number of the LED pin
const int BUTTON_PIN_02 = 8; // the number of the pushbutton pin
const int LED_PIN_02    = 5; // the number of the LED pin

/**** Pines RGB ******************************/
const int LED_PIN_RGB_01 = 3;
const int LED_PIN_RGB_02 = 6;
const int LED_PIN_RGB_03 = 9;
/**** Pin entrada audio **********************/
#define AUDIO_IN A0

 // create ezButton object that attach to pins;
ezButton button01(BUTTON_PIN_01); 
ezButton button02(BUTTON_PIN_02); 


// variables will change:
int ledState[2] = {LOW,LOW};   // the current state of LEDs
int onSerialRead[2] = {0,0};
int rgbEnable = LOW;

/**** Declaracion de funciones ***************/
String checkConnection(String&);
void checkRGBstatus(String,String);
void sendToEsp(String);

void configButtonsAndLeds();
void configRGBAudio();

void runOnOffLeds(String,String);
void runRGBLights(String,String);

void setup() {
  //Serial.begin(115200);         // initialize serial
  Serial.begin(9600);  
  configButtonsAndLeds();
  configRGBAudio();
  delay(2000);
}

void loop() {
  String queue;
  String values = checkConnection(queue);
  runOnOffLeds(values,queue);
  checkRGBstatus(values,queue);
  if(rgbEnable){
      runRGBLights(values,queue);
  }
}

String checkConnection(String &queue) {
  String values = "*";
  if(Serial.available()>0) {
    values = Serial.readStringUntil('*');
    Serial.read();
    queue  = Serial.readStringUntil('*');
    onSerialRead[0] = 1;
    onSerialRead[1] = 1;
  }  
  /*
  for(i=0; i<availableBytes; i++) {
   stringAux[i] = Serial.readStringUntil('\0');
   stringAux[i+1] = '\0'; // Append a null
   onSerialRead[0] = 1;
   onSerialRead[1] = 1;
  }*/
    
  if(onSerialRead[0] == 1) {
    /*Serial.print(values);
    Serial.print(" " + queue);*/
    onSerialRead[0] = 0;
  }
  return values;
}

void configButtonsAndLeds() {
  pinMode(LED_PIN_01, OUTPUT);   // set arduino pin to output mode
  pinMode(LED_PIN_02, OUTPUT);
  button01.setDebounceTime(DEBOUNCE_TIME);
  button02.setDebounceTime(DEBOUNCE_TIME);
}

void configRGBAudio() {
  pinMode(LED_PIN_RGB_01,OUTPUT);
  pinMode(LED_PIN_RGB_02,OUTPUT);
  pinMode(LED_PIN_RGB_03,OUTPUT);
  pinMode(AUDIO_IN,INPUT);
  digitalWrite(LED_PIN_RGB_01,LOW);
  digitalWrite(LED_PIN_RGB_02,LOW);
  digitalWrite(LED_PIN_RGB_03,LOW);
}

void runOnOffLeds(String valueSerial, String queue) {
  button01.loop(); // MUST call the loop() function first
  button02.loop();
  if(button01.isPressed() || (queue.equals("leds") && valueSerial.equals("L0") && onSerialRead[1] == 1)) {
    ledState[0] = !ledState[0];
    sendToEsp(String(ledState[0]) + "0");
    digitalWrite(LED_PIN_01, ledState[0]); 
    onSerialRead[1] = 0;
  }

  if(button02.isPressed() || (queue.equals("leds") && valueSerial.equals("L1") && onSerialRead[1] == 1)) {
    ledState[1] = !ledState[1];
    sendToEsp(String(ledState[1]) + "1");
    digitalWrite(LED_PIN_02, ledState[1]); 
    onSerialRead[1] = 0;
  } 
}

void runRGBLights(String valueSerial, String queue) {
  if(queue.equals("rgb") && onSerialRead[1] == 1) {
    int red = 0, green = 0, blue = 0;
    red   = valueSerial.toInt();
    String greenS = getValue(valueSerial, ',', 1);
    green = greenS.toInt();
    String blueS = getValue(valueSerial, ',', 2);
    blue  = blueS.toInt();
    onSerialRead[1] = 0;
    // Con constrain nos aseguramos de que el valor esté en el rango del pwm
    //Para leds de anodo común utiliza, para rojo, por ejemplo: red = 255 - constrain(red, 0, 255);
    red   = constrain(red, 0, 255);
    green = constrain(green, 0, 255);
    blue  = constrain(blue, 0, 255);
    analogWrite(LED_PIN_RGB_01, red);
    analogWrite(LED_PIN_RGB_02, green);
    analogWrite(LED_PIN_RGB_03, blue);
  }
    /*
    if (Serial.read() == '*') {
     

      Serial.print(red, HEX);
      Serial.print(green, HEX);
      Serial.println(blue, HEX);

      fade(redPin, red, red_old);
      fade(greenPin, green, green_old);
      fade(bluePin, blue, blue_old);
      
      Serial.println("Color conseguido");
      //Almacenamos los datos de color anteriores.
      red_old = red;
      green_old = green;
      blue_old = blue;
    onSerialRead[1] = 0;
  }
    
  /***** Automatic Mod ****************/
  /*switch (valueSerial) {
    case 'P':
      int volumenAudio = analogRead(AUDIO_IN);
      Serial.println(volumenAudio);
      if(volumenAudio < 515) volumenAudio = 496; 
      int variacionSalida = map(volumenAudio,496,600,0,255);
      analogWrite(LED_PIN_RGB_01,variacionSalida);
    break;
  
    case 'p':
    break;
  }
  if(valueSerial == 'P') { 
    
  }*/
  /*
  if(iSAutomaticMod) {
    int volumenAudio = analogRead(AUDIO_IN);
    Serial.println(volumenAudio);
    if(volumenAudio < 515) volumenAudio = 496; 
    int variacionSalida = map(volumenAudio,496,600,0,255);
    analogWrite(LED_PIN_RGB_01,variacionSalida);
  }
  else {
    analogWrite(LED_PIN_RGB_01,valueSerial);
    analogWrite(LED_PIN_RGB_02,valueSerial);
    analogWrite(LED_PIN_RGB_03,valueSerial);
  }*/
}

void checkRGBstatus(String value, String queue) {
  if(queue.equals("control") && onSerialRead[1] == 1) {
    if(value.equals("Y")) {
      rgbEnable = HIGH;
    }
    else if(value.equals("N")) {
      rgbEnable = LOW;
      digitalWrite(LED_PIN_RGB_01,LOW);
      digitalWrite(LED_PIN_RGB_02,LOW);
      digitalWrite(LED_PIN_RGB_03,LOW);
    }
    onSerialRead[1] = 0;
  }
}

String getValue(String data, char separator, int index) {
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

void sendToEsp(String message) {
  Serial.print(message + "*" );
}
