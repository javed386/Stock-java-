package com;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import com.projeto.dao.CategoriaDAO;
import com.projeto.dao.EstoqueDAO;
import com.projeto.dao.FornecedorDAO;
import com.projeto.dao.ProdutoDAO;
import com.projeto.dao.RegistroVendasDAO;
import com.projeto.modelos.Categoria;
import com.projeto.modelos.Estoque;
import com.projeto.modelos.Fornecedor;
import com.projeto.modelos.Produto;
import com.projeto.util.CriaConexao;
import com.projeto.util.NecessarioParaTestes;


public class testefinal {
    private static EntityManager em = CriaConexao.getEntityManager();
    private static FornecedorDAO fornecedorDAO = new FornecedorDAO(em);
    private static CategoriaDAO categoriaDAO = new CategoriaDAO(em);
    private static ProdutoDAO produtoDAO = new ProdutoDAO(em);
    private static EstoqueDAO estoqueDAO = new EstoqueDAO(em);
    private static RegistroVendasDAO registroDAO = new RegistroVendasDAO(em);
    
    public static void main(String[] args) {
        
        NecessarioParaTestes.limparBancoDeDados();

        Categoria construção = new Categoria("Construção");
        Fornecedor leroy = new Fornecedor("Leroy Merlin", "99.289.741/0001-00", "11980390707","http://www.LeroyMerlin.com", "LeroyMerlin@gmail.com", 30 );
        Produto massa = new Produto("Massa de correr", "Massa de correr Leroy Merlin",new BigDecimal(35), construção, leroy);
        Estoque estoque = new Estoque(massa, new BigDecimal(30));

        em.getTransaction().begin();
        categoriaDAO.cadastrar(construção);
        fornecedorDAO.cadastrar(leroy);
        produtoDAO.cadastrar(massa);
        estoqueDAO.cadastrar(estoque);  

        em.getTransaction().commit();

        estoqueDAO.vender("Massa de correr", new BigDecimal(4));

        System.out.println(registroDAO.valorGanhoPorProduto("Massa de correr"));
        
        em.getTransaction().begin();
        estoque.setNome("Sei lá");
        em.getTransaction().commit();

        System.out.println(estoque.getNome());
       
    }
}
