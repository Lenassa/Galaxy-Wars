import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * @brief Classe permettant de realiser un affichage graphique de la galaxie
 * 
 * @brief La grille galactique est representee graphiquement dans un panneau, les 
 * @brief cases etant numerotees à partir du coin superieur gauche (0,0) jusqu'au
 * @brief coin inferieur droit (Largeur,Hauteur) 
 */
public final class Affichage extends JPanel {
	private static final long serialVersionUID = 1L;

	// Largeur et hauteur du panel graphique, calculés une seule fois à la construction
	private int larg, haut, largFen;
	private ArrayList<Planete> ListePlanete;
	private ArrayList<Vaisseau> ListeVaisseau;
	private ArrayList<Espece> ListeEspece;
	
	/**
	 * @brief Constructeur initialisant le panneau d'affichage
	 */
	public Affichage() {
		// calcul des dimensions du panneau en fonction des parametres de la galaxie
		larg = Constantes.Largeur*Constantes.GfxCase;
		haut = Constantes.Hauteur*Constantes.GfxCase;
		largFen = larg + 400;
		setPreferredSize(new Dimension(largFen,haut));
	}

	/**
	 * @brief Efface le contenu du panneau en redéssiantn le fond et la grille
	 * @param g Objet graphique permettant de dessiner dans le panneau
	 */
	public void efface(Graphics g) {
		// espace galactique : fond noir
		g.setColor(Color.BLACK);
		g.fillRect(0,0,largFen,haut);
		// grille galactique : lignes grises
		g.setColor(Color.GRAY);
		for (int x=Constantes.GfxCase ; x<larg + Constantes.GfxCase; x+=Constantes.GfxCase) {
			// affichage des lignes verticales
			g.drawLine(x, 0, x, haut);
		}
		for (int y=Constantes.GfxCase ; y<haut; y+=Constantes.GfxCase) {
			// affichage des lignes horizontales
			g.drawLine(0, y, larg, y);
		}
	}

	/**
	 * @brief Affiche une plan�te selon ses caract�ristiques
	 * @param g Objet graphique permettant de dessiner dans le panneau
	 * @param x Abcisse de la case contenant la plan�te 
	 * @param y Ordonn�e de la case contenant la plan�te
	 * @param t Taille de la plan�te
	 * @param c Couleur de la plan�te
	 */
	public void affichePlanete(Graphics g, int x, int y, int t,float pop, Color c) {
		// calcul des coordonnées graphiques du centre de la planète
		int xg = x*Constantes.GfxCase + Constantes.GfxCase/2;
		int yg = y*Constantes.GfxCase + Constantes.GfxCase/2;
		// calcul du rayon de la planète selon sa taille
		int tg = Constantes.GfxPlaneteBase + t/Constantes.GfxPlaneteFacteur;
		// affichage
		g.setColor(c);
		g.fillOval(xg-tg/2,yg-tg/2,tg,tg);
		if (pop != 1 && pop != 0) {
			g.setColor(new Color(255,255,255));
			g.drawOval(xg-(int)(pop*(tg/2)), yg-(int)(pop*(tg/2)), (int)(pop*tg), (int)(pop*tg));
		}
	}
	
	/**
	 * @brief Affiche un vaisseau selon ses caractéristiques
	 * @param g Objet graphique permettant de dessiner dans le panneau
	 * @param x Abcisse de la case contenant le vaisseau 
	 * @param y Ordonnée de la case contenant le vaisseau
	 * @param r Résistance du vaisseau
	 * @param s Chaîne symbolisant la propulsion et l'équipement du vaisseau
	 * @param c Couleur du vaisseau
	 */
	public void afficheVaisseau(Graphics g, int x, int y, int r, String s, Color c) {
		// calcul des coordonnées graphiques du centre de la planète
		int xg = x*Constantes.GfxCase + Constantes.GfxCase/2;
		int yg = y*Constantes.GfxCase + Constantes.GfxCase/2;
		// calcul du côté du vaisseau selon sa taille
		int tg = Constantes.GfxVaisseauBase + r/Constantes.GfxVaisseauFacteur;
		// affichage du carré
		g.setColor(c);
		g.fillRect(xg-tg/2,yg-tg/2,tg,tg);
		// calcul des dimensions graphiques de la chaîne à afficher
		int lg = (int)Math.ceil(g.getFont().getStringBounds(s,((Graphics2D)g).getFontRenderContext()).getWidth());
		int hg = (int)Math.ceil(g.getFont().getStringBounds(s,((Graphics2D)g).getFontRenderContext()).getHeight());
		// affichage de la chaîne sur le carré
		g.setColor(Color.WHITE);
		g.drawString(s, xg-lg/2, yg+hg/2);
	}

	/**
	 * @brief Provoque le rafraichissement du panneau
	 */
	@SuppressWarnings("unchecked")
	public void rafraichir(ArrayList<Planete> planetes, ArrayList<Vaisseau> vaisseaux, ArrayList<Espece> espece) {

		ListePlanete = (ArrayList<Planete>) planetes.clone(); // recopie la liste pour éviter des problèmes de synchronisation
		ListeVaisseau =(ArrayList<Vaisseau>) vaisseaux.clone(); // recopie la liste pour éviter des problèmes de synchronisation
		ListeEspece = (ArrayList<Espece>) espece.clone(); 
		repaint();
	}

