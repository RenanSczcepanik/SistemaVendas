/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.test;

import br.edu.uricer.dao.ClienteDAO;
import br.edu.uricer.model.Cliente;
import br.edu.uricer.model.Venda;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author renan
 */
public class TestaClienteDAO {
    public static void main(String[] args) {
        Integer idCriado;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaVendasPU");        

        ClienteDAO clienteDAO = new ClienteDAO(emf);

        Cliente cliente = new Cliente();
        
        cliente.setNome("Rafael");
        cliente.setCidade("São Paulo");
        
        ///idCriado = clienteDAO.create(cliente);
        
        
        System.out.println("editando cliente");
        Venda venda = new Venda();
        venda.setIdCli(cliente);
        
        List<Venda> vendas = new ArrayList();
        vendas.add(venda);
        Cliente cliente2 = new Cliente();
        cliente2.setVendaCollection(vendas);
        cliente2.setNome("Rafael Gonzalo");
        cliente2.setCidade("São Paulo");
        
        try {
            clienteDAO.edit(cliente2);
        } catch (Exception ex) {
            Logger.getLogger(TestaClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Cliente"+ clienteDAO.findCliente(3).toString());
        
        
        
        
         
        
    }      
}
