package br.com.teste_yank.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaEscolhaDep extends JFrame{
	JButton btConsulta;
	JButton btInserir;
	JButton btAlterar;
	JButton btVoltar;
	
	public TelaEscolhaDep() {
		super("Opções para Departamentos");
		formEscolhaDep();
	}
	
	public void formEscolhaDep() {
		setLayout(new BorderLayout());
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setLayout(new FlowLayout());
		
		btConsulta = new JButton("Consultar Departamentos");
		btConsulta.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaConsultaDep telaConsultaDep = new TelaConsultaDep();
				telaConsultaDep.setSize(300, 300);
				telaConsultaDep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaConsultaDep.setVisible(true);
			}
		});
		btInserir = new JButton("Inserir Departamentos");
		btInserir.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaInserirDep telaInserirDep = new TelaInserirDep();
				telaInserirDep.setSize(500, 150);
				telaInserirDep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaInserirDep.setVisible(true);
			}
		});
		btAlterar = new JButton("Alterar Departamentos");
		btAlterar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaRecebeDep telaRecebeDep = new TelaRecebeDep();
				telaRecebeDep.setSize(500, 150);
				telaRecebeDep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaRecebeDep.setVisible(true);
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
