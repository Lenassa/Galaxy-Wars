import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Marion DENMAT - Loïs DE SAUVECANNE
 *
 * @brief Classe gérant la simulation de conquète galactique au tour par tour.
 */
public class Galaxy {

	private static Entite[][] grille;
	private static ArrayList<Planete> ListePlanete;
	private static ArrayList<Espece> ListeEspece;
	private static ArrayList<Vaisseau> ListeVaisseau;
	private static int tour;
	
	/**
	 * @return
	 */
	public static Entite[][] getGalaxy() {
		return grille;
	}
	
	/**
	 * @return
	 */
	public static ArrayList<Vaisseau> getListeVaisseau() {
		return ListeVaisseau;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param valeur
	 */
	public static void setCase(int x, int y, Entite valeur) {
		grille[x][y] = valeur;
	}
	
	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public static Entite getCase(int x, int y) {
		return grille[x][y];
	}
	
	/**
	 * @param liste
	 */
	public static void setListeVaisseau(ArrayList<Vaisseau> liste) {
		ListeVaisseau = liste;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public static Espece getEspece(int id) {
		return ListeEspece.get(id);
	}
	
	public static int getTour() {
		return tour;
	}
	
	/**
	 * @param x
	 * @return
	 */
	public static int putInBoundsX(int x) {
		if (x < 0) {
			x = Math.abs(x);
			x = x % Constantes.Largeur;
			x = Constantes.Largeur - x;
		} else {
			x = x % Constantes.Largeur;
		}
		return x;
	}
	
	/**
	 * @param y
	 * @return
	 */
	public static int putInBoundsY(int y) {
		if (y < 0) {
			y = Math.abs(y);
			y = y % Constantes.Hauteur;
			y = Constantes.Hauteur - y;
		} else {
			y = y % Constantes.Hauteur;
		}
		return y;
	}
	
	/**
	 * 
	 * @return Vrai ssi la partie est terminée
	 */
	public static Boolean victoire() {
		
		ArrayList<Planete> empire;
		ArrayList<Vaisseau> flotte;
		int compteur = 0;
		
		for (Espece espece:ListeEspece) {
			empire = espece.getEmpirePlanete();
			flotte = espece.getEmpireFlotte();
			
			//DEBUG
			//System.out.println("debug victoire: "+empire.isEmpty()+"-"+flotte.isEmpty());
			
			if (!empire.isEmpty() || !flotte.isEmpty()) {
				compteur++;
				if (compteur > 1) return false;
			}
		}
		return true;
	}
	
	
	/**
	 * @param proprietaire
	 * @param construit
	 * @return
	 */
	public static Vaisseau creerVaisseau(int proprietaire, boolean construit) {
		
		Random rand = new Random();
		Propulseur propulseur;
		ArrayList<Equipement> equipement;
		ArrayList<Integer> idEquipement;
		int DistancePropulsion, nbAlea, AleaDegatVaisseau, AleaDegatPlanete, AleaResistance, carburant, Alea, nbEquipement;
		
		//génération puissance de déplacement du propulseur entre 1 et VaisseauDeplacementMax
		DistancePropulsion = rand.nextInt(Constantes.VaisseauDeplacementMax) + 1;
		//génération du type de propulseur
		nbAlea = rand.nextInt(3); 
		if (nbAlea == 1) {

			//Elimine le cas d'un vaisseau diagonale à 1. Toute case étant au moins à 2 pour lui, il serait immobilisé			 
			if (DistancePropulsion == 1) DistancePropulsion += 1;
			propulseur = new PropulseurDiagonale( DistancePropulsion );
		} else {
			if(nbAlea == 2) {
				propulseur = new PropulseurOmnidirectionnel( DistancePropulsion );
			} else {
				propulseur = new PropulseurLineaire( DistancePropulsion );
			}
		}
		
		
		
		//génération du carburant
		carburant = rand.nextInt( Constantes.VaisseauCarbuMax - Constantes.VaisseauCarbuMin + 1) + Constantes.VaisseauCarbuMin;
		
		//Génération de l'équipement
		AleaDegatVaisseau = rand.nextInt(Constantes.VaisseauDegatVaisseauMax) + Constantes.VaisseauDegatVaisseauMin;
		AleaDegatPlanete = rand.nextInt(Constantes.VaisseauDegatPlaneteMax) + Constantes.VaisseauDegatPlaneteMin;
		
		equipement = new ArrayList<Equipement>(0);
		idEquipement = new ArrayList<Integer>(0);
		//Tout vaisseau doit pouvoir se recharger en carburant
		equipement.add(new EquipementCarburant());
		//Ajoute l'ID correspondant à l'équipement
		idEquipement.add(0);
		
		//Assure au moins un equipement en plus du rechargement de carburant, voir jusqu'a un total de 4 equipements
		nbEquipement = rand.nextInt(3)+2;
		while (equipement.size() < nbEquipement) {
			//Ajoute ou non un equipement pour la colonisation au vaisseau si celui-ci ne le possede pas deja
			Alea = rand.nextInt(2);
			if (Alea == 1 && !idEquipement.contains(1)) {
				equipement.add(new EquipementColonisation());
				idEquipement.add(1);
			}
			//Ajoute ou non un equipement pour attaquer des vaisseaux au vaisseau si celui-ci ne le possede pas deja
			Alea = rand.nextInt(2);
			if (Alea == 1 && !idEquipement.contains(2)) {
				equipement.add(new EquipementAttaqueVaisseau(AleaDegatVaisseau));
				idEquipement.add(2);
			}
			//Ajoute ou non un equipement pour attaquer des planetes au vaisseau si celui-ci ne le possede pas deja
			Alea = rand.nextInt(2);
			if (Alea == 1 && !idEquipement.contains(3)) {
				equipement.add(new EquipementAttaquePlanete(AleaDegatPlanete));
				idEquipement.add(3);
			}
		}
		
		

		
		//Creation d'un nouveau vaisseau et ajout à la liste de ceux existant et ajout dans la grille
		AleaResistance = rand.nextInt(Constantes.VaisseauResistanceMax - Constantes.VaisseauResistanceMin + 1) + Constantes.VaisseauResistanceMin;
		
		Vaisseau vaisseau = new Vaisseau(AleaResistance, propulseur, equipement, idEquipement, carburant, construit, proprietaire, 0, 0);
		return vaisseau;
	}
	
	/**
	 * @param ListeEspeceClone
	 */
	public static void sortieConsole(ArrayList<Espece> ListeEspeceClone) {
		
		int populationTotale, nbPlanete;
		ArrayList<Planete> EspeceEmpire;
		ArrayList<Vaisseau> EspeceFlotte;
		
		System.out.println("Tour : "+tour);
		System.out.println("**********************************************\n");
		
		for (Espece e:ListeEspeceClone) {
			
			System.out.println("Espece : "+e.getId());
			
			populationTotale = 0;
			EspeceFlotte = e.getEmpireFlotte();
			EspeceEmpire = e.getEmpirePlanete();
			
			nbPlanete = EspeceEmpire.size();
			for (Planete p:EspeceEmpire) {
				populationTotale += p.getPopulation();
			}
			System.out.println("Possède "+nbPlanete+" planètes,\npour une population totale de "+populationTotale+" individus.");
			System.out.println("Effectif de la flotte : "+EspeceFlotte.size());
			System.out.println("**********************************************");
		}
		System.out.println("/////////////////////////////////////////////////");
	}
	
	
	/**
	 * @param col
	 * @param color
	 * @return
	 */
	public static boolean couleurUtilisee(ArrayList<Color> col, Color color) {
		
		//Evite les couleurs trop claires ou trop sombres
		if (color.getGreen() < 49 /*|| color.getGreen() > 220*/) return true;
		if (color.getBlue() < 49 /*|| color.getBlue() > 220*/) return true;
		if (color.getRed() < 49 /*|| color.getRed() > 220*/) return true;
		
		if (col.size() == 0) {
			return false;
		} else {
			
			//Evite les couleurs trop proches
			int ecart;
			for (Color c:col) {
				ecart = Math.abs(c.getGreen()-color.getGreen())
						+ Math.abs(c.getRed()-color.getRed())
						+ Math.abs(c.getBlue()-color.getBlue());
				if (ecart < 130) return true;
			}
			return false;
		}
	}
	
	/**
	 * @param a
	 * @param b
	 * @return
	 */
	public static int max(int a, int b) {
		if (a > b) return a;
		else return b;
	}
	
	public static int min(int a, int b) {
		if (a < b) return b;
		else return a;
	}
	

	/**
	 * @brief 
	 */
	public static void start() {
		
		Random rand = new Random();
		int nbPlanete, nbEspece, xx, yy, tailletaille, R, V, B, nbAlea;
		float natalite, productivite;
		ArrayList<Planete> EmpirePlanete;
		ArrayList<Vaisseau> EmpireFlotte;
		Color color;
		ArrayList<Color> ListeCouleur;
		
		nbPlanete = max(rand.nextInt(Constantes.PlaneteMax - Constantes.PlaneteMin) + Constantes.PlaneteMin, 2);
			
		do {
			nbEspece = min(max(rand.nextInt(Constantes.EspeceMax - Constantes.EspeceMin) + Constantes.EspeceMin, 2),Constantes.Hauteur - 1);
		} while(nbEspece > nbPlanete);
		
		System.out.println("debug - nbPlanete = " + nbPlanete);
		System.out.println("debug - nbEspece = " + nbEspece);
		
		grille = new Entite[Constantes.Largeur][Constantes.Hauteur];
		
		for (int i = 0; i < Constantes.Largeur;i++) {
			for (int j = 0; j < Constantes.Hauteur;j++) {
				grille[i][j] = null;
			}
		}
		
		
		////////////////////////////
		// Génération des espèces //
		////////////////////////////

		ListeEspece = new ArrayList<Espece>(0);
		ListeCouleur = new ArrayList<Color>(0);

		for (int i = 0; i < nbEspece; ++i) {

			do {
				R = Math.abs(rand.nextInt())%256;
				V = Math.abs(rand.nextInt())%256;
				B = Math.abs(rand.nextInt())%256;
				color = new Color(R,V,B);
			} while(couleurUtilisee(ListeCouleur, color));
			
			do {
				natalite = rand.nextFloat();
			} while(natalite > 0.3 || natalite < 0.1);
			
			do {
				productivite = rand.nextFloat();
			} while(productivite > 0.3 || productivite < 0.05);
				
			ListeEspece.add(new Espece(i, color, natalite, productivite));
		}
		
		/////////////////////////////
		// Génération des planètes //
		/////////////////////////////
		
		ListePlanete = new ArrayList<Planete>(nbPlanete);
		Planete planete = null;
		boolean planetePlacee;
		
		for (int i = 0; i < nbPlanete; ++i) {
			
			do {
				planetePlacee = false;
				xx = Math.abs(rand.nextInt())%Constantes.Largeur; // abscisse
				yy = Math.abs(rand.nextInt())%Constantes.Hauteur; // ordonnée
				tailletaille = Math.abs(rand.nextInt())%(Constantes.PlaneteTailleMax-Constantes.PlaneteTailleMin+1) + Constantes.PlaneteTailleMin; // taille
			
				if (grille[xx][yy] == null) {
				
					planete = new Planete(xx, yy, tailletaille);
					grille[xx][yy] = planete;
					ListePlanete.add(planete);
					planetePlacee = true;
				}
			} while(!planetePlacee);
				
			//Création des planètes-mères avec une population égale à taillePlanete/2
			if (i < nbEspece && planetePlacee) {
				
				EmpirePlanete = new ArrayList<Planete>(i);
				ListePlanete.get(i).setProprietaire(i);
				ListePlanete.get(i).setPopulation( (int) Math.floor( ListePlanete.get(i).getTaillePlanete()/2 ) );
				EmpirePlanete.add(planete);
				ListeEspece.get(i).setEmpirePlanete(EmpirePlanete);
			}
		}
		

		
		//////////////////////////////
		// Génération des vaisseaux //
		//////////////////////////////
		
		ListeVaisseau = new ArrayList<Vaisseau>(0);
		ArrayList<Integer[]> EmplacementsPossibles;
		Vaisseau vaisseau;
		
		for (int i = 0; i < nbEspece; ++i) {
			
			EmpireFlotte = new ArrayList<Vaisseau>(0);
			
			for (int j = 0; j < Constantes.nbVaisseauInitial; j++) {
				
				//Emplacement autour de la planète sans Entite (donc libre)
				EmplacementsPossibles = ListePlanete.get(i).getSurroundings();
				
				vaisseau = creerVaisseau(i,true);
				
				/*
				 * EmplacementsPossibles est composé d'une suite de couple x,y
				 * Donc il y a EmplacementsPossibles.size() / 2 couples
				 * chaque couple commence tous les 2 indices, d'où le *2 après
				 * Ainsi, 0 accède [0,1], 1 accède [2,3], 2 accède [4, 5]...
				 */
				nbAlea = rand.nextInt(EmplacementsPossibles.size());
				
				vaisseau.setX(EmplacementsPossibles.get(nbAlea)[0]);
				vaisseau.setY(EmplacementsPossibles.get(nbAlea)[1]);
				
				//ajout dans la grille au coordonnée du couple aléatoire choisi, soit nbAlea et nbAlea + 1
				grille[EmplacementsPossibles.get(nbAlea)[0]][EmplacementsPossibles.get(nbAlea)[1]] = vaisseau;
				
				ListeVaisseau.add(vaisseau);
				EmpireFlotte.add(vaisseau);
			}
			ListeEspece.get(i).setEmpireFlotte(EmpireFlotte);
		}
		tour = 0;
	}

	
	/**
	 * @param args
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) {
		
		//Test de sécurité afin d'assurer une taille minimale à la grille
		if (Constantes.Largeur > 2 && Constantes.Hauteur > 2) {
		
			ArrayList<Vaisseau> ListeVaisseauClone;
			ArrayList<Vaisseau> EspeceFlotte;
			int [] Deplacement;
		
			// crÃ©ation du panneau d'affichage
			Affichage panneau = new Affichage();

			// crÃ©ation de la fenÃªtre principale contenant le panneau
			Fenetre fenetre = new Fenetre(panneau);

			// boucle de simulation
		
			start();
		
			panneau.rafraichir(ListePlanete, ListeVaisseau, ListeEspece);
		
			try {
				Thread.sleep(Constantes.TourMs);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		
			while (!victoire() && tour < Constantes.TourMax) {
				// dÃ©compte des tours
				tour += 1;
			
				//////////////////////////////
				// Reproduction des espèces //
				//////////////////////////////
			
				for (Planete planete:ListePlanete) {
				
					if (planete.getProprietaire() != -1)
						planete.reproduction();
					
					//DEBUG
					//System.out.println("("+planete.getY()+","+planete.getX()+") : nouvelle population : "
					//	+planete.getPopulation()+", espèce : "+planete.getProprietaire());
					
					/*panneau.rafraichir(ListePlanete, ListeVaisseau, ListeEspece);
					 try {
						Thread.sleep(2);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}*/
				}
			
				/////////////////////////////
				// Production de vaisseaux //
				/////////////////////////////
			
				for (Planete planete:ListePlanete) {
				
					if (planete.getProprietaire() != -1)
						planete.constructionVaisseau();
					
					//DEBUG
					//System.out.println("("+planete.getX()+","+planete.getY()+") : avancement construction : "
					//		+(planete.getConstruction().getIntegrite()*100/planete.getConstruction().getResistance())+
					//		"%, espèce : "+planete.getProprietaire());
					//
					//System.out.println("Productivité de l'espèce : "+Galaxy.getEspece(planete.getProprietaire()).getProductivite());
					
					/*panneau.rafraichir(ListePlanete, ListeVaisseau, ListeEspece);
					 try {
						Thread.sleep(2);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}*/
				}	
			
			
				///////////////////////////////
				// Deplacement des vaisseaux //
				///////////////////////////////
			
				ListeVaisseauClone = (ArrayList<Vaisseau>) ListeVaisseau.clone();
			
				for (Vaisseau vaisseau : ListeVaisseauClone) {
				
					Deplacement = new int[2];
				
					Deplacement = vaisseau.move();
					grille[vaisseau.getX()][vaisseau.getY()] = null;
				
					if(Deplacement[0] != -1 && Deplacement[1] != -1) {
					
						vaisseau.setX(Deplacement[0]);
						vaisseau.setY(Deplacement[1]);
						grille[Deplacement[0]][Deplacement[1]] = vaisseau;
						
						//DEBUG
						//System.out.println("debug-vaisseau(x,y): "+vaisseau+"("+vaisseau.getX()+","+vaisseau.getY()+")");
					} else {

						EspeceFlotte = ListeEspece.get(vaisseau.getProprietaire()).getEmpireFlotte();
						EspeceFlotte.remove(vaisseau);
						ListeEspece.get(vaisseau.getProprietaire()).setEmpireFlotte(EspeceFlotte);
						ListeVaisseau.remove(vaisseau);
						Galaxy.setListeVaisseau(ListeVaisseau);
					}
				
					/*panneau.rafraichir(ListePlanete, ListeVaisseau, ListeEspece);
				
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}*/
				}
			
			
				////////////////////////////////
				// Interactions des vaisseaux //
				////////////////////////////////
			
				ListeVaisseauClone = (ArrayList<Vaisseau>) ListeVaisseau.clone();
			
				for (Vaisseau vaisseau : ListeVaisseauClone) {
				
					if (ListeVaisseau.contains(vaisseau))
						vaisseau.Interact();
				
					/*panneau.rafraichir(ListePlanete, ListeVaisseau, ListeEspece);
				
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}*/
				}

				///////////////////////
				// Affichage Console //
				///////////////////////
			
				sortieConsole(ListeEspece);
			
			
				/////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////
				
				panneau.rafraichir(ListePlanete, ListeVaisseau, ListeEspece);
			
				/////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////
			
			
				// temporisation avant prochain tour
				try {
					Thread.sleep(Constantes.TourMs);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
			try {
				Thread.sleep(2*Constantes.TourMs);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			// fermeture de la fenêtre
			fenetre.dispose();
		}  else {
			System.out.println("Veuillez entrer une Largeur et une Hauteur supérieures à 3 afin d'assurer le bon fonctionnement du programme.");
		}
	}
}
