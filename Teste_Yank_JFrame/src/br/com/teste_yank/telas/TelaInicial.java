package br.com.teste_yank.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaInicial extends JFrame{
	JButton btUsuarios;
	JButton btDepartamentos;
	
	public TelaInicial() {
		super("Tela Inicial");
		formInicial();
		
	}
	
	public void formInicial() {
		setLayout(new BorderLayout());
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setLayout(new FlowLayout());
		
		btUsuarios = new JButton("Usuários");
		btUsuarios.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaUser telaEscolhaUser = new TelaEscolhaUser();
				telaEscolhaUser.setSize(350, 150);
				telaEscolhaUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaEscolhaUser.setVisible(true);
				
			}
		});
		
		btDepartamentos = new JButton("Departamentos");
		btDepartamentos.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaDep telaEscolhaDep = new TelaEscolhaDep();
				telaEscolhaDep.setSize(400, 150);
				telaEscolhaDep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaEscolhaDep.setVisible(true);
				
			}
		});
		
		panelBotoes.add(btUsuarios);
		panelBotoes.add(btDepartamentos);
		
		add(panelBotoes, BorderLayout.CENTER);	
	}
	
}
