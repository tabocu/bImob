package br.com.blackseed.blackimob.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Pessoa extends Item {

    private List<Telefone> telefoneList = new ArrayList<>();

    private List<Email> emailList = new ArrayList<>();

    public boolean isPessoaFisica() {
        return this instanceof Fisica;
    }

    public boolean isPessoaJuridica() {
        return this instanceof Juridica;
    }

    public List<Telefone> telefones() {
        return telefoneList;
    }

    public List<Email> emails() {
        return emailList;
    }
//
//    @Override
//    public String toString() {
//        if(isPessoaFisica())
//            return ((Fisica)this).toString();
//        else if(isPessoaJuridica())
//            return ((Juridica)this).toString();
//        return "";
//    }

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
            stringBuffer//.append(super.toString())
                    .append("\n\nCpf: ")
                    .append(cpf)
                    .append("\nNome: ")
                    .append(nome);
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
            stringBuffer//.append(super.toString())
                    .append("\n\nCnpj: ")
                    .append(cnpj)
                    .append("\nNome fantasia: ")
                    .append(nomeFantasia)
                    .append("\nRazao social: ")
                    .append(razaoSocial);
            return stringBuffer.toString();
        }
    }
}
