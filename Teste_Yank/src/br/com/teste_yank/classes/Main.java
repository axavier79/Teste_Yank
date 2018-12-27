package br.com.teste_yank.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		Connection conn = null;
		int escolha = 0;
		String SQL = "";
		String nomeTabela = "";
		String nomeUsuario = "";
		int idadeUsuario = 0;
		int codDepartamento = 0;
		int codUsuario = 0;
		String nomeDepartamento = "";
		int qtdeFuncionarios = 0;
		String campoDepartamento = "";
		String campoUsuario = "";
		String nomesColDep = "";
		String nomesColUsers = "";
		String consulta = "";
		String infos = "";
		String novaInfo = "";
		int y = 1;
		
		//Receber a informação do usuário
		try{
			escolha = Integer.parseInt(JOptionPane.showInputDialog(
				"Escolha a opção desejada digitando o número correspondente:\n"
				+ "1 - Inserção de usuário\n"
				+ "2 - Inserção de departamento\n"
				+ "3 - Procurar por nome de usuário\n"
				+ "4 - Procurar por nome de departamento\n"
				+ "5 - Atualização de usuário\n"
				+ "6 - Atualização de departamento"));
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ocorrido: " + e, 
					"Erro na inserção do número correspondente à opção desejada", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		//Conectar ao BD
		try{
			String BD = "jdbc:mysql://localhost:3306/teste_yank?useTimezone=true&serverTimezone=UTC";
			String user = "root";
			String senha = "";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Abrir conexão ao BD
			try{				
				//Informações para conexão com o BD
				conn = DriverManager.getConnection(
						BD,
						user,
						senha);
			}catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao conectar ao BD: " + e.getMessage(), 
						"Erro ocorrido", 
						JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,
					"Problema ao carregar o driver de conexão ao BD: " + ex.getMessage(), 
					"Erro ocorrido", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		switch (escolha) {
		case 1:
			//Receber o nome do usuário a ser inserido
			try{
				nomeUsuario = JOptionPane.showInputDialog(
					"Digite o nome de usuário a ser inserido:\n");
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			//Receber a idade do usuário a ser inserido
			try{
				idadeUsuario = Integer.parseInt(JOptionPane.showInputDialog(
					"Digite a idade do usuário a ser inserido:\n"));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			//Receber o código do departamento do novo usuário
			try{
				codDepartamento = Integer.parseInt(JOptionPane.showInputDialog(
					"Digite o código do departamento do novo usuário:\n"));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			
			//Inserção dos dados
			nomeTabela = "usuario";
			SQL = "INSERT INTO " +nomeTabela+ " (NOME_USUARIO, IDADE, FK_DEPARTAMENTO) VALUES (?, ?, ?)";
			try{
				PreparedStatement pstm = conn.prepareStatement(SQL);
				pstm.setString(1, nomeUsuario);
				pstm.setInt(2, idadeUsuario);
				pstm.setInt(3, codDepartamento);
				pstm.execute();
				pstm.close();
				conn.close();
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			break;
			
		case 2:
			//Receber o nome do departamento a ser inserido
			try{
				nomeDepartamento = JOptionPane.showInputDialog(
					"Digite o nome do departamento a ser inserido:\n");
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			//Receber a quantidade de funcionários
			try{
				qtdeFuncionarios = Integer.parseInt(JOptionPane.showInputDialog(
					"Digite a quantidade de funcionários a ser inserido:\n"));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			
			//Inserção dos dados
			nomeTabela = "departamento";
			SQL = "INSERT INTO " +nomeTabela+ " (NOME_DEPARTAMENTO, QTDE_FUNCIONARIOS) VALUES (?, ?)";
			try{
				PreparedStatement pstm = conn.prepareStatement(SQL);
				pstm.setString(1, nomeDepartamento);
				pstm.setInt(2, qtdeFuncionarios);
				pstm.execute();
				pstm.close();
				conn.close();
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			break;
			
		case 3:			
			//Receber o nome do usuario a ser pesquisado
			nomeUsuario = JOptionPane.showInputDialog(
					"Digite o nome do usuario a ser pesquisado:");
			try{
				consulta = "SELECT * FROM USUARIO";
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(consulta);
				
				while (rs.next()) {
					if(rs.getString("NOME_USUARIO").equals(nomeUsuario)) {
						infos ="";
						infos += "\nResultado " +y+ ":";
						infos += "\nID= " +rs.getInt("ID_USUARIO");
						infos += "\nNome= " +rs.getString("NOME_USUARIO");
						infos += "\nIdade= " +rs.getInt("IDADE");
						String join = "SELECT usuario.FK_DEPARTAMENTO, departamento.NOME_DEPARTAMENTO"
								+ " FROM usuario INNER JOIN departamento"
								+ " ON usuario.FK_DEPARTAMENTO = departamento.ID_DEPARTAMENTO"
								+ " WHERE usuario.FK_DEPARTAMENTO = ?;";
						System.out.println(join);
						PreparedStatement prepare = conn.prepareStatement(join);
						prepare.setInt(1, rs.getInt("FK_DEPARTAMENTO"));
						ResultSet result = prepare.executeQuery();
						while(result.next()) {
							infos += "\nDep.= " +result.getString("NOME_DEPARTAMENTO");
						}
						prepare.close();
						y++;
					}
					if (infos.equals("")){
						infos = "Usuário não encontrado.";
					}
				}
				//Zera o contador y
				y=0;
				stm.close();
				conn.close();
				//Exibir as informações
				JOptionPane.showMessageDialog(null,
						infos, 
						"Informações encontradas", 
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na recuperação da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			break;
			
		case 4:
			//Receber o nome do departamento a ser pesquisado
			nomeDepartamento = JOptionPane.showInputDialog(
					"Digite o nome do departamento a ser pesquisado:");
			try{
				consulta = "SELECT * FROM DEPARTAMENTO";
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(consulta);
				
				while (rs.next()) {
					if(rs.getString("NOME_DEPARTAMENTO").equals(nomeDepartamento)) {
						infos ="";
						infos += "\nResultado " +y+ ":";
						infos += "\nID= " +rs.getInt("ID_DEPARTAMENTO");
						infos += "\nNome= " +rs.getString("NOME_DEPARTAMENTO");
						infos += "\nQtde Func.= " +rs.getInt("QTDE_FUNCIONARIOS");
						y++;
					}
					if (infos.equals("")){
						infos = "Departamento não encontrado.";
					}
				}
				//Zera o contador y
				y=0;
				stm.close();
				conn.close();
				//Exibir as informações
				JOptionPane.showMessageDialog(null,
						infos, 
						"Informações encontradas", 
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na recuperação da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			break;
			
		case 5:
			//Receber o usuário a ser atualizado
			try{
				//Início - Consulta aos nomes dos usuários
				infos = "";
				consulta = "SELECT * FROM USUARIO";
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(consulta);
				while(rs.next()) {
					infos += "\n" +rs.getInt("ID_USUARIO")+ " - ";
					infos += rs.getString("NOME_USUARIO");
				}
				//Fim -  Consulta aos nomes dos usuários
				
				//Receber o nome do usuário a ser atualizado
				codUsuario = Integer.parseInt(JOptionPane.showInputDialog(
						"Digite o número do usuario a ser atualizado:"+infos));

				//Início - Consulta aos nomes das colunas
				ResultSetMetaData meta = rs.getMetaData();
				for (int x=2; x <= meta.getColumnCount(); x++){
					if (x<=meta.getColumnCount()) {
						nomesColUsers += "\n"+meta.getColumnName(x);
					}
					
				}
				//Fim - Consulta aos nomes das colunas
				
				//Receber a coluna a ser atualizada
				campoUsuario = JOptionPane.showInputDialog(
					"Digite o nome da coluna a ser atualizada (igual à informação abaixo):"+nomesColUsers);
				
				//Receber a atualização
				novaInfo = JOptionPane.showInputDialog(
						"Digite a nova informação:");
				//Atualização da tabela
				nomeTabela = "usuario";			
				SQL = "UPDATE " +nomeTabela+ " SET " +campoUsuario+ " = ? WHERE ID_USUARIO = " +codUsuario;
				try{
					PreparedStatement pstm = conn.prepareStatement(SQL);
					pstm.setString(1, novaInfo);
					pstm.execute();
					pstm.close();
					JOptionPane.showMessageDialog(null,
							"Campo " +campoUsuario+ " atualizado com sucesso!", 
							"Atualização bem sucedida", 
							JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ocorrido: " + e, 
							"Erro na inserção da informação", 
							JOptionPane.ERROR_MESSAGE);
				}
				
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
			break;
			
		case 6:
			//Receber o departamento a ser atualizado
			try{
				//Início - Consulta aos nomes dos departamentos
				infos = "";
				consulta = "SELECT * FROM DEPARTAMENTO";
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(consulta);
				while(rs.next()) {
					infos += "\n" +rs.getInt("ID_DEPARTAMENTO")+ " - ";
					infos += rs.getString("NOME_DEPARTAMENTO");
				}
				//Fim -  Consulta aos nomes dos departamentos
				
				//Receber o nome do departamento a ser atualizado
				codDepartamento = Integer.parseInt(JOptionPane.showInputDialog(
						"Digite o número do departamento a ser atualizado:"+infos));

				//Início - Consulta aos nomes das colunas
				ResultSetMetaData meta = rs.getMetaData();
				for (int x=2; x <= meta.getColumnCount(); x++){
					if (x<=meta.getColumnCount()) {
						nomesColDep += "\n"+meta.getColumnName(x);
					}
					
				}
				//Fim - Consulta aos nomes das colunas
				
				//Receber a coluna a ser atualizada
				campoDepartamento = JOptionPane.showInputDialog(
					"Digite o nome da coluna a ser atualizada (igual à informação abaixo):"+nomesColDep);
				
				//Receber a atualização
				novaInfo = JOptionPane.showInputDialog(
						"Digite a nova informação:");
				//Atualização da tabela
				nomeTabela = "departamento";			
				SQL = "UPDATE " +nomeTabela+ " SET " +campoDepartamento+ " = ? WHERE ID_DEPARTAMENTO = " +codDepartamento;
				try{
					PreparedStatement pstm = conn.prepareStatement(SQL);
					pstm.setString(1, novaInfo);
					pstm.execute();
					pstm.close();
					JOptionPane.showMessageDialog(null,
							"Campo " +campoDepartamento+ " atualizado com sucesso!", 
							"Atualização bem sucedida", 
							JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ocorrido: " + e, 
							"Erro na inserção da informação", 
							JOptionPane.ERROR_MESSAGE);
				}
				
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ocorrido: " + e, 
						"Erro na inserção da informação", 
						JOptionPane.ERROR_MESSAGE);
			}
				
			break;
			
		default:
			break;
		}
		
		//Fechar conexão com o BD
		try{
			conn.close();
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				"Erro ao fechar a conexão ao BD: " + e.getMessage(), 
				"Erro ocorrido", 
				JOptionPane.ERROR_MESSAGE);
		}

	}

}
