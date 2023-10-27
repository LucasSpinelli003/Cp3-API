package br.com.fiap.banco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.banco.exception.IdNotFoundException;
import br.com.fiap.banco.model.Empresa;
import br.com.fiap.banco.model.Vaga;

public class VagaDao {
		private Connection conn;
		
		public VagaDao(Connection conn) {
			this.conn = conn;
		}
		
		public Vaga pesquisar(int codigo) throws SQLException, IdNotFoundException {
			PreparedStatement stm = conn.prepareStatement("select * from tb_vaga where cd_vaga = ?");
			stm.setInt(1, codigo);
			
			ResultSet result = stm.executeQuery();
			
			if (!result.next()) {
				throw new IdNotFoundException("Categoria n�o encontrada");
			}
			
			int id = result.getInt("cd_vaga");
			String titulo = result.getString("ds_titulo");
			String nm_vaga = result.getString("ds_vaga");
			double salario = result.getInt("vl_salario");
			String data = result.getString("dt_publicacao");
			Vaga vaga = new Vaga(id, titulo, nm_vaga, salario, data);

			return vaga;
		}
		
		public void cadastrar(Vaga vaga) throws SQLException {
			PreparedStatement stm = conn.prepareStatement("insert into tb_vaga "
					+ "(cd_vaga, ds_titulo, ds_vaga, vl_salario, dt_publicacao) values (?, ?, ?, ?, ?)");
			
			stm.setInt(1, vaga.getCodigo());
			stm.setString(2, vaga.getNome());
			stm.setString(3, vaga.getVaga());
			stm.setDouble(4, vaga.getSalario());
			stm.setString(5, vaga.getData());
			
			stm.executeUpdate();
		}
		
}

