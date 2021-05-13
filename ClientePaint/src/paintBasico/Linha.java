package paintBasico;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;

public class Linha extends Figura {
    protected Ponto p1, p2;
	
    public Linha (int x1, int y1, int x2, int y2) throws Exception {
        this (x1, y1, x2, y2, Color.BLACK);
    }
    
    public Linha (int x1, int y1, int x2, int y2, Color cor, Color preen) throws Exception{
        super(cor);

        this.p1 = new Ponto (x1,y1,cor);
        this.p2 = new Ponto (x2,y2,cor);
    }
	
    public Linha (int x1, int y1, int x2, int y2, Color cor) throws Exception{
        super(cor);

        this.p1 = new Ponto (x1,y1,cor);
        this.p2 = new Ponto (x2,y2,cor);
    }

    public Linha (String s) throws Exception{
        StringTokenizer quebrador = new StringTokenizer(s,":");

        quebrador.nextToken();

        int   x1  = Integer.parseInt(quebrador.nextToken());
        int   y1  = Integer.parseInt(quebrador.nextToken());

        int   x2  = Integer.parseInt(quebrador.nextToken());
        int   y2  = Integer.parseInt(quebrador.nextToken());

        Color cor = new Color (Integer.parseInt(quebrador.nextToken()),  // R
                               Integer.parseInt(quebrador.nextToken()),  // G
                               Integer.parseInt(quebrador.nextToken())); // B

        this.p1  = new Ponto (x1,y1,cor);
        this.p2  = new Ponto (x2,y2,cor);
        this.cor = cor;
    }

    public void setP1 (int x, int y) throws Exception {
		this.p1 = new Ponto (x,y,this.getCor());
    }

    public void setP2 (int x, int y) throws Exception {
		this.p2 = new Ponto (x,y,this.getCor());
    }

    public Ponto getP1 () {
        return this.p1;
    }

    public Ponto getP2 () {
        return this.p2;
    }

    public void torneSeVisivel (Graphics g) {
        g.setColor(this.cor);
        g.drawLine(this.p1.getX(), this.p1.getY(),   
                   this.p2.getX(), this.p2.getY()); 
    }

    public String toString() {
        return "l:" +
               this.p1.getX() +
               ":" +
               this.p1.getY() +
               ":" +
               this.p2.getX() +
               ":" +
               this.p2.getY() +
               ":" +
               this.getCor().getRed() +
               ":" +
               this.getCor().getGreen() +
               ":" +
               this.getCor().getBlue();
    }
    
    public boolean cliquePertence (int x, int y){
        int HIT_BOX_SIZE = 5;
        int boxX = x - HIT_BOX_SIZE / 2;
        int boxY = y - HIT_BOX_SIZE / 2;
        int width = HIT_BOX_SIZE;
        int height = HIT_BOX_SIZE;
        
        Line2D linha_aux;
        linha_aux = new Line2D.Double();
        linha_aux.setLine((int)this.p1.getX(), (int)this.p1.getY(), (int)this.p2.getX(), (int)this.p2.getY());
        
        if(linha_aux.intersects(boxX, boxY, width, height)) {
            return true;
        }
        
        return false;
    }
    
    public void move (int x, int y) throws Exception{
        Ponto ponto_aux = new Ponto (x, y);
        if(this.p2.getY() > this.p1.getY()){
            if(this.p2.getX() > this.p1.getX()){

                ponto_aux.setX(x + (int)Math.abs(x - this.p1.getX()));
                ponto_aux.setY(y + (int)Math.abs(y - this.p1.getY()));
            }
            else{

                ponto_aux.setX(x + (int)Math.abs(x - this.p1.getX()));
                ponto_aux.setY(y + (int)Math.abs(y - this.p1.getY()));
            }
        }
        else{
            if(this.p2.getX() < this.p1.getX()){

                ponto_aux.setX(x + (int)Math.abs(x - this.p1.getX()));
                ponto_aux.setY(y + (int)Math.abs(y - this.p1.getY()));
            }
            else{

                ponto_aux.setX(x + (int)Math.abs(x - this.p1.getX()));
                ponto_aux.setY(y + (int)Math.abs(y - this.p1.getY()));
            }
        }
    }

}
