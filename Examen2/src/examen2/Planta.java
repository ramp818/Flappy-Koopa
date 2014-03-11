package examen2;


public class Planta extends Base{
    
       /**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto Planta.
	 * @param posY es el <code>posiscion en y</code> del objeto Planta.
	 * @param image es la <code>animacion</code> del objeto Planta.
	 */
    
    public Planta(int posX,int posY,Animacion image){
		super(posX,posY,image);
	}
    
}
