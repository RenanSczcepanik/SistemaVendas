/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.dao;

import br.edu.uricer.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.uricer.model.Cliente;
import br.edu.uricer.model.Fornecedor;
import br.edu.uricer.model.Produto;
import br.edu.uricer.model.Venda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author renan
 */
public class VendaDAO implements Serializable {

    public VendaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda vendas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCli = vendas.getIdCli();
            if (idCli != null) {
                idCli = em.getReference(idCli.getClass(), idCli.getId());
                vendas.setIdCli(idCli);
            }
            Fornecedor idFornec = vendas.getIdFornec();
            if (idFornec != null) {
                idFornec = em.getReference(idFornec.getClass(), idFornec.getId());
                vendas.setIdFornec(idFornec);
            }
            Produto idProd = vendas.getIdProd();
            if (idProd != null) {
                idProd = em.getReference(idProd.getClass(), idProd.getId());
                vendas.setIdProd(idProd);
            }
            em.persist(vendas);
            if (idCli != null) {
                idCli.getVendasCollection().add(vendas);
                idCli = em.merge(idCli);
            }
            if (idFornec != null) {
                idFornec.getVendasCollection().add(vendas);
                idFornec = em.merge(idFornec);
            }
            if (idProd != null) {
                idProd.getVendasCollection().add(vendas);
                idProd = em.merge(idProd);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda vendas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVendas = em.find(Venda.class, vendas.getId());
            Cliente idCliOld = persistentVendas.getIdCli();
            Cliente idCliNew = vendas.getIdCli();
            Fornecedor idFornecOld = persistentVendas.getIdFornec();
            Fornecedor idFornecNew = vendas.getIdFornec();
            Produto idProdOld = persistentVendas.getIdProd();
            Produto idProdNew = vendas.getIdProd();
            if (idCliNew != null) {
                idCliNew = em.getReference(idCliNew.getClass(), idCliNew.getId());
                vendas.setIdCli(idCliNew);
            }
            if (idFornecNew != null) {
                idFornecNew = em.getReference(idFornecNew.getClass(), idFornecNew.getId());
                vendas.setIdFornec(idFornecNew);
            }
            if (idProdNew != null) {
                idProdNew = em.getReference(idProdNew.getClass(), idProdNew.getId());
                vendas.setIdProd(idProdNew);
            }
            vendas = em.merge(vendas);
            if (idCliOld != null && !idCliOld.equals(idCliNew)) {
                idCliOld.getVendasCollection().remove(vendas);
                idCliOld = em.merge(idCliOld);
            }
            if (idCliNew != null && !idCliNew.equals(idCliOld)) {
                idCliNew.getVendasCollection().add(vendas);
                idCliNew = em.merge(idCliNew);
            }
            if (idFornecOld != null && !idFornecOld.equals(idFornecNew)) {
                idFornecOld.getVendasCollection().remove(vendas);
                idFornecOld = em.merge(idFornecOld);
            }
            if (idFornecNew != null && !idFornecNew.equals(idFornecOld)) {
                idFornecNew.getVendasCollection().add(vendas);
                idFornecNew = em.merge(idFornecNew);
            }
            if (idProdOld != null && !idProdOld.equals(idProdNew)) {
                idProdOld.getVendasCollection().remove(vendas);
                idProdOld = em.merge(idProdOld);
            }
            if (idProdNew != null && !idProdNew.equals(idProdOld)) {
                idProdNew.getVendasCollection().add(vendas);
                idProdNew = em.merge(idProdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendas.getId();
                if (findVendas(id) == null) {
                    throw new NonexistentEntityException("The vendas with id " + id + " no longer exists.");
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
            Venda vendas;
            try {
                vendas = em.getReference(Venda.class, id);
                vendas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendas with id " + id + " no longer exists.", enfe);
            }
            Cliente idCli = vendas.getIdCli();
            if (idCli != null) {
                idCli.getVendasCollection().remove(vendas);
                idCli = em.merge(idCli);
            }
            Fornecedor idFornec = vendas.getIdFornec();
            if (idFornec != null) {
                idFornec.getVendasCollection().remove(vendas);
                idFornec = em.merge(idFornec);
            }
            Produto idProd = vendas.getIdProd();
            if (idProd != null) {
                idProd.getVendasCollection().remove(vendas);
                idProd = em.merge(idProd);
            }
            em.remove(vendas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendasEntities() {
        return findVendasEntities(true, -1, -1);
    }

    public List<Venda> findVendasEntities(int maxResults, int firstResult) {
        return findVendasEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
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

    public Venda findVendas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
