package br.com.digidev.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EntidadeReceitaDespesa entity.
 */
public class EntidadeReceitaDespesaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO = (EntidadeReceitaDespesaDTO) o;
        if(entidadeReceitaDespesaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entidadeReceitaDespesaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntidadeReceitaDespesaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
