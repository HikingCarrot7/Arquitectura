package com.sw.model;

/**
 *
 * @author CarlosÁlvarez
 */
public class ConversionHex
{

    private final ConversionBin conversionBin;
    private final ConversionDec conversionDec;

    public ConversionHex(ConversionBin conversionBin, ConversionDec conversionDec)
    {
        this.conversionBin = conversionBin;
        this.conversionDec = conversionDec;
    }

    public String deDecAHex(int numero)
    {
        String hex = "", varTemp = "", binario = conversionBin.deDecABinario(numero);
        int aux = binario.length();

        while (aux++ % 4 != 0)
            varTemp += "0";

        binario = varTemp + binario;
        varTemp = "";

        for (int i = binario.length() - 1; i >= 0; i--)
        {
            varTemp += String.valueOf(binario.charAt(i));

            if (varTemp.length() % 4 == 0)
            {

                int valor = conversionDec.deBinADecimal(reverse(varTemp));

                if (valor < 10)
                    hex += valor + "";
                else
                    hex += String.valueOf((char) (55 + valor));

                varTemp = "";

            }

        }

        return reverse(hex);

    }

    public String deBinAHex(String binario)
    {
        return deDecAHex(conversionDec.deBinADecimal(binario));
    }

    public String deOctalAHex(String octalString)
    {
        return deBinAHex(conversionBin.deOctalABinario(octalString));
    }

    private String reverse(String text)
    {
        String textReversed = "";

        for (int i = text.length() - 1; i >= 0; i--)
            textReversed += String.valueOf(text.charAt(i));

        return textReversed;

    }

}
