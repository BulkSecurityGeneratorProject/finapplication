package br.com.digidev.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Conta entity.
 */
public class ContaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private BigDecimal saldoInicial;

    private Long tipoContaId;

    private Long userId;

    private BigDecimal valorTotalTipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Long getTipoContaId() {
        return tipoContaId;
    }

    public void setTipoContaId(Long tipoContaId) {
        this.tipoContaId = tipoContaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ContaDTO(BigDecimal valor){
        this.valorTotalTipo =  valor;
    }

    public ContaDTO(){
        //Default
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContaDTO contaDTO = (ContaDTO) o;
        if(contaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", saldoInicial='" + getSaldoInicial() + "'" +
            "}";
    }

    public BigDecimal getValorTotalTipo() {
        return valorTotalTipo;
    }

    public void setValorTotalTipo(BigDecimal valorTotalTipo) {
        this.valorTotalTipo = valorTotalTipo;
    }
}
