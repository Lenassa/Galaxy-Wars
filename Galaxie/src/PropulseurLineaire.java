import java.util.ArrayList;

/**
 * @author Marion DENMAT - Lo�s DE SAUVECANNE
 *
 * @brief Classe g�rant les propulseurs � d�placement lin�aire. H�rite de la classe abstraite Propulseur.
 */
public class PropulseurLineaire extends Propulseur {
	
	/**
	 * @param DistancePropulsion
	 */
	public PropulseurLineaire(int DistancePropulsion) {
		super(DistancePropulsion);
	}

	/**
	 * @see Propulseur#DeplacementsPossibles(int, int)
	 * 
	 * @brief 
	 */
	@Override
	public ArrayList<Integer[]> DeplacementsPossibles(int x, int y) {
		
		ArrayList<Integer[]> CasesEligibles = new ArrayList<Integer[]>(0);
		Entite[][] grille = Galaxy.getGalaxy();
		int X, Y;
		Integer[] coordonnee;
		
		/*
		 * Les cases test�es sont comprises dans le carr� de c�t� DistancePropulsion + 1
		 * centr� sur la position du vaisseau.
		 * Dans le cas du d�placement lin�aire, les cases nous int�ressant sont celles pour qui
		 * Y parcours le carr� et X == x
		 * X parcours le carr� et Y == y.
		 * 
		 * Soit, dans les boucles, i == 0 || j == 0;
		 * Tant que (|i| + |j|) <= DistancePropulsion pour rester � port� du vaisseau
		 */
		
		for (int i = - DistancePropulsion; i < DistancePropulsion + 1; ++i) {
			
			X = Galaxy.putInBoundsX(x + i);
			
			for (int j = - DistancePropulsion; j < DistancePropulsion + 1; ++j) {
			
				Y = Galaxy.putInBoundsY(y + j);
			
				if ((Math.abs(i) + Math.abs(j) <= DistancePropulsion)
						&& (i == 0 || j == 0) && (grille[X][Y] == null)) {
			
					coordonnee = new Integer[2];
					coordonnee[0] = X;
					coordonnee[1] = Y;
					CasesEligibles.add(coordonnee);
				}
			}
		}
		
		return CasesEligibles;
	}
}
