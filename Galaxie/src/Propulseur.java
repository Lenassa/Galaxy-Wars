import java.util.ArrayList;

/**
 * @author Marion DENMAT - Loïs DE SAUVECANNE
 *
 * @brief Classe abstraite implémentant les méthodes communes et identiques aux différents propulseur, ainsi que déclarant les méthode commune mais différant dans l'exécution.
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
