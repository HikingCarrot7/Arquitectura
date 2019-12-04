package com.sw.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author TODOS
 */
public class BufferedImageLoader
{

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
