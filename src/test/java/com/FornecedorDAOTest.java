package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.projeto.dao.FornecedorDAO;
import com.projeto.modelos.Fornecedor;
import com.projeto.util.CriaConexao;
import com.projeto.util.NecessarioParaTestes;

public class FornecedorDAOTest {
    private static EntityManager em;
    private static FornecedorDAO fornecedorDAO;

    @BeforeAll
    public static void setUp(){
        em = CriaConexao.getEntityManager(); 
        fornecedorDAO = new FornecedorDAO(em);
    }

    @AfterAll
    public static void tearDown(){
        em.close();
    }

    @BeforeEach
    public void depoisDeCada(){
        NecessarioParaTestes.limparBancoDeDados();
    }

    @Test
    public void cadastrarFornecedor(){
        NecessarioParaTestes.CriaValorParaTeste();
        assertEquals(fornecedorDAO.buscarPorId(NecessarioParaTestes.ultimoIdFornecedor()).getNome(), "Teste");
    }

    @Test
    public void buscarPorId(){
        NecessarioParaTestes.CriaValorParaTeste();
        assertEquals(fornecedorDAO.buscarPorId(NecessarioParaTestes.ultimoIdFornecedor()).getNome(), "Teste");
    }
    
    @Test
    public void excluirTemQueDarIllegalArgumentSeForOPrimeiroItemDoBancoDeDados(){
        NecessarioParaTestes.CriaValorParaTeste();

        em.getTransaction().begin();
        fornecedorDAO.excluir(NecessarioParaTestes.ultimoIdFornecedor());
        em.getTransaction().commit();

        assertThrows(IllegalArgumentException.class, () -> {fornecedorDAO.buscarPorId(null);});
    }

    @Test
    public void atualizarFornecedor(){
       NecessarioParaTestes.CriaValorParaTeste();

        em.getTransaction().begin();
        Fornecedor fornecedorModificado =fornecedorDAO.buscarPorId(NecessarioParaTestes.ultimoIdFornecedor());
        fornecedorModificado.setNome("Teste2");
        fornecedorDAO.atualizar(fornecedorModificado);
        em.getTransaction().commit();

        assertEquals(fornecedorDAO.buscarPorId(NecessarioParaTestes.ultimoIdFornecedor()).getNome(), "Teste2");

    }

   
}
