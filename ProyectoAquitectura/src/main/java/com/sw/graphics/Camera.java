package com.sw.graphics;

import com.sw.main.Panel;

/**
 *
 * @author Mohammed
 */
public class Camera
{

    private int x;
    private int y;
    private volatile boolean stopCamera;

    public Camera(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void moveCamera()
    {

        new Thread(() ->
        {

            for (int i = 0; i < 40 && !stopCamera; i++)
                try
                {

                    Thread.sleep(Panel.DELAY / 40);

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

    public boolean isStopCamera()
    {
        return stopCamera;
    }

    public void setStopCamera(boolean stopCamera)
    {
        this.stopCamera = stopCamera;
    }

}
