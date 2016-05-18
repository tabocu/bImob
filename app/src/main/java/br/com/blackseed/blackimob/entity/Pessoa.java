package br.com.blackseed.blackimob.entity;

public abstract class Pessoa extends Item {

    private boolean favorito;

    public boolean isFavorito() { return favorito; }

    public void setFavorito(boolean favorito) {this.favorito = favorito;}

    public boolean isPessoaFisica() {
        return this instanceof Fisica;
    }

    public boolean isPessoaJuridica() {
        return this instanceof Juridica;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static class Fisica extends Pessoa {

        private String cpf;
        private String nome;

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        @Override
        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(super.toString())
                    .append("Cpf: ")
                    .append(cpf)
                    .append("\nNome: ")
                    .append(nome)
                    .append("\n");
            return stringBuffer.toString();
        }
    }

    public static class Juridica extends Pessoa {

        private String cnpj;

        private String nomeFantasia;

        private String razaoSocial;

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }

        public String getNomeFantasia() {
            return nomeFantasia;
        }

        public void setNomeFantasia(String nomeFantasia) {
            this.nomeFantasia = nomeFantasia;
        }

        public String getRazaoSocial() {
            return razaoSocial;
        }

        public void setRazaoSocial(String razaoSocial) {
            this.razaoSocial = razaoSocial;
        }

        @Override
        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(super.toString())
                    .append("Cnpj: ")
                    .append(cnpj)
                    .append("\nNome fantasia: ")
                    .append(nomeFantasia)
                    .append("\nRazao social: ")
                    .append(razaoSocial)
                    .append("\n");
            return stringBuffer.toString();
        }
    }
}
