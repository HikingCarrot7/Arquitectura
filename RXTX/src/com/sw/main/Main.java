package com.sw.main;

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;

/**
 *
 * @author Mohammed
 */
public class Main
{

    public static void main(String[] args)
    {
        Enumeration<?> puerto = CommPortIdentifier.getPortIdentifiers();

        while (puerto.hasMoreElements())
            System.out.println(((CommPortIdentifier) puerto.nextElement()).getName());

    }

}
