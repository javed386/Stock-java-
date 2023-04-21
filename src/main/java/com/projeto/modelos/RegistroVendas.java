package com.projeto.modelos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "registro_de_vendas")
public class RegistroVendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String produto;
    private BigDecimal quantidade;
    private BigDecimal valorDaCompra;
    private LocalDateTime dataDaCompra;
    public RegistroVendas(String produto, LocalDateTime dataDaCompra, BigDecimal quantidade, BigDecimal valorDaCompra) {
        this.produto = produto;
        this.dataDaCompra = dataDaCompra;
        this.quantidade = quantidade;
        this.valorDaCompra = valorDaCompra.multiply(quantidade);
    }

    public RegistroVendas(){}

    
    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }
    public LocalDateTime getDataDaCompra() {
        return dataDaCompra;
    }
    public void setDataDaCompra(LocalDateTime dataDaCompra) {
        this.dataDaCompra = dataDaCompra;
    }
    public BigDecimal getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getValorDaCompra() {
        return valorDaCompra;
    }
    public void setValorDaCompra(BigDecimal valorDaCompra) {
        this.valorDaCompra = valorDaCompra;
    }

    
}
