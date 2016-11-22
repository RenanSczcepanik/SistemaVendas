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

    public void create(Fornecedor fornecedores) {
        if (fornecedores.getVendasCollection() == null) {
            fornecedores.setVendasCollection(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Venda> attachedVendasCollection = new ArrayList<Venda>();
            for (Venda vendasCollectionVendasToAttach : fornecedores.getVendasCollection()) {
                vendasCollectionVendasToAttach = em.getReference(vendasCollectionVendasToAttach.getClass(), vendasCollectionVendasToAttach.getId());
                attachedVendasCollection.add(vendasCollectionVendasToAttach);
            }
            fornecedores.setVendasCollection(attachedVendasCollection);
            em.persist(fornecedores);
            for (Venda vendasCollectionVendas : fornecedores.getVendasCollection()) {
                Fornecedor oldIdFornecOfVendasCollectionVendas = vendasCollectionVendas.getIdFornec();
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

    public void edit(Fornecedor fornecedores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor persistentFornecedores = em.find(Fornecedor.class, fornecedores.getId());
            Collection<Venda> vendasCollectionOld = persistentFornecedores.getVendasCollection();
            Collection<Venda> vendasCollectionNew = fornecedores.getVendasCollection();
            Collection<Venda> attachedVendasCollectionNew = new ArrayList<Venda>();
            for (Venda vendasCollectionNewVendasToAttach : vendasCollectionNew) {
                vendasCollectionNewVendasToAttach = em.getReference(vendasCollectionNewVendasToAttach.getClass(), vendasCollectionNewVendasToAttach.getId());
                attachedVendasCollectionNew.add(vendasCollectionNewVendasToAttach);
            }
            vendasCollectionNew = attachedVendasCollectionNew;
            fornecedores.setVendasCollection(vendasCollectionNew);
            fornecedores = em.merge(fornecedores);
            for (Venda vendasCollectionOldVendas : vendasCollectionOld) {
                if (!vendasCollectionNew.contains(vendasCollectionOldVendas)) {
                    vendasCollectionOldVendas.setIdFornec(null);
                    vendasCollectionOldVendas = em.merge(vendasCollectionOldVendas);
                }
            }
            for (Venda vendasCollectionNewVendas : vendasCollectionNew) {
                if (!vendasCollectionOld.contains(vendasCollectionNewVendas)) {
                    Fornecedor oldIdFornecOfVendasCollectionNewVendas = vendasCollectionNewVendas.getIdFornec();
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
            Fornecedor fornecedores;
            try {
                fornecedores = em.getReference(Fornecedor.class, id);
                fornecedores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornecedores with id " + id + " no longer exists.", enfe);
            }
            Collection<Venda> vendasCollection = fornecedores.getVendasCollection();
            for (Venda vendasCollectionVendas : vendasCollection) {
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

    public List<Fornecedor> findFornecedoresEntities() {
        return findFornecedoresEntities(true, -1, -1);
    }

    public List<Fornecedor> findFornecedoresEntities(int maxResults, int firstResult) {
        return findFornecedoresEntities(false, maxResults, firstResult);
    }

    private List<Fornecedor> findFornecedoresEntities(boolean all, int maxResults, int firstResult) {
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

    public Fornecedor findFornecedores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornecedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornecedoresCount() {
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
