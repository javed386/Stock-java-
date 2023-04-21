package com.projeto.util;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import com.projeto.dao.CategoriaDAO;
import com.projeto.dao.EstoqueDAO;
import com.projeto.dao.FornecedorDAO;
import com.projeto.dao.ProdutoDAO;
import com.projeto.modelos.Categoria;
import com.projeto.modelos.Estoque;
import com.projeto.modelos.Fornecedor;
import com.projeto.modelos.Produto;

public class NecessarioParaTestes {
    private static EntityManager em = CriaConexao.getEntityManager();
    private static EstoqueDAO estoqueDAO = new EstoqueDAO(em);
    static CategoriaDAO categoriaDAO = new CategoriaDAO(em);
    static ProdutoDAO produtoDAO = new ProdutoDAO(em);
    static FornecedorDAO fornecedorDAO = new FornecedorDAO(em);

    public static void limparBancoDeDados() {
        
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Estoque").executeUpdate();
            em.createQuery("DELETE FROM Produto").executeUpdate();
            em.createQuery("DELETE FROM Categoria").executeUpdate();
            em.createQuery("DELETE FROM Fornecedor").executeUpdate();
            em.createQuery("DELETE FROM RegistroVendas").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        }
    }

    public static Long ultimoIdCategoria() {
        EntityManager em = CriaConexao.getEntityManager();
        String jpql = "SELECT MAX(c.id) FROM Categoria c";
        Long ultimo = em.createQuery(jpql, Long.class).getSingleResult();
        return ultimo != null ? ultimo : 0L;
    }

    public static Long ultimoIdEstoque() {
        EntityManager em = CriaConexao.getEntityManager();
        String jpql = "SELECT MAX(e.id) FROM Estoque e";
        Long ultimo = em.createQuery(jpql, Long.class).getSingleResult();
        return ultimo != null ? ultimo : 0L;
    }

    public static Long ultimoIdProduto() {
        EntityManager em = CriaConexao.getEntityManager();
        String jpql = "SELECT MAX(p.id) FROM Produto p";
        Long ultimo = em.createQuery(jpql, Long.class).getSingleResult();
        return ultimo != null ? ultimo : 0L;
    }

    public static Long ultimoIdFornecedor() {
        EntityManager em = CriaConexao.getEntityManager();
        String jpql = "SELECT MAX(f.id) FROM Fornecedor f";
        Long ultimo = em.createQuery(jpql, Long.class).getSingleResult();
        return ultimo != null ? ultimo : 0L;
    }

    public static Long ultimoIdVendas() {
        EntityManager em = CriaConexao.getEntityManager();
        String jpql = "SELECT MAX(v.id) FROM RegistroVendas v";
        Long ultimo = em.createQuery(jpql, Long.class).getSingleResult();
        return ultimo != null ? ultimo : 0L;
    }

    public static void CriaValorParaTeste(){
        Categoria categoria2 = new Categoria("Teste");
        Fornecedor fornecedor2 = new Fornecedor("Teste", "99.289.741/0001-00", "11980390707", "https://www.pichau.com.br","Pichaufornecedor@Gmail.com", 30);
        Produto produto2 = new Produto("Teste", "Teste", new BigDecimal(450), categoria2, fornecedor2);
        Estoque estoque2 = new Estoque(produto2, new BigDecimal(12));

        em.getTransaction().begin();
        categoriaDAO.cadastrar(categoria2);
        fornecedorDAO.cadastrar(fornecedor2);
        produtoDAO.cadastrar(produto2);
        estoqueDAO.cadastrar(estoque2);
        em.getTransaction().commit();

    }
}
