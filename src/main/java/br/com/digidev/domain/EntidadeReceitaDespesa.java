package br.com.digidev.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entidade a pagar ou receber o lancamento
 * @author DigiDev Team
 */
@ApiModel(description = "Entidade a pagar ou receber o lancamento @author DigiDev Team")
@Entity
@Table(name = "entidade_receita_despesa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntidadeReceitaDespesa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Responsavel a receber ou pagar
     */
    @NotNull
    @ApiModelProperty(value = "Responsavel a receber ou pagar", required = true)
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public EntidadeReceitaDespesa descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public User getUser() {
        return user;
    }

    public EntidadeReceitaDespesa user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntidadeReceitaDespesa entidadeReceitaDespesa = (EntidadeReceitaDespesa) o;
        if (entidadeReceitaDespesa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entidadeReceitaDespesa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntidadeReceitaDespesa{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
