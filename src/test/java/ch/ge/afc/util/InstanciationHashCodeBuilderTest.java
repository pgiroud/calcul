package ch.ge.afc.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.junit.Test;



public class InstanciationHashCodeBuilderTest {

	private static boolean hashCodeDynamique = true;
	private static final String CAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private Random random = new Random();
	
	@Test
	public void testEntier() {
		List<Parent> parents = construireParents();
		Date dateDebut = new Date();
		test(parents);
		long delta = new Date().getTime() - dateDebut.getTime();
		System.out.println("Temps avec instanciation : " + delta);
		hashCodeDynamique = false;
		dateDebut = new Date();
		test(parents);
		delta = new Date().getTime() - dateDebut.getTime();
		System.out.println("Temps sans instanciation : " + delta);
	}
	
	private void test(List<Parent> parents) {
		new HashSet<Parent>(parents);
	}
	
	
	private List<Parent> construireParents() {
		// Agir sur la taille et ne pas oublier d'augmenter la mémoire
		// -Xms128m -Xmx512m
		int taille = 100;
		List<Parent> parents = new ArrayList<Parent>(taille);
		for (int i = 0; i < taille; i++) {
			Parent parent = new Parent(genererNomAleatoire(),genererNomAleatoire());
			for (int j = 0; j < 10; j++) {
				parent.addEnfant(genererAgeAleatoire(), genererNomAleatoire());
			}
			parents.add(parent);
		}
		return parents;
	}
	
	private String genererNomAleatoire() {
		StringBuilder builder = new StringBuilder();
		int longueur = random.nextInt(20);
		for (int i = 0; i < longueur; i++) {
			builder.append(CAR.charAt(random.nextInt(52)));
		}
		return builder.toString();
	}
	
	private int genererAgeAleatoire() {
		return random.nextInt(18);
	}
	
	private class Parent {
		
		private List<Enfant> enfants = new ArrayList<Enfant>();
		private String nomPersonne1;
		private String nomPersonne2;
		
		public Parent(String nom1, String nom2) {
			this.nomPersonne1 = nom1;
			this.nomPersonne2 = nom2;
		}
		
		public void addEnfant(int age, String nom) {
			enfants.add(new Enfant(age,nom));
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Parent)) return false;
			Parent parent = (Parent)obj;
			
			return parent.nomPersonne1.equals(nomPersonne1)
				&& parent.nomPersonne2.equals(nomPersonne2)
				&& parent.enfants.equals(enfants);
		}

		@Override
		public int hashCode() {
			if (hashCodeDynamique) {
				return new HashCodeBuilder().add(nomPersonne1).add(nomPersonne2).add(enfants).hash();
			} else {
				 int result = HashCodeStatic.SEED;
				 result = HashCodeStatic.hash(result, nomPersonne1);
				 result = HashCodeStatic.hash(result, nomPersonne2);
				 result = HashCodeStatic.hash(result, enfants);
				 return result;
			}
		}
		
		
	}
	
	private class Enfant {
		private final int age;
		private final String nom;
		
		public Enfant(int age, String nom) {
			this.age = age;
			this.nom = nom;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Enfant)) return false;
			Enfant enfant = (Enfant)obj;
			return age == enfant.age && nom.equals(enfant.nom);
		}

		@Override
		public int hashCode() {
			if (hashCodeDynamique) {
				return new HashCodeBuilder().add(age).add(nom).hash();
			} else {
				 int result = HashCodeStatic.SEED;
				 result = HashCodeStatic.hash(result, age);
				 result = HashCodeStatic.hash(result, nom);
				 return result;
			}
		}
	}
	
	private static class HashCodeStatic {
		private static final int fODD_PRIME_NUMBER = 37;
		/**
		 * An initial value for a <code>hashCode</code>, to which is added
		 * contributions from fields. Using a non-zero value decreases collisons of
		 * <code>hashCode</code> values.
		 */
		public static final int SEED = 23;

		/**
		 * booleans.
		 */
		public static int hash(final int aSeed, final boolean aBoolean) {
			return FirstTerm(aSeed) + (aBoolean ? 1 : 0);
		}

		/**
		 * chars.
		 */
		public static int hash(final int aSeed, final char aChar) {
			return FirstTerm(aSeed) + (int) aChar;
		}

		/**
		 * ints.
		 */
		public static int hash(final int aSeed, final int aInt) {
			/*
			 * Implementation Note Note that byte and short are handled by this
			 * method, through implicit conversion.
			 */
			return FirstTerm(aSeed) + aInt;
		}

		/**
		 * longs.
		 */
		public static int hash(final int aSeed, final long aLong) {
			return FirstTerm(aSeed) + (int) (aLong ^ (aLong >>> 32));
		}

		/**
		 * floats.
		 */
		public static int hash(final int aSeed, final float aFloat) {
			return hash(aSeed, Float.floatToIntBits(aFloat));
		}

		/**
		 * doubles.
		 */
		public static int hash(final int aSeed, final double aDouble) {
			return hash(aSeed, Double.doubleToLongBits(aDouble));
		}

		/**
		 * <code>aObject</code> is a possibly-null object field, and possibly an
		 * array.
		 * <p/>
		 * If <code>aObject</code> is an array, then each element may be a primitive
		 * or a possibly-null object.
		 */
		public static int hash(final int aSeed, final Object aObject) {
			int result = aSeed;
			if (aObject == null) {
				result = hash(result, 0);
			} else if (!isArray(aObject)) {
				result = hash(result, aObject.hashCode());
			} else {
				int length = Array.getLength(aObject);
				for (int idx = 0; idx < length; ++idx) {
					Object item = Array.get(aObject, idx);
					// recursive call!
					result = hash(result, item);
				}
			}
			return result;
		}

		private static int FirstTerm(final int aSeed) {
			return fODD_PRIME_NUMBER * aSeed;
		}

		private static boolean isArray(final Object aObject) {
			return aObject.getClass().isArray();
		}
	}
}
