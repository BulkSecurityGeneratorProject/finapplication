package br.com.digidev.service.mapper;

import br.com.digidev.domain.*;
import br.com.digidev.service.dto.TipoContaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoConta and its DTO TipoContaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface TipoContaMapper extends EntityMapper <TipoContaDTO, TipoConta> {

    @Mapping(source = "user.id", target = "userId")
    TipoContaDTO toDto(TipoConta tipoConta); 

    @Mapping(source = "userId", target = "user")
    TipoConta toEntity(TipoContaDTO tipoContaDTO); 
    default TipoConta fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(id);
        return tipoConta;
    }
}
