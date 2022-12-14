package com.github.bb9leko.ifood.cadastro;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;
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

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.bb9leko.ifood.cadastro.dto.AdicionarPratoDTO;
import com.github.bb9leko.ifood.cadastro.dto.AdicionarRestauranteDTO;
import com.github.bb9leko.ifood.cadastro.dto.AtualizarRestauranteDTO;
import com.github.bb9leko.ifood.cadastro.dto.PratoDTO;
import com.github.bb9leko.ifood.cadastro.dto.PratoMapper;
import com.github.bb9leko.ifood.cadastro.dto.RestauranteDTO;
import com.github.bb9leko.ifood.cadastro.dto.RestauranteMapper;


@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@Tag(name = "restaurante")
@RolesAllowed("propietario")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = 
@OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
@SecurityRequirement(name = "ifood-oauth", scopes = {})
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;
    
    @Inject 
    PratoMapper pratoMapper;
    
    @Inject
    JsonWebToken jwt;

    @GET
    public List<RestauranteDTO> buscar() {
    	Stream<Restaurante> restaurantes = Restaurante.streamAll();
    	return restaurantes.map(r -> restauranteMapper.toRestauranteDTO(r)).collect(Collectors.toList());
       
    }

    @POST
    @Transactional
    @APIResponse(responseCode = "201", description = "Caso restaurante seja cadastrado com sucesso")
    //@APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    public Response adicionar(@Valid AdicionarRestauranteDTO dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        //restaurante.propietario = sub;
        restaurante.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, AtualizarRestauranteDTO dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteOp.get();
        
        /*if (!restaurante.proprietario.equals(sub)) {
            throw new ForbiddenException();
        }*/
        
        restauranteMapper.toRestaurante(dto, restaurante);
        
        restaurante.persist();
        //return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);

        restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });
    }
    
    /*
    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name = "prato")
    public List<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    	if(restauranteOp.isEmpty()) {
    		throw new NotFoundException("Restaurante n??o existe!");
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
        });*/
    

}