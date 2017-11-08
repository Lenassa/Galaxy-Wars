import java.util.ArrayList;

/**
 * @author Marion DENMAT - Lo�s DE SAUVECANNE
 *
 * @brief Classe abstraite impl�mentant les m�thodes communes et identiques aux diff�rents propulseur, ainsi que d�clarant les m�thode commune mais diff�rant dans l'ex�cution.
 */
public abstract class Propulseur {
	
	protected int DistancePropulsion;
	
	/**
	 * @param DistancePropulsion : entier
	 */
	public Propulseur (int DistancePropulsion) {
		
		this.DistancePropulsion = DistancePropulsion;
	}
	
	/**
	 * @param x : entier
	 * @param y : entier
	 * @return Liste de couples d'entier
	 */
	public abstract ArrayList<Integer[]> DeplacementsPossibles(int x, int y);
	
	/**
	 * @brief Retourne la distance de propulsion du propulseur
	 * @return entier
	 */
	public int getDistancePropulsion() {
		return DistancePropulsion;
	}
}
