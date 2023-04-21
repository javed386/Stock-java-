package com.projeto.modelos;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;


@Entity
@Table(name = "fornecedores")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String telefone;
    private String site;
    private String email;
    private int taxaEntrega;

    public Fornecedor(String nome, String cnpj, String telefone, String site, String email, int taxaEntrega) {
        this.nome = nome;
    
        CNPJValidator validator = new CNPJValidator();
        try {
            validator.assertValid(cnpj);
        } catch (InvalidStateException e) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
    
        this.cnpj = cnpj;
    
        // Validação do telefone
        if (telefone != null && !telefone.matches("[0-9]{10,11}")) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        this.telefone = telefone;
    
        // Validação do site
        if (site != null && !site.matches("^https?://.*$")) {
            throw new IllegalArgumentException("Site inválido");
        }
        this.site = site;
    
        // Validação do email
        if (email != null && !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    
        this.taxaEntrega = taxaEntrega;
    }

    public Fornecedor() {
    }


    public int getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(int taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }


}