	/**
	 * @brief Réaffiche le panneau à la demande
	 * @param g Objet graphique permettant de dessiner dans le panneau
	 */
	// @override
	public void paintComponent(Graphics g) {
		// affichage par défaut
		super.paintComponent(g);

		// l'espace et la grille galactique
		efface(g);
		
		g.setXORMode(Color.BLACK);
		
		//Affichage sur le c�t�
		int X=Constantes.Largeur + 1,Y=1,hg,xg,yg,tg,compteur;
		String ss;
		//Affichage planete vide
		xg = X*Constantes.GfxCase + Constantes.GfxCase/2;
		yg = 0*Constantes.GfxCase + Constantes.GfxCase/2;
		// calcul du rayon de la planète selon sa taille
		tg = Constantes.GfxPlaneteBase + 50/Constantes.GfxPlaneteFacteur;
		// affichage
		g.setColor(new Color(255,255,255));
		g.fillOval(xg-2*tg,yg-tg/2,tg,tg);
		
		ss = "Plan�te vide";
		xg -= Constantes.GfxCase/3;
		hg = (int)Math.ceil(g.getFont().getStringBounds(ss,((Graphics2D)g).getFontRenderContext()).getHeight());
		g.drawString(ss, xg, yg+hg/2);
		
		//Affichage numero tour
		ss ="Tour n�"+ Galaxy.getTour();
		xg = (Constantes.Largeur + 5)*Constantes.GfxCase + Constantes.GfxCase/2;
		hg = (int)Math.ceil(g.getFont().getStringBounds(ss,((Graphics2D)g).getFontRenderContext()).getHeight());
		g.drawString(ss, xg, yg);
		
		//Affichage une planete par espece plus informations espece
		for (Espece e:ListeEspece) {
			if (e.getEmpirePlanete().size() != 0 || e.getEmpireFlotte().size() != 0) {
				//Recadrage si beaucoup d'espece
				if (Y > Constantes.Hauteur) {
					Y = 1;
					X += 2;
				}
				//Affichage planete
				xg = X*Constantes.GfxCase + Constantes.GfxCase/2;
				yg = Y*Constantes.GfxCase + Constantes.GfxCase/2;
				// calcul du rayon de la planète selon sa taille
				tg = Constantes.GfxPlaneteBase + 50/Constantes.GfxPlaneteFacteur;
				// affichage
				g.setColor(e.getColor());
				g.fillOval(xg-2*tg,yg-tg/2,tg,tg);

				//Affichage informations
				xg -= Constantes.GfxCase/3;
				yg -= Constantes.GfxCase/3;
				g.setColor(new Color(255,255,255));
				compteur = 0;
				for (Planete pp:e.getEmpirePlanete()) {
					compteur += pp.getPopulation();
				}
				ss = "Poss�de "+e.getEmpirePlanete().size()+" plan�tes,";
				hg = (int)Math.ceil(g.getFont().getStringBounds(ss,((Graphics2D)g).getFontRenderContext()).getHeight());
				g.drawString(ss, xg, yg+hg/2);
			
				ss = "population totale de "+compteur+" individus";
				hg = (int)Math.ceil(g.getFont().getStringBounds(ss,((Graphics2D)g).getFontRenderContext()).getHeight());
				hg += Constantes.GfxCase/2;
				g.drawString(ss, xg, yg+hg/2);
			
				ss = "Effectif de la Flotte : "+e.getEmpireFlotte().size();
				hg = (int)Math.ceil(g.getFont().getStringBounds(ss,((Graphics2D)g).getFontRenderContext()).getHeight());
				hg += Constantes.GfxCase;
				g.drawString(ss, xg, yg+hg/2);
				Y+=1;
			}
		}


		// affichage des planètes et vaisseaux
		for (Planete p : ListePlanete) {
			float populationPercent;
			if (p.getProprietaire() != -1) {
				populationPercent = ((float)p.getPopulation())/p.getTaillePlanete();
				affichePlanete(g,p.getX(),p.getY(),p.getTaillePlanete(),populationPercent,ListeEspece.get(p.getProprietaire()).getColor());
			} else {
				affichePlanete(g,p.getX(),p.getY(),p.getTaillePlanete(),0,new Color(255,255,255));
			}
		}
		
		String s = new String();
		for (Vaisseau p : ListeVaisseau) {
			if (p.getPropulseur().getClass() == PropulseurLineaire.class) {
				s = "+";
			}
			if (p.getPropulseur().getClass() == PropulseurDiagonale.class) {
				s = "x";
			}
			if (p.getPropulseur().getClass() == PropulseurOmnidirectionnel.class) {
				s = "*";
			}
			s += "p|";
			if (p.getIdEquipement().contains(1)) {
				s += "c";
			}
			if (p.getIdEquipement().contains(2)) {
				s += "s";
			}
			if (p.getIdEquipement().contains(3)) {
				s += "t";
			}
			afficheVaisseau(g,p.getX(),p.getY(),p.getIntegrite(),s,ListeEspece.get(p.getProprietaire()).getColor());
		}
	}
	
}
