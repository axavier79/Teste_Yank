package br.com.teste_yank.telas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TelaEscolhaUser extends JFrame{
	JButton btConsulta;
	JButton btInserir;
	JButton btAlterar;
	JButton btVoltar;
	
	public TelaEscolhaUser() {
		super("Opções para Usuários");
		formEscolhaUser();
	}

	public void formEscolhaUser() {
		GridLayout g = new GridLayout(2, 1);
		Container container = getContentPane();
		setLayout(g);
		
		btConsulta = new JButton("Consultar Usuário");
		btConsulta.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaConsultaUser telaConsultaUser = new TelaConsultaUser();
				telaConsultaUser.setSize(250, 150);
				telaConsultaUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaConsultaUser.setVisible(true);

			}
		});
		btInserir = new JButton("Inserir Usuário");
		btInserir.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaInserirUser telaInserirUser = new TelaInserirUser();
				telaInserirUser.setSize(250, 120);
				telaInserirUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaInserirUser.setVisible(true);
			}
		});
		btAlterar = new JButton("Alterar Usuário");
		btAlterar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaRecebeUser telaRecebeUser = new TelaRecebeUser();
				telaRecebeUser.setSize(400, 100);
				telaRecebeUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaRecebeUser.setVisible(true);
			}
		});
		btVoltar = new JButton("Voltar para Início");
		btVoltar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		add(btConsulta);
		add(btInserir);
		add(btAlterar);
		add(btVoltar);
		
	}
}
