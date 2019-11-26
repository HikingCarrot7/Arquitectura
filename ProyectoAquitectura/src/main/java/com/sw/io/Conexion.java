package com.sw.io;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author Mohammed
 */
public class Conexion
{

    private static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    private static SerialPortEventListener listener = new SerialPortEventListener()
    {

        @Override
        public void serialEvent(SerialPortEvent serialPortEvent)
        {

            try
            {

                if (ino.isMessageAvailable())
                    System.out.println(ino.printMessage());

            } catch (SerialPortException | ArduinoException ex)
            {
                System.out.println(ex.getMessage());
            }

        }

    };

    public static void main(String[] args)
    {

        try
        {

            ino.arduinoRX("COM5", 9600, listener);

        } catch (ArduinoException | SerialPortException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}
