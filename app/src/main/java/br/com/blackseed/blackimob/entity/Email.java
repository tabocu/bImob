package br.com.blackseed.blackimob.entity;

/**
 * Created by tabocu on 10/05/16.
 */
public class Email extends Item {

    private String endereco;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString())
                .append("Email: ")
                .append(endereco)
                .append("\n");
        return stringBuffer.toString();
    }
}
