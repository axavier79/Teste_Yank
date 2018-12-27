package br.com.teste_yank.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
		super("Digite altera��es");
		codigo = codigoDep;
		nome = nomeDep;
		qtdeFunc = qtdFunc;
		formRecebeDep(codigo, nome, qtdeFunc);
	}
	
	public void formRecebeDep(int codDep, String nomeDep, int qtFunc) {
		setLayout(new BorderLayout());
		
		JPanel painel1 = new JPanel();
		painel1.setLayout(new FlowLayout());
		JPanel painel2 = new JPanel();
		painel2.setLayout(new FlowLayout());
		
		lblCodDep = new JLabel("C�d. do departamento que ser� alterado:");
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
					//Abrir conex�o ao BD
					try{				
						//Informa��es para conex�o com o BD
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
								"Altera��o", 
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
							"Problema ao carregar o driver de conex�o ao BD: " + ex.getMessage(), 
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
		
		painel1.add(lblCodDep);
		painel1.add(txfCodDep);
		painel1.add(lblNome);
		painel1.add(txfNome);
		painel1.add(lblQtFunc);
		painel1.add(txfQtFunc);
		painel2.add(btnAltera);
		painel2.add(btCancelar);
		
		add(painel1, BorderLayout.NORTH);
		add(painel2, BorderLayout.CENTER);
	}

}
