void setup() {

  Serial.begin(9600);
}

void loop() {

  //Serial.println(() + "," + (random(40) + 10));

  char buffer[15] = "";
  sprintf(buffer, "%d,%d,%d,%d", (int) (analogRead(A0) * (255.0 / 1023.0)), (random(40) + 10), 0, 0);
  Serial.println(buffer);

  delay(300);

}
