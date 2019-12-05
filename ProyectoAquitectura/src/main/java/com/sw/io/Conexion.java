package com.sw.io;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.sw.controller.SerialDataController;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author Carlos Álvarez Trejo.
 */
public class Conexion implements SerialPortEventListener
{

    public static int DELAY_CONEXION = 300; // Retraso de conexión.
    private final String PUERTO = "COM4"; // Puerto.
    private PanamaHitek_Arduino ino;
    private SerialDataController dataController;

    public Conexion(SerialDataController dataController)
    {

        ino = new PanamaHitek_Arduino();

        this.dataController = dataController;

    }

    /**
     * Iniciamos la conexión con el puerto serial.
     */
    public void iniciarConexion()
    {

        new Thread(() ->
        {

            try
            {

                ino.arduinoRX(PUERTO, 115200, this); // Configuramos los 115200 baudios.

            } catch (ArduinoException | SerialPortException ex)
            {
                System.out.println(ex.getMessage());
            }

        }).start();

    }

    /**
     *
     * Cada vez que reciben datos del puerto serial se llama a este método.
     *
     * @param serialPortEvent El objeto que se crea cuando se leen datos del puerto.
     *
     */
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent)
    {

        try
        {

            if (ino.isMessageAvailable()) // Si datos que leer, los leemos.
            {
                dataController.gestionarSerialData(ino.printMessage()); // Obtenemos los datos y se los pasamos al controlador.
                ino.flushSerialPort(); // Limpiamos la entrada.

            }

        } catch (SerialPortException | ArduinoException | InterruptedException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}
