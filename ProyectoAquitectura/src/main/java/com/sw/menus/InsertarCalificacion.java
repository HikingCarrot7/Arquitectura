package com.sw.menus;

import com.sw.graphics.Drawable;
import java.awt.Graphics2D;

/**
 *
 * @author HikingC7
 */
public class InsertarCalificacion implements Drawable
{

    private String calif;

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Graphics2D g)
    {

    }

    public void escribirCalificacion(int caracter)
    {
        System.out.println(caracter);
    }

}
