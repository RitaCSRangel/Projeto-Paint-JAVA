package paintBasico;
import java.awt.*;
import java.awt.event.*;
import static java.awt.event.KeyEvent.VK_DELETE;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import Cliente.Parceiro;
import Comunicados.ComunicadoDeDesenho;
import Comunicados.ComunicadoDeConsulta;
import Comunicados.PedidoDeAbrir;
import Comunicados.PedidoDeConsultar;
import Comunicados.PedidoDeSalvar;
import Comunicados.RespostaDeConsultar;

public class Janela extends JFrame {
    
    /*Antes de qualquer coisa é necessário deixar claro que o nosso programa paint que será acessado por
    vários usuários é o Cliente da nossa relação cliente servidor e, como tal, ele possui alguns papéis a 
    cumprir como esclarecido nas video aulas. O primeiro papel é realizar sua conexão com o servidor e, para
    isso, precisamos de um host e uma porta. Nesse primeiro ponto do código o que fazemos é declarar uma 
    porta e um host padrão que serão utilizados caso o usuário não especifique o contrário na inicialização
    de seu programa.
    
    Mas onde deve ser feita a continuação dessa conexão Cliente Servidor? No momento em que o usuário quiser 
    fechar o nosso paint. É importante lembrar que o foco na construção dessa estrutura é que o cliente possa
    salvar seus desenhos em um banco de dados através de uma conexão com o servidor e recuperá-los quando tentar
    abrir o programa novamente.
    */
    
    //MUDANÇA 1
    public static final String HOST_PADRAO  = "localhost";
    public static final int    PORTA_PADRAO = 3000;	
	
    //INSTANCIANDO BOTÕES
    /*Essa área do código faz parte do nosso projeto base de paint no qual o programa instancia vários
    botões com o uso da classe JButton*/
    private JButton btnPonto   = new JButton ("Ponto"),
                    btnLinha     = new JButton ("Linha"),
                    btnCirculo   = new JButton ("Circulo"),
                    btnElipse    = new JButton ("Elipse"),                
                    btnQuadrado  = new JButton ("Quadrado"),
                    btnRetangulo = new JButton ("Retangulo"),
                    btnPoligono  = new JButton ("Poligono"),
                    btnEscrita   = new JButton ("Escrita"),
                    btnCores     = new JButton ("Contorno"),
                    btnPreen     = new JButton ("Preenchimento"),
                    btnAbrir     = new JButton ("Abrir"),
                    btnConsultar = new JButton ("Consultar"),
                    btnSalvar    = new JButton ("Salvar"),
                    btnApagar    = new JButton ("Apagar"),
                    btnSair      = new JButton ("Sair"),
                    btnSelect    = new JButton ("Selecionar"),
                    btnMover     = new JButton ("Mover");
                   
    //INSTANCIANDO O PAINEL DE DESENHO
    /*Essa área do código faz parte do nosso projeto base de paint no qual o programa instancia um painel que
    irá conter os desenhos feitos pelo usuário. Esse painel está usando uma subclasse criada por nós mais 
    a frente nesse código*/
    private MeuJPanel pnlDesenho = new MeuJPanel ();

    //INSTANCIANDO AS LABELS DE INFORMAÇÃO P/ O USUARIO
    /*Essa área do código faz parte do nosso projeto base de paint no qual o programa instancia duas labels
    que mostrarão pequenas mensagens ao usuário, dizendo a ele onde está seu mouse e o que deve fazer caso
    tenha escolhido realizar alguma função.*/
    private JLabel statusBar1 = new JLabel ("Mensagem:"),
                   statusBar2 = new JLabel ("Coordenada:");
                   
    //DECLARANDO AS VARIAVEIS BOOLEANAS
    /*Essa área do código faz parte do nosso projeto base de paint no qual o programa instancia várias 
    variáveis booleanas que serão usadas no controle de tarefas. Quando clicarmos em um botão específico
    criaremos uma instância de uma classe específica que ativará e desativará variáveis específicas para
    a realização da função escolhida pelo usuário.*/
    protected boolean 
	esperaPonto, 
	esperaInicioReta, esperaFimReta, desenhandoReta, 
        esperaInicioCirculo, esperaFimCirculo, desenhandoCirculo, 
        esperaInicioElipse, esperaFimElipse, desenhandoElipse, 
        esperaInicioQuadrado, esperaFimQuadrado, desenhandoQuadrado, 
        esperaInicioRetangulo, esperaFimRetangulo, desenhandoRetangulo, 
        esperaInicioPol, esperaFimPol, desenhandoPoligono, 
        esperaInicioTexto, esperaFimTexto, 
	esperaSelect, esperaMover, esperaUp, esperaDown, 
        aberto, apagar;
    
    //INSTANCIANDO OBJETOS DE TEXTO
    /*Essa área do código faz parte do nosso projeto base de paint no qual o programa instancia uma
    variável de texto usada não somente na hora do usuário escrever um texto em seu painel mas também
    para que ele possa fornecer informações ao nosso paint.*/
    private String stringTexto = null;
   
    //INSTANCIANDO A VARIAVEL QUE IRÁ ARMAZENAR COR DE CONTORNO
    private Color corContorno = Color.black; 
   
    //INSTANCIANDO A VARIAVEL QUE IRÁ DECLARAR COR DE PREENCHIMENTO
    private Color corPreenchimento = new Color(0,0,0,0); 
   
    //DECLARANDO O PONTO AUXILIAR QUE GUARDARÁ COORDENADAS
    private Ponto p1; 
    
    //DECLARANDO OS VETORES DE LOCALIZAÇÃO QUE GUARDARÃO COORDENADAS
    int x[] = new int[90];
    int y[] = new int[90]; 
    
    //DECLARANDO VARIÁVEIS AUXILIARES DE POLÍGONO, SELEÇÃO DE IMAGEM E DE NÚMERO DE VÉRTICES
    int xDragged, yDragged; 
    int selecionado; 
    int vertices, vezesverices; 
   
