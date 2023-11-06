package br.com.fiap.banco.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.banco.dao.EmpresaDao;
import br.com.fiap.banco.dao.VagaDao;
import br.com.fiap.banco.exception.BadInfoException;
import br.com.fiap.banco.exception.IdNotFoundException;
import br.com.fiap.banco.factory.ConnectionFactory;
import br.com.fiap.banco.model.Empresa;
import br.com.fiap.banco.model.Vaga;

public class VagaService {
	private VagaDao vagaDao;
	private EmpresaDao empresaDao;
	
	public VagaService() throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionFactory.getConnection();
		vagaDao = new VagaDao(conn);
	}
	
	public void cadastrar(Vaga vaga) throws ClassNotFoundException, SQLException, BadInfoException {
		//Implementar algumas regras:
		//Nome � obrigat�rio e n�o pode ter mais do que 50 caracteres
		validar(vaga);
		
		
		vagaDao.cadastrar(vaga);
	}
	
	private void validar(Vaga vaga) throws BadInfoException {
		if (vaga.getNome() == null || vaga.getNome().length() > 50) {
			throw new BadInfoException("Nome invalido, não pode ser nulo e deve conter no maximo 50 caracteres");
		}	
	}
	
	public Vaga pesquisar(int codigo) throws ClassNotFoundException, SQLException, IdNotFoundException{
		Vaga v = vagaDao.pesquisar(codigo);
		
		if (v.getEmpresa() != null) {
			Empresa e = empresaDao.pesquisar(v.getEmpresa().getCodigo()); 
			v.setEmpresa(e);
		}
		return v;
	}
	
	public void atualizar(Vaga vaga) throws ClassNotFoundException, SQLException, IdNotFoundException, BadInfoException {
		validar(vaga);	
		vagaDao.atualizar(vaga);
	}
	
	public void remover(int codigo) throws ClassNotFoundException, SQLException, IdNotFoundException {
		vagaDao.remover(codigo);
	}
	
	public List<Vaga> listar() throws ClassNotFoundException, SQLException{
		return vagaDao.listar();
	}
	
}
