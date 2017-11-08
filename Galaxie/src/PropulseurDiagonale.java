import java.util.ArrayList;

/**
 * @author Marion DENMAT - Loïs DE SAUVECANNE
 *
 * @brief Classe gérant les propulseurs à déplacement diagonal. Hérite de la classe abstraite Propulseur.
 */
public class PropulseurDiagonale extends Propulseur {

	/**
	 * @param DistancePropulsion
	 */
	public PropulseurDiagonale(int DistancePropulsion) {
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
		 * Les cases de la grille vérifiées sont [x - DistancePropulsion, y - DistancePropulsion] à [x + DistancePropulsion, y + DistancePropulsion]
		 * 										 [x - DistancePropulsion, y + DistancePropulsion] à [x + DistancePropulsion, y - DistancePropulsion]
		 * L'emplacement actuel du vaisseau est aussi testé, mais étant occupé par le vaisseau cela n'influe
		 * en rien sur le résultat.
		 */
		
		
		/////////////////////////////////////
		// Cases disponibles sur axes +Y +X//
		/////////////////////////////////////
		
		/*
		 * Les cases testées sont comprises dans le carré de côté DistancePropulsion + 1
		 * centré sur la position du vaisseau.
		 * Dans le cas du déplacement diagonal, les cases nous intéressant sont celles telles que
		 * dans les boucles |i| == |j|
		 * Cela revient à donner les cas {[1,1], [-1,1], [-1,-1], [1,-1]}
		 * et les suivants : [2,2], [2,-2], [-2,-2], [-2,2], [3,3], [3,-3], ...
		 * 
		 * Tant que (|i| + |j|) <= DistancePropulsion pour rester à porté du vaisseau
		 */
		
		for (int i = - DistancePropulsion; i < DistancePropulsion + 1; ++i) {
			
			X = Galaxy.putInBoundsX(x + i);
			
			for (int j = - DistancePropulsion; j < DistancePropulsion + 1; ++j) {
			
				Y = Galaxy.putInBoundsY(y + j);
			
				if ((Math.abs(i) + Math.abs(j) <= DistancePropulsion) 
						&& (Math.abs(i) == Math.abs(j)) && (grille[X][Y] == null)) {
			
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
