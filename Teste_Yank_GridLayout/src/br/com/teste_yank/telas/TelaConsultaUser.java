package br.com.teste_yank.telas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaConsultaUser extends JFrame{
	String nome;
	
	public TelaConsultaUser() {
		super("Consulta de Usuários");
		formConsultaUser();
	}
	
	public void formConsultaUser() {
		GridLayout g = new GridLayout(4, 1);
		Container container = getContentPane();
		setLayout(g);
		
		JLabel lblNome = new JLabel("Nome do Usuário a ser pesquisado:");
		JTextField txfNome = new JTextField(20);
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				nome = txfNome.getText();
				pesquisar(nome);
			}
		});
		JButton btVoltar = new JButton("Voltar");
		btVoltar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		add(lblNome);
		add(txfNome);
		add(btnPesquisar);
		add(btVoltar);
		
	}
	
	private void pesquisar(String nomeUsuario) {
		//Conectar ao BD
		try{
			String BD = "jdbc:mysql://localhost:3306/teste_yank?useTimezone=true&serverTimezone=UTC";
			String user = "root";
			String senha = "";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
						
			//Abrir conexão ao BD
			try{				
				String infos ="";
				int y = 1;
				
				//Informações para conexão com o BD
				Connection conn = DriverManager.getConnection(
						BD,
						user,
						senha);
				String consulta = "SELECT * FROM USUARIO";
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(consulta);
				
				while (rs.next()) {
					if(rs.getString("NOME_USUARIO").equals(nomeUsuario)) {
						infos += "\nResultado " +y+ ":";
						infos += "\nID= " +rs.getInt("ID_USUARIO");
						infos += "\nNome= " +rs.getString("NOME_USUARIO");
						infos += "\nIdade= " +rs.getInt("IDADE");
						String join = "SELECT usuario.FK_DEPARTAMENTO, departamento.NOME_DEPARTAMENTO"
								+ " FROM usuario INNER JOIN departamento"
								+ " ON usuario.FK_DEPARTAMENTO = departamento.ID_DEPARTAMENTO"
								+ " WHERE usuario.FK_DEPARTAMENTO = ?;";
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

				stm.close();
				conn.close();
				//Exibir as informações
				JOptionPane.showMessageDialog(null,
						infos, 
						"Informações encontradas", 
						JOptionPane.INFORMATION_MESSAGE);
			
			}catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao conectar ao BD: " + e.getMessage(), 
						"Erro ocorrido", 
						JOptionPane.ERROR_MESSAGE);
			}
			
		}catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,
					"Problema ao carregar o driver de conexão ao BD: " + ex.getMessage(), 
					"Erro ocorrido", 
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
