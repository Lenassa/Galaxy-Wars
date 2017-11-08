
public class EquipementAttaqueVaisseau implements Equipement {

	private int id;
	private int DegatsVaisseau;
	
	/**
	 * @param DegatsVaisseau
	 * @brief 
	 */
	public EquipementAttaqueVaisseau(int DegatsVaisseau) {
		this.id = 2;
		this.DegatsVaisseau = DegatsVaisseau;
	}
	
	/* (non-Javadoc)
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
		EquipementAttaqueVaisseau other = (EquipementAttaqueVaisseau) obj;
		return this.id == other.id;
	}
	
	/* (non-Javadoc)
	 * @see Equipement#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}
	
	/**
	 * @return
	 */
	public int getDegatsVaisseau() {
		return this.DegatsVaisseau;
	}
}
