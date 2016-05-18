package br.com.blackseed.blackimob.entity;

public class Imovel extends Item {

    private String apelido;
    private String tipo;
    private boolean favorito;
    private Endereco endereco;

    public Endereco getEndereco() { return endereco; }

    public void setEndereco(Endereco endereco){ this.endereco = endereco; }


    public boolean isFavorito() { return favorito; }

    public void setFavorito(boolean favorito) {this.favorito = favorito;}

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString())
                .append("Apelido: ")
                .append(apelido)
                .append("\nTipo: ")
                .append(tipo)
                .append("\n");
        return stringBuffer.toString();
    }

}
