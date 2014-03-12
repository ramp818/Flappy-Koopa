package examen2;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class FlappyKoopa extends JFrame implements Runnable, KeyListener
{
    private static final long serialVersionUID = 1L;
    //Se declaran las variables.
    //Se declaran variables tipo Image
    private Image dbImage;
    private Image background;
    private Image ImagenGameOver;
    private Image ImagenPausa;
    private Image nivel1;
    //Se declaran variables tipo Graphics
    private Graphics dbg;
    //Se declaran variables tipo int
    private int score;
    private int velKoopaX;
    private int velKoopaY;
    private int gravedad;
    private int contBoton;
    //Se declaran variables tipo long
    private long tiempoActual;
    private long tiempoInicial;
    //Se declaran variables tipo Pajaro
    private Pajaro koopa;
    //Se declaran variables tipo Planta
    private Planta barraArriba;
    private Planta barraAbajo;
    private Planta espacio;
    //Se declaran variables tipo Animacion
    private Animacion animKoopa;
    private Animacion animBarra;
    private Animacion animEspacio;
    //Se declaran variables tipo SoundClip
    private SoundClip musicaFondo;
    private SoundClip salto;
    //Se declaran variables tipo boolean
    private boolean pausa;
    private boolean gameOver;
    private boolean boton;
    private boolean inicio;
    //Se declaran variables Linked list
    private LinkedList bArriba;
    private LinkedList bAbajo;
    private LinkedList space;
    
    
    
    public FlappyKoopa(){
       
        //Se inicializan variables
        score=0;
        velKoopaX=0;
        velKoopaY=5;
        gravedad=2;
        boton=false;
        inicio=false;
        bArriba= new LinkedList();
        bAbajo= new LinkedList();
        space= new LinkedList();
        
        //Se cargan imagenes
        
        background = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/fondo.png"));
        ImagenPausa= Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/pausa.gif"));
        ImagenGameOver= Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/GameOver.gif"));
        nivel1= Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/Level1.gif")); 
        
        // Carga personaje bueno
        Image Koopa1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k0.gif"));
        Image Koopa2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k1.gif"));
        Image Koopa3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k2.gif"));
        Image Koopa4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k3.gif"));
        Image Koopa5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k4.gif"));
        Image Koopa6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k5.gif"));
        Image Koopa7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k6.gif"));
        Image Koopa8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k7.gif"));
        Image Koopa9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/k8.gif"));
        
        animKoopa= new Animacion();
        animKoopa.sumaCuadro(Koopa1,100);
        animKoopa.sumaCuadro(Koopa2,100);
        animKoopa.sumaCuadro(Koopa3,100);
        animKoopa.sumaCuadro(Koopa4,100);
        animKoopa.sumaCuadro(Koopa5,100);
        animKoopa.sumaCuadro(Koopa6,100);
        animKoopa.sumaCuadro(Koopa7,100);
        animKoopa.sumaCuadro(Koopa8,100);
        animKoopa.sumaCuadro(Koopa9,100);
        
        koopa=new Pajaro(100,300,animKoopa);
        
        //Carga plantas
        Image barra0 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/barra.gif"));
        animBarra = new Animacion();
        animBarra.sumaCuadro(barra0,100);
        
        //Carga imagen transparente
        Image transparente = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/espacio.gif"));
        animEspacio = new Animacion();
        animEspacio.sumaCuadro(transparente,100);
        
        //Cargan Sonidos
        salto = new SoundClip("sounds/salto.wav");
        
        //Caracteristicas JFrame
        setSize(1200,600);
        addKeyListener(this);
        
        Thread th = new Thread(this);
        //Empieza el hilo
        th.start();
    
        for (int j = 600; j < 2800; j += 270){
            int i = (int)(Math.random() * (-200) + (-50));
            bArriba.add(new Planta(j, i, animBarra));
            space.add(new Planta(j,i+400,animEspacio));
            bAbajo.add(new Planta(j, i+550, animBarra));  
        }  
   }
    
   /** 
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
     * la posicion en x o y dependiendo de la direccion, finalmente 
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     * 
     */
        public void run() {
            tiempoActual = System.currentTimeMillis();
            while (true) {
                if (!pausa && inicio && !gameOver) {
                    checaColision();
                    actualiza();
            }
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    System.out.println("Error en " + ex.toString());
                }   
            }
        }
        
       /**
	 * Metodo usado para actualizar la posicion de objetos.
	 * 
	 */
        
        public void actualiza(){
           
            if(inicio){
            
                //Actualiza animaciones
                long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
                tiempoActual += tiempoTranscurrido;
                koopa.getAnimacion().actualiza(tiempoTranscurrido);
            
                //movimiento pajaro
                if(!boton){
                
                    koopa.setPosY(koopa.getPosY() + (gravedad * velKoopaY));
                } 
                else{
                
                    koopa.setPosY(koopa.getPosY() - (gravedad * velKoopaY));
                } 
                koopa.setPosX(koopa.getPosX() + velKoopaX);
            
            }
           
            
        }
        
        /**
	 * Metodo usado para checar las colisiones del objeto Pajaro
	 * con las orillas del <code>JFrame</code>.
	 */
        
        public void checaColision(){
            
            if(koopa.getPosY() + koopa.getAlto() > getHeight()-79){
                gameOver=true;
            }
            
            
        }
        
        /**
	 * Metodo <I>paint</I> sobrescrito de la clase <code>JFrame</code>,
	 * heredado de la clase Container.<P>
	 * En este metodo lo que hace es actualizar el contenedor
	 * @param g es el <code>objeto grafico</code> usado para dibujar.
	 */
	public void paint(Graphics g){
		// Inicializan el DoubleBuffer
		if (dbImage == null){
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		// Actualiza la imagen de fondo.
		dbg.setColor(getBackground ());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

		// Actualiza el Foreground.
		dbg.setColor(getForeground());
		paint1(dbg);

		// Dibuja la imagen actualizada
		g.drawImage (dbImage, 0, 0, this);
	}
       
       /**
	 * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
	 * heredado de la clase Container.<P>
	 * En este metodo se dibuja la imagen con la posicion actualizada,
	 * ademas que cuando la imagen es cargada te despliega una advertencia.
	 * @param g es el <code>objeto grafico</code> usado para dibujar.
	 */
        
        public void paint1(Graphics g){
            
            g.drawImage(background, 0, 0, this);
            
            if(koopa != null){
                g.drawImage(koopa.getAnimacion().getImagen(), koopa.getPosX(), koopa.getPosY(), this);
            }
            
            for (int i = 0; i < bArriba.size(); i++) {
                barraArriba = (Planta) (bArriba.get(i));
                espacio = (Planta) (space.get(i));
                barraAbajo = (Planta) (bAbajo.get(i));
                g.drawImage(barraArriba.getAnimacion().getImagen(), barraArriba.getPosX(), barraArriba.getPosY(), this);
                g.drawImage(espacio.getAnimacion().getImagen(), espacio.getPosX(), espacio.getPosY(), this);
                g.drawImage(barraAbajo.getAnimacion().getImagen(), barraAbajo.getPosX(), barraAbajo.getPosY(), this);
                
            }
            
            if(!inicio){
                
               g.drawImage(nivel1, 450, 250, this); 
            }
            
            if(pausa){
                
                g.drawImage(ImagenPausa, 450, 250, this);
            }
            if(gameOver){
                
                g.drawImage(ImagenGameOver, 450, 250, this);
            }
        }
        
   /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
       
       inicio=true; 
       if (e.getKeyCode() == KeyEvent.VK_UP) {    //Presiono flecha arriba
                
            boton=true;
            salto.play();
       } 
       else if (e.getKeyCode() == KeyEvent.VK_P){
                
            pausa=!pausa;
       }
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {
       
    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    
    public void keyReleased(KeyEvent e) {
        
        boton=false;
    }
}


