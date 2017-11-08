/**
 * @author Marion DENMAT - Lo�s DE SAUVECANNE
 *
 * @brief Classe regroupant toutes les constantes n�cessaires au bon fonctionnement de la simulation. Permet de changer rapidement des param�tres relatif � son ex�cution.
 */

public class Constantes {
	// Caractéristiques de la galaxie
	public static final int Largeur = 30; // largeur de la grille galactique (en nombre de cases)
	public static final int Hauteur = 20; // hauteur de la grille galactique (en nombre de cases)
	
	// Caractéristiques des planètes
	public static final int PlaneteTailleMin = 100; // taille minimale d'une planète
	public static final int PlaneteTailleMax = 1000; // taille maximale d'une planète
	public static final int PlaneteMin = (int) Math.floor(Largeur*Hauteur)/30;
	public static final int PlaneteMax = (int) Math.floor(Largeur*Hauteur)/10;
	public static final int PlaneteRechargeCarbu = 5;
	
	// Caract�ristiques des esp�ces
	public static final int EspeceMin = (int) Math.floor(Largeur*Hauteur)/90;
	public static final int EspeceMax = (int) Math.floor(Largeur*Hauteur)/30;
	
	// Caractéristiques des vaisseaux
	public static final int VaisseauResistanceMin = 10; // résistance minimale d'un vaisseau
	public static final int VaisseauResistanceMax = 100; // résistance maximale d'un vaisseau
	public static final int VaisseauCarbuMax = 10;
	public static final int VaisseauCarbuMin = 5;
	public static final int nbVaisseauInitial = 2;
	public static final int VaisseauDeplacementMax = 5;
	public static final int VaisseauDegatVaisseauMin = 10;
	public static final int VaisseauDegatVaisseauMax = 30;
	public static final int VaisseauDegatPlaneteMin = 50;
	public static final int VaisseauDegatPlaneteMax = 150;
	
	// Caractéristiques des propulsions
	public static final int PropulsionPorteeMin = 1; // portée minimal d'une propulsion
	public static final int PropulsionPorteeMax = 5; // portée maximal d'une propulsion

	// Paramètres de la simulation
	public static final int TourMax = 500; // nombre de tours maximum
	public static final int TourMs = 300; // durée d'un tour en millisecondes
	
	// Paramètres d'affichage
	public static final int GfxCase = 50; // taille des cases en pixel
	public static final int GfxPlaneteBase = 25; // taille de base des planètes en pixels
	public static final int GfxPlaneteFacteur = 45; // proportion des planètes relative à leur taille
	public static final int GfxVaisseauBase = 5; // taille de base des vaisseaux en pixels
	public static final int GfxVaisseauFacteur = 5; // proportion des vaisseaux relative à leur résistance
}
