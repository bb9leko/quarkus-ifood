package com.github.bb9leko.ifood.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.github.bb9leko.ifood.cadastro.Prato;
import com.github.bb9leko.ifood.cadastro.dto.AdicionarPratoDTO;
import com.github.bb9leko.ifood.cadastro.dto.PratoDTO;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

	PratoDTO toDTO(Prato p);
	
	Prato toPrato(AdicionarPratoDTO dto);
	
	//void toPrato(AtualizarPratoDTO dto, @MappingTarget Prato prato);
	
	
}
