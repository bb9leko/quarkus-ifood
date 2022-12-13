package com.github.bb9leko.ifood.cadastro.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

public class BuscarRestauranteDTO {
	
    public String propietario;

    public String cnpj;

    public String nomeFantasia;

    @OneToOne(cascade = CascadeType.ALL)
    public LocalizacaoDTO localizacao;
    
    public Date dataCriacao;
    
    public Date dataAtualizacao;

}
