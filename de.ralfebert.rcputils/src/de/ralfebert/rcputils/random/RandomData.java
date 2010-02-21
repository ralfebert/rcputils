/*******************************************************************************
 * Copyright (c) 2008 Ralf Ebert
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Ralf Ebert - initial API and implementation
 *******************************************************************************/
package de.ralfebert.rcputils.random;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.eclipse.core.runtime.Assert;

import de.ralfebert.rcputils.types.DateHelper;

/**
 * RandomData generates random values. This is helpful for generating test data
 * for prototypes and test cases.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public class RandomData {

	private int seed;

	public static final String[] GIVEN_NAMES = new String[] { "Alexander", "Andreas", "Angela", "Angelika", "Barbara",
			"Bernd", "Birgit", "Brigitte", "Bärbel", "Carl", "Christa", "Christiane", "Christine", "Dagmar", "Detlef",
			"Dieter", "Elisabeth", "Elke", "Frank", "Gabriele", "Gerd", "Gerhard", "Gisela", "Gudrun", "Günter",
			"Hans", "Heike", "Heinz", "Helga", "Helmut", "Holger", "Horst", "Ingrid", "Jens", "Joachim", "Jutta",
			"Jörg", "Jürgen", "Karin", "Klaus", "Kornelia", "Manfred", "Maria", "Marion", "Marlies", "Matthias",
			"Michael", "Monika", "Norbert", "Peter", "Petra", "Rainer", "Ralf", "Regina", "Reinhard", "Renate", "Rolf",
			"Rüdiger", "Sabine", "Sigrid", "Silvia", "Susanne", "Thomas", "Ulrich", "Ulrike", "Ursula", "Ute", "Uwe",
			"Werner", "Wolfgang" };

	public static final String[] LAST_NAMES = new String[] { "Müller", "Schmidt", "Schneider", "Fischer", "Weber",
			"Meyer", "Wagner", "Becker", "Schulz", "Hoffmann", "Schäfer", "Koch", "Bauer", "Richter", "Klein", "Wolf",
			"Schröder", "Neumann", "Schwarz", "Zimmermann", "Braun", "Krüger", "Hofmann", "Hartmann", "Lange",
			"Schmitt", "Werner", "Schmitz", "Krause", "Meier", "Lehmann", "Schmid", "Schulze", "Maier", "Köhler",
			"Herrmann", "König", "Walter", "Mayer", "Huber", "Kaiser", "Fuchs", "Peters", "Lang", "Scholz", "Möller",
			"Weiß", "Jung", "Hahn", "Schubert", "Vogel", "Friedrich", "Keller", "Günther", "Frank", "Berger",
			"Winkler", "Roth", "Beck", "Lorenz", "Baumann", "Franke", "Albrecht", "Schuster", "Simon", "Ludwig",
			"Böhm", "Winter", "Kraus", "Martin", "Schumacher", "Krämer", "Vogt", "Stein", "Jäger", "Otto", "Sommer",
			"Groß", "Seidel", "Heinrich", "Brandt", "Haas", "Schreiber", "Graf", "Schulte", "Dietrich", "Ziegler",
			"Kuhn", "Kühn", "Pohl", "Engel", "Horn", "Busch", "Bergmann", "Thomas", "Voigt", "Sauer", "Arnold",
			"Wolff", "Pfeiffer" };

	public static final String[] CITIES = new String[] { "Berlin", "Hamburg", "München", "Köln", "Frankfurt am Main",
			"Stuttgart", "Dortmund", "Essen", "Düsseldorf", "Bremen", "Hannover", "Leipzig", "Dresden", "Nürnberg",
			"Duisburg", "Bochum", "Wuppertal", "Bielefeld", "Bonn", "Mannheim", "Karlsruhe", "Wiesbaden", "Münster",
			"Gelsenkirchen", "Augsburg", "Mönchengladbach", "Aachen", "Braunschweig", "Chemnitz", "Kiel", "Krefeld",
			"Halle (Saale)", "Magdeburg", "Freiburg im Breisgau", "Oberhausen", "Lübeck", "Erfurt", "Rostock", "Mainz",
			"Kassel", "Hagen", "Hamm", "Saarbrücken", "Mülheim an der Ruhr", "Herne", "Ludwigshafen am Rhein",
			"Osnabrück", "Solingen", "Leverkusen", "Oldenburg", "Neuss", "Potsdam", "Heidelberg", "Paderborn",
			"Darmstadt", "Würzburg", "Regensburg", "Ingolstadt", "Heilbronn", "Göttingen", "Ulm", "Recklinghausen",
			"Wolfsburg", "Pforzheim", "Bottrop", "Offenbach am Main", "Bremerhaven", "Fürth", "Remscheid",
			"Reutlingen", "Moers", "Koblenz", "Bergisch Gladbach", "Salzgitter", "Siegen", "Erlangen", "Trier",
			"Hildesheim", "Cottbus", "Jena", "Gera" };

	public static final String[] COUNTRIES = new String[] { "Deutschland", "Österreich", "Schweiz", "Polen",
			"Tschechische Republik", "Frankreich" };

	public static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
	public static final String MIXED_CHARS = UPPERCASE_CHARS + LOWERCASE_CHARS;

	public RandomData(int seed) {
		super();
		this.seed = seed;
	}

	public RandomData() {
		super();
		this.seed = (int) System.currentTimeMillis();
		newData();
	}

	public String somePersonName() {
		return someGivenName() + " " + someLastName();
	}

	public String someLastName() {
		return someElement(LAST_NAMES);
	}

	public String someGivenName() {
		return someElement(GIVEN_NAMES);
	}

	@SuppressWarnings("unchecked")
	public <E> E someElement(Collection<E> elements) {
		return (E) someElement(elements.toArray());
	}

	public <E> E someElement(E[] elements) {
		return elements[someNumber(0, elements.length)];
	}

	public int someNumber(int fromInclusive, int toExclusive) {
		return fromInclusive + (seed % (toExclusive - fromInclusive));
	}

	public long someNumber(long fromInclusive, long toExclusive) {
		return fromInclusive + (seed % (toExclusive - fromInclusive));
	}

	public double someNumber(double fromInclusive, double toExclusive) {
		return fromInclusive + (seed % (toExclusive - fromInclusive));
	}

	public String someStreet() {
		return someElement(LAST_NAMES) + "straße " + someNumber(1, 100);
	}

	public String someZipCode() {
		return someDigits(5);
	}

	public String someCity() {
		return someElement(CITIES);
	}

	public String someDigits(int count) {
		StringBuilder string = new StringBuilder();
		Random random = new Random(seed);
		for (int i = 1; i <= count; i++) {
			string.append(String.valueOf(random.nextInt(10)));
		}
		return string.toString();
	}

	public String someCharacters(int count, String characters) {
		StringBuilder string = new StringBuilder();
		Random random = new Random(seed);
		for (int i = 1; i <= count; i++) {
			string.append(characters.charAt(random.nextInt(characters.length())));
		}
		return string.toString();
	}

	public String someEmail() {
		return someGivenName().toLowerCase() + "." + someLastName().toLowerCase() + "@example.com";
	}

	public String somePhoneNumber() {
		return somePhoneNumber("/");
	}

	public String somePhoneNumber(String separator) {
		return "0" + someDigits(3) + separator + someDigits(7);
	}

	public <T extends Enum<T>> T someValue(Class<T> enumType) {
		return someElement(enumType.getEnumConstants());
	}

	public void newData() {
		seed = new Random(seed).nextInt(Integer.MAX_VALUE - 1);
		Assert.isTrue(seed >= 0, "seed has to be positive: " + seed);
	}

	public Date someDate(int fromYear, int tillYear) {
		return DateHelper.getDateByDayMonthYear(someNumber(1, 29), someNumber(1, 12), someNumber(fromYear, tillYear));
	}

	public boolean someBoolean() {
		return someNumber(0, 2) == 0;
	}

}