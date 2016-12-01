/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.dao;

import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import br.edu.uricer.model.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uricer.model.Venda;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author renan
 */
public class ClienteDAO implements Serializable {

    public ClienteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Integer create(Cliente cliente) {
        if (cliente.getVendaCollection() == null) {
            cliente.setVendaCollection(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Venda> attachedVendaCollection = new ArrayList<Venda>();
            for (Venda vendaCollectionVendaToAttach : cliente.getVendaCollection()) {
                vendaCollectionVendaToAttach = em.getReference(vendaCollectionVendaToAttach.getClass(), vendaCollectionVendaToAttach.getId());
                attachedVendaCollection.add(vendaCollectionVendaToAttach);
            }
            cliente.setVendaCollection(attachedVendaCollection);
            em.persist(cliente);
            for (Venda vendaCollectionVenda : cliente.getVendaCollection()) {
                Cliente oldIdCliOfVendaCollectionVenda = vendaCollectionVenda.getIdCli();
                vendaCollectionVenda.setIdCli(cliente);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
                if (oldIdCliOfVendaCollectionVenda != null) {
                    oldIdCliOfVendaCollectionVenda.getVendaCollection().remove(vendaCollectionVenda);
                    oldIdCliOfVendaCollectionVenda = em.merge(oldIdCliOfVendaCollectionVenda);
                }
            }
            em.getTransaction().commit();
            Integer idCriado = cliente.getId();
            return idCriado;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Collection<Venda> vendaCollectionOld = persistentCliente.getVendaCollection();
            Collection<Venda> vendaCollectionNew = cliente.getVendaCollection();
            Collection<Venda> attachedVendaCollectionNew = new ArrayList<Venda>();
       /*     
            for (Venda vendaCollectionNewVendaToAttach : vendaCollectionNew) {
                vendaCollectionNewVendaToAttach = em.getReference(vendaCollectionNewVendaToAttach.getClass(), vendaCollectionNewVendaToAttach.getId());
                attachedVendaCollectionNew.add(vendaCollectionNewVendaToAttach);
            }
       */
            vendaCollectionNew = attachedVendaCollectionNew;
            cliente.setVendaCollection(vendaCollectionNew);
            cliente = em.merge(cliente);
            for (Venda vendaCollectionOldVenda : vendaCollectionOld) {
                if (!vendaCollectionNew.contains(vendaCollectionOldVenda)) {
                    vendaCollectionOldVenda.setIdCli(null);
                    vendaCollectionOldVenda = em.merge(vendaCollectionOldVenda);
                }
            }
            for (Venda vendaCollectionNewVenda : vendaCollectionNew) {
                if (!vendaCollectionOld.contains(vendaCollectionNewVenda)) {
                    Cliente oldIdCliOfVendaCollectionNewVenda = vendaCollectionNewVenda.getIdCli();
                    vendaCollectionNewVenda.setIdCli(cliente);
                    vendaCollectionNewVenda = em.merge(vendaCollectionNewVenda);
                    if (oldIdCliOfVendaCollectionNewVenda != null && !oldIdCliOfVendaCollectionNewVenda.equals(cliente)) {
                        oldIdCliOfVendaCollectionNewVenda.getVendaCollection().remove(vendaCollectionNewVenda);
                        oldIdCliOfVendaCollectionNewVenda = em.merge(oldIdCliOfVendaCollectionNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Collection<Venda> vendaCollection = cliente.getVendaCollection();
            for (Venda vendaCollectionVenda : vendaCollection) {
                vendaCollectionVenda.setIdCli(null);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
