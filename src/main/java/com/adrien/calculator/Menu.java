package com.adrien.calculator;

import java.util.Scanner;

public class Menu {
	private Scanner scan = new Scanner(System.in);

	public void getMenu() {

		String calcule;
		do {
			System.out.println("entrez votre calcule (exit pour quitter):");
			calcule = scan.next();
			if (!calcule.equals("exit")) {
				Calculatrice casio = new Calculatrice();
				double res = casio.resolveBloc(calcule);
				System.out.println(res);
			}

		} while (!calcule.equals("exit"));

	}
}
