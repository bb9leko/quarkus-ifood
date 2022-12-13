package com.github.bb9leko.ifood.cadastro.dto;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class AdicionarRestauranteDTO {

	@NotEmpty
	@NotNull
    public String propietario;

    public String cnpj;

    //Hibernate Validator 
    @Size(min = 3, max = 6)
    public String nomeFantasia;

    @OneToOne(cascade = CascadeType.ALL)
    public LocalizacaoDTO localizacao;

}
