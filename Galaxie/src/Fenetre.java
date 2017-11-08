import javax.swing.JFrame;

/**
 * @brief Classe engendrant une fenetre principale d'application associee a� un panneau
 */
public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * @param pan : JPanel
	 * @brief Genere une fenetre non redimensionnable, toujours visible, fermant le programme a la fermeture de la fenetre
	 */
	public Fenetre(Affichage pan) {
		// titrage de la fenêtre
		super("Galaxy Wars (alpha)");
		
		// réglage des paramètres
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);

		// ajout de la grille
		getContentPane().add(pan);
		pack();
		
		// affichage centré
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
