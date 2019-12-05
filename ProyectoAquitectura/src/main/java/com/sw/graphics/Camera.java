package com.sw.graphics;

import com.sw.io.Conexion;

/**
 * Mueve el campo de visión de la gráfica para dar la sensación se movimiento cuando se gráfica.
 *
 * @author Eusebio Ajas Santos.
 */
public class Camera
{

    private int x; //Posición en x de la cámara.
    private int y;
    private volatile boolean cameraStopped; // Cámara detenida (se regresó al menú).

    public Camera(int x, int y)
    {
        this.x = x;
        this.y = y;

    }

    /**
     * Mueve la cámara 40 pixeles cada vez que se añade una línea.
     */
    public void moveCamera()
    {

        /**
         * Cuando se añade una línea, dentro del programa ocurre un retraso de 300 milisegundos. Este hilo mueve la cámara 40 píxeles durante ese tiempo.
         */
        new Thread(() ->
        {

            for (int i = 0; i < 40 && !cameraStopped; i++)
                try
                {

                    Thread.sleep(Conexion.DELAY_CONEXION / 40);

                    setX(getX() + 1);

                } catch (InterruptedException ex)
                {
                    System.out.println(ex.getMessage());
                }

        }).start();

    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public boolean isCameraStopped()
    {
        return cameraStopped;
    }

    public void setCameraStopped(boolean stopCamera)
    {
        this.cameraStopped = stopCamera;
    }

}
