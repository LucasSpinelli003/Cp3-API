package br.com.fiap.banco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.fiap.banco.exception.IdNotFoundException;
import br.com.fiap.banco.model.Empresa;

public class EmpresaDao {
		private Connection conn;
		
		public EmpresaDao(Connection conn) {
			this.conn = conn;
		}
		
		public Empresa pesquisar(int id) throws ClassNotFoundException, SQLException, IdNotFoundException {
			PreparedStatement stm = conn.prepareStatement("select * from" + " tb_empresa where cd_empresa = ?");
			
			stm.setInt(1, id);
			
			ResultSet result = stm.executeQuery();
		
			if (!result.next()) {
				throw new IdNotFoundException("Empresa não encontrada");
			}
			Empresa empresa = parse(result);
			stm.close();
			return empresa;
		}
		
		public void cadastrar(Empresa empresa) throws SQLException {
			PreparedStatement stm = conn.prepareStatement("insert into tb_empresa "
					+ "(cd_empresa, nm_empresa, nr_funcionario) values (?, ?, ?)");
			
			stm.setInt(1, empresa.getCodigo());
			stm.setString(2, empresa.getEmpresa());
			stm.setInt(3, empresa.getNumeroFunc());
			
			stm.executeUpdate();
			stm.close();
		}
	
		public List<Empresa> listar() throws ClassNotFoundException, SQLException {

			PreparedStatement stm = conn.prepareStatement("select * from tb_empresa");
			ResultSet result = stm.executeQuery();

			List<Empresa> lista = new ArrayList<Empresa>();
			
			while (result.next()) {
					
					Empresa prod = parse(result);
					lista.add(prod);	
				
			}
			// Retornar a lista de produto
			stm.close();
			return lista;
		}

		//Método auxiliar que recebe o resultado do banco e retorna o objeto produto
		private Empresa parse(ResultSet result) throws SQLException {
			// Obter os valores do produto
			int codigo = result.getInt("cd_empresa");
			String nome = result.getString("nm_empresa");
			int estoque = result.getInt("nr_funcionario");
			
			
			// Instanciar o produto com os valores
			Empresa empresa = new Empresa(codigo, nome, estoque);

			return empresa;
		}

		public void atualizar(Empresa empresa) throws ClassNotFoundException, SQLException, IdNotFoundException {

			PreparedStatement stm = conn.prepareStatement("update tb_empresa set nm_empresa = ?, nr_funcionario = ? where cd_empresa = ?");
			//Setar os parametros na Query
			stm.setString(1, empresa.getEmpresa());
			stm.setInt(2, empresa.getNumeroFunc());
			stm.setInt(3, empresa.getCodigo());
			// Executar a Query
			int linha = stm.executeUpdate();
			stm.close();
			if (linha == 0)
				throw new IdNotFoundException("Empresa não encontrada para atualizar");
		}

		public void remover(int id) throws ClassNotFoundException, SQLException, IdNotFoundException {
			
			// PreparedStatement
			PreparedStatement stm = conn.prepareStatement("delete from tb_empresa where cd_empresa = ?");
			// Setar os parametros na Query
			stm.setInt(1, id);
			// Executar a Query
			int linha = stm.executeUpdate();
			stm.close();
			if (linha == 0)
				throw new IdNotFoundException("Empresa não encontrada para remoção");
		}

		

		
}

