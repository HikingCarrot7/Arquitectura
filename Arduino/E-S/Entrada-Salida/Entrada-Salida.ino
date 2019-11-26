
static bool bandera = false;

void setup() {

  Serial.begin(9600);
  pinMode(2, INPUT);
  pinMode(3, OUTPUT);

}

void loop()
{

  if (!bandera)
  {

    int fila = 9, columna = 9;
    int** matrizCaracol = iniciarMatriz(fila, columna);

    rellenarCaracol(matrizCaracol, fila, columna);

    imprimirMatriz(matrizCaracol, fila, columna, "<-Matriz caracol->\n");

    bandera = true;

  }

}

void rellenarCaracol(int** const matriz, int filas, int columnas)
{
  int inicio = 0, final = columnas, i, n = 1, tope = filas * columnas;

  while (n <= tope)
  {

    for (i = inicio; i < final; i++)
      matriz[inicio][i] = n++;

    for (i = inicio + 1; i <= filas - 1; i++)
      matriz[i][final - 1] = n++;

    for (i = final - 2; i >= inicio; i--)
      matriz[filas - 1][i] = n++;

    for (i = filas - 2; i > inicio; i--)
      matriz[i][inicio] = n++;

    inicio++;
    final--;

    filas--;

  }

}

void imprimirMatriz(int** const matriz, int filas, int columnas, const char* const titulo)
{
  int i, j;

  char buffer[15] = "";
  sprintf(buffer , "%s", titulo);
  Serial.println(buffer);

  for (i = 0; i < filas; i++)
  {

    for (j = 0; j < columnas ; j++)
    {
      char buffer[8] = "";
      sprintf(buffer, "[%02d] ", matriz[i][j]);
      Serial.print(buffer);

    }

    Serial.println("");

  }

}

int** iniciarMatriz(const int fila, const int columna)
{
  int** matriz = (int**) malloc(fila * sizeof(int*)), i, j;

  for (i = 0; i < fila; i++)
    matriz[i] = (int*) malloc(columna * sizeof(int));

  return matriz;

}
