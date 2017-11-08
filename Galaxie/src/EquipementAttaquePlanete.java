
public class EquipementAttaquePlanete implements Equipement {

	private int id;
	private int DegatsPlanete;
	
	/**
	 * @param DegatsPlanete : entier
	 * @brief id est intialise a 3 pour cet equipement
	 */
	public EquipementAttaquePlanete(int DegatsPlanete) {
		this.id = 3;
		this.DegatsPlanete = DegatsPlanete;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EquipementAttaquePlanete other = (EquipementAttaquePlanete) obj;
		return this.id == other.id;
	}
	
	/**
	 * @see Equipement#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}
	
	/**
	 * @brief Renouvelle la valeur de DegatsPlanete
	 * @return entier
	 */
	public int getDegatsPlanete() {
		return this.DegatsPlanete;
	}
}
