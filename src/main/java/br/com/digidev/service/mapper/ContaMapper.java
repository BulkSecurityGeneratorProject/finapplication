package br.com.digidev.service.mapper;

import br.com.digidev.domain.*;
import br.com.digidev.service.dto.ContaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Conta and its DTO ContaDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoContaMapper.class, UserMapper.class, })
public interface ContaMapper extends EntityMapper <ContaDTO, Conta> {

    @Mapping(source = "tipoConta.id", target = "tipoContaId")

    @Mapping(source = "user.id", target = "userId")
    ContaDTO toDto(Conta conta); 

    @Mapping(source = "tipoContaId", target = "tipoConta")

    @Mapping(source = "userId", target = "user")
    Conta toEntity(ContaDTO contaDTO); 
    default Conta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Conta conta = new Conta();
        conta.setId(id);
        return conta;
    }
}
