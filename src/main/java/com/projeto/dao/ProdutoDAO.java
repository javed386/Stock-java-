package com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.projeto.exceptions.ValorNaoEncontrado;
import com.projeto.modelos.Produto;

public class ProdutoDAO {
    private EntityManager em;

    public ProdutoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        // Verifica se já existe um produto com o mesmo nome no banco de dados
        String jpql = "SELECT COUNT(p) FROM Produto p WHERE p.nome = :nome";
        Long count = em.createQuery(jpql, Long.class).setParameter("nome", produto.getNome().replaceAll("[^\\p{ASCII}]", "")).getSingleResult();
        if (count > 0) {
            throw new RuntimeException("Já existe um produto com o mesmo nome no banco de dados.");
        }
        
        em.persist(produto);
    }

    public void excluir(Long id) {
        try{
            Produto produto = em.find(Produto.class, id);
            String jpql = "DELETE FROM Estoque e WHERE e.produto.id = :produtoId";
            em.createQuery(jpql).setParameter("produtoId", id).executeUpdate();
            em.remove(produto);
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Produto não encontrado");
        }
        
    }
    
    public Produto buscarPorId(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
        Produto produto = em.find(Produto.class, id);
        if(produto == null){
            throw new ValorNaoEncontrado("Valor nao foi encontrado");
        }
        return produto;
    }

    public List<Produto> buscarTodos(){
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    public void atualizar(Produto produto){
        em.merge(produto);
    }

    public Produto buscarPorNome(String nome){
        try{
            String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
            return em.createQuery(jpql, Produto.class).setParameter("nome", nome).getSingleResult();
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Valor nao foi encontrado");
        }   
    }
    

    
}
