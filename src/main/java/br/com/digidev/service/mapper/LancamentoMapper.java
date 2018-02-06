package br.com.digidev.service.mapper;

import br.com.digidev.domain.*;
import br.com.digidev.service.dto.LancamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lancamento and its DTO LancamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {ContaMapper.class, EntidadeReceitaDespesaMapper.class, CategoriaMapper.class, UserMapper.class, })
public interface LancamentoMapper extends EntityMapper <LancamentoDTO, Lancamento> {

    @Mapping(source = "lancamento.id", target = "lancamentoId")

    @Mapping(source = "conta.id", target = "contaId")

    @Mapping(source = "entidade.id", target = "entidadeId")

    @Mapping(source = "categoria.id", target = "categoriaId")

    @Mapping(source = "categoria.descricao", target = "categoriaDescricao")

    @Mapping(source = "conta.descricao", target = "contaDescricao")

    @Mapping(source = "entidade.descricao", target = "entidadeDescricao")

    @Mapping(source = "user.id", target = "userId")
    LancamentoDTO toDto(Lancamento lancamento);

    @Mapping(source = "lancamentoId", target = "lancamento")
    @Mapping(target = "lancamentoPais", ignore = true)

    @Mapping(source = "contaId", target = "conta")

    @Mapping(source = "entidadeId", target = "entidade")

    @Mapping(source = "categoriaId", target = "categoria")

    @Mapping(source = "userId", target = "user")
    Lancamento toEntity(LancamentoDTO lancamentoDTO);
    default Lancamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lancamento lancamento = new Lancamento();
        lancamento.setId(id);
        return lancamento;
    }
}
