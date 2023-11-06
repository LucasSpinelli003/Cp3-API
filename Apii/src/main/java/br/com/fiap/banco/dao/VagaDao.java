package br.com.fiap.banco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.banco.exception.IdNotFoundException;
import br.com.fiap.banco.model.Empresa;
import br.com.fiap.banco.model.Vaga;

public class VagaDao {

	private Connection conn;
	
	public VagaDao(Connection conn) {
		this.conn = conn;
	}

	public Vaga pesquisar(int id) throws ClassNotFoundException, SQLException, IdNotFoundException {
		PreparedStatement stm = conn.prepareStatement("select * from" + " tb_vaga where cd_vaga = ?");
		
		stm.setInt(1, id);
		
		ResultSet result = stm.executeQuery();
	
		if (!result.next()) {
			throw new IdNotFoundException("Vaga não encontrada");
		}
		Vaga vaga = parse(result);
		stm.close();
		return vaga;
	}
	
	public void cadastrar(Vaga vaga) throws SQLException {
		PreparedStatement stm = conn.prepareStatement("insert into tb_vaga "
				+ "(cd_vaga, ds_titulo, ds_vaga,vl_salario,dt_publicacao) values (?, ?, ?, ?, ?)");
		
		stm.setInt(1, vaga.getCodigo());
		stm.setString(2, vaga.getNome());
		stm.setString(3, vaga.getVaga());
		stm.setDouble(4, vaga.getSalario());
		stm.setString(5, vaga.getData());
		
		stm.executeUpdate();
		stm.close();
		
	}
	public void atualizar(Vaga vaga) throws ClassNotFoundException, SQLException, IdNotFoundException {

		PreparedStatement stm = conn.prepareStatement("update tb_vaga set ds_titulo = ?, ds_vaga = ?, vl_salario = ?, dt_publicacao = ?  where cd_vaga = ?");
		//Setar os parametros na Query
		stm.setString(1, vaga.getNome());
		stm.setString(2, vaga.getVaga());
		stm.setDouble(3, vaga.getSalario());
		stm.setString(4, vaga.getData());
		stm.setInt(5,vaga.getCodigo());
		// Executar a Query
		int linha = stm.executeUpdate();
		stm.close();
		if (linha == 0)
			throw new IdNotFoundException("Vaga não encontrada para atualizar");
	}
	
	public void remover(int id) throws ClassNotFoundException, SQLException, IdNotFoundException {
		
		// PreparedStatement
		PreparedStatement stm = conn.prepareStatement("delete from tb_vaga where cd_vaga = ?");
		// Setar os parametros na Query
		stm.setInt(1, id);
		// Executar a Query
		int linha = stm.executeUpdate();
		stm.close();
		if (linha == 0)
			throw new IdNotFoundException("Vaga não encontrada para remoção");
	}
	
	public List<Vaga> listar() throws ClassNotFoundException, SQLException {

		PreparedStatement stm = conn.prepareStatement("select * from tb_vaga");
		ResultSet result = stm.executeQuery();

		List<Vaga> lista = new ArrayList<Vaga>();
		
		while (result.next()) {
			Vaga prod = parse(result);
			lista.add(prod);
		}
		// Retornar a lista de produto
		stm.close();
		return lista;
	}


	//Método auxiliar que recebe o resultado do banco e retorna o objeto produto
	private Vaga parse(ResultSet result) throws SQLException {
		// Obter os valores do produto
		int codigo = result.getInt("cd_vaga");
		String titulo = result.getString("ds_titulo");
		String ds = result.getString("ds_vaga");
		double salario = result.getDouble("vl_salario");
		String data = result.getString("dt_publicacao");
		
		
		int idEmpresa = result.getInt("cd_empresa");
		
		
		// Instanciar o produto com os valores
		Vaga vaga = new Vaga(codigo, titulo, ds,salario,data);
		
		if (idEmpresa != 0) {
			Empresa empresa = new Empresa();
			empresa.setCodigo(idEmpresa);
			vaga.setEmpresa(empresa);
		}
		
		return vaga;
	}
}