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
 * Tipo de Conta cadastra ex: Carteira pessoal, Conta Poupanca etc.
 * @author DigiDev Team
 */
@ApiModel(description = "Tipo de Conta cadastra ex: Carteira pessoal, Conta Poupanca etc. @author DigiDev Team")
@Entity
@Table(name = "tipo_conta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoConta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descricao do tipo conta
     */
    @NotNull
    @ApiModelProperty(value = "Descricao do tipo conta", required = true)
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

    public TipoConta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public User getUser() {
        return user;
    }

    public TipoConta user(User user) {
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
        TipoConta tipoConta = (TipoConta) o;
        if (tipoConta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoConta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoConta{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
