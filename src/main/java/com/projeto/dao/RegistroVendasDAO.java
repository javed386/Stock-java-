package com.projeto.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

    import com.projeto.exceptions.ValorNaoEncontrado;
import com.projeto.modelos.RegistroVendas;
import com.projeto.util.CriaConexao;

public class RegistroVendasDAO {
    private EntityManager em =  CriaConexao.getEntityManager();

    
    public RegistroVendasDAO(EntityManager em) {
        this.em = em;
    }

    public RegistroVendas buscarVendaPorId(Long id){
        if (id == null || id < 0) {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
        RegistroVendas venda = em.find(RegistroVendas.class, id);
        if (venda == null) {
            throw new ValorNaoEncontrado("Venda não encontrada");
        }
        return venda;
    }

    public List<RegistroVendas> buscarVendasEmUmPeriodo(LocalDateTime dataInicio, LocalDateTime dataFinal){
        try{
            String jpql = "SELECT r FROM RegistroVendas r WHERE r.dataDaCompra BETWEEN :dataInicio AND :dataFinal";
            return em.createQuery(jpql, RegistroVendas.class).setParameter("dataInicio", dataInicio).setParameter("dataFinal", dataFinal).getResultList();
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Nenhum registro de compra foi encontrado");
        }
        
    }

    public BigDecimal buscarLucroEmUmPeriodo(LocalDateTime dataInicio, LocalDateTime dataFinal){
        try{
            String JPQL = "SELECT SUM(r.valorDaCompra) FROM RegistroVendas r WHERE r.dataDaCompra BETWEEN :dataInicio AND :dataFim";
            BigDecimal lucro = em.createQuery(JPQL, BigDecimal.class)
            .setParameter("dataInicio", dataInicio)
            .setParameter("dataFim", dataFinal)
            .getSingleResult();
            if(lucro == null){
                throw new ValorNaoEncontrado("Produto não encontrado");
            }
            return lucro;
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Produto não encontrado");
        }
        
    }

    public BigDecimal quantidadeDeVendasProduto(String nomeProduto){
        try{
            String JPQL = "SELECT SUM(r.quantidade) FROM RegistroVendas r WHERE r.produto = :nomeProduto";
            BigDecimal quantidadePorProduto = em.createQuery(JPQL, BigDecimal.class).setParameter("nomeProduto", nomeProduto).getSingleResult();
            if(quantidadePorProduto == null){
                throw new ValorNaoEncontrado("Produto não encontrado");
            }
            return quantidadePorProduto;
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Produto não encontrado");
        }
        
    }

    public BigDecimal valorGanhoPorProduto(String nomeProduto){
        try{
            String JPQL = "SELECT SUM(r.valorDaCompra) FROM RegistroVendas r WHERE r.produto = :nomeProduto";
            BigDecimal valorPorProduto =  em.createQuery(JPQL, BigDecimal.class).setParameter("nomeProduto", nomeProduto).getSingleResult();
            if(valorPorProduto == null){
                throw new ValorNaoEncontrado("Produto nao encontrado");
            }
            return valorPorProduto;
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Produto nao encontrado");
        }
        
    }

    public BigDecimal lucroPorProdutoEmPeriodo(String nomeProduto, LocalDateTime dataInicio, LocalDateTime dataFinal){
        try{
            String JPQL = "SELECT SUM(r.valorDaCompra) FROM RegistroVendas r WHERE r.produto = :nomeProduto AND r.dataDaCompra BETWEEN :dataInicio AND :dataFinal";
            BigDecimal lucroPorProduto =  em.createQuery(JPQL, BigDecimal.class)
            .setParameter("nomeProduto", nomeProduto).setParameter("dataInicio", dataInicio)
            .setParameter("dataFinal", dataFinal).getSingleResult();
            if(lucroPorProduto == null){
                throw new ValorNaoEncontrado("Produto não encontrado");
            }
            return lucroPorProduto;
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Produto não encontrado");
        }
        
    }


}
