package com.projeto.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.projeto.exceptions.ValorNaoEncontrado;
import com.projeto.modelos.Fornecedor;
import com.projeto.util.CriaConexao;

public class FornecedorDAO {
    EntityManager em = CriaConexao.getEntityManager();

    public FornecedorDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Fornecedor fornecedor){
        String jpql = "SELECT COUNT(f) FROM Fornecedor f WHERE f.nome = :nome";
        Long quantidadeNome = em.createQuery(jpql, Long.class).setParameter("nome", fornecedor.getNome()).getSingleResult();
        String jpqlCNPJ = "SELECT COUNT(f) FROM Fornecedor f WHERE f.cnpj = :CNPJ";
        Long quantidadeCNPJ = em.createQuery(jpqlCNPJ, Long.class).setParameter("CNPJ", fornecedor.getCnpj()).getSingleResult();
        if(quantidadeNome > 0 && quantidadeCNPJ > 0){
            throw new ValorNaoEncontrado("Fornecedor já cadastrado com o mesmo nome ou CNPJ");
        }
        
        em.persist(fornecedor);
    }

    public Fornecedor buscarPorId(Long id){
        if (id == null || id < 0) {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
        Fornecedor fornecedor = em.find(Fornecedor.class, id);
        if(fornecedor == null){
            throw new ValorNaoEncontrado("Fornecedor não encontrado");
        }
        return fornecedor;
    }

    public void excluir(Long id) {
        try{
            String jpql2 = "UPDATE Produto SET fornecedor = null WHERE fornecedor.id = :id";
            em.createQuery(jpql2).setParameter("id", id).executeUpdate();
            String jpql = "DELETE FROM Fornecedor WHERE id = :id";
            em.createQuery(jpql).setParameter("id", id).executeUpdate();
        }catch(NoResultException | IllegalArgumentException e){
            throw new ValorNaoEncontrado("Fornecedor não encontrado");
        }
        
    }

    public void atualizar(Fornecedor fornecedor){
        em.merge(fornecedor);
    }
}
