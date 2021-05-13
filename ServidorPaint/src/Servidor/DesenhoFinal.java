package Servidor;
import java.util.Vector;
import BD.dbos.InfosFiguras;
import BD.dbos.ItemFiguras;

public class DesenhoFinal {
	

	private InfosFiguras figuras;
	private Vector<String> itens;
	
	
	public DesenhoFinal (InfosFiguras figuras, Vector<String> itens) {
            this.figuras = figuras;
            this.itens = itens;
	}
	
	//SETTERS
        public void setFigura(InfosFiguras figuras) {
		this.figuras = figuras;
	}
        
        public void setItens(Vector<String> itens) {
		this.itens = itens;
	}
        
        //GETTERS
	public InfosFiguras getDesenho() {
            return figuras;
	}

	public Vector<String> getItens() {
		return itens;
	}
}
