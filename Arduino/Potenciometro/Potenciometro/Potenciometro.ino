void setup() {

  Serial.begin(9600);
}

void loop() {

  Serial.println((int) (analogRead(3) * (255.0 / 1023.0)));

  delay(20);

}
