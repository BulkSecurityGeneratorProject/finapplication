package br.com.digidev.service.mapper;

import br.com.digidev.domain.*;
import br.com.digidev.service.dto.EntidadeReceitaDespesaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EntidadeReceitaDespesa and its DTO EntidadeReceitaDespesaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface EntidadeReceitaDespesaMapper extends EntityMapper <EntidadeReceitaDespesaDTO, EntidadeReceitaDespesa> {

    @Mapping(source = "user.id", target = "userId")
    EntidadeReceitaDespesaDTO toDto(EntidadeReceitaDespesa entidadeReceitaDespesa); 

    @Mapping(source = "userId", target = "user")
    EntidadeReceitaDespesa toEntity(EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO); 
    default EntidadeReceitaDespesa fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntidadeReceitaDespesa entidadeReceitaDespesa = new EntidadeReceitaDespesa();
        entidadeReceitaDespesa.setId(id);
        return entidadeReceitaDespesa;
    }
}
