import java.util.ArrayList;
import java.util.Random;

/**
 * @author Marion DENMAT - Loïs DE SAUVECANNE
 *
 * @brief Classe gérant les vaisseaux, implémentant l'interface Entite.
 */
public class Vaisseau implements Entite {

	private int resistance, integrite, carburant, proprietaire, x, y, carburantMax;
	private Propulseur propulseur;
	private ArrayList<Equipement> equipement;
	private ArrayList<Integer> idEquipement;
	private boolean est_construit;
	
	/*Constructeur*/
	/**
	 * @param resistance
	 * @param prop
	 * @param equip
	 * @param carbu
	 * @param construit
	 * @param proprio
	 * @param X
	 * @param Y
	 */
	public Vaisseau(int resistance, Propulseur prop, ArrayList<Equipement> equip, ArrayList<Integer> idEquip, int carbu, boolean construit, int proprio, int X, int Y) {
		
		this.resistance = resistance;
			if (construit) {
				//Vaisseau à l'initialisation
				integrite = resistance;
			} else {
				//Vaisseau créer au cours de la simulation
				integrite = 0;
			}
		propulseur = prop;
		carburantMax = carbu;
		carburant = carburantMax;
		equipement = equip;
		est_construit = construit;
		proprietaire = proprio;
		x = X;
		y = Y;
		idEquipement = idEquip;
	}
	
	
	/*Méthode de suppression*/
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
		Vaisseau other = (Vaisseau) obj;
		return this.resistance == other.resistance && this.integrite == other.integrite && this.propulseur.equals(other.propulseur)
				&& this.carburant == other.carburant && this.equipement.equals(other.equipement) && this.idEquipement.equals(other.idEquipement) && this.est_construit == other.est_construit
				&& this.proprietaire == other.proprietaire && this.x == other.x && this.y == other.y;
	}
	
	
	/* (non-Javadoc)
	 * @see Entite#getSurroundings()
	 */
	public ArrayList<Integer[]> getSurroundings() {
		
		int X, Y;
		Entite[][] grille = Galaxy.getGalaxy();
		ArrayList<Integer[]> Cases = new ArrayList<Integer[]>();
		Integer[] coordonnee;
		
		for (int i = -1; i < 2; i++) {
			
			X = Galaxy.putInBoundsX(this.x + i);
			
			for (int j = -1; j < 2; j++) {
				
				Y = Galaxy.putInBoundsY(this.y + j);
				
				/*
				 * !((grille[X][Y].getProprietaire() == this.proprietaire) && 
								(grille[X][Y].getClass() == Vaisseau.class)
								Ignore toute Entite de classe Vaisseau et de même propriétaire que le vaisseau interragissant
				 */
				if ((grille[X][Y] != null) && 
						!((grille[X][Y].getProprietaire() == this.proprietaire) && 
								(grille[X][Y].getClass() == Vaisseau.class))) {
					//Si l'Entite est un Vaisseau ennemi
					if (grille[X][Y].getClass() == Vaisseau.class && grille[X][Y].getProprietaire() != this.proprietaire) {
						if (idEquipement.contains(2)) {
							coordonnee = new Integer[2];
							coordonnee[0] = X;
							coordonnee[1] = Y;
							Cases.add(coordonnee);
						}
					}
					//Si l'Entite est une planète ennemi
					if (grille[X][Y].getClass() == Planete.class && grille[X][Y].getProprietaire() != this.proprietaire) {
						if (idEquipement.contains(3)) {
							coordonnee = new Integer[2];
							coordonnee[0] = X;
							coordonnee[1] = Y;
							Cases.add(coordonnee);
						}
					}
					//Si l'Entite est une planete amie
					if (grille[X][Y].getClass() == Planete.class && grille[X][Y].getProprietaire() == this.proprietaire) {
						if (idEquipement.contains(0)) {
							coordonnee = new Integer[2];
							coordonnee[0] = X;
							coordonnee[1] = Y;
							Cases.add(coordonnee);
						}
					}
					//Si l'Entite est une planete vide
					if (grille[X][Y].getClass() == Planete.class && grille[X][Y].getProprietaire() == -1) {
						if (idEquipement.contains(1)) {
							coordonnee = new Integer[2];
							coordonnee[0] = X;
							coordonnee[1] = Y;
							Cases.add(coordonnee);
						}
					}
				}
			}
		}
		return Cases;
	}
	
	//Rechargement en carburant
	/**
	 * @brief 
	 */
	public void RechargeCarburant() {
		
		this.carburant = min(this.carburantMax, this.carburant + Constantes.PlaneteRechargeCarbu);
		
		//DEBUG
		//System.out.println("("+this.x+","+this.y+") Rechargement en Carburant");
	}
	
	//Attaque d'un vaisseau ennemi
	/**
	 * @param vaisseau
	 */
	public void AttaqueVaisseau(Vaisseau vaisseau) {
		
		vaisseau.setIntegrite(vaisseau.getIntegrite() - ((EquipementAttaqueVaisseau) this.equipement.get(this.idEquipement.indexOf(2))).getDegatsVaisseau());
		if (vaisseau.getIntegrite() < 1) {
			
			Espece espece = Galaxy.getEspece(vaisseau.getProprietaire());
			ArrayList<Vaisseau> EmpireFlotte = espece.getEmpireFlotte();
			EmpireFlotte.remove(vaisseau);
			espece.setEmpireFlotte(EmpireFlotte);
			
			ArrayList<Vaisseau> ListeVaisseau = Galaxy.getListeVaisseau();
			Galaxy.setCase(vaisseau.x, vaisseau.y, null);
			ListeVaisseau.remove(vaisseau);
			Galaxy.setListeVaisseau(ListeVaisseau);
			
			//DEBUG
			//System.out.println("("+vaisseau.getY()+","+vaisseau.getX()+") Destruction du vaisseau");
		}
	}
	
	//Attaque d'une planète ennemie
	/**
	 * @param planete
	 */
	public void AttaquePlanete(Planete planete) {
		
		planete.setPopulation(planete.getPopulation() - ((EquipementAttaquePlanete) this.equipement.get(this.idEquipement.indexOf(3))).getDegatsPlanete());
		if (planete.getPopulation() < 1) {
			
			Espece espece = Galaxy.getEspece(planete.getProprietaire());
			ArrayList<Planete> EmpirePlanete = espece.getEmpirePlanete();
			EmpirePlanete.remove(planete);
			espece.setEmpirePlanete(EmpirePlanete);
			
			planete.setProprietaire(-1);
			Galaxy.setCase(planete.getX(), planete.getY(), planete);
			
			//DEBUG
			//System.out.println("("+planete.getX()+","+planete.getY()+") Planète attaquée");
		}
	}
	
	/* (non-Javadoc)
	 * @see Entite#min(int, int)
	 */
	public int min(int a, int b) {
		if (a > b) return b;
		else return a;
	}
	
	//Colonisation d'une planète vide
	/**
	 * @param planete
	 */
	public void Colonisation(Planete planete) {
		
		planete.setPopulation(min(this.integrite,planete.getTaillePlanete()));
		planete.setProprietaire(this.proprietaire);
		planete.setConstruction(null);
		
		ArrayList<Vaisseau> ListeVaisseau = Galaxy.getListeVaisseau();
		Espece espece = Galaxy.getEspece(this.proprietaire);
		ArrayList<Vaisseau> EmpireFlotte = espece.getEmpireFlotte();
		ArrayList<Planete> EmpirePlanete = espece.getEmpirePlanete();
		
		Galaxy.setCase(this.x,this.y,null); //Supprime le VAISSEAU de la grille. Il s'agit du couple (x,y) du VAISSEAU
		ListeVaisseau.remove(this);
		
		EmpireFlotte.remove(this);
		EmpirePlanete.add(planete);
		
		Galaxy.setListeVaisseau(ListeVaisseau);
		espece.setEmpireFlotte(EmpireFlotte);
		
		//DEBUG
		//System.out.println("("+planete.getX()+","+planete.getY()+") Planète colonisée");
	}
	
	/*Interaction*/
	/**
	 * @brief 
	 */
	public void Interact() {
		
		ArrayList<Integer[]> Cases = this.getSurroundings();
		if (Cases.size() > 0) {
			
			Random rand = new Random();
			int CaseAlea = rand.nextInt(Cases.size());
			Entite entite = Galaxy.getCase(Cases.get(CaseAlea)[0], Cases.get(CaseAlea)[1]);
			
			if (entite.getClass().equals(Vaisseau.class)) {
				if (entite.getProprietaire() != this.getProprietaire()) {
					this.AttaqueVaisseau((Vaisseau)entite);
				}
			} else {
				if (entite.getClass() == Planete.class) {
					if (entite.getProprietaire() == proprietaire) {
						this.RechargeCarburant();
					} else {
						if (entite.getProprietaire() == -1) {
							this.Colonisation((Planete)entite);
						} else {
							this.AttaquePlanete((Planete)entite);
						}
					}
				} else {
					System.out.println("Erreur : aucune classe correspondant à Entite, Vaisseau ou Planete");
					System.out.println("debug - Classe = " + entite.getClass());
				}
			}
		}
	}
	
	/*Déplacement du vaisseau*/
	/**
	 * @return
	 */
	public int[] move() {
		
		ArrayList<Integer[]> CasesEligibles = new ArrayList<Integer[]>();
		Random rand = new Random();
		int CasesChoisies;
		int[] position = new int[2];
		
		if (this.carburant == 0) {
			
			position[0] = -1;
			position[1] = -1;
		} else {
			
			CasesEligibles = this.propulseur.DeplacementsPossibles(this.x, this.y);
			this.carburant--;
			
			if (CasesEligibles.size() > 0) {
				
				/*
				 * ArrayList<Integer[]> NewCasesEligibles = new ArrayList<Integer[]>();
				 * ArrayList<Integer[]> CasesWithInteraction = this.getSurroundings();
				 * for (Integer[] Case : CasesEligibles) {
				 * 	CasesWithInteraction = this.getSurroundings();
				 * 	for (Integer[] CaseInter : CasesWithInteraction) {
				 * 		
				 * 	}
				 * }
				 * */
				
				CasesChoisies = rand.nextInt(CasesEligibles.size());
		
				position[0] = CasesEligibles.get(CasesChoisies)[0];
				position[1] = CasesEligibles.get(CasesChoisies)[1];
			} else {
				
				position[0] = this.x;
				position[1] = this.y;
			}
		}
		return position;
	}
	
	/**
	 * @return
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * @return
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @return
	 */
	public int getIntegrite() {
		
		return this.integrite;
	}
	
	/**
	 * @param integrite
	 */
	public void setIntegrite(int integrite) {
		
		this.integrite = integrite;
	}
	
	/**
	 * @return
	 */
	public int getResistance() {
		
		return this.resistance;
	}
	
	/**
	 * @return
	 */
	public Propulseur getPropulseur() {
		
		return this.propulseur;
	}
	
	/**
	 * @return
	 */
	public ArrayList<Equipement> getEquipement() {
		return this.equipement;
	}
	
	/**
	 * @return
	 */
	public int getCarburantMax() {
		return this.carburantMax;
	}
	
	/**
	 * @return
	 */
	public int getCarburant() {
		
		return this.carburant;
	}
	
	/**
	 * @param carburant
	 */
	public void setCarburant(int carburant) {
		
		this.carburant = carburant;
	}
	
	/**
	 * @return
	 */
	public boolean getConstruit() {
		
		return this.est_construit;
	}
	
	/**
	 * @param est_construit
	 */
	public void setConstruit(boolean est_construit) {
		
		this.est_construit = est_construit;
	}
	
	public ArrayList<Integer> getIdEquipement() {
		return this.idEquipement;
	}

	/* (non-Javadoc)
	 * @see Entite#getProprietaire()
	 */
	@Override
	public int getProprietaire() {
		return this.proprietaire;
	}
}