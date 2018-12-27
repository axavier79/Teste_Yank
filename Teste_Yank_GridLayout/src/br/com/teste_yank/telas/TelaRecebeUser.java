package br.com.teste_yank.telas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaRecebeUser extends JFrame{
	JLabel lblCodUser;
	int codUser;
	String nome;
	int idade;
	int dep;
	
	public TelaRecebeUser() {
		super("Código do Usuário");
		formRecebeUser();
	}
	
	public void formRecebeUser() {
		GridLayout g = new GridLayout(2, 1);
		Container container = getContentPane();
		setLayout(g);
		
		lblCodUser = new JLabel("Cód. do usuário a ser alterado:");
		JTextField txfCodUser = new JTextField(3);
		
		JButton btnProcura = new JButton("Procurar");
		btnProcura.addActionListener(new ActionListener() {	
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
						
						String consulta = "SELECT * FROM USUARIO";
						Statement stm = conn.createStatement();
						ResultSet rs = stm.executeQuery(consulta);
						codUser = Integer.parseInt(txfCodUser.getText());
						while(rs.next()) {
							int idUser = rs.getInt("ID_USUARIO");
							if(codUser == idUser) {
								nome = rs.getString("NOME_USUARIO");
								idade = rs.getInt("IDADE");
								dep = rs.getInt("FK_DEPARTAMENTO");
								break;
							}
						}
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
				
				TelaAlteraUser telaAlteraUser = new TelaAlteraUser(codUser, nome, idade, dep);
				telaAlteraUser.setSize(500, 150);
				telaAlteraUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaAlteraUser.setVisible(true);
			}
		});
		
		JButton btVoltar = new JButton("Voltar");
		btVoltar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		add(lblCodUser);
		add(txfCodUser);
		add(btnProcura);
		add(btVoltar);

	}

}
