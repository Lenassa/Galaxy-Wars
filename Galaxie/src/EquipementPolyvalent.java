/**
 * @author Marion DENMAT - Loïs DE SAUVECANNE
 *
 * @brief Classe gérant l'équipement polyvalent des vaisseaux.
 */
public class EquipementPolyvalent implements Equipement {
	
	private int DegatsVaisseau, DegatsPlanete;
	
	/**
	 * @param DegatsVaisseau : entier representant les degats fait aux vaisseaux
	 * @param DegatsPlanete : entier representant les degats fait aux vaisseaux
	 */
	public EquipementPolyvalent(int DegatsVaisseau, int DegatsPlanete) {
		
		this.DegatsVaisseau = DegatsVaisseau;
		this.DegatsPlanete = DegatsPlanete;
	}

	/**
	 * @return Degats effectues aux vaisseaux 
	 */
	public int getDegatsVaisseau() {
		return DegatsVaisseau;
	}

	/**
	 * @return Degats effectues aux planetes
	 */
	public int getDegatsPlanete() {
		return DegatsPlanete;
	}

	/**
	 * @see Equipement#getId()
	 */
	@Override
	public int getId() {
		return 0;
	}
}
