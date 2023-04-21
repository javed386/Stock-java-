package com;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.projeto.dao.CategoriaDAO;
import com.projeto.dao.FornecedorDAO;
import com.projeto.dao.ProdutoDAO;
import com.projeto.exceptions.ValorNaoEncontrado;
import com.projeto.modelos.Categoria;
import com.projeto.modelos.Fornecedor;
import com.projeto.modelos.Produto;
import com.projeto.util.CriaConexao;
import com.projeto.util.NecessarioParaTestes;



public class CategoriaDAOTest {
    private static EntityManager em;
    private static CategoriaDAO categoriaDAO;
    static ProdutoDAO produtoDAO;
    static FornecedorDAO fornecedorDAO;

    

    @BeforeAll
    public static void setUp() {
        em = CriaConexao.getEntityManager();
        categoriaDAO = new CategoriaDAO(em);
        produtoDAO = new ProdutoDAO(em);
        fornecedorDAO = new FornecedorDAO(em);
    }
    
    @AfterAll
    public static void tearDown() {
        em.close();
    }

    @BeforeEach
    public void limparBancoDeDados() {
    NecessarioParaTestes.limparBancoDeDados();
}

    @Test
    public void testCadastrarCategoria() {
        NecessarioParaTestes.CriaValorParaTeste();

        assertEquals(categoriaDAO.buscarPorNome("Teste").getNome(), "Teste");
        
    } 


    @Test
    public void testExcluirCategoria(){
        NecessarioParaTestes.CriaValorParaTeste();
        em.getTransaction().begin();
        categoriaDAO.excluir(NecessarioParaTestes.ultimoIdCategoria());
        em.getTransaction().commit();

        assertThrows(ValorNaoEncontrado.class, () -> {categoriaDAO.buscarPorNome("Teste");});
    }

    @Test
    public void testBuscarCategoriaPorId() {
        NecessarioParaTestes.CriaValorParaTeste();
        assertEquals(categoriaDAO.buscarPorId(NecessarioParaTestes.ultimoIdCategoria()).getNome(), "Teste");
    }

    @Test
    public void testBuscarCategoriaPorNome() {
        NecessarioParaTestes.CriaValorParaTeste();
        assertEquals(categoriaDAO.buscarPorNome("Teste").getNome(), "Teste");
    }

    @Test
    public void testGetProdutosPorCategoria(){
        Categoria categoria = new Categoria("Teste");
        Fornecedor fornecedor = new Fornecedor("Teste", "99.289.741/0001-00", "11980390707", "https://www.pichau.com.br","Pichaufornecedor@Gmail.com", 30);
        Produto produto = new Produto("Teste3", "Teste3", new BigDecimal(450), categoria, fornecedor);
        Produto produto2 = new Produto("Teste2", "Teste2", new BigDecimal(450), categoria, fornecedor);

        NecessarioParaTestes.limparBancoDeDados();
        em.getTransaction().begin();
        categoriaDAO.cadastrar(categoria);
        fornecedorDAO.cadastrar(fornecedor);
        produtoDAO.cadastrar(produto);
        produtoDAO.cadastrar(produto2);
        em.getTransaction().commit();

        List<Produto> produtosEsperados = new ArrayList<>();
        produtosEsperados.add(produto);
        produtosEsperados.add(produto2);

        List<Produto> produtos = categoriaDAO.getProdutosPorCategoria("Teste");

        assertEquals(produtos, produtosEsperados);

        NecessarioParaTestes.limparBancoDeDados();
        
    }

    
}
