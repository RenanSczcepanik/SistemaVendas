/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.dao;

import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import br.edu.uricer.model.Fornecedores;
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
public class FornecedoresJpaController implements Serializable {

    public FornecedoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fornecedores fornecedores) {
        if (fornecedores.getVendasCollection() == null) {
            fornecedores.setVendasCollection(new ArrayList<Vendas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Vendas> attachedVendasCollection = new ArrayList<Vendas>();
            for (Vendas vendasCollectionVendasToAttach : fornecedores.getVendasCollection()) {
                vendasCollectionVendasToAttach = em.getReference(vendasCollectionVendasToAttach.getClass(), vendasCollectionVendasToAttach.getId());
                attachedVendasCollection.add(vendasCollectionVendasToAttach);
            }
            fornecedores.setVendasCollection(attachedVendasCollection);
            em.persist(fornecedores);
            for (Vendas vendasCollectionVendas : fornecedores.getVendasCollection()) {
                Fornecedores oldIdFornecOfVendasCollectionVendas = vendasCollectionVendas.getIdFornec();
                vendasCollectionVendas.setIdFornec(fornecedores);
                vendasCollectionVendas = em.merge(vendasCollectionVendas);
                if (oldIdFornecOfVendasCollectionVendas != null) {
                    oldIdFornecOfVendasCollectionVendas.getVendasCollection().remove(vendasCollectionVendas);
                    oldIdFornecOfVendasCollectionVendas = em.merge(oldIdFornecOfVendasCollectionVendas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fornecedores fornecedores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedores persistentFornecedores = em.find(Fornecedores.class, fornecedores.getId());
            Collection<Vendas> vendasCollectionOld = persistentFornecedores.getVendasCollection();
            Collection<Vendas> vendasCollectionNew = fornecedores.getVendasCollection();
            Collection<Vendas> attachedVendasCollectionNew = new ArrayList<Vendas>();
            for (Vendas vendasCollectionNewVendasToAttach : vendasCollectionNew) {
                vendasCollectionNewVendasToAttach = em.getReference(vendasCollectionNewVendasToAttach.getClass(), vendasCollectionNewVendasToAttach.getId());
                attachedVendasCollectionNew.add(vendasCollectionNewVendasToAttach);
            }
            vendasCollectionNew = attachedVendasCollectionNew;
            fornecedores.setVendasCollection(vendasCollectionNew);
            fornecedores = em.merge(fornecedores);
            for (Vendas vendasCollectionOldVendas : vendasCollectionOld) {
                if (!vendasCollectionNew.contains(vendasCollectionOldVendas)) {
                    vendasCollectionOldVendas.setIdFornec(null);
                    vendasCollectionOldVendas = em.merge(vendasCollectionOldVendas);
                }
            }
            for (Vendas vendasCollectionNewVendas : vendasCollectionNew) {
                if (!vendasCollectionOld.contains(vendasCollectionNewVendas)) {
                    Fornecedores oldIdFornecOfVendasCollectionNewVendas = vendasCollectionNewVendas.getIdFornec();
                    vendasCollectionNewVendas.setIdFornec(fornecedores);
                    vendasCollectionNewVendas = em.merge(vendasCollectionNewVendas);
                    if (oldIdFornecOfVendasCollectionNewVendas != null && !oldIdFornecOfVendasCollectionNewVendas.equals(fornecedores)) {
                        oldIdFornecOfVendasCollectionNewVendas.getVendasCollection().remove(vendasCollectionNewVendas);
                        oldIdFornecOfVendasCollectionNewVendas = em.merge(oldIdFornecOfVendasCollectionNewVendas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fornecedores.getId();
                if (findFornecedores(id) == null) {
                    throw new NonexistentEntityException("The fornecedores with id " + id + " no longer exists.");
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
            Fornecedores fornecedores;
            try {
                fornecedores = em.getReference(Fornecedores.class, id);
                fornecedores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornecedores with id " + id + " no longer exists.", enfe);
            }
            Collection<Vendas> vendasCollection = fornecedores.getVendasCollection();
            for (Vendas vendasCollectionVendas : vendasCollection) {
                vendasCollectionVendas.setIdFornec(null);
                vendasCollectionVendas = em.merge(vendasCollectionVendas);
            }
            em.remove(fornecedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fornecedores> findFornecedoresEntities() {
        return findFornecedoresEntities(true, -1, -1);
    }

    public List<Fornecedores> findFornecedoresEntities(int maxResults, int firstResult) {
        return findFornecedoresEntities(false, maxResults, firstResult);
    }

    private List<Fornecedores> findFornecedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fornecedores.class));
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

    public Fornecedores findFornecedores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornecedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornecedoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fornecedores> rt = cq.from(Fornecedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
