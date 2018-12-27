package br.com.teste_yank.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
		setLayout(new BorderLayout());
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setLayout(new FlowLayout());
		
		btConsulta = new JButton("Consultar Usuário");
		btConsulta.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaConsultaUser telaConsultaUser = new TelaConsultaUser();
				telaConsultaUser.setSize(300, 300);
				telaConsultaUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaConsultaUser.setVisible(true);

			}
		});
		btInserir = new JButton("Inserir Usuário");
		btInserir.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaInserirUser telaInserirUser = new TelaInserirUser();
				telaInserirUser.setSize(700, 150);
				telaInserirUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaInserirUser.setVisible(true);
			}
		});
		btAlterar = new JButton("Alterar Usuário");
		btAlterar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaRecebeUser telaRecebeUser = new TelaRecebeUser();
				telaRecebeUser.setSize(700, 150);
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
		
		
		panelBotoes.add(btConsulta);
		panelBotoes.add(btInserir);
		panelBotoes.add(btAlterar);
		panelBotoes.add(btVoltar);
		
		add(panelBotoes, BorderLayout.CENTER);
	}
}
