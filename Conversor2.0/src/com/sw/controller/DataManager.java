package com.sw.controller;

import com.sw.model.Conversor;
import com.sw.view.Interfaz;
import javax.swing.JLabel;

/**
 * Gestor de los cálculos del programa.
 *
 *
 * @author Ricardo Nicolás Canul Ibarra.
 */
public class DataManager
{

    private final Interfaz interfaz;
    private final Conversor conversionManager;

    public DataManager(Interfaz interfaz)
    {
        this.interfaz = interfaz;
        conversionManager = new Conversor();
    }

    /**
     * Muestra el resultado del cálculo.
     *
     * @author Ricardo Nicolás Canul Ibarra.
     *
     * @since 1.0
     */
    public void updateCampos()
    {
        int baseEntrada = Integer.parseInt((String) interfaz.getEntradaOpcion().getSelectedItem());
        int baseSalida = Integer.parseInt((String) interfaz.getSalidaOpcion().getSelectedItem());
        String entrada = interfaz.getEntrada().getText();

        if (validarEntrada())
        {
            interfaz.getSalida().setText(conversionManager.fromDecToAnyBase(conversionManager.fromAnyBaseToDec(entrada, baseEntrada), baseSalida));
            rellenarCamposDef(baseEntrada, entrada);
        }

    }

    /**
     * Rellena los campos por defecto de la parte inferior.
     *
     * @param baseEntrada La base del número de entrada.
     * @param entrada El número en cualquier base.
     *
     * @author Ricardo Nicolás Canul Ibarra.
     */
    private void rellenarCamposDef(int baseEntrada, String entrada)
    {
        interfaz.getDecimalDef().setText(conversionManager.fromDecToAnyBase(conversionManager.fromAnyBaseToDec(entrada, baseEntrada), 10));
        interfaz.getBinarioDef().setText(conversionManager.fromDecToAnyBase(conversionManager.fromAnyBaseToDec(entrada, baseEntrada), 2));
        interfaz.getOctalDef().setText(conversionManager.fromDecToAnyBase(conversionManager.fromAnyBaseToDec(entrada, baseEntrada), 8));
        interfaz.getHexadecimalDef().setText(conversionManager.fromDecToAnyBase(conversionManager.fromAnyBaseToDec(entrada, baseEntrada), 16));
    }

    /**
     * Valida si la entrada es válida con respecto a la base seleccionada.
     *
     * @return <code>true</code> si la entrada es válida con respecto a la base seleccionada o <code>false</code> en caso contrario.
     *
     * @author Ricardo Nicolás Canul Ibarra.
     *
     * @since 1.0
     */
    public boolean validarEntrada()
    {
        int baseEntrada = Integer.parseInt((String) interfaz.getEntradaOpcion().getSelectedItem());

        if (validarBase(interfaz.getEntrada().getText(), baseEntrada == 16 ? "^[0-9A-F]+$" : "^[0-" + (baseEntrada - 1) + "]+$"))
        {
            interfaz.getValorInvalido().setText("");
            return true;

        } else
        {
            interfaz.getValorInvalido().setText("¡Valor inválido!");
            interfaz.getLimpiar().setEnabled(true);
            return false;

        }

    }

    /**
     * Muestra en pantalla información sobre el nombre de la base que está seleccionada.
     *
     * @param label La etiqueta a actualizar la información.
     * @param opcion La opción correspondiente a mostrar.
     *
     * @author Ricardo Nicolás Canul Ibarra.
     *
     * @since 1.0
     */
    public void infoBase(JLabel label, String opcion)
    {
        switch (Integer.parseInt(opcion))
        {
            case 2:
                label.setText("(Binario)");
                break;
            case 8:
                label.setText("(Octal)");
                break;
            case 10:
                label.setText("(Decimal)");
                break;
            case 16:
                label.setText("(Hexadecimal)");
                break;
            default:
                label.setText("");
                break;
        }

    }

    /**
     * Limpia los campos.
     *
     * @author Ricardo Nicolás Canul Ibarra.
     *
     * @since 1.0
     */
    public void limpiarCampos()
    {
        interfaz.getEntrada().setText("");
        interfaz.getSalida().setText("");
        interfaz.getDecimalDef().setText("");
        interfaz.getBinarioDef().setText("");
        interfaz.getOctalDef().setText("");
        interfaz.getHexadecimalDef().setText("");
    }

    /**
     * Valida si el texto especificado concuerda con la expresión regular especificada.
     *
     * @param text El texto a validar.
     * @param regex La expresión regular.
     *
     * @return <code>true</code> el texto concuerda con la expresión regular o <code>false</code> en caso contrario.
     *
     * @since 1.0
     */
    private boolean validarBase(String text, String regex)
    {
        return text.matches(regex);
    }

}
