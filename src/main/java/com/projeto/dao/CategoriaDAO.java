package com.projeto.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.util.List;
import com.projeto.exceptions.ValorNaoEncontrado;
import com.projeto.modelos.Categoria;
import com.projeto.modelos.Produto;


public class CategoriaDAO {

    private EntityManager em;

    public CategoriaDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Categoria categoria){
        String jpql = "SELECT COUNT(p) FROM Categoria p WHERE p.nome = :nome";
        Long quantidade = em.createQuery(jpql, Long.class).setParameter("nome", categoria.getNome()).getSingleResult();
        if(quantidade > 0){
            throw new ValorNaoEncontrado("Categoria já cadastrada");
        }
        this.em.persist(categoria);
    }

    public void excluir(Long id){
        try{
            String jpql2 = "UPDATE Produto SET categoria = null WHERE categoria.id = :id";
            em.createQuery(jpql2).setParameter("id", id).executeUpdate();
            String jpql = "DELETE FROM Categoria WHERE id = :id";
            em.createQuery(jpql).setParameter("id", id).executeUpdate();
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("id não encontrado");
        }

        
    }

    public Categoria buscarPorId(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
        Categoria categoria = em.find(Categoria.class, id);
        if(categoria == null){
            throw new ValorNaoEncontrado("Categoria nao encontrada");
        }
        return categoria;

    }


    public Categoria buscarPorNome(String nome){
        try{
            String jpql = "SELECT c FROM Categoria c WHERE c.nome = :nome ";
            return em.createQuery(jpql, Categoria.class).setParameter("nome", nome).setMaxResults(1).getSingleResult();
        }catch (NoResultException e) {
            throw new ValorNaoEncontrado("Valor nao foi encontrado");
            
        }
   
    }

    public List<Produto> getProdutosPorCategoria(String categoria) {
        try{
            String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :categoria";
            return em.createQuery(jpql, Produto.class).setParameter("categoria", categoria).getResultList();
        }catch (NoResultException e) {
            throw new ValorNaoEncontrado("Valor nao foi encontrado");
        }
        
    }


}
