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

public class TelaInserirDep extends JFrame{
	String nome;
	int QtFunc;
	
	public TelaInserirDep() {
		super("Inserir Departamento");
		formInserirDep();
	}
	
	public void formInserirDep() {
		GridLayout g = new GridLayout(3, 2);
		Container container = getContentPane();
		setLayout(g);
		
		JLabel lblNome = new JLabel("Nome:");
		JTextField txfNome = new JTextField(20);
		JLabel lblQtFunc = new JLabel("Qtde. Func.:");
		JTextField txfQtFunc = new JTextField(3);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				nome = txfNome.getText();
				QtFunc = Integer.parseInt(txfQtFunc.getText());
				salvar(nome, QtFunc);
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
		add(lblQtFunc);
		add(txfQtFunc);
		add(btnSalvar);
		add(btVoltar);

	}
	
	private void salvar(String nomeDepartamento, int QtFunc) {
		//Conectar ao BD
		try{
			String BD = "jdbc:mysql://localhost:3306/teste_yank?useTimezone=true&serverTimezone=UTC";
			String user = "root";
			String senha = "";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Abrir conexão ao BD
			try{				
				//Informações para conexão com o BD
				Connection conn = DriverManager.getConnection(
						BD,
						user,
						senha);
				
				//Inserção dos dados
				String nomeTabela = "departamento";
				String SQL = "INSERT INTO " +nomeTabela+ " (NOME_DEPARTAMENTO, QTDE_FUNCIONARIOS) VALUES (?, ?)";
				try{
					PreparedStatement pstm = conn.prepareStatement(SQL);
					pstm.setString(1, nomeDepartamento);
					pstm.setInt(2, QtFunc);
					pstm.execute();
					pstm.close();
					conn.close();
					
					JOptionPane.showMessageDialog(null,
							"Departamento inserido com sucesso.", 
							"Informação", 
							JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ocorrido: " + e, 
							"Erro na inserção da informação", 
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
					"Problema ao carregar o driver de conexão ao BD: " + ex.getMessage(), 
					"Erro ocorrido", 
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
