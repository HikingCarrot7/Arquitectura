package com.sw.model;

/**
 *
 * @author CarlosÁlvarez
 */
public class ConversionOct
{

    private final ConversionBin conversionBin;
    private final ConversionDec conversionDec;

    public ConversionOct(ConversionBin conversionBin, ConversionDec conversionDec)
    {
        this.conversionBin = conversionBin;
        this.conversionDec = conversionDec;
    }

    public String deDecAOctal(int numero)
    {
        String octal = "", varTemp = "", binario = conversionBin.deDecABinario(numero);
        int aux = binario.length();

        while (aux++ % 3 != 0)
            varTemp += "0";

        binario = varTemp + binario;
        varTemp = "";

        for (int i = binario.length() - 1; i >= 0; i--)
        {
            varTemp += String.valueOf(binario.charAt(i));

            if (varTemp.length() % 3 == 0)
            {
                int valor = conversionDec.deBinADecimal(reverse(varTemp));

                octal += valor + "";
                varTemp = "";

            }

        }

        return reverse(octal);

    }

    public String deBinAOctal(String binario)
    {
        return deDecAOctal(conversionDec.deBinADecimal(binario));
    }

    public String deHexAOctal(String hexString)
    {
        return deBinAOctal(conversionBin.deHexABinario(hexString));
    }

    private String reverse(String text)
    {
        String textReversed = "";

        for (int i = text.length() - 1; i >= 0; i--)
            textReversed += String.valueOf(text.charAt(i));

        return textReversed;

    }

}