    //OBJETO QUE VAI GUARDAR A FONTE DO TEXTO
    private Font  fonteTexto = new Font("Arial", 0, 14); 
    
    //VARIÁVEIS AUXILIARES PARA ALTERAÇÃO DE TAMANHO E ESTILO DE FONTE
    private int size, style; 
    
    //VETORES QUE ARMAZENAM AS FIGURAS DESENHADAS NA TELA
    private Vector<Figura> figuras = new Vector<Figura>(); 
    private Vector<Figura> aux = new Vector<Figura>(); 
    
    //------------------------------------------ CONSTRUTOR ----------------------------------------------- 
    /*Essa área do código faz parte do nosso projeto base de paint e seu nome é auto explicativo */

    public Janela () {
        
        //NOME DO EDITOR SENDO PASSADO PARA O CONSTRUTOR MAIS BÁSICO DE JPANEL
        super("Editor Grafico");
        
        //TRATAMENTOS DE EXCEÇAO NA HORA DE COLOCAR AS IMAGENS NOS BOTÕES 
        try {
            Image btnMoverImg = ImageIO.read(getClass().getResource("/resources/mover.png"));
            btnMover.setIcon(new ImageIcon(btnMoverImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo mover.png não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }
        
        try {
            Image btnSelectImg = ImageIO.read(getClass().getResource("/resources/selecionar.png"));
            btnSelect.setIcon(new ImageIcon(btnSelectImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo selecionar.png não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }
        
        try {
            Image btnPontoImg = ImageIO.read(getClass().getResource("/resources/ponto.jpg"));
            btnPonto.setIcon(new ImageIcon(btnPontoImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo ponto.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try {
            Image btnLinhaImg = ImageIO.read(getClass().getResource("/resources/linha.jpg"));
            btnLinha.setIcon(new ImageIcon(btnLinhaImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo linha.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try {
            Image btnCirculoImg = ImageIO.read(getClass().getResource("/resources/circulo.jpg"));
            btnCirculo.setIcon(new ImageIcon(btnCirculoImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo circulo.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try {
            Image btnElipseImg = ImageIO.read(getClass().getResource("/resources/elipse.jpg"));
            btnElipse.setIcon(new ImageIcon(btnElipseImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo elipse.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }
        try {
            Image btnQuadradoImg = ImageIO.read(getClass().getResource("/resources/quadrado.jpg"));
            btnQuadrado.setIcon(new ImageIcon(btnQuadradoImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo quadrado.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }
        try {
            Image btnRetanguloImg = ImageIO.read(getClass().getResource("/resources/retangulo.jpg"));
            btnRetangulo.setIcon(new ImageIcon(btnRetanguloImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo retangulo.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try {
            Image btnPoligonoImg = ImageIO.read(getClass().getResource("/resources/poligono.jpg"));
            btnPoligono.setIcon(new ImageIcon(btnPoligonoImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo poligono.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }
        try {
            Image btnEscritaImg = ImageIO.read(getClass().getResource("/resources/escrita.jpg"));
            btnEscrita.setIcon(new ImageIcon(btnEscritaImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo escrita.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }
        try {
            Image btnCoresImg = ImageIO.read(getClass().getResource("/resources/cores.jpg"));
            btnCores.setIcon(new ImageIcon(btnCoresImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo cores.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }
        try {
            Image btnPreenImg = ImageIO.read(getClass().getResource("/resources/preenchimento.png"));
            btnPreen.setIcon(new ImageIcon(btnPreenImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo preenchimento.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try {
            Image btnAbrirImg = ImageIO.read(getClass().getResource("/resources/abrir.jpg"));
            btnAbrir.setIcon(new ImageIcon(btnAbrirImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo abrir.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try {
            Image btnSalvarImg = ImageIO.read(getClass().getResource("/resources/salvar.jpg"));
            btnSalvar.setIcon(new ImageIcon(btnSalvarImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo salvar.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try {
            Image btnApagarImg = ImageIO.read(getClass().getResource("/resources/apagar.jpg"));
            btnApagar.setIcon(new ImageIcon(btnApagarImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo apagar.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try {
            Image btnSairImg = ImageIO.read(getClass().getResource("/resources/sair.jpg"));
            btnSair.setIcon(new ImageIcon(btnSairImg));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo sair.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }
   
        //---------------------------------------------------------------------------------------------------
        
        //ADICIONANDO A INTERFACE ACTIONLISTENER PARA "OUVIR" AS AÇÕES DO USUÁRIO INSTANCIAR A CLASSE DEFINIDA ENTRE PARENTESES
        /*Essa área do código faz parte do nosso projeto base de paint e seu nome é auto explicativo */
        btnAbrir.addActionListener(new Abrir()); 
        btnSalvar.addActionListener(new Salvar());
        btnPonto.addActionListener (new DesenhoDePonto());
        btnLinha.addActionListener (new DesenhoDeReta ());
        btnCirculo.addActionListener(new DesenhoDeCirculo());
        btnElipse.addActionListener(new DesenhoDeElipse());
        btnQuadrado.addActionListener(new DesenhoDeQuadrado());
        btnRetangulo.addActionListener(new DesenhoDeRetangulo());
        btnPoligono.addActionListener(new DesenhoDePoligono());
        btnEscrita.addActionListener(new EscreveTexto());
        btnCores.addActionListener(new EscolhaCorContorno());
        btnPreen.addActionListener(new EscolhaCorPreenchimento());
        btnSair.addActionListener (new ParedeDeJanela());
        btnSelect.addActionListener(new SelecionarImagem());
        btnMover.addActionListener(new MoverImagem());
        btnApagar.addActionListener(new Apagar ());
        btnConsultar.addActionListener(new Consultar ());

        //INSTANCIANDO O PAINEL DE BOTÕES, DANDO UM LAYOUT PARA ELE E ADICIONANDO OS BOTÕES
        JPanel     pnlBotoes = new JPanel(); 
        GridLayout flwBotoes = new GridLayout(2,7); 
        pnlBotoes.setLayout (flwBotoes);       
        pnlBotoes.add (btnAbrir);
        pnlBotoes.add (btnSalvar);
        pnlBotoes.add (btnSelect);
        pnlBotoes.add (btnMover);
        pnlBotoes.add(btnConsultar);
        pnlBotoes.add (btnPonto);
        pnlBotoes.add (btnLinha);
        pnlBotoes.add (btnCirculo);
        pnlBotoes.add (btnElipse);
        pnlBotoes.add (btnPreen);
        pnlBotoes.add (btnCores);
        pnlBotoes.add (btnApagar);
        pnlBotoes.add (btnSair);
        pnlBotoes.add (btnQuadrado);
        pnlBotoes.add (btnRetangulo);
        pnlBotoes.add (btnPoligono);
        pnlBotoes.add (btnEscrita);

        //INSTANCIANDO O PAINEL DE STATUS, DANDO UM LAYOUT PARA ELE E ADICIONANDO OS BOTÕES
        JPanel     pnlStatus = new JPanel(); 
        GridLayout grdStatus = new GridLayout(1,2); 
        pnlStatus.setLayout(grdStatus);        
        pnlStatus.add(statusBar1);
        pnlStatus.add(statusBar2);
	
         //COLOCANDO TUDO EM UM CONTAINER, PONDO UM LAYOUT NESSE CONTAINER E ORGANIZANDO SEUS ELEMENTOS
        Container cntForm = this.getContentPane(); 
        cntForm.setLayout (new BorderLayout());
        cntForm.add (pnlBotoes,  BorderLayout.NORTH);
        cntForm.add (pnlDesenho, BorderLayout.CENTER);
        cntForm.add (pnlStatus,  BorderLayout.SOUTH);
		
        //CARACTERÍSTICAS DA JANELA
        this.addWindowListener (new FechamentoDeJanela()); 
        this.setSize (1024,768);        
        this.setVisible (true); 
    }
    
    //----------------------------------------- FIM CONSTRUTOR -------------------------------------------------------

    //SUB CLASSE MEUJPANEL E SUAS HERANÇAS
    private class MeuJPanel extends    JPanel 
							
                            implements MouseListener, 
                                       KeyListener,
                                       MouseMotionListener 
                            
    {

	//CONSTRUTOR
	public MeuJPanel() {
            super(); 

            this.addMouseListener       (this); 
            this.addMouseMotionListener (this); 
            this.addKeyListener         (this); 
        }
        
        //METODO PAINT QUE VAI PEGAR AS FIGURAS DO VETOR DE FIGURAS E DEIXÁ-LAS VISÍVEIS
	public void paint (Graphics g) {
		for (int i=0 ; i<figuras.size(); i++)
		figuras.get(i).torneSeVisivel(g);
	}
	
        //AO PRESSIONAR O MOUSE OS IFS ENTRAM EM VERIFICAÇÃO, A BOOLEANA QUE ESTIVER COMO TRUE VAI DITAR 
        //EM QUAL IF ENTRA E ESSE IF DIZ O QUE DEVE SER FEITO NAQUELA OCASIÃO. CADA BOTÃO TEM UMA 
        //BOOLEANA QUE CONTROLA SUA AÇÃO.
	public void mousePressed (MouseEvent e) {

        
            if (esperaPonto) {

                try {
                    figuras.add (new Ponto (e.getX(), e.getY(), corContorno)); 
                } catch (Exception e1) { 
                    e1.printStackTrace();
                }

                figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics()); 	 
                esperaPonto = false;
            }


            else if (esperaInicioReta) {
                esperaInicioReta = false;
                desenhandoReta = true;

                try {
                    p1 = new Ponto (e.getX(), e.getY(), corContorno);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }  	      
                statusBar1.setText("Mensagem: solte o mouse no ponto final da reta");    
            }

            else if(esperaInicioCirculo){
                esperaInicioCirculo = false;
                desenhandoCirculo = true;

                try {
                    p1 = new Ponto (e.getX(), e.getY(), corContorno);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                statusBar1.setText("Mensagem: solte o mouse o ponto final do circulo");
            }

            else if(esperaInicioElipse){
                esperaInicioElipse = false;
                desenhandoElipse = true;

                try {
                    p1 = new Ponto (e.getX(), e.getY(), corContorno);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                statusBar1.setText("Mensagem: solte o mouse no ponto final da elipse");
            }

            else if (esperaInicioQuadrado) {
                esperaInicioQuadrado = false;
                desenhandoQuadrado = true;

                try {
                    p1 = new Ponto (e.getX(), e.getY(), corContorno);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }			

                x[0] = p1.getX();
                y[0] = p1.getY();
                statusBar1.setText("Mensagem: solte o mouse no ponto final do quadrado");    
            }

            else if (esperaInicioRetangulo) {
                esperaInicioRetangulo = false;
                desenhandoRetangulo = true;

                try {
                    p1 = new Ponto (e.getX(), e.getY(), corContorno);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                x[0] = p1.getX();
                y[0] = p1.getY();
                statusBar1.setText("Mensagem: solte o mouse no ponto final do retangulo");    
            }

            else if(esperaInicioPol){
                System.out.println("entrou poli");
                esperaInicioPol = false;
                desenhandoPoligono = true;
                esperaFimPol = false;

                try {
                    p1 = new Ponto (e.getX(), e.getY(), corContorno, corPreenchimento);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }			

                stringTexto = JOptionPane.showInputDialog(null, "Número de vertices", "Números Vértices:", JOptionPane.PLAIN_MESSAGE);
                x[0] = p1.getX();
                y[0] = p1.getY();
                vertices = Integer.parseInt(stringTexto);
                vezesverices = 1;
                statusBar1.setText("Mensagem: clique o ponto final do poligono");       
            }

            else if(desenhandoPoligono){
                esperaInicioPol = false;
                esperaFimPol = false;
                if(vezesverices < vertices){				
                    try {
                        p1 = new Ponto (e.getX(), e.getY(), corContorno, corPreenchimento);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    x[vezesverices] = p1.getX();
                    y[vezesverices] = p1.getY();
                    vezesverices++;
                    System.out.println("passou ponto");
                }

                if(vezesverices == vertices){
                    System.out.println("saiu poli");
                    esperaInicioPol = false;
                    try {
                        figuras.add (new Poligono(x, y, vertices, corContorno, corPreenchimento));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                        figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        statusBar1.setText("Mensagem:");
                        desenhandoPoligono = false;
                        esperaInicioPol = true;
                    }
            }


            else if(esperaInicioTexto){

                try {
                    esperaInicioTexto = true;
                    p1 = new Ponto (e.getX(), e.getY(), corContorno, corPreenchimento);
                    stringTexto = JOptionPane.showInputDialog(null, "Texto:", "Digite alguma coisa", JOptionPane.PLAIN_MESSAGE);
                    figuras.add (new Text(p1.getX(), p1.getY(), stringTexto, corContorno, corPreenchimento, fonteTexto));
                    figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                    statusBar1.setText("Mensagem: Digite o texto a ser exibido");
                    String nova_string = figuras.get(figuras.size()-1).toString();
                    System.out.println(nova_string);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }                                                       
	}  
        
        //A MESMA COISA ACONTECE AO SOLTAR O MOUSE, A BOOL QUE ESTIVER COMO TRUE ENTRA AQUI E REALIZA SUA AÇÃO. 
        public void mouseReleased (MouseEvent e) {
            if (esperaFimReta) {
                esperaInicioReta = true;
                esperaFimReta = false;
                desenhandoReta = false;
                RepintaTela();

                try {
                    figuras.add (new Linha(p1.getX(), p1.getY(), e.getX(), e.getY(), corContorno));
		} catch (Exception e1) {
                    e1.printStackTrace();
		}
                
                figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                statusBar1.setText("Mensagem:");    
            }
            else
                if (esperaFimCirculo){
                    esperaInicioCirculo = true;
                    esperaFimCirculo = false;
                    desenhandoCirculo = false;
                    RepintaTela();
                    
                    try {
			figuras.add(new Circulo(p1.getX(), p1.getY(), e.getX(), e.getY(), corContorno, corPreenchimento));
                    } catch (Exception e1) {
			e1.printStackTrace();
                    }
                    figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                    statusBar1.setText("Mensagem:");   
                }
                else
                    if(esperaFimElipse){
                        esperaFimElipse = false;
                        desenhandoElipse = false;
                        esperaInicioElipse = true;
                        RepintaTela();

                        try {
                            figuras.add(new Elipse(p1.getX(), p1.getY(), e.getX(), e.getY(), corContorno, corPreenchimento));
			} catch (Exception e1) {
                            e1.printStackTrace();
			}
                        figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        statusBar1.setText("Mensagem:");
                    }
                    else
                        if (esperaFimQuadrado) {
                            esperaFimQuadrado = false;
                            desenhandoQuadrado = false;
                            esperaInicioQuadrado = true;
                            RepintaTela();

                            try {
				figuras.add (new Quadrado(x, y, corContorno, corPreenchimento));
                            } catch (Exception e1) {
				e1.printStackTrace();
                            }
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                            statusBar1.setText("Mensagem:");    
                        }
                        else
                            if (esperaFimRetangulo) {
                                esperaFimRetangulo = false;
                                desenhandoRetangulo = false;
                                esperaInicioRetangulo = true;
                                RepintaTela();

                                try {
                                    figuras.add (new Retangulo(x, y, corContorno, corPreenchimento));
				} catch (Exception e1) {
                                    e1.printStackTrace();
				}
                                figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                                statusBar1.setText("Mensagem:");
                            }
        }
        
        //MESMA LÓGICA DOS ANTERIORES, MAS O MOUSE CLICKED É USADO APENAS PARA SELEÇÃO DE IMAGENS
        public void mouseClicked (MouseEvent e) {
            pnlDesenho.setFocusable(true);
            pnlDesenho.requestFocusInWindow();
            
            if(esperaSelect){
                for(int i = figuras.size()-1; i >= 0; i--){
                
                    try {
			p1 = new Ponto(e.getX(), e.getY());
                    } catch (Exception e1) {
			e1.printStackTrace();
                    }
                    if(figuras.elementAt(i).cliquePertence(p1.getX(), p1.getY())){
                        selecionado = i;
                        statusBar1.setText("Mensagem: Figura selecionada");
                        i = -1;
                    }
                }
            }
        }
        
        public void mouseEntered (MouseEvent e) {
        }

        public void mouseExited (MouseEvent e) {
        }
        
        //PARA ARRASTAR E MOVER, MESMA LÓGICA DOS ANTERIORES
        public void mouseDragged(MouseEvent e) {
            pnlDesenho.setFocusable(true);
            pnlDesenho.requestFocusInWindow();
            if (esperaMover && esperaSelect) {
                try {
                    figuras.get(selecionado).move(e.getX(), e.getY());
		} catch (Exception e1) {
                    e1.printStackTrace();
		}
                    figuras.get(selecionado).torneSeVisivel(pnlDesenho.getGraphics());
                    RepintaTela(0);
            }
            else 
                if(esperaMover && !esperaSelect) { 
                    statusBar1.setText("Mensagem: Selecione uma imagem primeiro");
                }
                else
                    if (desenhandoReta) {
                        RepintaTela();
                        esperaFimReta = true;
                        Graphics g = pnlDesenho.getGraphics();
                        g.drawLine(e.getX(), e.getY(), p1.getX(), p1.getY());                        
                    }
                    else
                        if(desenhandoCirculo){
                            esperaFimCirculo = true;
                            RepintaTela();
                            Circulo circ;
                            try {
				circ = new Circulo(p1.getX(), p1.getY(), e.getX(), e.getY(), corContorno, corPreenchimento);
				circ.torneSeVisivel(pnlDesenho.getGraphics());
                            } catch (Exception e1) {
				e1.printStackTrace();
                            }                                  
                        }
                        else
                            if (desenhandoElipse){
                                esperaFimElipse = true;
                                RepintaTela();
                                Elipse elip;
				try {
                                    elip = new Elipse(p1.getX(), p1.getY(), e.getX(), e.getY(), corContorno, corPreenchimento);
                                    elip.torneSeVisivel(pnlDesenho.getGraphics());
				} catch (Exception e1) {
                                    e1.printStackTrace();
                                }                              
                            }
                            else
                                if (desenhandoQuadrado){
                                    esperaFimQuadrado = true;
                                            
                                    x[1] = e.getX();
                                    y[1] = e.getY();
                                    Quadrado quad;
                                    try {
					quad = new Quadrado(x, y, corContorno, corPreenchimento);
					quad.torneSeVisivel(pnlDesenho.getGraphics());
                                    } catch (Exception e1) {
					e1.printStackTrace();
                                    }
                                    
                                    RepintaTela();
                                }
                                else if (desenhandoRetangulo) {
                                        esperaFimRetangulo = true;

                                        x[1] = e.getX();
                                        y[1] = e.getY();
                                        Retangulo ret;
					try {
                                            ret = new Retangulo (x, y, corContorno, corPreenchimento);
                                            ret.torneSeVisivel(pnlDesenho.getGraphics());
					} catch (Exception e1) {
                                            e1.printStackTrace();
					}
                                        RepintaTela();
                                }

        }
        
        //MOSTRA AS COORDENADAS DO MOUSE NA LABEL DE COORDENADAS
        public void mouseMoved(MouseEvent e) {
            statusBar2.setText("Coordenada: "+e.getX()+","+e.getY());
        }

        public void keyTyped(KeyEvent ke) {
          
        }
        
        //PERMITE DELETAR UMA IMAGEM ATRAVÉS DO BOTÃO DELETE
        public void keyPressed(KeyEvent ke) {
            if(esperaSelect == true && ke.getKeyCode() == VK_DELETE){
              figuras.remove(selecionado);
              esperaSelect = false;
              RepintaTela();
           }
        }

        public void keyReleased(KeyEvent ke) {
            
        }
    }

 // ------------- SUBCLASSES QUE TOMAM CONTA DAS BOOLEANAS. "SE EU CLICAR NESSE BOTÃO, AS BOOLEANAS ATIVAS SERÃO:" ------------
    
    //SUBCLASSE DESENHO DE PONTO
    private class DesenhoDePonto implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = false;         
            esperaPonto      = true;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false;
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;
            statusBar1.setText("Mensagem: clique o local do ponto desejado");
        }
    }
    
    //SUBCLASSE DESENHO DE RETA
    private class DesenhoDeReta implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = false;          
            esperaPonto      = false;
            esperaInicioReta = true;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false;
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;
            statusBar1.setText("Mensagem: clique o ponto inicial da reta");
        }
    }
    
    //SUBCLASSE DESENHO DE CÍRCULO
    private class DesenhoDeCirculo implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = false;          
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = true;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false;
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;
            statusBar1.setText("Mensagem: clique o ponto central do circulo");
        }
    }
    
    //SUBCLASSE DESENHO DE ELIPSE
    private class DesenhoDeElipse implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = false;           
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = true;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false;         
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;
            statusBar1.setText("Mensagem: clique o ponto central da Elipse");
        }
    }
    
    //SUBCLASSE DESENHO DE QUADRADO
    private class DesenhoDeQuadrado implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = false;            
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = true;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false;     
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;           
            statusBar1.setText("Mensagem: clique o ponto central da quadrado");
        }
    }
    
    //SUBCLASSE DESENHO DE RETÂNGULO
    private class DesenhoDeRetangulo implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = false;           
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = true;
            esperaFimRetangulo = false; 
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;
            statusBar1.setText("Mensagem: clique o ponto central da retangulo");
        }
    }
    
    //SUBCLASSE DESENHO DE POLÍGONO
    private class DesenhoDePoligono implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = false;           
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false; 
            esperaInicioPol = true;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;
            statusBar1.setText("Mensagem: clique o ponto central da retangulo");
        }
    }
    
    //SUBCLASSE APAGAR
    private class Apagar implements ActionListener{
        public void actionPerformed(ActionEvent e){
            apagar = true;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = esperaSelect;          
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false; 
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;            
            if (esperaSelect && apagar) { 
            	figuras.remove(selecionado);
            	statusBar1.setText("Mensagem: Apagado !");
            	esperaSelect = false;
            }
            else {
            	statusBar1.setText("Mensagem: Selecione uma figura antes de apagar.");
            }
            RepintaTela(0);          
        }
    }
    
    //SUBCLASSE SELECIONAR IMAGEM
    private class SelecionarImagem implements ActionListener{
        public void actionPerformed(ActionEvent e){
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = false;
            esperaSelect = true;           
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false; 
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;
            statusBar1.setText("Mensagem: clique para selecionar");
        }
    }
    
    //SUBCLASSE MOVER IMAGEM
    private class MoverImagem implements ActionListener{
        public void actionPerformed(ActionEvent e){
            apagar = false;
            esperaDown = false;
            esperaUp = false;
            esperaMover = true;
            esperaSelect = esperaSelect;            
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo = false; 
            esperaInicioPol = false;
            esperaFimPol = false;
            esperaInicioTexto = false;
            esperaFimTexto    = false;
            statusBar1.setText("Mensagem:");
        }
    }     
    
    //SUBCLASSE TEXTO
    @SuppressWarnings("serial")//Deixa de lado os warinings da classe abaixo
	private class EscreveTexto extends JFrame implements ActionListener
    {
        public void actionPerformed (ActionEvent e)    
        {
            setCursor(new Cursor(Cursor.TEXT_CURSOR));
            apagar = false;
            esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
            esperaFimCirculo = false;
            esperaInicioElipse = false;
            esperaFimElipse = false;
            esperaInicioRetangulo = false;
            esperaFimRetangulo  = false;
            esperaInicioQuadrado = false;
            esperaFimQuadrado = false;
            esperaInicioTexto = true;
            esperaFimTexto    = false;
            esperaInicioPol   = false;
            esperaFimPol      = false;                                  
            statusBar1.setText("Mensagem: ");
        }
    }
    
    //SUB CLASSE QUE ABRE O J COLOR CHOOSER E APLICA A COR ESCOLHIDA AO OBJETO CORCONTORNO
    private class EscolhaCorContorno implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            JColorChooser javacor = new JColorChooser();
            Color corC = javacor.showDialog(btnCores, "Selecione a cor", Color.yellow);
            corContorno = corC;
        }
    }
    
    //SUB CLASSE QUE ABRE O J COLOR CHOOSER E APLICA A COR ESCOLHIDA AO OBJETO CORPREENCHIMENTO
    private class EscolhaCorPreenchimento implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            JColorChooser javacor = new JColorChooser();
            Color corP = javacor.showDialog(btnCores, "Selecione a cor", Color.yellow);
            corPreenchimento = corP;
        }
    }
    
    //SUB CLASSE FECHAMENTO DE JANELA QUE DÁ A OPÇÃO DE SALVAR AO FECHAR A JANELA SOFRE MUDANÇAS DO CLIENTE-SERVIDOR
    private class FechamentoDeJanela extends WindowAdapter {
        public void windowClosing (WindowEvent e) {
            
            String Texto=null; 
            String SaidaSalva=("Salvando"); 
            String SaidaSemSalvar=("Saindo sem salvar"); 
            Texto = JOptionPane.showInputDialog("Deseja salvar? (Sim ou Nao)");
            if(Texto.equals("Sim")==true || Texto.equals("sim")==true){
            	
                 /* Como visto ao longo das vídeo aulas e mencionado no início desse código, o cliente deve ser capaz de se conectar
                ao servidor e, para que isso seja feito, utilizamos a nossa classe parceiro, apresentada nas aulas do fazedor de 
                continhas e reimplementada nesse código. Aqui, estamos instanciando um objeto servidor da classe parceiro que terá
                como parâmetros a conexão, o transmissor e o receptor. Contudo, ao invés de criarmos e passarmos esses parâmetros 
                aqui, fizemos uma subclasse chamada ConectarComServidor que fará as funções iniciais de um cliente (criar a 
                conexão, passar o host e a porta, criar o transmissor, o receptor etc.) tudo visando o conceito
                de reutilização da POO.
                */
                //MUDANÇA 2
                Parceiro server = conectarAoServidor();
                
                /* Para que a implementação do nosso banco de dados funcione corretamente é necessário que o usuário forneça dois 
                dados cruciais para as nossas tabelas: O nome do seu arquivo de desenho e o seu nome de usuário. Essas informações
                servirão para futuras consultas e procuras de arquivos no banco de dados e precisamos passá-las para o nosso
                ComunicadoDesenho, a classe responsável pelos objetos de desenho transmitidos e recebidos nessa relação de cliente e
                servidor.
                */
                //MUDANÇA 3
                String nomeDesenho = JOptionPane.showInputDialog("Digite o nome do arquivo: ");
                String nomeCliente = JOptionPane.showInputDialog("Digite seu nome: ");                            
                ComunicadoDeDesenho d = new ComunicadoDeDesenho ();
                d.setNome(nomeDesenho);
                d.setID(nomeCliente);
                
                /*
                Aqui é quando enciamos todas as figuras que desejamos salvar, todas essas que estão na tela e que o usuário optou
                por armazenar, para o vetor figurinhas existente na nossa classe ComunicadoDeDesenho. Para que todas as figuras, que
                atualmente estão armazenados no vetor figura desta classe, sejam enviadas para o nosso ComunicadoDeDesenho utilizamos
                um for. Ao final, criamos um objeto da classe pedidodesalvar chamada salvando e passamos, como parâmetro, o nosso 
                bjeto desenho que agora contém o nome do arquivo, o id do usuário e os desenhos criados por ele nessa utilização do
                paint. Para concluir, chamamos o método receba (responsável por transmitir) do nosso objeto servidor da classe 
                parceiro e mandamos através dele o objeto salvando pertencente a classe PedidoDeSalvar, ou seja, indiretamente fazemos
                um pedido de salvar todas essas informações adquiridas e no final mostramos a mensagem de que foi salvo com sucesso
                caso uma exceção não tenha sido encontrada e encerramos o programa.
                */
                //MUDANÇA 4               
                try {
                    for(int k = 0; k<figuras.size(); k++){
                    	System.out.println(k);
                    	d.addDesenho(figuras.elementAt(k).toString());
                    }
                      PedidoDeSalvar pds = new PedidoDeSalvar(d);
                      server.receba(pds);                   
                      JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                      
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Não foi salvo!");
                    ex.printStackTrace();
                    System.out.println("erro: "+ex.getMessage());
                }
          
                System.exit(0);
             }
            else{
                JOptionPane.showMessageDialog(null, SaidaSemSalvar);
                String string1 = new String(SaidaSemSalvar);
                System.out.println(string1);
                System.exit(0);		
            }
        }
    }
    
    //SUBCLASSE QUE CUIDA DA CONEXÃO COM O SERVIDOR
    private Parceiro conectarAoServidor() {
        
        /* Como passado na aula de classes específicas do cliente aqui fazemos a devida conexão do cliente com o servidor:
        - Criamos um objeto da classe socket chamado conexão inicialmente null
        -Dentro de um try catch nós:
            - Criamos um objeto do tipo string chamado host no qual colocamos nossa constante de host padrão.
            - Criamos uma variável do tipo int chamada porta na qual colocamos nossa constante de porta padrão.
            - Instanciamos o nosso objeto conexão, passando o host e a porta. 
        - Se der errado, o catch pega a exceção  emanda uma mensagem de erro. 
        - Se tudo der certo seguimos adiante criando nosso transmissor null e, dentro de um try catch, fazemos com que receba
        o outpút de conexão, é aqui que entram aquelas classes especiais que introduzimos com o import .
        - O mesmo vale para o receptor no qual pegamos o input da conexão.
        - Por fim criamos nosso objeto servidor da classe parceiro e passamos os dados necessários: conexãop, transmissor e 
        receptor.
         */  
        
        Socket conexao=null;
        try
        {
            String host = Janela.HOST_PADRAO;
            int porta= Janela.PORTA_PADRAO;
            
            conexao = new Socket (host, porta);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return null;
        }

        ObjectOutputStream transmissor=null;
        try
        {
            transmissor =
            new ObjectOutputStream(
            conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return null;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor =
            new ObjectInputStream(
            conexao.getInputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return null;
        }

        Parceiro servidor=null;
        try
        {
            servidor =
            new Parceiro (conexao, receptor, transmissor);
            return servidor;
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return null;
        }
    	
    	
    }   
    
    //SUB CLASSE SALVAR
    //SOFRE MUDANÇAS DO CLIENTE-SERVIDOR
    private class Salvar implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            Parceiro server = conectarAoServidor();
            

            String nomeDesenho = JOptionPane.showInputDialog("Digite o nome do arquivo: ");
            String nomeCliente = JOptionPane.showInputDialog("Digite seu nome: ");
            
            System.out.println("nD: "+nomeDesenho);
            System.out.println("nC: "+nomeCliente);
            

            ComunicadoDeDesenho d = new ComunicadoDeDesenho();
            d.setNome(nomeDesenho);
            d.setID(nomeCliente);
            
            
            try {

                  for(int k = 0; k<figuras.size(); k++){
                	   System.out.println(k);
                	   d.addDesenho(figuras.elementAt(k).toString());
                  }

                  PedidoDeSalvar pds = new PedidoDeSalvar(d);
                  server.receba(pds);
                  
                  JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                  
            } 
            catch (Exception ex) {
            	  JOptionPane.showMessageDialog(null, "Não foi salvo!");
            	  ex.printStackTrace();
                  System.out.println("erro: "+ex.getMessage());
            }
      
               
        }
    }
    
    //SUB CLASSE CONSULTAR
    //SOFRE MUDANÇAS DO CLIENTE-SERVIDOR
    private class Consultar implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Parceiro server = conectarAoServidor();
           
            try {
                  PedidoDeConsultar pdc = new PedidoDeConsultar();
                  server.receba(pdc);
                  RespostaDeConsultar rc = (RespostaDeConsultar) server.envie();
                  Vector<ComunicadoDeConsulta> desenhosConsultados = rc.getDesenhos();
                  
                  new JanelaConsulta(desenhosConsultados);
                  
                  JOptionPane.showMessageDialog(null, "Consultado com sucesso!");
                  
            } 
            catch (Exception ex) {
            	  JOptionPane.showMessageDialog(null, "Não foi salvo!");
            	  ex.printStackTrace();
                  System.out.println("erro: "+ex.getMessage());
            }
      
               
        }
    }
    
    //SUB CLASSE PAREDE DE JANELA
    //SOFRE MUDANÇAS DO CLIENTE-SERVIDOR
    private class ParedeDeJanela implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            String Texto=null; 
            String SaidaSalva=("Salvando"); 
            String SaidaSemSalvar=("Saindo sem salvar"); 
            Texto = JOptionPane.showInputDialog("Deseja salvar? (Sim ou Nao)");
            if(Texto.equals("Sim")==true || Texto.equals("sim")==true){
            	
                Parceiro server = conectarAoServidor();
                
                String nomeDesenho = JOptionPane.showInputDialog("Digite o nome do arquivo: ");
                String nomeCliente = JOptionPane.showInputDialog("Digite seu nome: ");
                
                System.out.println("nD: "+nomeDesenho);
                System.out.println("nC: "+nomeCliente);
                
                ComunicadoDeDesenho d = new ComunicadoDeDesenho();
                d.setNome(nomeDesenho);
                d.setID(nomeCliente);
                
                
                try {
                    for(int k = 0; k<figuras.size(); k++){
                    	System.out.println(k);
                    	d.addDesenho(figuras.elementAt(k).toString());
                    }
                    
                    PedidoDeSalvar pds = new PedidoDeSalvar(d);
                      server.receba(pds);
                      
                      JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                      
                } 
                catch (Exception ex) {
                	  JOptionPane.showMessageDialog(null, "Não foi salvo!");
                	  ex.printStackTrace();
                      System.out.println("erro: "+ex.getMessage());
                }
          
            System.exit(0);
		
            }
            else{
                JOptionPane.showMessageDialog(null, SaidaSemSalvar);
                String string1 = new String(SaidaSemSalvar);
                System.out.println(string1);
                System.exit(0);		
            }
        }
    }
    
    private class Abrir implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String p = "p";
            String l = "l";
            String c = "c";
            String el = "e";
            String r = "r";
            String q = "q";
            String g = "g";
            String t = "t";
            aberto = false;
            
            try {
            	Parceiro server = conectarAoServidor();
            	String nomeDesenho = JOptionPane.showInputDialog("Qual o nome do desenho que deseja abrir?");
            	String nomeCliente = JOptionPane.showInputDialog("Qual o nome do cliente que fez o upload?");
            	PedidoDeAbrir pda = new PedidoDeAbrir(nomeDesenho, nomeCliente);
            	server.receba(pda);
            	ComunicadoDeDesenho d = (ComunicadoDeDesenho) server.envie();
            	Vector<String> desenhos  = d.getDesenho();
            		
                    figuras.clear();
                    RepintaTela();
                    for(int k=0;k<desenhos.size();k++) {
                    	StringTokenizer scanner = new StringTokenizer(desenhos.get(k),":");
                        String tipo = scanner.nextToken();
                        System.out.println("inicio");
                        System.out.println(tipo);
                        if(p.equals(tipo)){
                            figuras.add (new Ponto (Integer.parseInt(scanner.nextToken()), 
                                                    Integer.parseInt(scanner.nextToken()), 
                                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                                        Integer.parseInt(scanner.nextToken()), 
                                                        Integer.parseInt(scanner.nextToken())))
                                        );
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                            scanner.nextToken();
                        }
                        if(l.equals(tipo)){
                            figuras.add (new Linha(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            new Color(Integer.parseInt(scanner.nextToken()), 
                                                    Integer.parseInt(scanner.nextToken()), 
                                                    Integer.parseInt(scanner.nextToken())))
                                        );
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                            scanner.nextToken();
                        }
                        if(c.equals(tipo)){
                            figuras.add(new Circulo(Integer.parseInt(scanner.nextToken()), 
                                    Integer.parseInt(scanner.nextToken()), 
                                    Integer.parseInt(scanner.nextToken()), 
                                    Integer.parseInt(scanner.nextToken()), 
                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken())), 
                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()),
                                            Integer.parseInt(scanner.nextToken())))
                                        );
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        }
                        if(el.equals(tipo)){
                            figuras.add (new Elipse(Integer.parseInt(scanner.nextToken()), 
                                    Integer.parseInt(scanner.nextToken()), 
                                    Integer.parseInt(scanner.nextToken()), 
                                    Integer.parseInt(scanner.nextToken()), 
                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken())), 
                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()),
                                            Integer.parseInt(scanner.nextToken())))
                                         );
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        }
                        if(q.equals(tipo)){
                            int x[] = new int[99], y[] = new int[99];
                            x[0] = Integer.parseInt(scanner.nextToken());
                            y[0] = Integer.parseInt(scanner.nextToken());
                            Integer.parseInt(scanner.nextToken());
                            Integer.parseInt(scanner.nextToken());
                            x[1] = Integer.parseInt(scanner.nextToken());
                            y[1] = Integer.parseInt(scanner.nextToken());
                            Integer.parseInt(scanner.nextToken());
                            Integer.parseInt(scanner.nextToken());
                            Integer.parseInt(scanner.nextToken());
                            figuras.add (new Quadrado(x, y, 
                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken())), 
                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()),
                                            Integer.parseInt(scanner.nextToken()))));
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        }
                        if(r.equals(tipo)){
                            int x[] = new int[99], y[] = new int[99];
                            x[0] = Integer.parseInt(scanner.nextToken());
                            x[1] = Integer.parseInt(scanner.nextToken());
                            y[0] = Integer.parseInt(scanner.nextToken());
                            y[1] = Integer.parseInt(scanner.nextToken());
                            Integer.parseInt(scanner.nextToken());
                            Integer.parseInt(scanner.nextToken());
                            figuras.add (new Retangulo(x, y, 
                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken())), 
                                    new Color(Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()), 
                                            Integer.parseInt(scanner.nextToken()),
                                            Integer.parseInt(scanner.nextToken())))
                                        );
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        }
                        if(t.equals(tipo)){
                            int x = Integer.parseInt(scanner.nextToken());
                            int y = Integer.parseInt(scanner.nextToken());
                            String txt =  scanner.nextToken();
                            Color cor1 = new Color(Integer.parseInt(scanner.nextToken()), Integer.parseInt(scanner.nextToken()), Integer.parseInt(scanner.nextToken()));
                            Color cor2 = new Color(Integer.parseInt(scanner.nextToken()), Integer.parseInt(scanner.nextToken()), Integer.parseInt(scanner.nextToken()));
                            int size = Integer.parseInt(scanner.nextToken());
                            String family = scanner.nextToken();
                            int style = Integer.parseInt(scanner.nextToken());
                            Font fonte =  new Font (family, style, size);
                            
                            figuras.add (new Text(x, y, txt, cor1, cor2, fonte));
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        }
                        if(g.equals(tipo)){
                            int n = Integer.parseInt(scanner.nextToken());
                            int x[] = new int[99], y[] = new int[99];
                            for(int i = 0; i < n; i++){
                                x[i] = Integer.parseInt(scanner.nextToken());
                                y[i] = Integer.parseInt(scanner.nextToken());
                            }
                            figuras.add (new Poligono(x, y, n, new Color(Integer.parseInt(scanner.nextToken()), 
                                                                         Integer.parseInt(scanner.nextToken()), 
                                                                         Integer.parseInt(scanner.nextToken())), 
                                                               new Color(Integer.parseInt(scanner.nextToken()), 
                                                                         Integer.parseInt(scanner.nextToken()), 
                                                                         Integer.parseInt(scanner.nextToken()), 
                                                                         Integer.parseInt(scanner.nextToken()))));
                            figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        }
    
                    }
                    JOptionPane.showMessageDialog(null, "Desenho: "+d.getNome().toUpperCase()+" do cliente: "+d.getID().toUpperCase()+" aberto com sucesso. ");

        }catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Não há arquivos");
		}
    }
  }
  //SUB CLASSES REPAINT COM E SEM PARÂMETROS
    private void RepintaTela(){
        
        try {
            Thread.sleep(10);                
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        pnlDesenho.resize(pnlDesenho.getHeight()+1, pnlDesenho.getWidth()+1);
        pnlDesenho.resize(pnlDesenho.getHeight()-1, pnlDesenho.getWidth()-1);
    }
    
    private void RepintaTela(int x){
        
        try {
            Thread.sleep(x);                 
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        pnlDesenho.resize(pnlDesenho.getHeight()+1, pnlDesenho.getWidth()+1);
        pnlDesenho.resize(pnlDesenho.getHeight()-1, pnlDesenho.getWidth()-1);
    }
}
