/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author renan
 */
@Entity
@Table(name = "VENDAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendas.findAll", query = "SELECT v FROM Vendas v"),
    @NamedQuery(name = "Vendas.findById", query = "SELECT v FROM Vendas v WHERE v.id = :id")})
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "ID_CLI", referencedColumnName = "ID")
    @ManyToOne
    private Cliente idCli;
    @JoinColumn(name = "ID_FORNEC", referencedColumnName = "ID")
    @ManyToOne
    private Fornecedor idFornec;
    @JoinColumn(name = "ID_PROD", referencedColumnName = "ID")
    @ManyToOne
    private Produto idProd;

    public Venda() {
    }

    public Venda(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getIdCli() {
        return idCli;
    }

    public void setIdCli(Cliente idCli) {
        this.idCli = idCli;
    }

    public Fornecedor getIdFornec() {
        return idFornec;
    }

    public void setIdFornec(Fornecedor idFornec) {
        this.idFornec = idFornec;
    }

    public Produto getIdProd() {
        return idProd;
    }

    public void setIdProd(Produto idProd) {
        this.idProd = idProd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uricer.model.Vendas[ id=" + id + " ]";
    }
    
}
