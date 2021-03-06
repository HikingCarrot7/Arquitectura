package com.sw.utilities;

/**
 *
 * Representa un punto en el plano R^2.
 *
 * @author Eusebio Ajas Santos.
 */
public class Point
{

    public double x;
    public double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public int getIntegerX()
    {
        return (int) x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public int getIntegerY()
    {
        return (int) y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

}
