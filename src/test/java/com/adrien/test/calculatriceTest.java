package com.adrien.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.adrien.calculator.Calculatrice;

class calculatriceTest {
	private Calculatrice casio;

	@BeforeEach
	public void initCalculette() {
		System.out.println("initiation d'une instance de calculette");
		casio = new Calculatrice();
	}

	@AfterEach
	public void undefCalculatte() {
		System.out.println("mise de l'instance de calculette à null");
		casio = null;
	}

	// test de le résolution de block de calcule
	@ParameterizedTest(name = "{0} doit être égal à {1}")
	@CsvSource({ "5*5,25", "5*(5+2*2),45" })
	public void resolveBlocTest(String arg, double res) {
		Double resultat = casio.resolveBloc(arg);

		assertThat(resultat).isEqualTo(res);

	}

	// test de l'adition
	@ParameterizedTest(name = "{0} + {1} doit être égale à {2}")
	@CsvSource({ "1,1,2", "1,0,1", "128,213.5,341.5" })
	public void additionTest(double arg1, double arg2, double res) {
		Double resultat = casio.opperationSimple(arg1, '+', arg2);

		assertThat(resultat).isEqualTo(res);

	}

	// test de la soustraction
	@ParameterizedTest(name = "{0} - {1} doit être égale à {2}")
	@CsvSource({ "1,1,0", "1,0,1", "128,213.5,-85.5" })
	public void substractionTest(double arg1, double arg2, double res) {
		Double resultat = casio.opperationSimple(arg1, '-', arg2);

		assertThat(resultat).isEqualTo(res);

	}

	// test de la multiplication
	@ParameterizedTest(name = "{0} * {1} doit être égale à {2}")
	@CsvSource({ "1,1,1", "1,0,0", "128,213.5,27328" })
	public void multiplicationTest(double arg1, double arg2, double res) {
		Double resultat = casio.opperationSimple(arg1, '*', arg2);

		assertThat(resultat).isEqualTo(res);

	}

	// test de la division
	@ParameterizedTest(name = "{0} / {1} doit être égale à {2}")
	@CsvSource({ "1,1,1", "0,1,0", "128,213.5,0.59953161592505854800936768149883" })
	public void divisionTest(double arg1, double arg2, double res) {
		Double resultat = casio.opperationSimple(arg1, '/', arg2);

		assertThat(resultat).isEqualTo(res);

	}

	@Test
	public void extractBlocBeforeTest() {
		List<String> entre = new ArrayList<String>();
		entre.add("(");
		entre.add("5");
		entre.add("*");
		entre.add("(");
		entre.add("55");
		entre.add("+");
		entre.add("1");
		entre.add(")");
		entre.add(")");
		String resultat = casio.extractBlocBefore(entre);
		assertThat(resultat).isEqualTo("5*(55+1)");
	}

	@Test
	public void extractBlocBeforeTest2() {
		List<String> entre = new ArrayList<String>();
		entre.add("5");
		entre.add("*");
		entre.add("(");
		entre.add("55");
		entre.add("+");
		entre.add("1");
		entre.add(")");
		String resultat = casio.extractBlocBefore(entre);
		assertThat(resultat).isEqualTo("55+1");
	}

	@Test
	public void extractBlocAfterTest() {
		List<String> entre = new ArrayList<String>();
		entre.add("(");
		entre.add("5");
		entre.add("*");
		entre.add("(");
		entre.add("55");
		entre.add("+");
		entre.add("1");
		entre.add(")");
		entre.add(")");
		String resultat = casio.extractBlocAfter(entre);
		assertThat(resultat).isEqualTo("5*(55+1)");
	}

}
