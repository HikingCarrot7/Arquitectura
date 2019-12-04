package com.sw.graphics;

import com.sw.io.Conexion;

/**
 *
 * @author TODOS
 */
public class Camera
{

    private int x;
    private int y;
    private volatile boolean cameraStopped;

    public Camera(int x, int y)
    {
        this.x = x;
        this.y = y;

    }

    public void moveCamera()
    {

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
