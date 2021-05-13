package paintBasico;
import java.awt.*;
import java.util.*;

public class Retangulo extends Figura {
    protected int xiz[] = new int[99];
    protected int yipisilom[] = new int[99];
    
    protected int ladox, ladoy;
	
    public Retangulo (int x[], int y[]) throws Exception{
        this (x, y, Color.BLACK, Color.WHITE);
    }
	
    public Retangulo (int x[], int y[], Color cor) throws Exception{
        this (x, y, cor, Color.WHITE);
    }
    
    public Retangulo (int x[], int y[], Color cor, Color preen) throws Exception{
        super(cor,preen);

        this.ladox = Math.abs(x[0] - x[1]);
        this.ladoy = Math.abs(y[0] - y[1]);
        
        
        
        this.xiz[0] = x[0]; this.yipisilom[0] = y[0];
        if(y[1] > y[0]){
            if(x[1] > x[0]){

                this.xiz[1] = x[0] + this.ladox; this.yipisilom[1] = y[0];
                this.xiz[2] = x[0] + this.ladox; this.yipisilom[2] = y[0] + this.ladoy;
                this.xiz[3] = x[0];             this.yipisilom[3] = y[0] + this.ladoy;
            }
            else{

                this.xiz[1] = x[0];             this.yipisilom[1] = y[0] + this.ladoy; 
                this.xiz[2] = x[0] - this.ladox; this.yipisilom[2] = y[0] + this.ladoy; 
                this.xiz[3] = x[0] - this.ladox; this.yipisilom[3] = y[0]; 
            }
        }
        else{
            if(x[1] < x[0]){

                this.xiz[1] = x[0] - this.ladox; this.yipisilom[1] = y[0]; 
                this.xiz[2] = x[0] - this.ladox; this.yipisilom[2] = y[0] - this.ladoy; 
                this.xiz[3] = x[0];             this.yipisilom[3] = y[0] - this.ladoy; 
            }
            else{
                //4º quadrante
                this.xiz[1] = x[0];             this.yipisilom[1] = y[0] - this.ladoy; 
                this.xiz[2] = x[0] + this.ladox; this.yipisilom[2] = y[0] - this.ladoy; 
                this.xiz[3] = x[0] + this.ladox; this.yipisilom[3] = y[0]; 
            }
        }
    }

    public Retangulo (String q) throws Exception{
        StringTokenizer quebrador = new StringTokenizer(q,":");

        quebrador.nextToken();

        int   x   = Integer.parseInt(quebrador.nextToken());
        int   y   = Integer.parseInt(quebrador.nextToken());

        int   lado   = Integer.parseInt(quebrador.nextToken());

        Color cor = new Color (Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken()),  
                               Integer.parseInt(quebrador.nextToken())); 

//        this.centro = new Ponto (x,y,cor);
//        this.raio   = r;
//        this.cor    = cor;
        //aqui vamos ter que pegar também a cor do preenchimento
    }

    public void setP0 (int x, int y) {
        this.xiz[0] = x;
        this.yipisilom[0] = y;
    }
    
    public void setPontos (int x[], int y[]) {
        this.xiz = x;
        this.yipisilom = y;
    }

    public void setLadox (int ladox) {
        this.ladox = ladox;
    }
    
    public void setLadoy (int ladoy) {
        this.ladoy = ladoy;
    }
    
    public int getLadox () {
        return this.ladox;
    }
    
    public int getLadoy () {
        return this.ladoy;
    }

    public Ponto getP0 () throws Exception{
        Ponto P0 = new Ponto(this.xiz[0], this.yipisilom[0]);
        return P0;
    }

    public void torneSeVisivel (Graphics g) {
        g.setColor (this.preen);
        g.fillPolygon (xiz, yipisilom, 4);
        g.setColor (this.cor);
        g.drawPolygon (xiz, yipisilom, 4);
    }

    public String toString() {
        return "r:" +
               this.xiz[0] +
               ":" +
               this.xiz[2] +
               ":" +
               this.yipisilom[0] +
               ":" +
               this.yipisilom[2] +
               ":" +
               this.ladox +
               ":" +
               this.ladoy +
               ":" +
               this.getCor().getRed() +
               ":" +
               this.getCor().getGreen() +
               ":" +
               this.getCor().getBlue() + 
                ":" +
               this.getPreen().getRed() +
                ":" +
               this.getPreen().getGreen() +
                ":" +
               this.getPreen().getBlue() +
                ":" +
               this.getPreen().getAlpha();
    }
    
    public boolean cliquePertence (int x, int y){
        Polygon poligono_aux = new Polygon(xiz, yipisilom, 4);
        if(poligono_aux.contains((double)x, (double)y))
            return true;
        else
            return false;
    }
    
    public void move (int x, int y){
        int x1[] = new int [4];
        int y1[] = new int [4];
        
        x1[0] = x - this.ladox/2;  y1[0] = y + this.ladoy/2;
        x1[1] = x + this.ladox/2;  y1[1] = y + this.ladoy/2;
        x1[2] = x + this.ladox/2;  y1[2] = y - this.ladoy/2;
        x1[3] = x - this.ladox/2;  y1[3] = y - this.ladoy/2;
        
        
        setPontos(x1,y1);
    }
    
}
