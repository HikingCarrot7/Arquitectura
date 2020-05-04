package com.sw.main;

import com.sw.model.ConversionBin;
import com.sw.model.ConversionDec;
import com.sw.model.ConversionHex;
import com.sw.model.ConversionOct;
import com.sw.view.Interfaz;

/**
 *
 * @author CarlosÁlvarez
 */
public class Principal
{

    public static void main(String[] args)
    {
        ConversionBin conversionBin = new ConversionBin();
        ConversionDec conversionDec = new ConversionDec();
        ConversionHex conversionHex = new ConversionHex(conversionBin, conversionDec);
        ConversionOct conversionOct = new ConversionOct(conversionBin, conversionDec);

        Interfaz.inicioInterfaz(conversionBin, conversionDec, conversionHex, conversionOct);
    }

}
