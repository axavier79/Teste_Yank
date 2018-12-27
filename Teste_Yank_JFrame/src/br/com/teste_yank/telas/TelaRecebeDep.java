package br.com.teste_yank.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

public class TelaRecebeDep extends JFrame{
	JLabel lblCodDep;
	int codDep;
	String nome;
	int qtFunc;
	
	public TelaRecebeDep() {
		super("Código do Departamento");
		formRecebeDep();
	}
	
	public void formRecebeDep() {
		setLayout(new BorderLayout());
		
		JPanel painel = new JPanel();
		painel.setLayout(new FlowLayout());
		
		lblCodDep = new JLabel("Cód. departamento a ser alterado:");
		JTextField txfCodDep = new JTextField(3);
		
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
						
						String consulta = "SELECT * FROM DEPARTAMENTO";
						Statement stm = conn.createStatement();
						ResultSet rs = stm.executeQuery(consulta);
						codDep = Integer.parseInt(txfCodDep.getText());
						while(rs.next()) {
							int idDep = rs.getInt("ID_DEPARTAMENTO");
							if(codDep == idDep) {
								nome = rs.getString("NOME_DEPARTAMENTO");
								qtFunc = rs.getInt("QTDE_FUNCIONARIOS");
								break;
							}
						}
						System.out.println(nome+qtFunc);
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
				System.out.println(codDep+nome+qtFunc);
				TelaAlteraDep telaAlteraDep = new TelaAlteraDep(codDep, nome, qtFunc);
				telaAlteraDep.setSize(800, 150);
				telaAlteraDep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaAlteraDep.setVisible(true);
			}
		});
		
		JButton btVoltar = new JButton("Voltar");
		btVoltar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		painel.add(lblCodDep);
		painel.add(txfCodDep);
		painel.add(btnProcura);
		painel.add(btVoltar);
		
		add(painel, BorderLayout.CENTER);
	}

}
