package br.com.teste_yank.principal;

import javax.swing.JFrame;

import br.com.teste_yank.telas.TelaInicial;

public class Main {

	public static void main(String[] args) {
		TelaInicial telaInicial = new TelaInicial();
		telaInicial.setSize(300, 100);
		telaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		telaInicial.setVisible(true);

	}

}
