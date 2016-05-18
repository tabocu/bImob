package br.com.blackseed.blackimob.entity;

public class Imovel extends Item {

    private String apelido;
    private String cep;
    private String tipo;
    private boolean favorito;

    public boolean isFavorito() { return favorito; }

    public void setFavorito(boolean favorito) {this.favorito = favorito;}

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getCep() { return cep; }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString())
                .append("Apelido: ")
                .append(apelido)
                .append("\nCep: ")
                .append(cep)
                .append("\nTipo: ")
                .append(tipo)
                .append("\n");
        return stringBuffer.toString();
    }

}
