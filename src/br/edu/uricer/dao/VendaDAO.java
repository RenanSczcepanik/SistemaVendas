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

    public void create(Venda venda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCli = venda.getIdCli();
            if (idCli != null) {
                idCli = em.getReference(idCli.getClass(), idCli.getId());
                venda.setIdCli(idCli);
            }
            Fornecedor idFornec = venda.getIdFornec();
            if (idFornec != null) {
                idFornec = em.getReference(idFornec.getClass(), idFornec.getId());
                venda.setIdFornec(idFornec);
            }
            Produto idProd = venda.getIdProd();
            if (idProd != null) {
                idProd = em.getReference(idProd.getClass(), idProd.getId());
                venda.setIdProd(idProd);
            }
            em.persist(venda);
            if (idCli != null) {
                idCli.getVendaCollection().add(venda);
                idCli = em.merge(idCli);
            }
            if (idFornec != null) {
                idFornec.getVendaCollection().add(venda);
                idFornec = em.merge(idFornec);
            }
            if (idProd != null) {
                idProd.getVendaCollection().add(venda);
                idProd = em.merge(idProd);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda venda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVenda = em.find(Venda.class, venda.getId());
            Cliente idCliOld = persistentVenda.getIdCli();
            Cliente idCliNew = venda.getIdCli();
            Fornecedor idFornecOld = persistentVenda.getIdFornec();
            Fornecedor idFornecNew = venda.getIdFornec();
            Produto idProdOld = persistentVenda.getIdProd();
            Produto idProdNew = venda.getIdProd();
            if (idCliNew != null) {
                idCliNew = em.getReference(idCliNew.getClass(), idCliNew.getId());
                venda.setIdCli(idCliNew);
            }
            if (idFornecNew != null) {
                idFornecNew = em.getReference(idFornecNew.getClass(), idFornecNew.getId());
                venda.setIdFornec(idFornecNew);
            }
            if (idProdNew != null) {
                idProdNew = em.getReference(idProdNew.getClass(), idProdNew.getId());
                venda.setIdProd(idProdNew);
            }
            venda = em.merge(venda);
            if (idCliOld != null && !idCliOld.equals(idCliNew)) {
                idCliOld.getVendaCollection().remove(venda);
                idCliOld = em.merge(idCliOld);
            }
            if (idCliNew != null && !idCliNew.equals(idCliOld)) {
                idCliNew.getVendaCollection().add(venda);
                idCliNew = em.merge(idCliNew);
            }
            if (idFornecOld != null && !idFornecOld.equals(idFornecNew)) {
                idFornecOld.getVendaCollection().remove(venda);
                idFornecOld = em.merge(idFornecOld);
            }
            if (idFornecNew != null && !idFornecNew.equals(idFornecOld)) {
                idFornecNew.getVendaCollection().add(venda);
                idFornecNew = em.merge(idFornecNew);
            }
            if (idProdOld != null && !idProdOld.equals(idProdNew)) {
                idProdOld.getVendaCollection().remove(venda);
                idProdOld = em.merge(idProdOld);
            }
            if (idProdNew != null && !idProdNew.equals(idProdOld)) {
                idProdNew.getVendaCollection().add(venda);
                idProdNew = em.merge(idProdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venda.getId();
                if (findVenda(id) == null) {
                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
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
            Venda venda;
            try {
                venda = em.getReference(Venda.class, id);
                venda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venda with id " + id + " no longer exists.", enfe);
            }
            Cliente idCli = venda.getIdCli();
            if (idCli != null) {
                idCli.getVendaCollection().remove(venda);
                idCli = em.merge(idCli);
            }
            Fornecedor idFornec = venda.getIdFornec();
            if (idFornec != null) {
                idFornec.getVendaCollection().remove(venda);
                idFornec = em.merge(idFornec);
            }
            Produto idProd = venda.getIdProd();
            if (idProd != null) {
                idProd.getVendaCollection().remove(venda);
                idProd = em.merge(idProd);
            }
            em.remove(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendaEntities() {
        return findVendaEntities(true, -1, -1);
    }

    public List<Venda> findVendaEntities(int maxResults, int firstResult) {
        return findVendaEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendaEntities(boolean all, int maxResults, int firstResult) {
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

    public Venda findVenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaCount() {
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
