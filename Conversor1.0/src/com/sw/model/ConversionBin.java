package com.sw.model;

/**
 *
 * @author CarlosÁlvarez
 */
public class ConversionBin
{

    public String deDecABinario(int numero)
    {
        String binario = "";

        if (numero == 0)
            return "0";

        while (numero != 0)
            if (numero % 2 == 0)
            {
                binario += "0";
                numero /= 2;

            } else
            {
                binario += "1";
                numero = (int) Math.floor((double) numero / 2);

            }

        return reverse(binario);

    }

    public String deHexABinario(String hexString)
    {
        String binario = "";
        int valor;

        for (int i = 0; i < hexString.length(); i++)
        {
            char temp = hexString.charAt(i);

            if (!Character.isDigit(temp))
                valor = ((int) temp - 65) + 10;
            else
                valor = (int) temp - 48;

            binario += String.format("%04d", Integer.parseInt(deDecABinario(valor)));

        }

        return binario;

    }

    public String deOctalABinario(String octalString)
    {
        String binario = "";
        int valor;

        for (int i = 0; i < octalString.length(); i++)
        {
            valor = Integer.parseInt(String.valueOf(octalString.charAt(i)));

            binario += String.format("%03d", Integer.parseInt(deDecABinario(valor)));

        }

        return binario;

    }

    private String reverse(String text)
    {
        String textReversed = "";

        for (int i = text.length() - 1; i >= 0; i--)
            textReversed += String.valueOf(text.charAt(i));

        return textReversed;

    }

}
