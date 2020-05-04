package com.sw.model;

/**
 *
 * Conversiones necesarias para el sistema de numeración.
 *
 * @author Emmanuel Chable Collí.
 * @author Eusebio Ajas Santos.
 */
public class Conversor
{

    /**
     * Convierte un número decimal a una base especificada.
     *
     * @param number El número a convertir a cualquier base.
     * @param base La base a la cual se convertirá el número decimal.
     *
     * @return El número decimal convertido a la base especificada.
     *
     * @author Emmanuel Chable Collí.
     *
     * @since 1.0
     */
    public String fromDecToAnyBase(int number, int base)
    {
        String converted = "";

        if (number == 0)
            return "0";

        while (number != 0)
        {
            int temp = number % base;
            converted += temp < 10 ? temp : String.valueOf((char) (temp + 55));
            number /= base;

        }

        return reverse(converted);

    }

    /**
     *
     * Convierte un número en cualquier base a decimal.
     *
     * @param anyBase Un número que esta en cualquier base.
     * @param base La base a la que está el número.
     *
     * @return El número en su forma decimal.
     *
     * @author Eusebio Ajas Santos.
     *
     * @since 1.0
     */
    public int fromAnyBaseToDec(String anyBase, int base)
    {
        char[] numbers = anyBase.toCharArray();
        int valor = 0, exp = 0;

        for (int i = numbers.length - 1; i >= 0; i--)
            valor += Math.pow(base, exp++) * (Character.isDigit(numbers[i]) ? Integer.parseInt(String.valueOf(numbers[i])) : ((int) numbers[i]) - 55);

        return valor;

    }

    /**
     * Retorna la cadena invertida que especifiquemos.
     *
     * @param text La cadena a invertir.
     *
     * @return La cadena invertida.
     *
     * @author Eusebio Ajas Santos.
     *
     * @since 1.0
     */
    private String reverse(String text)
    {
        String textReversed = "";

        for (int i = text.length() - 1; i >= 0; i--)
            textReversed += String.valueOf(text.charAt(i));

        return textReversed;
    }

}
