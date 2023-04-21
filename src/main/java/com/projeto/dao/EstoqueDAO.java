package com.projeto.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.projeto.exceptions.ValorNaoEncontrado;
import com.projeto.modelos.Estoque;
import com.projeto.modelos.Produto;
import com.projeto.modelos.RegistroVendas;

public class EstoqueDAO {
    private EntityManager em;

    public EstoqueDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Estoque estoque) {
        em.persist(estoque);
    }
    public void atualizar(Estoque estoque) {
        em.merge(estoque);
    }
    public Estoque consultar(Long id) {
        try{
            return em.find(Estoque.class, id);
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Valor não contrado com esse id");
        }
        
    }
    public Iterable<Estoque> consultarTodos() {
        return em.createQuery("SELECT e FROM Estoque e", Estoque.class).getResultList();
    }
    public void excluir(Long id){
        try{
            Estoque estoque = em.find(Estoque.class, id);
            em.remove(estoque);
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Valor com esse id não foi encontrado");
        }
        
    }

    
    public Produto informacoesProduto(Long l){
        try{
            String jpql = "SELECT p FROM Estoque e JOIN Produto p ON e.produto.id = p.id WHERE e.id = :l";
            return em.createQuery(jpql, Produto.class).setParameter("l", l).getSingleResult();
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Valor nao foi encontrado");
        }
    }  

    public Estoque buscarEstoquePorProdutoNome(String nome){
        try{
            String jpql = "SELECT e FROM Estoque e WHERE e.produto.nome = :nome";
            return em.createQuery(jpql, Estoque.class).setParameter("nome", nome).getSingleResult();
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Valor nao foi encontrado");
        }
        
    }

    public Estoque buscarEstoquePorProdutoId(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
        Estoque estoque = em.find(Estoque.class, id);
        if(estoque == null){
            throw new ValorNaoEncontrado("Valor nao foi encontrado");
        }
        return estoque;
        
        
    }

    public void vender(String nome, BigDecimal quantidade){
        try{
            ProdutoDAO produtoDAO = new ProdutoDAO(em);
            Produto produto = produtoDAO.buscarPorNome(nome);
            String jpql2 = "SELECT e FROM Estoque e WHERE e.produto.id = :produto_id";
            Estoque estoque = em.createQuery(jpql2, Estoque.class).setParameter("produto_id", produto.getId()).getSingleResult();
            if(estoque.getQuantidade().compareTo(quantidade) < 0){
                throw new IllegalArgumentException("a quantidade da venda é maior que a quantidade desse produto no estoque");
            }
            RegistroVendas registro = new RegistroVendas(produto.getNome(), LocalDateTime.now(), quantidade, produto.getPreco() );
        
            
            em.getTransaction().begin();
        
            estoque.setQuantidade(estoque.getQuantidade().subtract(quantidade).setScale(0));
            em.persist(registro);
        
            em.getTransaction().commit();
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Valor nao foi encontrado");
        }

    }    

    


}
