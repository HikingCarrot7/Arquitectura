package com.sw.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Eusebio Ajas Santos.
 */
public class BufferedImageLoader
{

    /**
     * Carga una imagen y la devuelve.
     *
     * @param path La ruta de la imagen.
     *
     * @return La imagen.
     */
    public BufferedImage loadImage(String path)
    {

        try
        {

            return ImageIO.read(new File(path));

        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return null;

    }

}
