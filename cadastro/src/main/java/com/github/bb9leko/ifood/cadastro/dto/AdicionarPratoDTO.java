package com.github.bb9leko.ifood.cadastro.dto;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.github.bb9leko.ifood.cadastro.Restaurante;

public class AdicionarPratoDTO {
	
	@NotEmpty
	@NotNull
	public String nome;
    
    public String descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    public Restaurante restaurante;

}
