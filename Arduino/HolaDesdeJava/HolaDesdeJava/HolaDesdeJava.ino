
const int LED = 13;

void setup()
{

  Serial.begin(9600);
  pinMode(LED, OUTPUT);
  digitalWrite(13, LOW);

}

void loop()
{

  Serial.println("Hola desde arduino");
}
