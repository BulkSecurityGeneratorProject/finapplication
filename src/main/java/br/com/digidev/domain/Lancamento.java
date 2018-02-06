package br.com.digidev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.com.digidev.domain.enumeration.Tipo;

/**
 * Lancamento da despesa ou receita
 * @author DigiDev Team
 */
@ApiModel(description = "Lancamento da despesa ou receita @author DigiDev Team")
@Entity
@Table(name = "lancamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lancamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;

    /**
     * Tipo do lancamento
     */
    @NotNull
    @ApiModelProperty(value = "Tipo do lancamento", required = true)
    @Column(name = "data", nullable = false)
    private LocalDate data;

    /**
     * Data do vencimento ou pagamento
     */
    @NotNull
    @ApiModelProperty(value = "Data do vencimento ou pagamento", required = true)
    @Column(name = "descricao", nullable = false)
    private String descricao;

    /**
     * Descricao do lancamento
     */
    @NotNull
    @ApiModelProperty(value = "Descricao do lancamento", required = true)
    @Column(name = "valor", precision=10, scale=2, nullable = false)
    private BigDecimal valor;

    /**
     * Indica o sucesso do pagamento ou recebimento
     */
    @ApiModelProperty(value = "Indica o sucesso do pagamento ou recebimento")
    @Column(name = "pago_recebido")
    private Boolean pagoRecebido;

    @ManyToOne
    private Lancamento lancamento;

    @OneToMany(mappedBy = "lancamento")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lancamento> lancamentoPais = new HashSet<>();

    @ApiModelProperty(value = "Numero da parcela")
    private Integer parcela;

    @ManyToOne
    private Conta conta;

    @ManyToOne
    private EntidadeReceitaDespesa entidade;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Lancamento tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public Lancamento data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public Lancamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Lancamento valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean isPagoRecebido() {
        return pagoRecebido;
    }

    public Lancamento pagoRecebido(Boolean pagoRecebido) {
        this.pagoRecebido = pagoRecebido;
        return this;
    }

    public void setPagoRecebido(Boolean pagoRecebido) {
        this.pagoRecebido = pagoRecebido;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public Lancamento lancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
        return this;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public Set<Lancamento> getLancamentoPais() {
        return lancamentoPais;
    }

    public Lancamento lancamentoPais(Set<Lancamento> lancamentos) {
        this.lancamentoPais = lancamentos;
        return this;
    }

    public Lancamento addLancamentoPai(Lancamento lancamento) {
        this.lancamentoPais.add(lancamento);
        lancamento.setLancamento(this);
        return this;
    }

    public Lancamento removeLancamentoPai(Lancamento lancamento) {
        this.lancamentoPais.remove(lancamento);
        lancamento.setLancamento(null);
        return this;
    }

    public void setLancamentoPais(Set<Lancamento> lancamentos) {
        this.lancamentoPais = lancamentos;
    }

    public Conta getConta() {
        return conta;
    }

    public Lancamento conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public EntidadeReceitaDespesa getEntidade() {
        return entidade;
    }

    public Lancamento entidade(EntidadeReceitaDespesa entidadeReceitaDespesa) {
        this.entidade = entidadeReceitaDespesa;
        return this;
    }

    public void setEntidade(EntidadeReceitaDespesa entidadeReceitaDespesa) {
        this.entidade = entidadeReceitaDespesa;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Lancamento categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public User getUser() {
        return user;
    }

    public Lancamento user(User user) {
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
        Lancamento lancamento = (Lancamento) o;
        if (lancamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lancamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lancamento{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", data='" + getData() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor='" + getValor() + "'" +
            ", pagoRecebido='" + isPagoRecebido() + "'" +
            "}";
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }
}
