package br.com.fiap.banco.resource;

import java.sql.SQLException;
import java.util.List;

import br.com.fiap.banco.exception.BadInfoException;
import br.com.fiap.banco.exception.IdNotFoundException;
import br.com.fiap.banco.model.Empresa;
import br.com.fiap.banco.service.EmpresaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

public class EmpresaResource {
	private EmpresaService service;
	
	public EmpresaResource() throws ClassNotFoundException, SQLException {
		service = new EmpresaService();
	}
	
	//GET http://localhost:8080/07-WebApi/api/produto (Listar todos os produtos)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Empresa> lista() throws ClassNotFoundException, SQLException {
		return service.listar();
	}
	
	//GET http://localhost:8080/07-WebApi/api/produto/1 (Pesquisar pelo Id)
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response busca(@PathParam("id") int codigo) throws ClassNotFoundException, SQLException {
		try {
			return Response.ok(service.pesquisar(codigo)).build();
		} catch (IdNotFoundException e) {
			//Retornar 404 caso o produto não exista
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	//POST http://localhost:8080/07-WebApi/api/produto/ (Cadastrar um produto)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Empresa empresa, @Context UriInfo uri) throws ClassNotFoundException, SQLException {
		try {
			service.cadastrar(empresa);
			//Recupera o path (URL atual(http://localhost:8080/07-WebApi/api/produto/))
			UriBuilder uriBuilder = uri.getAbsolutePathBuilder();
			//Adiciona o id do produto que foi criado na URL
			uriBuilder.path(String.valueOf(empresa.getCodigo()));
			//Retornar o status 201 com a URL para acessar o produto criado
			return Response.created(uriBuilder.build()).build();
		} catch (BadInfoException e) {
			e.printStackTrace();
			//Retornar o status 400 bad request
			return Response.status(Status.BAD_REQUEST)
								.entity(e.getMessage()).build();
		}
	}
	
	//PUT 	 (Atualizar um produto)
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Empresa empresa, @PathParam("id") int codigo) throws ClassNotFoundException, SQLException {
		try {
			empresa.setCodigo(codigo);
			service.atualizar(empresa);
			return Response.ok().build();
		} catch (IdNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		} catch(BadInfoException e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	//DELETE http://localhost:8080/07-WebApi/api/produto/1 (Apagar um produto)
	@DELETE
	@Path("/{id}")
	public Response remover(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
		try {
			service.remover(id);
			return Response.noContent().build();
		} catch (IdNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
}





