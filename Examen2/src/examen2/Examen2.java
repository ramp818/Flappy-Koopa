package examen2;

import javax.swing.JFrame;

/**
 *
 * @author Ruben Martinez y Angel Gonzalez 
 */
public class Examen2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FlappyKoopa juego= new FlappyKoopa();
        juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	juego.setVisible(true);
    }
    
}
