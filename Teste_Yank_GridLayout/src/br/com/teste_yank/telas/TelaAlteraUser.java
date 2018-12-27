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

public class TelaAlteraUser extends JFrame{
	JLabel lblCodUser;
	JTextField txfCodUser;
	JLabel lblNome;
	JTextField txfNome;
	JLabel lblIdade;
	JTextField txfIdade;
	JLabel lblCodDep;
	JTextField txfCodDep;
	int codigo;
	String nome;
	int idade;
	int dep;
	
	public TelaAlteraUser(int codigoUser, String nomeUser, int idadeUser, int codDepUser) {
		super("Digite alterações");
		codigo = codigoUser;
		nome = nomeUser;
		idade = idadeUser;
		dep = codDepUser;
		formRecebeUser(codigo, nome, idade, dep);
	}
	
	public void formRecebeUser(int codUser, String nomeUser, int idadeUser, int codDepUser) {
		GridLayout g = new GridLayout(5, 2);
		Container container = getContentPane();
		setLayout(g);
		
		lblCodUser = new JLabel("Cód. do usuário que será alterado:");
		txfCodUser = new JTextField(3);
		txfCodUser.setText(Integer.toString(codUser));
		txfCodUser.setEditable(false);
		lblNome = new JLabel("Nome:");
		txfNome = new JTextField(20);
		txfNome.setText(nomeUser);
		lblIdade = new JLabel("Idade:");
	    txfIdade = new JTextField(3);
	    txfIdade.setText(Integer.toString(idadeUser));
		lblCodDep = new JLabel("Cód. do Departamento:");
		txfCodDep = new JTextField(3);
		txfCodDep.setText(Integer.toString(codDepUser));
		
		
		
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
						int novaIdade = Integer.parseInt(txfIdade.getText());
						int novoCodDep = Integer.parseInt(txfCodDep.getText());
						
						String SQL = "UPDATE usuario SET NOME_USUARIO = ?, IDADE = ?, FK_DEPARTAMENTO = ? WHERE usuario.ID_USUARIO = ?";
						PreparedStatement pstm = conn.prepareStatement(SQL);
						pstm.setString(1, novoNome);
						pstm.setInt(2, novaIdade);
						pstm.setInt(3, novoCodDep);
						pstm.setInt(4, codUser);
						pstm.executeUpdate();
						pstm.close();
						
						JOptionPane.showMessageDialog(null,
								"Usuário alterado com sucesso", 
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
		
		add(lblCodUser);
		add(txfCodUser);
		add(lblNome);
		add(txfNome);
		add(lblIdade);
		add(txfIdade);
		add(lblCodDep);
		add(txfCodDep);
		add(btnAltera);
		add(btCancelar);
		
	}

}
