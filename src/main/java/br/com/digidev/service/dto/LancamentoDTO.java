package br.com.digidev.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import br.com.digidev.domain.enumeration.Tipo;

/**
 * A DTO for the Lancamento entity.
 */
public class LancamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Tipo tipo;

    @NotNull
    private LocalDate data;

    @NotNull
    private String descricao;

    @NotNull
    private BigDecimal valor;

    private Boolean pagoRecebido;

    private Long lancamentoId;

    private Long contaId;

    private Long entidadeId;

    private Long categoriaId;

    private Long userId;

    private Integer parcela;

    private Boolean recorrente;

    private Integer quantidadeParcelas;

    private String contaDescricao;

    private String categoriaDescricao;

    private String entidadeDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean isPagoRecebido() {
        return pagoRecebido;
    }

    public void setPagoRecebido(Boolean pagoRecebido) {
        this.pagoRecebido = pagoRecebido;
    }

    public Long getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Long lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public Long getEntidadeId() {
        return entidadeId;
    }

    public void setEntidadeId(Long entidadeReceitaDespesaId) {
        this.entidadeId = entidadeReceitaDespesaId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    public Boolean isRecorrente() {
        return recorrente;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public String getContaDescricao() {
        return contaDescricao;
    }

    public void setContaDescricao(String contaDescricao) {
        this.contaDescricao = contaDescricao;
    }

    public String getCategoriaDescricao() {
        return categoriaDescricao;
    }

    public void setCategoriaDescricao(String categoriaDescricao) {
        this.categoriaDescricao = categoriaDescricao;
    }

    public String getEntidadeDescricao() {
        return entidadeDescricao;
    }

    public void setEntidadeDescricao(String entidadeDescricao) {
        this.entidadeDescricao = entidadeDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LancamentoDTO lancamentoDTO = (LancamentoDTO) o;
        if(lancamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lancamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LancamentoDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", data='" + getData() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor='" + getValor() + "'" +
            ", pagoRecebido='" + isPagoRecebido() + "'" +
            "}";
    }
}
