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

public class TelaAlteraDep extends JFrame{
	JLabel lblCodDep;
	JTextField txfCodDep;
	JLabel lblNome;
	JTextField txfNome;
	JLabel lblQtFunc;
	JTextField txfQtFunc;
	int codigo;
	String nome;
	int qtdeFunc;
	
	public TelaAlteraDep(int codigoDep, String nomeDep, int qtdFunc) {
		super("Digite alterações");
		codigo = codigoDep;
		nome = nomeDep;
		qtdeFunc = qtdFunc;
		formRecebeDep(codigo, nome, qtdeFunc);
	}
	
	public void formRecebeDep(int codDep, String nomeDep, int qtFunc) {
		GridLayout g = new GridLayout(4, 2);
		Container container = getContentPane();
		setLayout(g);
		
		lblCodDep = new JLabel("Cód. do departamento que será alterado:");
		txfCodDep = new JTextField(3);
		txfCodDep.setText(Integer.toString(codDep));
		txfCodDep.setEditable(false);
		lblNome = new JLabel("Nome:");
		txfNome = new JTextField(20);
		txfNome.setText(nomeDep);
		lblQtFunc = new JLabel("Qtd. Func.:");
	    txfQtFunc = new JTextField(3);
	    txfQtFunc.setText(Integer.toString(qtFunc));

		
		JButton btnAltera = new JButton("Alterar");
		btnAltera.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
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
						
						String novoNome = txfNome.getText();
						int novaQtFunc = Integer.parseInt(txfQtFunc.getText());
						
						String SQL = "UPDATE DEPARTAMENTO SET NOME_DEPARTAMENTO = ?, QTDE_FUNCIONARIOS = ? WHERE DEPARTAMENTO.ID_DEPARTAMENTO = ?";
						PreparedStatement pstm = conn.prepareStatement(SQL);
						pstm.setString(1, novoNome);
						pstm.setInt(2, novaQtFunc);
						pstm.setInt(3, codDep);
						pstm.executeUpdate();
						pstm.close();
						
						JOptionPane.showMessageDialog(null,
								"Departamento alterado com sucesso", 
								"Alteração", 
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
						
					}catch (SQLException erro) {
						JOptionPane.showMessageDialog(null,
								"Erro ao conectar ao BD: " + erro.getMessage(), 
								"Erro ocorrido", 
								JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (ClassNotFoundException ex) {
					JOptionPane.showMessageDialog(null,
							"Problema ao carregar o driver de conexão ao BD: " + ex.getMessage(), 
							"Erro ocorrido", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
				
		});
		
		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		add(lblCodDep);
		add(txfCodDep);
		add(lblNome);
		add(txfNome);
		add(lblQtFunc);
		add(txfQtFunc);
		add(btnAltera);
		add(btCancelar);
		
	}

}
