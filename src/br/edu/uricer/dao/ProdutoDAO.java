/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.dao;

import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import br.edu.uricer.model.Produto;
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
public class ProdutoDAO implements Serializable {

    public ProdutoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produtos) {
        if (produtos.getVendasCollection() == null) {
            produtos.setVendasCollection(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Venda> attachedVendasCollection = new ArrayList<Venda>();
            for (Venda vendasCollectionVendasToAttach : produtos.getVendasCollection()) {
                vendasCollectionVendasToAttach = em.getReference(vendasCollectionVendasToAttach.getClass(), vendasCollectionVendasToAttach.getId());
                attachedVendasCollection.add(vendasCollectionVendasToAttach);
            }
            produtos.setVendasCollection(attachedVendasCollection);
            em.persist(produtos);
            for (Venda vendasCollectionVendas : produtos.getVendasCollection()) {
                Produto oldIdProdOfVendasCollectionVendas = vendasCollectionVendas.getIdProd();
                vendasCollectionVendas.setIdProd(produtos);
                vendasCollectionVendas = em.merge(vendasCollectionVendas);
                if (oldIdProdOfVendasCollectionVendas != null) {
                    oldIdProdOfVendasCollectionVendas.getVendasCollection().remove(vendasCollectionVendas);
                    oldIdProdOfVendasCollectionVendas = em.merge(oldIdProdOfVendasCollectionVendas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produtos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProdutos = em.find(Produto.class, produtos.getId());
            Collection<Venda> vendasCollectionOld = persistentProdutos.getVendasCollection();
            Collection<Venda> vendasCollectionNew = produtos.getVendasCollection();
            Collection<Venda> attachedVendasCollectionNew = new ArrayList<Venda>();
            for (Venda vendasCollectionNewVendasToAttach : vendasCollectionNew) {
                vendasCollectionNewVendasToAttach = em.getReference(vendasCollectionNewVendasToAttach.getClass(), vendasCollectionNewVendasToAttach.getId());
                attachedVendasCollectionNew.add(vendasCollectionNewVendasToAttach);
            }
            vendasCollectionNew = attachedVendasCollectionNew;
            produtos.setVendasCollection(vendasCollectionNew);
            produtos = em.merge(produtos);
            for (Venda vendasCollectionOldVendas : vendasCollectionOld) {
                if (!vendasCollectionNew.contains(vendasCollectionOldVendas)) {
                    vendasCollectionOldVendas.setIdProd(null);
                    vendasCollectionOldVendas = em.merge(vendasCollectionOldVendas);
                }
            }
            for (Venda vendasCollectionNewVendas : vendasCollectionNew) {
                if (!vendasCollectionOld.contains(vendasCollectionNewVendas)) {
                    Produto oldIdProdOfVendasCollectionNewVendas = vendasCollectionNewVendas.getIdProd();
                    vendasCollectionNewVendas.setIdProd(produtos);
                    vendasCollectionNewVendas = em.merge(vendasCollectionNewVendas);
                    if (oldIdProdOfVendasCollectionNewVendas != null && !oldIdProdOfVendasCollectionNewVendas.equals(produtos)) {
                        oldIdProdOfVendasCollectionNewVendas.getVendasCollection().remove(vendasCollectionNewVendas);
                        oldIdProdOfVendasCollectionNewVendas = em.merge(oldIdProdOfVendasCollectionNewVendas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produtos.getId();
                if (findProdutos(id) == null) {
                    throw new NonexistentEntityException("The produtos with id " + id + " no longer exists.");
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
            Produto produtos;
            try {
                produtos = em.getReference(Produto.class, id);
                produtos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produtos with id " + id + " no longer exists.", enfe);
            }
            Collection<Venda> vendasCollection = produtos.getVendasCollection();
            for (Venda vendasCollectionVendas : vendasCollection) {
                vendasCollectionVendas.setIdProd(null);
                vendasCollectionVendas = em.merge(vendasCollectionVendas);
            }
            em.remove(produtos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutosEntities() {
        return findProdutosEntities(true, -1, -1);
    }

    public List<Produto> findProdutosEntities(int maxResults, int firstResult) {
        return findProdutosEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProdutos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
