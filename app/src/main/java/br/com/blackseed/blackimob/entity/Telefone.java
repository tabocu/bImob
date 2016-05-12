package br.com.blackseed.blackimob.entity;

/**
 * Created by tabocu on 10/05/16.
 */
public class Telefone extends Item {

    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append("\t")
                .append(super.toString())
                .append("\tNumero: ")
                .append(numero)
                .append("\n");
        return stringBuffer.toString();
    }
}
