import java.util.ArrayList;

/**
 * @author Marion DENMAT - Lo�s DE SAUVECANNE
 *
 * @brief Classe g�rant les propulseurs � d�placement omnidirectionnel. H�rite de la classe abstraite Propulseur.
 */
public class PropulseurOmnidirectionnel extends Propulseur {

	/**
	 * @param DistancePropulsion
	 */
	public PropulseurOmnidirectionnel(int DistancePropulsion) {
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
		
		
		//////////////////////////////////
		// D�placement Omnidirectionnel //
		//////////////////////////////////
		
		/*
		 * Les cases test�es sont comprises dans le carr� de c�t� DistancePropulsion + 1
		 * centr� sur la position du vaisseau.
		 * Dans le cas du d�placement diagonal, les cases nous int�ressant sont celles telles que
		 * (|i| + |j|) <= DistancePropulsion afin de rester � port� du vaisseau
		 */
		
		for (int i = - this.DistancePropulsion; i < this.DistancePropulsion + 1; ++i) {
			
			X = Galaxy.putInBoundsX(x + i);
			
			for (int j = - this.DistancePropulsion; j < this.DistancePropulsion + 1; ++j) {
			
				Y = Galaxy.putInBoundsY(y + j); 
			
				if ((Math.abs(i) + Math.abs(j) <= this.DistancePropulsion) && (grille[X][Y] == null)) {
			
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
