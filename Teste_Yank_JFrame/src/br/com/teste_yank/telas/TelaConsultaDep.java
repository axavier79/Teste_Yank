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

public class TelaConsultaDep extends JFrame{
	String nome;
	
	public TelaConsultaDep() {
		super("Consulta de Departamentos");
		formConsultaDep();
	}
	
	public void formConsultaDep() {
		setLayout(new BorderLayout());
		
		JPanel painel = new JPanel();
		painel.setLayout(new FlowLayout());
		
		JLabel lblNome = new JLabel("Departamento a ser pesquisado:");
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
		
		painel.add(lblNome);
		painel.add(txfNome);
		painel.add(btnPesquisar);
		painel.add(btVoltar);
		
		add(painel, BorderLayout.CENTER);
	}
	
	private void pesquisar(String nomeDepartamento) {
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
				String consulta = "SELECT * FROM DEPARTAMENTO";
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
		}catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,
					"Problema ao carregar o driver de conexão ao BD: " + ex.getMessage(), 
					"Erro ocorrido", 
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
