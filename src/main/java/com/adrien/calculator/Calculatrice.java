package com.adrien.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class Calculatrice {

	public Double add(double arg1, double arg2) {
		return arg1 + arg2;
	}

	public Double substraction(double arg1, double arg2) {
		return arg1 - arg2;
	}

	public Double multiplication(double arg1, double arg2) {
		return arg1 * arg2;
	}

	public Double division(double arg1, double arg2) {
		// divition par zero interdite
		if (arg2 == 0) {
			return null;
		} else {
			return arg1 / arg2;
		}
	}

	public Double resolveBloc(String arg) {
		System.out.println(arg);
		Character[] chars = ArrayUtils.toObject(arg.toCharArray());
		List<String> elements = new ArrayList<String>();
		int indexNumber = 0;
		for (int i = 0; i < chars.length; i++) {
			if (Character.isDigit(chars[i]) || chars[i] == '.'
					|| (chars[i] == '-' && !Character.isDigit(chars[i - 1]))) {
				try {
					elements.set(indexNumber, elements.get(indexNumber) + chars[i]);
				} catch (Exception e) {
					elements.add(String.valueOf(chars[i]));
				}
			} else {
				// si plus un chiffre on passe au suivant et on ajoute l'opperation
				elements.add(String.valueOf(chars[i]));
				indexNumber = elements.size();

			}
		}
		// dans un premier temps on fait les multiplications
		for (int i = 0; i < elements.size(); i++) {
			String element = elements.get(i);

			if (element.equals("*")) {
				String elementm1 = "";
				String elementp1 = "";
				try {
					elementm1 = elements.get(i - 1);
				} catch (Exception e) {
					System.out.println("elementm1 ereure : " + arg);
				}
				try {
					elementp1 = elements.get(i + 1);
				} catch (Exception e) {
					System.out.println("elementp1 erreur : " + arg);
				}

				double numBefor;
				double numAfter;
				String blocBefore = "";
				String blocAfter = "";
				if (elementm1.equals(")")) {
					blocBefore = "(" + this.extractBlocBefore(elements.subList(0, i)) + ")";
					numBefor = this.resolveBloc(blocBefore);
				} else {
					blocBefore = elementm1;
					numBefor = Double.parseDouble(elementm1);
				}

				if (elementp1.equals("(")) {
					blocAfter = "(" + this.extractBlocAfter(elements.subList(i + 1, elements.size())) + ")";
					numAfter = this.resolveBloc(blocAfter);
				} else {
					blocAfter = elementp1;
					numAfter = Double.parseDouble(elementp1);
				}

				// création d'un nouveau bloc à résoudre avec le res de la multiplication
				String toReplace = blocBefore + "\\*" + blocAfter;
				String newArg = arg.replaceAll(toReplace, String.valueOf(this.multiplication(numBefor, numAfter)));
				System.out.println("arg : " + arg);
				System.out.println("toReplace : " + toReplace);
				System.out.println("res multipli : " + String.valueOf(this.multiplication(numBefor, numAfter)));
				System.out.println("newArg : " + newArg);
				try {
					return Double.parseDouble(newArg);
				} catch (Exception e) {
					System.out.println("un autre bloc :  " + newArg);
					break;
					// return this.resolveBloc(newArg);
				}

			}
		}

		// System.out.println(number.size() + "les nombre :");
		for (int i = 0; i < elements.size(); i++) {
			// System.out.println(i + " // " + number.get(i));
		}
		return (double) 0;
	}

	public String extractBlocBefore(List<String> elements) {
		// on inverse la liste pour simplifier le parcour de celle ci si bloc avant
		Collections.reverse(elements);
		String bloc = "";
		int deep = 1;
		// on parcours la liste à partir du 2eme element car 1er = parenthese fermente
		// du bloc
		Iterator<String> it = elements.iterator();
		it.next();
		do {
			String element = (String) it.next();
			bloc = element + bloc;
			if (element.equals(")")) {
				deep++;
			} else if (element.equals("(")) {
				deep--;
			}

		} while (it.hasNext() && deep != 0);
		// on supprime le premier element du bloc car c'est la parenthese
		// ouvrante du bloc
		bloc = bloc.substring(1, bloc.length());

		return bloc;

	}

	public String extractBlocAfter(List<String> elements) {
		String bloc = "";
		int deep = 1;
		// on parcours la liste à partir du 2eme element car 1er = parenthese ouvrante
		// du bloc
		Iterator<String> it = elements.iterator();
		it.next();
		do {
			String element = (String) it.next();
			bloc += element;
			if (element.equals("(")) {
				deep++;
			} else if (element.equals(")")) {
				deep--;
			}

		} while (it.hasNext() && deep != 0);
		// on supprime le dernier element du bloc car c'est la parenthese
		// fermente du bloc
		bloc = bloc.substring(0, bloc.length() - 1);

		return bloc;

	}

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.getMenu();

	}

}
