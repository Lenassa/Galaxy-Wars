import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Marion DENMAT - Loïs DE SAUVECANNE
 *
 * @brief Classe gérant les espèces et les méthodes associées
 */
public class Espece {

	private int id;
	private Color color;
	private float natalite;
	private float productivite;
	private ArrayList<Planete> EmpirePlanete;
	private ArrayList<Vaisseau> EmpireFlotte;
	
	/**
	 * @param id : entier identifiant l'espece. Unique.
	 * @param color : couleur representant l'espece. Unique.
	 * @param natalite : float representant le taux de natalite de l'espece
	 * @param productivite : float representant le taux de productivite de l'espece
	 * @brief EmpirePlanete est instanciee en liste de planete de taille 0
	 * @brief EmpireFlotte est instanciee en liste de vaisseau de taille 0
	 */
	public Espece(int id, Color color, float natalite, float productivite) {
		
		this.id = id;
		this.color = color;
		this.natalite = natalite;
		this.productivite = productivite;
		this.EmpirePlanete = new ArrayList<Planete>(0);
		this.EmpireFlotte = new ArrayList<Vaisseau>(0);
	}

	/**
	 * @brief Retourne l'empire de l'espece
	 * @return liste de planete
	 */
	public ArrayList<Planete> getEmpirePlanete() {
		return this.EmpirePlanete;
	}

	/**
	 * @param empirePlanete : liste de Planete
	 */
	public void setEmpirePlanete(ArrayList<Planete> empirePlanete) {
		this.EmpirePlanete = empirePlanete;
	}

	/**
	 * @brief Retourne la flotte de l'espece
	 * @return liste de vaisseau
	 */
	public ArrayList<Vaisseau> getEmpireFlotte() {
		return this.EmpireFlotte;
	}

	/**
	 * @param empireFlotte : liste de Vaisseau
	 */
	public void setEmpireFlotte(ArrayList<Vaisseau> empireFlotte) {
		this.EmpireFlotte = empireFlotte;
	}

	/**
	 * @brief Retourne l'identifiant de l'espece
	 * @return entier
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param id : entier
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @brief Retourne la couleur propre a l'espece
	 * @return Color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @brief Retourne le taux de natalite de l'espece
	 * @return float
	 */
	public float getNatalite() {
		return this.natalite;
	}

	/**
	 * @brief Retourne le taux de productivite de l'espece
	 * @return float
	 */
	public float getProductivite() {
		return this.productivite;
	}
}