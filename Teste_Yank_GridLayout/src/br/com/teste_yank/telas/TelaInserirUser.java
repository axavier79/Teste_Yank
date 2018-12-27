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

public class TelaInserirUser extends JFrame{
	String nome;
	int idade;
	int codDep;
	
	public TelaInserirUser() {
		super("Inserir Usu�rio");
		formInserirUser();
	}
	
	public void formInserirUser() {
		GridLayout g = new GridLayout(4, 2);
		Container container = getContentPane();
		setLayout(g);
		
		JLabel lblNome = new JLabel("Nome:");
		JTextField txfNome = new JTextField(20);
		JLabel lblIdade = new JLabel("Idade:");
		JTextField txfIdade = new JTextField(3);
		JLabel lblCodDep = new JLabel("C�d. Dep.:");
		JTextField txfCodDep = new JTextField(20);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				nome = txfNome.getText();
				idade = Integer.parseInt(txfIdade.getText());
				codDep = Integer.parseInt(txfCodDep.getText());
				salvar(nome, idade, codDep);
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
		add(lblIdade);
		add(txfIdade);
		add(lblCodDep);
		add(txfCodDep); 
		add(btnSalvar);
		add(btVoltar);
		
	}
	
	private void salvar(String nomeUsuario, int idadeUser, int codDepUser) {
		//Conectar ao BD
		try{
			String BD = "jdbc:mysql://localhost:3306/teste_yank?useTimezone=true&serverTimezone=UTC";
			String user = "root";
			String senha = "";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Abrir conex�o ao BD
			try{				
				//Informa��es para conex�o com o BD
				Connection conn = DriverManager.getConnection(
						BD,
						user,
						senha);
				
				//Inser��o dos dados
				String nomeTabela = "usuario";
				String SQL = "INSERT INTO " +nomeTabela+ " (NOME_USUARIO, IDADE, FK_DEPARTAMENTO) VALUES (?, ?, ?)";
				try{
					PreparedStatement pstm = conn.prepareStatement(SQL);
					pstm.setString(1, nomeUsuario);
					pstm.setInt(2, idadeUser);
					pstm.setInt(3, codDepUser);
					pstm.execute();
					pstm.close();
					conn.close();
					
					JOptionPane.showMessageDialog(null,
							"Usu�rio inserido com sucesso.", 
							"Informa��o", 
							JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ocorrido: " + e, 
							"Erro na inser��o da informa��o", 
							JOptionPane.ERROR_MESSAGE);
				}
			}catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao conectar ao BD: " + e.getMessage(), 
						"Erro ocorrido", 
						JOptionPane.ERROR_MESSAGE);
			}
			
		}catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,
					"Problema ao carregar o driver de conex�o ao BD: " + ex.getMessage(), 
					"Erro ocorrido", 
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
