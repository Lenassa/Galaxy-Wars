
public class EquipementColonisation implements Equipement {

	private int id;
	
	/**
	 * @brief 
	 */
	public EquipementColonisation() {
		this.id = 1;
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
		EquipementColonisation other = (EquipementColonisation) obj;
		return this.id == other.id;
	}
	
	/* (non-Javadoc)
	 * @see Equipement#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}
}
