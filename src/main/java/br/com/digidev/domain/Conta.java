package br.com.digidev.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Conta: Ex: Carteira, Conta itau, Conta Bradesco
 * @author DigiDev Team
 */
@ApiModel(description = "Conta: Ex: Carteira, Conta itau, Conta Bradesco @author DigiDev Team")
@Entity
@Table(name = "conta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    /**
     * descricao da conta
     */
    @NotNull
    @ApiModelProperty(value = "descricao da conta", required = true)
    @Column(name = "saldo_inicial", precision=10, scale=2, nullable = false)
    private BigDecimal saldoInicial;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoConta tipoConta;

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

    public Conta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public Conta saldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
        return this;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public Conta tipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
        return this;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public User getUser() {
        return user;
    }

    public Conta user(User user) {
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
        Conta conta = (Conta) o;
        if (conta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Conta{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", saldoInicial='" + getSaldoInicial() + "'" +
            "}";
    }
}
