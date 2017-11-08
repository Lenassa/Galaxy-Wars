import java.util.ArrayList;
import java.util.Random;

/**
 * @author Marion DENMAT - Loïs DE SAUVECANNE
 *
 * @brief Classe gérant les planètes, implémentant l'interface Entite
 */
public class Planete implements Entite {

	private int x, y, taillePlanete, proprietaire, population;
	private Vaisseau construction;


	/**
	 * @param xx : position en abssice dans la grille de la planète
	 * @param yy : position en ordonnée dans la grille de la planète
	 * @param tailletaille : taille de la planète
	 * @brief La population est initialiséé à 0, le propriétaire est mit à la valeur correspondant à l'absence de proprietaire (-1), la construction est initialisée à null
	 */
	public Planete(int xx, int yy, int tailletaille) {
		
		x = xx; // abscisse
		y= yy; // ordonnÃ©e
		taillePlanete = tailletaille; // taille
		population = 0;
		proprietaire = -1;
		construction = null;
	}
	
	
	/*Méthode de suppression*/
	/** (non-Javadoc)
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
		Planete other = (Planete) obj;
		return this.proprietaire == other.proprietaire && this.x == other.x && this.y == other.y
				&& this.taillePlanete == other.taillePlanete && this.population == other.population && this.construction.equals(other.construction);
	}
	
	
	/** (non-Javadoc)
	 * @see Entite#getSurroundings()
	 * @brief Retourne les couples de coordonnees des cases libres dans un carre de 3*3 dont la planete est le centre
	 * @return liste de couples de coordonnees (x,y)
	 * @post Cases initialise
	 */
	public ArrayList<Integer[]> getSurroundings() {
		
		int X, Y;
		Integer[] coordonnee;
		Entite[][] grille = Galaxy.getGalaxy();
		ArrayList<Integer[]> Cases = new ArrayList<Integer[]>();
		
		for (int i = -1; i < 2; i++) {
			
			X = Galaxy.putInBoundsX(this.x + i);
			
			for (int j = -1; j < 2; j++) {
				
				Y = Galaxy.putInBoundsY(this.y + j);
				
				if (grille[X][Y] == null) {
					
					coordonnee = new Integer[2];
					coordonnee[0] = X;
					coordonnee[1] = Y;
					Cases.add(coordonnee);
				}
			}
		}
		
		return Cases;
	}
	
	
	/**
	 * @brief Tente de placer un vaisseau sur la grille de la Galaxie. Instancie construction à null si reussite.
	 * @brief En cas de reussite, insere le vaisseau dans la liste de l'empire correspondant et dans la liste de vaisseau generale.
	 * @return faux si le vaisseau n'a pas pu se placer dans la grille, vrai autrement.
	 */
	public boolean vaisseauOut() {
		ArrayList<Integer[]> Cases;
		Random rand = new Random();
		Cases = this.getSurroundings();
		
		if (Cases.size() > 0) {
		
			int CasesChoisies = rand.nextInt(Cases.size());
			ArrayList<Vaisseau> ListeVaisseau = Galaxy.getListeVaisseau();
			
			Espece espece = Galaxy.getEspece(this.proprietaire);
			ArrayList<Vaisseau> EmpireFlotte = espece.getEmpireFlotte();
			
			this.construction.setX(Cases.get(CasesChoisies)[0]);
			this.construction.setY(Cases.get(CasesChoisies)[1]);
			EmpireFlotte.add(this.construction);
			ListeVaisseau.add(this.construction);
			
			espece.setEmpireFlotte(EmpireFlotte);
			Galaxy.setListeVaisseau(ListeVaisseau);
			Galaxy.setCase(Cases.get(CasesChoisies)[0], Cases.get(CasesChoisies)[1], this.construction);
			this.construction = null;
			return true;
		}
		return false;
	}
	
	
	/**
	 * @brief Si la construction est terminee @see #Planete.vaisseauOut()
	 * @brief Si la construction est instanciee null, l'instancie avec un nouveau vaisseau @see Galaxy.creerVaisseau(int proprietaire, boolean construit)
	 * @brief Si la construction est instanciee avec un Vaisseau, modifie son integrite a min(resistance, integrite+(TauxProductivite*population))
	 * @brief Si integrite == resistance, construit du Vaisseau devient vrai
	 */
	public void constructionVaisseau() {
		int integrite, resistance;
		Espece espece = Galaxy.getEspece(this.proprietaire);
		
		if (this.construction != null)
			if (this.construction.getConstruit()) {
				vaisseauOut();
				
				//DEBUG
				//System.out.println("debug - vaisseau sorti");
			}
		
		if (this.construction == null)
			this.construction = Galaxy.creerVaisseau(proprietaire, false);
		
		if (this.construction != null) {
			
			integrite = this.construction.getIntegrite();
			resistance = this.construction.getResistance();
			integrite = min(resistance, (int) Math.ceil(integrite + (espece.getProductivite() * this.population)));
			this.construction.setIntegrite(integrite);
			
			if (this.construction.getIntegrite() == this.construction.getResistance())
				this.construction.setConstruit(true);
		}
	}

	
	/**
	 * @brief Augmentation de la population de la planete
	 */
	public void reproduction() {
		Espece espece = Galaxy.getEspece(this.proprietaire);
		this.population = min(this.taillePlanete, (int) Math.ceil((1 + espece.getNatalite()) * this.population));
	}
	
	
	/**
	 * @see Entite#min(int, int)
	 */
	public int min(int a, int b) {
		if (a > b) return b;
		else return a;
	}
	
	/**
	 * @return x : abscisse de la planete dans la grille
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return y : ordonnee de la planete dans la grille
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return taille : taille de la planete
	 */
	public int getTaillePlanete() {
		return this.taillePlanete;
	}

	/**
	 * @param proprietaire : entier identifiant le proprietaire
	 * @brief Change le proprietaire de la palanete
	 * @post this.proprietaire = proprietaire
	 */
	public void setProprietaire(int proprietaire) {
		this.proprietaire = proprietaire;
	}

	/**
	 * @return construction de la planete : vaisseau ou null
	 */
	public Vaisseau getConstruction() {
		return this.construction;
	}

	/**
	 * @param construction : Vaisseau definissant la construction
	 * @brief null ou Vaisseau
	 * @post this.construction = Vaisseau ou null
	 */
	public void setConstruction(Vaisseau construction) {
		this.construction = construction;
	}

	/**
	 * @see Entite#getProprietaire()
	 */
	@Override
	public int getProprietaire() {
		return this.proprietaire;
	}
	
	/**
	 * @return population de la planete (entier)
	 */
	public int getPopulation() {
		return this.population;
	}
	
	/**
	 * @param population : entier representant la population de la planete
	 * @post this.population = population
	 */
	public void setPopulation (int population) {
		this.population = population;
	}
}