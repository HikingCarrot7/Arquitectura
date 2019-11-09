const int ledPin = 13;

void setup() {

  Serial.begin(9600);
  pinMode(ledPin, OUTPUT);



}

void loop() {

  static int delayPeriod = 100;
  static int countDir = 1;

  digitalWrite(ledPin, HIGH);
  delay(delayPeriod);
  digitalWrite(ledPin, LOW);
  delay(delayPeriod);

  countDir = checkDirChange(delayPeriod, countDir);
  
  delayPeriod += 100 * countDir;

  Serial.print("New wait time : ");
  Serial.println(delayPeriod);
  
}

int checkDirChange(int delayPeriod, int countDir)
{
  if(delayPeriod == 1000 || delayPeriod == 0)
    countDir *= -1;

    Serial.println(countDir < 0 ? "Bajando" : "Subiendo");

    return countDir;
}
