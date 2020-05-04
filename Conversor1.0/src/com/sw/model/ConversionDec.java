package com.sw.model;

/**
 *
 * @author CarlosÁlvarez
 */
public class ConversionDec
{

    public int deBinADecimal(String binario)
    {
        char[] bits = binario.toCharArray();
        int valor = 0, aux = 0;

        for (int i = bits.length - 1; i >= 0; i--)
            valor += Math.pow(2, aux++) * Integer.parseInt(String.valueOf(bits[i]));

        return valor;
    }

    public int deHexADecimal(String hexString)
    {
        char[] bits = hexString.toCharArray();
        int valor = 0, aux = 0;

        for (int i = bits.length - 1; i >= 0; i--)
            valor += Math.pow(16, aux++) * (Character.isDigit(bits[i]) ? Integer.parseInt(String.valueOf(bits[i])) : ((int) bits[i]) - 55);

        return valor;

    }

    public int deOctalADecimal(String octalString)
    {
        char[] bits = octalString.toCharArray();
        int valor = 0, aux = 0;

        for (int i = bits.length - 1; i >= 0; i--)
            valor += Math.pow(8, aux++) * Integer.parseInt(String.valueOf(bits[i]));

        return valor;

    }

}
