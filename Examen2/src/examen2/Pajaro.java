package examen2;


public class Pajaro extends Base{
    
        /**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto Pajaro.
	 * @param posY es el <code>posiscion en y</code> del objeto Pajaro.
	 * @param image es la <code>animacion</code> del objeto Pajaro.
	 */
    
    public Pajaro(int posX,int posY,Animacion image){
		super(posX,posY,image);
	}
}
