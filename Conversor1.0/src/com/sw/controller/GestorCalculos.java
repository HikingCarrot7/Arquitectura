package com.sw.controller;

import com.sw.model.ConversionBin;
import com.sw.model.ConversionDec;
import com.sw.model.ConversionHex;
import com.sw.model.ConversionOct;
import com.sw.view.Interfaz;
import javax.swing.JComboBox;

/**
 *
 * @author CarlosÁlvarez
 */
public class GestorCalculos
{

    private ConversionBin conversionBin;
    private ConversionDec conversionDec;
    private ConversionHex conversionHex;
    private ConversionOct conversionOct;

    public GestorCalculos(ConversionBin conversionBin, ConversionDec conversionDec, ConversionHex conversionHex, ConversionOct conversionOct)
    {
        this.conversionBin = conversionBin;
        this.conversionDec = conversionDec;
        this.conversionHex = conversionHex;
        this.conversionOct = conversionOct;
    }

    public void mostrarResultados(Interfaz interfaz)
    {
        JComboBox<String> entradaOpcion = interfaz.getEntradaOpcion();
        JComboBox<String> salidaOpcion = interfaz.getSalidaOpcion();

        switch ((String) entradaOpcion.getSelectedItem())
        {
            case "Decimal":

                switch ((String) salidaOpcion.getSelectedItem())
                {
                    case "Binario":

                        interfaz.getSalida().setText(conversionBin.deDecABinario(Integer.parseInt(interfaz.getEntrada().getText())));
                        break;

                    case "Octal":

                        interfaz.getSalida().setText(conversionOct.deDecAOctal(Integer.parseInt(interfaz.getEntrada().getText())));
                        break;

                    case "Hexadecimal":

                        interfaz.getSalida().setText(conversionHex.deDecAHex(Integer.parseInt(interfaz.getEntrada().getText())));
                        break;

                    default:

                        interfaz.getSalida().setText(interfaz.getEntrada().getText());
                        break;
                }

                interfaz.getDecimalDef().setText(interfaz.getEntrada().getText());
                interfaz.getBinarioDef().setText(conversionBin.deDecABinario(Integer.parseInt(interfaz.getEntrada().getText())));
                interfaz.getOctalDef().setText(conversionOct.deDecAOctal(Integer.parseInt(interfaz.getEntrada().getText())));
                interfaz.getHexadecimalDef().setText(conversionHex.deDecAHex(Integer.parseInt(interfaz.getEntrada().getText())));

                break;

            case "Binario":

                switch ((String) salidaOpcion.getSelectedItem())
                {
                    case "Decimal":

                        interfaz.getSalida().setText("" + conversionDec.deBinADecimal(interfaz.getEntrada().getText()));
                        break;

                    case "Octal":

                        interfaz.getSalida().setText(conversionOct.deBinAOctal(interfaz.getEntrada().getText()));
                        break;

                    case "Hexadecimal":

                        interfaz.getSalida().setText(conversionHex.deBinAHex(interfaz.getEntrada().getText()));
                        break;

                    default:

                        interfaz.getSalida().setText(interfaz.getEntrada().getText());
                        break;
                }

                interfaz.getDecimalDef().setText("" + conversionDec.deBinADecimal(interfaz.getEntrada().getText()));
                interfaz.getBinarioDef().setText(interfaz.getEntrada().getText());
                interfaz.getOctalDef().setText(conversionOct.deBinAOctal(interfaz.getEntrada().getText()));
                interfaz.getHexadecimalDef().setText(conversionHex.deBinAHex(interfaz.getEntrada().getText()));

                break;

            case "Octal":

                switch ((String) salidaOpcion.getSelectedItem())
                {
                    case "Decimal":

                        interfaz.getSalida().setText("" + conversionDec.deOctalADecimal(interfaz.getEntrada().getText()));
                        break;

                    case "Binario":

                        interfaz.getSalida().setText(conversionBin.deOctalABinario(interfaz.getEntrada().getText()));
                        break;

                    case "Hexadecimal":

                        interfaz.getSalida().setText(conversionHex.deOctalAHex(interfaz.getEntrada().getText()));
                        break;

                    default:

                        interfaz.getSalida().setText(interfaz.getEntrada().getText());
                        break;
                }

                interfaz.getDecimalDef().setText("" + conversionDec.deOctalADecimal(interfaz.getEntrada().getText()));
                interfaz.getBinarioDef().setText(conversionBin.deOctalABinario(interfaz.getEntrada().getText()));
                interfaz.getOctalDef().setText(interfaz.getEntrada().getText());
                interfaz.getHexadecimalDef().setText(conversionHex.deOctalAHex(interfaz.getEntrada().getText()));

                break;

            case "Hexadecimal":

                switch ((String) salidaOpcion.getSelectedItem())
                {
                    case "Decimal":

                        interfaz.getSalida().setText("" + conversionDec.deHexADecimal(interfaz.getEntrada().getText()));
                        break;

                    case "Binario":

                        interfaz.getSalida().setText(conversionBin.deHexABinario(interfaz.getEntrada().getText()));
                        break;

                    case "Octal":

                        interfaz.getSalida().setText(conversionOct.deHexAOctal(interfaz.getEntrada().getText()));
                        break;

                    default:

                        interfaz.getSalida().setText(interfaz.getEntrada().getText());
                        break;
                }

                interfaz.getDecimalDef().setText("" + conversionDec.deHexADecimal(interfaz.getEntrada().getText()));
                interfaz.getBinarioDef().setText(conversionBin.deHexABinario(interfaz.getEntrada().getText()));
                interfaz.getOctalDef().setText(conversionOct.deHexAOctal(interfaz.getEntrada().getText()));
                interfaz.getHexadecimalDef().setText(interfaz.getEntrada().getText());

                break;

        }

    }

}
