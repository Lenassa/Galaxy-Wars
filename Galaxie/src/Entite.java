import java.util.ArrayList;

/**
 * @author Marion DENMAT - Lo�s DE SAUVECANNE
 *
 * @brief Interface d�clarant les m�thodes communes � la classe Planete et la classe Vaisseau. Cette interface permet � la galaxy de contenir des Planetes et des Vaisseaux
 */
public interface Entite {
	
	/**
	 * @return Entier identifiant le proprietaire
	 */
	public int getProprietaire();
	
	/**
	 * @return Liste de couple d'entier
	 * @post Liste initialisee, vide ou non.
	 */
	public ArrayList<Integer[]> getSurroundings();
	
	/**
	 * @param a : entier
	 * @param b : entier
	 * @return le plus grand entier, b si a == b
	 */
	public int min(int a, int b);
}