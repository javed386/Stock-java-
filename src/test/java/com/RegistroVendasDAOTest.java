package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.projeto.dao.EstoqueDAO;
import com.projeto.dao.RegistroVendasDAO;

import com.projeto.util.CriaConexao;
import com.projeto.util.NecessarioParaTestes;

public class RegistroVendasDAOTest {
    private static EntityManager em;
    private static RegistroVendasDAO registroVendasDAO;
    private static EstoqueDAO estoqueDAO;

    @BeforeAll
    public static void setUp() {
        em = CriaConexao.getEntityManager();
        registroVendasDAO = new RegistroVendasDAO(em);
        estoqueDAO = new EstoqueDAO(em);
    }

    @BeforeEach
    public void depoisDeCada(){
        NecessarioParaTestes.limparBancoDeDados();
    }

    @AfterAll
    public static void tearDown() {
        em.close();
    }

    @AfterEach
    public void depoisDeTudo(){
        NecessarioParaTestes.limparBancoDeDados();
    }

    @Test
    public void testBuscarVendaPorId(){
        NecessarioParaTestes.limparBancoDeDados();

        NecessarioParaTestes.CriaValorParaTeste();

        estoqueDAO.vender("Teste", new BigDecimal(2));
        
        assertEquals(registroVendasDAO.buscarVendaPorId(NecessarioParaTestes.ultimoIdVendas()).getProduto(), "Teste");

    }

    @Test
    public void testBuscarVendasEmUmPeriodo(){
        NecessarioParaTestes.CriaValorParaTeste();
        estoqueDAO.vender("Teste", new BigDecimal(2));
        assertNotEquals(registroVendasDAO.buscarVendasEmUmPeriodo(LocalDateTime.of(2023, 1, 18, 17, 59, 0), LocalDateTime.of(3000, 1, 1, 1, 1, 1)).size(), 0);
        
    }

    @Test
    public void testBuscarLucroEmUmPeriodo(){
        NecessarioParaTestes.CriaValorParaTeste();
        estoqueDAO.vender("Teste", new BigDecimal(4));
        assertEquals(registroVendasDAO
        .buscarLucroEmUmPeriodo(LocalDateTime.of(2023, 1, 18, 17, 59, 0)
        , LocalDateTime.of(3000, 1, 1, 1, 1, 1)), new BigDecimal("1800.00"));
    }

    @Test
    @Transactional
    public void testValorGanhoPorProduto(){
        NecessarioParaTestes.CriaValorParaTeste();
        estoqueDAO.vender("Teste", new BigDecimal(2));

        assertEquals(registroVendasDAO.valorGanhoPorProduto("Teste"), new BigDecimal("900.00"));
    }

    @Test
    public void testQuantidadeVendasProduto(){
        NecessarioParaTestes.CriaValorParaTeste();
        estoqueDAO.vender("Teste", new BigDecimal(2));
        assertEquals(registroVendasDAO.quantidadeDeVendasProduto("Teste"), new BigDecimal("2.00"));
    }

    @Test
    public void testLucroProdutoPorPeriodo(){
        NecessarioParaTestes.CriaValorParaTeste();
        estoqueDAO.vender("Teste", new BigDecimal(2));
        assertEquals(registroVendasDAO
        .lucroPorProdutoEmPeriodo("Teste", LocalDateTime.of(2023, 1, 18, 17, 59, 0)
        , LocalDateTime.of(3000, 1, 1, 1, 1, 1)), new BigDecimal("900.00"));
    }
}
    