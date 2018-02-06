package br.com.digidev.service.mapper;

import br.com.digidev.domain.*;
import br.com.digidev.service.dto.CategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Categoria and its DTO CategoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface CategoriaMapper extends EntityMapper <CategoriaDTO, Categoria> {

    @Mapping(source = "user.id", target = "userId")
    CategoriaDTO toDto(Categoria categoria); 

    @Mapping(source = "userId", target = "user")
    Categoria toEntity(CategoriaDTO categoriaDTO); 
    default Categoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }
}
