package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.projeto.dao.CategoriaDAO;
import com.projeto.dao.FornecedorDAO;
import com.projeto.dao.ProdutoDAO;
import com.projeto.exceptions.ValorNaoEncontrado;
import com.projeto.modelos.Produto;
import com.projeto.util.CriaConexao;
import com.projeto.util.NecessarioParaTestes;

public class ProdutoDAOTest {
    private static EntityManager em;
    static CategoriaDAO categoriaDAO;
    static ProdutoDAO produtoDAO;
    static FornecedorDAO fornecedorDAO;

    @BeforeAll
    public static void setUp(){
        em = CriaConexao.getEntityManager();
        categoriaDAO = new CategoriaDAO(em);
        produtoDAO = new ProdutoDAO(em);
        fornecedorDAO = new FornecedorDAO(em);
    }

    @AfterAll
    public static void tearDown(){
        em.close();
        NecessarioParaTestes.limparBancoDeDados();
    }

    @BeforeEach
    public void depoisDeCada(){
        NecessarioParaTestes.limparBancoDeDados();
    }

    @Test
    public void testCadastrarResultadoNomeDeveSerTeste(){
        NecessarioParaTestes.CriaValorParaTeste();
        assertEquals(produtoDAO.buscarPorId(NecessarioParaTestes.ultimoIdProduto()).getNome(), "Teste");
    }

    @Test
    public void testBuscarPorIdNomeDeveSerTeste(){
        NecessarioParaTestes.CriaValorParaTeste();
        
        Produto produto = produtoDAO.buscarPorNome("Teste");

        assertEquals(produto.getNome(), "Teste");
    }

    @Test
    public void TesteExcluirResultadoDeveSerNull() {
        NecessarioParaTestes.CriaValorParaTeste();

        em.getTransaction().begin();
        Long ultimoId = NecessarioParaTestes.ultimoIdProduto();
        produtoDAO.excluir(ultimoId);
        em.getTransaction().commit();

        assertThrows(ValorNaoEncontrado.class, () -> produtoDAO.buscarPorNome("Teste"));

    }

    @Test
    public void testBuscarTodosOsProdutos(){
        NecessarioParaTestes.CriaValorParaTeste();

        Iterable<Produto> produtos = produtoDAO.buscarTodos();

        assertNotNull(produtos);

        assertEquals(produtos, produtoDAO.buscarTodos());
    }

    @Test
    public void testBuscarProdutosPorNomeDeveRetornalTeste(){

        NecessarioParaTestes.CriaValorParaTeste();
        Produto produto = produtoDAO.buscarPorNome("Teste");
        assertEquals(produto.getNome(), "Teste");
    }
}
