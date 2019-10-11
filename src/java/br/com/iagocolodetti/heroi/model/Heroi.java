package br.com.iagocolodetti.heroi.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author iagocolodetti
 */
@Entity
@Table(name = "heroi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Heroi.findAllAtivo", query = "SELECT h FROM Heroi h WHERE h.ativo = 1"),
    @NamedQuery(name = "Heroi.logicalDelete", query = "UPDATE Heroi h SET h.ativo = 0 WHERE h.id = :id")
})
public class Heroi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome")
    @Expose
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    @Expose
    private Date dataCadastro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ativo")
    @Expose
    private boolean ativo;
    @JoinColumn(name = "universo_id", referencedColumnName = "id")
    @OneToOne
    @Expose
    private Universo universo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "heroi", orphanRemoval = true)
    @Expose
    private List<Poder> poderes;

    public Heroi() {
    }

    public Heroi(Integer id) {
        this.id = id;
    }
    
    public Heroi(Integer id, String nome, Date dataCadastro, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public Universo getUniverso() {
        return universo;
    }

    public void setUniverso(Universo universo) {
        this.universo = universo;
    }

    @XmlTransient
    public List<Poder> getPoderes() {
        return poderes;
    }

    public void setPoderes(List<Poder> poderes) {
        this.poderes = poderes;
    }
    
    @PrePersist
    private void prePersist() {
       poderes.forEach(poder -> poder.setHeroi(this));
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
        if (!(object instanceof Heroi)) {
            return false;
        }
        Heroi other = (Heroi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Heroi[ id=" + id + " ]";
    }

}
