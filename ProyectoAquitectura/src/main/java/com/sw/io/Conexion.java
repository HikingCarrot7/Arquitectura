package com.sw.io;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.sw.controller.SerialDataController;
import com.sw.menus.GraficaTemperatura;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author TODOS
 */
public class Conexion implements SerialPortEventListener
{

    public static int DELAY_CONEXION = 300;
    private final String PUERTO = "COM4";
    private PanamaHitek_Arduino ino;
    private SerialDataController dataController;

    public Conexion(GraficaTemperatura graficaTemperatura, SerialDataController dataController)
    {

        ino = new PanamaHitek_Arduino();

        this.dataController = dataController;

    }

    public void iniciarConexion()
    {

        new Thread(() ->
        {

            try
            {

                ino.arduinoRX(PUERTO, 115200, this);

            } catch (ArduinoException | SerialPortException ex)
            {
                System.out.println(ex.getMessage());
            }

        }).start();

    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent)
    {

        try
        {

            if (ino.isMessageAvailable())
            {
                dataController.gestionarSerialData(ino.printMessage());
                ino.flushSerialPort();

            }

        } catch (SerialPortException | ArduinoException | InterruptedException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}
