package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;


import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.projeto.dao.CategoriaDAO;
import com.projeto.dao.EstoqueDAO;
import com.projeto.dao.FornecedorDAO;
import com.projeto.dao.ProdutoDAO;
import com.projeto.exceptions.ValorNaoEncontrado;
import com.projeto.modelos.Estoque;
import com.projeto.util.CriaConexao;
import com.projeto.util.NecessarioParaTestes;

public class EstoqueDAOTest {
    private static EntityManager em;
    private static EstoqueDAO estoqueDAO;
    static CategoriaDAO categoriaDAO;
    static ProdutoDAO produtoDAO;
    static FornecedorDAO fornecedorDAO;

    @BeforeAll
    public static void setUp() {
        em = CriaConexao.getEntityManager();
        estoqueDAO = new EstoqueDAO(em);
        categoriaDAO = new CategoriaDAO(em);
        produtoDAO = new ProdutoDAO(em);
        fornecedorDAO = new FornecedorDAO(em);
    }

    @AfterAll
    public static void tearDown() {
        em.close();
    }
    

    @BeforeEach
    public void depoisDeCada(){
        NecessarioParaTestes.limparBancoDeDados();
    }

    @Test
    public void TesteSalvar(){

        NecessarioParaTestes.CriaValorParaTeste();
        assertEquals(estoqueDAO.consultar(NecessarioParaTestes.ultimoIdEstoque()).getNome(), "Teste");
        em.clear();

    }


    @Test
    public void TesteAtualizarQuantidadeDeveSer20(){

        NecessarioParaTestes.CriaValorParaTeste();

        Estoque estoque = estoqueDAO.buscarEstoquePorProdutoNome("Teste");

        em.getTransaction().begin();
        estoque.setQuantidade(new BigDecimal(20));
        em.getTransaction().commit();

        assertEquals(new BigDecimal(20), estoque.getQuantidade());

        em.clear();
    }


    @Test
    public void TesteConsultarTodosOsProdutosDoEstoque(){
        NecessarioParaTestes.CriaValorParaTeste();
        Iterable<Estoque> estoque = estoqueDAO.consultarTodos();
        assertEquals(estoque, estoqueDAO.consultarTodos());
    }
    

    @Test
    public void testBuscarPorNomeProduto(){
        NecessarioParaTestes.CriaValorParaTeste();
        String nome = estoqueDAO.buscarEstoquePorProdutoNome("Teste").getNome();
        assertEquals("Teste", nome);
    }

    @Test
    public void testVender(){
        NecessarioParaTestes.CriaValorParaTeste();
        estoqueDAO.vender("Teste", new BigDecimal(2));
        assertEquals(new BigDecimal(10), estoqueDAO.buscarEstoquePorProdutoNome("Teste").getQuantidade());

    }

    @Test
    public void TesteExcluirResultadoDeveSerNull(){

        NecessarioParaTestes.CriaValorParaTeste();

        Estoque estoque = estoqueDAO.buscarEstoquePorProdutoNome("Teste");

        em.getTransaction().begin();
        estoqueDAO.excluir(estoque.getId());
        em.getTransaction().commit();

        assertThrows(ValorNaoEncontrado.class, () -> {estoqueDAO.buscarEstoquePorProdutoNome("Teste3");});

    }
        

}
