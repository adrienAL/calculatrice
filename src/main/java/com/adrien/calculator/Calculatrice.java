package com.adrien.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class Calculatrice {
	public String lstOperation[] = { "*", "/", "+", "-", "^", "!" };

	public Double opperationSimple(double arg1, char opp, double arg2) {
		switch (opp) {
		case '*': {
			return arg1 * arg2;
		}
		case '/': {
			if (arg2 == 0) {
				return null;
			} else {
				return arg1 / arg2;
			}
		}
		case '+': {
			return arg1 + arg2;
		}
		case '-': {
			return arg1 - arg2;
		}
		case '^': {
			return Math.pow(arg1, arg2);
		}
		case '!': {
			Double resultat = 1.0;
			for (int i = 2; i <= arg1; i++) {
				resultat *= i;
			}
			return resultat;
		}
		default:
			return null;
		}
	}

	public Double resolveBloc(String arg) {
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
		for (int op = 0; op < lstOperation.length; op++) {
			String operation = lstOperation[op];
			if (arg.indexOf(operation) != -1) {
				for (int i = 0; i < elements.size(); i++) {
					String element = elements.get(i);

					if (element.equals(operation)) {
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

						Double numBefor;
						Double numAfter;
						String blocBefore = "";
						String blocAfter = "";
						if (elementm1.equals(")")) {
							blocBefore = this.extractBlocBefore(elements.subList(0, i));
							numBefor = this.resolveBloc(blocBefore);
							blocBefore = "(" + blocBefore + ")";
						} else {
							blocBefore = elementm1;
							numBefor = Double.parseDouble(elementm1);
						}

						if (elementp1.equals("(")) {
							blocAfter = this.extractBlocAfter(elements.subList(i + 1, elements.size()));
							numAfter = this.resolveBloc(blocAfter);
							blocAfter = "(" + blocAfter + ")";
						} else {
							blocAfter = elementp1;
							try {
								numAfter = Double.parseDouble(elementp1);
							} catch (Exception e) {
								numAfter = 0.0;
							}
						}

						// création d'un nouveau bloc à résoudre avec le res de la multiplication
						String toReplace = blocBefore + element + blocAfter;
						toReplace = this.echapCaract(toReplace);
						Double resultatOperation = this.opperationSimple(numBefor, element.charAt(0), numAfter);
						String newArg = "";
						if (resultatOperation != null) {
							newArg = arg.replaceAll(toReplace, String.valueOf(resultatOperation));
						} else {
							return null;
						}

						try {
							return Double.parseDouble(newArg);
						} catch (Exception e) {
							return this.resolveBloc(newArg);
						}

					}
				}
			}
		}
		return null;
	}

	private String echapCaract(String toReplace) {
		toReplace = toReplace.replaceAll("\\*", "\\\\*");
		toReplace = toReplace.replaceAll("\\/", "\\\\/");
		toReplace = toReplace.replaceAll("\\+", "\\\\+");
		toReplace = toReplace.replaceAll("\\-", "\\\\-");
		toReplace = toReplace.replaceAll("\\(", "\\\\(");
		toReplace = toReplace.replaceAll("\\)", "\\\\)");
		toReplace = toReplace.replaceAll("\\^", "\\\\^");
		return toReplace;
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
