package com.github.bb9leko.ifood.resources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.bb9leko.ifood.cadastro.Prato;
import com.github.bb9leko.ifood.cadastro.Restaurante;
import com.github.bb9leko.ifood.cadastro.dto.AdicionarPratoDTO;
import com.github.bb9leko.ifood.cadastro.dto.PratoDTO;
import com.github.bb9leko.ifood.mappers.PratoMapper;
import com.github.bb9leko.ifood.mappers.RestauranteMapper;


@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {
		
	@Inject
    RestauranteMapper restauranteMapper;
		
    @Inject 
    PratoMapper pratoMapper;

	   	    
    @GET
    @Path("{idRestaurante}/pratos")
    public List<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    	if(restauranteOp.isEmpty()) {
    		throw new NotFoundException("Restaurante não existe!");
    	}
    	Stream<Prato> pratos = Prato.stream("restaurante", restauranteOp.get());
    	return pratos.map(p -> pratoMapper.toDTO(p)).collect(Collectors.toList());
    }
    
    @POST
    @Transactional
    public Response adicionar(@Valid AdicionarPratoDTO dto) {
        Prato prato = pratoMapper.toPrato(dto);
        prato.persist();
        return Response.status(Response.Status.CREATED).build();
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, Prato dto) {
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        if (pratoOp.isEmpty()) {
            throw new NotFoundException();
        }
        Prato prato = pratoOp.get();
        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.restaurante = dto.restaurante;
        prato.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);

        pratoOp.ifPresentOrElse(Prato::delete, () -> {
            throw new NotFoundException();
        });
    }


}
