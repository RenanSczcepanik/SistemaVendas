/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.dao;

import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import br.edu.uricer.model.Fornecedor;
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
public class FornecedorDAO implements Serializable {

    public FornecedorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Integer create(Fornecedor fornecedor) {
        if (fornecedor.getVendaCollection() == null) {
            fornecedor.setVendaCollection(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Venda> attachedVendaCollection = new ArrayList<Venda>();
            for (Venda vendaCollectionVendaToAttach : fornecedor.getVendaCollection()) {
                vendaCollectionVendaToAttach = em.getReference(vendaCollectionVendaToAttach.getClass(), vendaCollectionVendaToAttach.getId());
                attachedVendaCollection.add(vendaCollectionVendaToAttach);
            }
            fornecedor.setVendaCollection(attachedVendaCollection);
            em.persist(fornecedor);
            for (Venda vendaCollectionVenda : fornecedor.getVendaCollection()) {
                Fornecedor oldIdFornecOfVendaCollectionVenda = vendaCollectionVenda.getIdFornec();
                vendaCollectionVenda.setIdFornec(fornecedor);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
                if (oldIdFornecOfVendaCollectionVenda != null) {
                    oldIdFornecOfVendaCollectionVenda.getVendaCollection().remove(vendaCollectionVenda);
                    oldIdFornecOfVendaCollectionVenda = em.merge(oldIdFornecOfVendaCollectionVenda);
                }
            }
            em.getTransaction().commit();
            Integer idCriado = fornecedor.getId();
            return idCriado;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fornecedor fornecedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor persistentFornecedor = em.find(Fornecedor.class, fornecedor.getId());
            Collection<Venda> vendaCollectionOld = persistentFornecedor.getVendaCollection();
            Collection<Venda> vendaCollectionNew = fornecedor.getVendaCollection();
            Collection<Venda> attachedVendaCollectionNew = new ArrayList<Venda>();
       /*     
            for (Venda vendaCollectionNewVendaToAttach : vendaCollectionNew) {
                vendaCollectionNewVendaToAttach = em.getReference(vendaCollectionNewVendaToAttach.getClass(), vendaCollectionNewVendaToAttach.getId());
                attachedVendaCollectionNew.add(vendaCollectionNewVendaToAttach);
            }
       */ 
            vendaCollectionNew = attachedVendaCollectionNew;
            fornecedor.setVendaCollection(vendaCollectionNew);
            fornecedor = em.merge(fornecedor);
            for (Venda vendaCollectionOldVenda : vendaCollectionOld) {
                if (!vendaCollectionNew.contains(vendaCollectionOldVenda)) {
                    vendaCollectionOldVenda.setIdFornec(null);
                    vendaCollectionOldVenda = em.merge(vendaCollectionOldVenda);
                }
            }
            for (Venda vendaCollectionNewVenda : vendaCollectionNew) {
                if (!vendaCollectionOld.contains(vendaCollectionNewVenda)) {
                    Fornecedor oldIdFornecOfVendaCollectionNewVenda = vendaCollectionNewVenda.getIdFornec();
                    vendaCollectionNewVenda.setIdFornec(fornecedor);
                    vendaCollectionNewVenda = em.merge(vendaCollectionNewVenda);
                    if (oldIdFornecOfVendaCollectionNewVenda != null && !oldIdFornecOfVendaCollectionNewVenda.equals(fornecedor)) {
                        oldIdFornecOfVendaCollectionNewVenda.getVendaCollection().remove(vendaCollectionNewVenda);
                        oldIdFornecOfVendaCollectionNewVenda = em.merge(oldIdFornecOfVendaCollectionNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fornecedor.getId();
                if (findFornecedor(id) == null) {
                    throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.");
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
            Fornecedor fornecedor;
            try {
                fornecedor = em.getReference(Fornecedor.class, id);
                fornecedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.", enfe);
            }
            Collection<Venda> vendaCollection = fornecedor.getVendaCollection();
            for (Venda vendaCollectionVenda : vendaCollection) {
                vendaCollectionVenda.setIdFornec(null);
                vendaCollectionVenda = em.merge(vendaCollectionVenda);
            }
            em.remove(fornecedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fornecedor> findFornecedorEntities() {
        return findFornecedorEntities(true, -1, -1);
    }

    public List<Fornecedor> findFornecedorEntities(int maxResults, int firstResult) {
        return findFornecedorEntities(false, maxResults, firstResult);
    }

    private List<Fornecedor> findFornecedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fornecedor.class));
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

    public Fornecedor findFornecedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornecedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornecedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fornecedor> rt = cq.from(Fornecedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
