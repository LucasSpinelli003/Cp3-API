package br.com.fiap.banco.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.banco.dao.EmpresaDao;
import br.com.fiap.banco.exception.BadInfoException;
import br.com.fiap.banco.exception.IdNotFoundException;
import br.com.fiap.banco.factory.ConnectionFactory;
import br.com.fiap.banco.model.Empresa;
import br.com.fiap.banco.model.Vaga;

public class EmpresaService {
	private EmpresaDao empresaDao;
	
	public EmpresaService() throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionFactory.getConnection();
		empresaDao = new EmpresaDao(conn);
	}
	
	public void cadastrar(Empresa empresa) throws ClassNotFoundException, SQLException, BadInfoException {
		validar(empresa);
		
		
		empresaDao.cadastrar(empresa);
	}
	
	private void validar(Empresa empresa) throws BadInfoException {
		if (empresa.getEmpresa() == null || empresa.getEmpresa().length() > 50) {
			throw new BadInfoException("Nome invalido, não pode ser nulo e deve conter no maximo 50 caracteres");
		}
	}
	
	public void atualizar(Empresa empresa) throws ClassNotFoundException, SQLException, IdNotFoundException, BadInfoException {
		validar(empresa);	
		empresaDao.atualizar(empresa);
	}
	
	public void remover(int codigo) throws ClassNotFoundException, SQLException, IdNotFoundException {
		empresaDao.remover(codigo);
	}
	
	public List<Empresa> listar() throws ClassNotFoundException, SQLException{
		return empresaDao.listar();
	}
	public Empresa pesquisar(int codigo) throws ClassNotFoundException, SQLException, IdNotFoundException{
		Empresa e = empresaDao.pesquisar(codigo);
		
		return e;
	}
	
}
