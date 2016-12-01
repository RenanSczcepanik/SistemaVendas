/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.test;

import br.edu.uricer.dao.ProdutoDAO;
import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import br.edu.uricer.model.Produto;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author renan
 */
public class TestaProdutoDAO {

    public static void main(String[] args) throws SQLException, NonexistentEntityException {

        Integer idCriado;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaVendasPU");        

        ProdutoDAO produtoDAO = new ProdutoDAO(emf);

        Produto produto = new Produto();
        
        produto.setNome("Cerveja bavaria");
        produto.setPrecoCusto(new BigDecimal("2.00"));
        produto.setPrecoVenda(new BigDecimal("4.5"));
        
        idCriado = produtoDAO.create(produto);
        
        System.out.println("id:"+ idCriado);


        produto = produtoDAO.findProduto(3);
        System.out.println("Pesquisada por nome");
        System.out.println("fds"+ produto.toString());
        
        
        System.out.println("Testa Update");

        Produto nProduto = new Produto();
        
        
      

        List<Produto> produtos =  new ArrayList();
        
        produtos = produtoDAO.findProdutoEntities();
        System.out.println("Produtos");
        for (Produto p : produtos) {
            System.out.println(p);
        }
        
        System.out.println("Por id:");
        
        produto = produtoDAO.findProduto(3);
        
        System.out.println(produto);
        
       
        
       
        
       

    }

}
    
