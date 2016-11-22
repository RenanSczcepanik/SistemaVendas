/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.dao;

import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import br.edu.uricer.model.Clientes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uricer.model.Vendas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author renan
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) {
        if (clientes.getVendasCollection() == null) {
            clientes.setVendasCollection(new ArrayList<Vendas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Vendas> attachedVendasCollection = new ArrayList<Vendas>();
            for (Vendas vendasCollectionVendasToAttach : clientes.getVendasCollection()) {
                vendasCollectionVendasToAttach = em.getReference(vendasCollectionVendasToAttach.getClass(), vendasCollectionVendasToAttach.getId());
                attachedVendasCollection.add(vendasCollectionVendasToAttach);
            }
            clientes.setVendasCollection(attachedVendasCollection);
            em.persist(clientes);
            for (Vendas vendasCollectionVendas : clientes.getVendasCollection()) {
                Clientes oldIdCliOfVendasCollectionVendas = vendasCollectionVendas.getIdCli();
                vendasCollectionVendas.setIdCli(clientes);
                vendasCollectionVendas = em.merge(vendasCollectionVendas);
                if (oldIdCliOfVendasCollectionVendas != null) {
                    oldIdCliOfVendasCollectionVendas.getVendasCollection().remove(vendasCollectionVendas);
                    oldIdCliOfVendasCollectionVendas = em.merge(oldIdCliOfVendasCollectionVendas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getId());
            Collection<Vendas> vendasCollectionOld = persistentClientes.getVendasCollection();
            Collection<Vendas> vendasCollectionNew = clientes.getVendasCollection();
            Collection<Vendas> attachedVendasCollectionNew = new ArrayList<Vendas>();
            for (Vendas vendasCollectionNewVendasToAttach : vendasCollectionNew) {
                vendasCollectionNewVendasToAttach = em.getReference(vendasCollectionNewVendasToAttach.getClass(), vendasCollectionNewVendasToAttach.getId());
                attachedVendasCollectionNew.add(vendasCollectionNewVendasToAttach);
            }
            vendasCollectionNew = attachedVendasCollectionNew;
            clientes.setVendasCollection(vendasCollectionNew);
            clientes = em.merge(clientes);
            for (Vendas vendasCollectionOldVendas : vendasCollectionOld) {
                if (!vendasCollectionNew.contains(vendasCollectionOldVendas)) {
                    vendasCollectionOldVendas.setIdCli(null);
                    vendasCollectionOldVendas = em.merge(vendasCollectionOldVendas);
                }
            }
            for (Vendas vendasCollectionNewVendas : vendasCollectionNew) {
                if (!vendasCollectionOld.contains(vendasCollectionNewVendas)) {
                    Clientes oldIdCliOfVendasCollectionNewVendas = vendasCollectionNewVendas.getIdCli();
                    vendasCollectionNewVendas.setIdCli(clientes);
                    vendasCollectionNewVendas = em.merge(vendasCollectionNewVendas);
                    if (oldIdCliOfVendasCollectionNewVendas != null && !oldIdCliOfVendasCollectionNewVendas.equals(clientes)) {
                        oldIdCliOfVendasCollectionNewVendas.getVendasCollection().remove(vendasCollectionNewVendas);
                        oldIdCliOfVendasCollectionNewVendas = em.merge(oldIdCliOfVendasCollectionNewVendas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getId();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            Collection<Vendas> vendasCollection = clientes.getVendasCollection();
            for (Vendas vendasCollectionVendas : vendasCollection) {
                vendasCollectionVendas.setIdCli(null);
                vendasCollectionVendas = em.merge(vendasCollectionVendas);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
