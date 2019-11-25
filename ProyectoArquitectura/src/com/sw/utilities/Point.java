package com.sw.utilities;

/**
 *
 * @author Mohammed
 */
public class Point
{

    public float x;
    public float y;

    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public int getIntegerX()
    {
        return (int) x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public int getIntegerY()
    {
        return (int) y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

}
