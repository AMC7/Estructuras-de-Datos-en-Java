package mx.unam.ciencias.edd;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import  java.util.Random;

import mx.unam.ciencias.edd.ArbolRojinegro.VerticeRojinegro;


@SuppressWarnings("rawtypes")
public class Graficador<T extends Coleccion> {
	public Random random;
	
	
    public T coleccion;
    public static Lista<Integer> Lista =new Lista<Integer>();
    public static ArbolBinarioCompleto <Integer> ArbolBinarioCompleto =new ArbolBinarioCompleto<Integer>();
    public static ArbolBinarioOrdenado<Integer> ArbolBinarioOrdenado =new ArbolBinarioOrdenado<Integer>();
    public static ArbolBinarioOrdenado<Integer> ArbolAVL =new ArbolAVL <Integer>();
    public static ArbolRojinegro<Integer> ArbolRojinegro =new ArbolRojinegro<Integer>();
    public static Grafica<Integer> Grafica =new Grafica<Integer>();
    public static Cola<Integer> Cola =new Cola<Integer>();
    public static Pila<Integer> Pila =new Pila<Integer>();
  
    
    
    Lista<Lista> lista =new Lista<Lista>();
    Lista<VerticeGrafica> listaGrafica =new Lista<VerticeGrafica>();
    Lista<Punto> puntos =new Lista<Punto>();
    Lista<Punto> adyascencias = new Lista<Punto>();
    Lista<Integer> auxiliar =new Lista<Integer>();
    String salto="\n";

        
    protected class Punto {
    int x; int y;
    	
    public Punto(int x, int y){
    this.x=x;
    this.y=y;
    }
    
    public String toString(){
    return"("+String.valueOf(x)+","+String.valueOf(y)+")";
    }
    	
    }
    
    protected class VerticeGrafica {
    	Integer elemento;
    	int indice;
    	Punto punto;
    	
    	public VerticeGrafica( Integer elemento,int i){
    		this.indice=i;
    		this.elemento=elemento;
    	
    	}
    	public String toString(){
    		return"(Elemento:"+String.valueOf(elemento)+","
    				+ "Indice:"+String.valueOf(indice)+",Punto:" +punto + " )";
    	}

    	
    	
    }
    
   @SuppressWarnings("unchecked")
	public Graficador(String s) throws Exception{
    	if (s.equals("Lista")){
		  this.coleccion=(T) Lista;
		  return;
    	}
    	if (s.equals("ArbolBinarioCompleto")){
  		  this.coleccion=(T) ArbolBinarioCompleto;
  		  return;
      	}
    	if (s.equals("ArbolAVL")){
    		  this.coleccion=(T) ArbolAVL;
    		  return;
        	}
    	if (s.equals("ArbolBinarioOrdenado")){
  		  this.coleccion=(T) ArbolBinarioOrdenado;
  		  return;
      	}
    	if (s.equals("ArbolRojinegro")){
  		  this.coleccion=(T) ArbolRojinegro;
  		  return;
      	}
    	if (s.equals("Grafica")){
    	this.coleccion=(T) Grafica;
  		return;
      	}
    	
    	if (s.equals("Pila")){
        this.coleccion=(T) Pila;
      	return;
          }
    	
    	if (s.equals("Cola")){
          this.coleccion=(T) Cola;
          return;
             }
    	
    	throw new Exception("No escribiste el nombre de una estructura de datos válida");
		  
	  }
    
    public static void generaArchivo(String direccion,String texto){
 	   try {
        	File archivo = new File(direccion);
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(texto);
            bw.close();
            
           
            
        } catch(IOException e) {
            System.err.println("No se ha podido crear el archivo " +e);
        }
 	   
    }
	
	
	
 
    
   
    @SuppressWarnings("unchecked")
	
    public  T  generadorDeColeccion(int numeroDeElementos){
     for(int i =0;i<numeroDeElementos;i++){
	 random=new Random();
	 int elemento =random.nextInt(30);
	 coleccion.agrega(elemento);
	}
	 return coleccion;
	 
		
	}
    
   
    @SuppressWarnings("unchecked")
	public T generadorDeColeccion(int numeroDeElementos,int rango){
    	
    	for(int i =0;i<numeroDeElementos;i++){
    	 random=new Random();
    	 int elemento =random.nextInt(rango);
    	 coleccion.agrega(elemento);
    	}
    	 return coleccion;
    	 
    		
    	}
    
    

    
    
    @SuppressWarnings("unchecked")
	public  T generadorDeColeccion(String s) throws Exception{
    	
    	char [] a = s.toCharArray();
    	
    	Lista<String> numero= new Lista<String>();
    	String regreso="";
    	numero.limpia();
    	for(int i =0;i<s.length();i++){
    	
    	regreso="";
    		
    	if(!esNumero(a[i])){i++;}
    	
    	//For que recorre el numero
    	for(int l =i;l<s.length();l++){
    	if(!esNumero(a[i])){break;}
        numero.agrega(String.valueOf(a[i++]));
    	}
    	
    	
    	
    	//For que convierte la lista de numeros el un string	
    	for(String string:numero){
    	regreso+=string;
    	}
    	
    	
    	//Se convierte regreso en un  integer , y lo agrega a la lista
    	if(regreso!=""&&regreso.length()<10){
    	int elemento=Integer.parseInt(regreso);
        coleccion.agrega(elemento);
    	}
    	
    	if(regreso.length()>=10){
    	throw new Exception("Pusiste un numero DEMASIADO grande");
    		
    	}
    	 numero.limpia();
    	}
    	 return coleccion;
    	 
    		
    	}
    
    @SuppressWarnings("unchecked")
   	public  T generadorDeColeccion(String s,String ti) throws Exception{
    	
       char [] a = s.toCharArray();
    	int indice=0;
    	Lista<String> numero= new Lista<String>();
    	String regreso="";
    	numero.limpia();
    	for(int i =0;i<s.length();i++){
    	
    	regreso="";
    		
    	if(!esNumero(a[i])){i++;}
    	
    	//For que recorre el numero
    	for(int l =i;l<s.length();l++){
    	if(!esNumero(a[i])){break;}
        numero.agrega(String.valueOf(a[i++]));
    	}
    	
    	
    	
    	//For que convierte la lista de numeros el un string	
    	for(String string:numero){
    	regreso+=string;
    	}
    	
    	
    	//Se convierte regreso en un  integer , y lo agrega a la lista
    	if(regreso!=""&&regreso.length()<10){
    	int elemento=Integer.parseInt(regreso);
        coleccion.agrega(elemento);
    	listaGrafica.agregaFinal(new VerticeGrafica(elemento,indice++));
    	}
    	
    	if(regreso.length()>=10){
    	throw new Exception("Pusiste un numero DEMASIADO grande");
    		
    	}
    	 numero.limpia();
    	}
    	
    	leeAdyascencias(ti);
    	
    	
    	 return coleccion;
    	 
    		
       	 
       		
       	}
    
    public  T leeAdyascencias(String ti) throws Exception{
    
    	  char [] aw = ti.toCharArray();
       	
       	Lista<String> numero= new Lista<String>();
       	String regreso="";
       	numero.limpia();
       	for(int i =0;i<ti.length();i++){
       	
       	regreso="";
       		
       	if(!esNumero(aw[i])){i++;}
       	
       	//For que recorre el numero
       	for(int l =i;l<ti.length();l++){
       	if(!esNumero(aw[i])){break;}
           numero.agrega(String.valueOf(aw[i++]));
       	}
       	
       	
       	
       	//For que convierte la lista de numeros el un string	
       	for(String string:numero){
       	regreso+=string;
       	}
       	
       	
       	//Se convierte regreso en un  integer , y lo agrega a la lista
       	if(regreso!=""&&regreso.length()<10){
       	int elemento=Integer.parseInt(regreso);
       	auxiliar.agrega(elemento);
          
       	}
       	
       	if(regreso.length()>=10){
       	throw new Exception("Pusiste un numero DEMASIADO grande");
       		
       	}
       	 numero.limpia();
       	}
       	
       	
       	
       	
       	
    	
    	
    	
    	
    	
    	 return coleccion;
    	 
    		
       	 
       		
       	}
       
     

	
	@SuppressWarnings("unchecked")
	public String toString() {
		if(coleccion.getClass()==Lista.getClass()){ 
			
		Lista<Integer> lista =(mx.unam.ciencias.edd.Lista) coleccion;
    	return graficaLista(lista);
    	
		}if(coleccion.getClass()==ArbolBinarioCompleto.getClass()||
    		coleccion.getClass()==ArbolBinarioOrdenado.getClass()||
    		coleccion.getClass()==ArbolAVL.getClass()
    		){
			
    	ArbolBinario arbol = (ArbolBinario)coleccion;
    	return graficaArbolBinario( arbol);
    	
		}if(coleccion.getClass()==ArbolRojinegro.getClass()){ 
			
    		ArbolRojinegro arbol = (ArbolRojinegro)coleccion;
        	return graficaArbolRojinegro( arbol);
    	
		}
		if(coleccion.getClass()==Grafica.getClass()){ 
			
			Grafica grafica = (Grafica)coleccion;
        	return graficaGrafica(grafica);
    	
		}
         
  		
    	
		
		
		else{
      
        return coleccion.toString();
    	}
        }


public String graficaLista(Lista<Integer> lista) {
	   
	  int longitudTotal=77; int posicion=0;  int indice=1;
	   
	  String resultado=creaEntrada(77*lista.getElementos(),30);
	  
	   for(int elemento :lista){
	    
	   resultado+=dibujaCuadrado(posicion)
		        +dibujaElemento(25+posicion,18,13,"black",String.valueOf(elemento))
		        +salto+salto;
		  	 
	  if(indice!=lista.getLongitud()){
	  resultado+=dibujaFlecha(posicion);
	 
	  }
	    
	  posicion+=longitudTotal;
	  indice++;
	    	
	  }
	    return resultado+=creaSalida();
	
	}
 
 
	public String graficaArbolBinario(ArbolBinario<Integer> arbol) {
	if(arbol.elementos==0){
		return creaEntrada(20,20)+ creaSalida();
	}else{
   int elementosNivelFinal=calculaElementosEnElUltimoNivel(arbol.profundidad());
	 Cola<VerticeArbolBinario<Integer>> cola= new Cola <VerticeArbolBinario<Integer>>();
	 String dibujoDelArbol =dibujaArbol(arbol.profundidad(),BFS(arbol,cola));
	 Punto[] arregloDePuntos =pasarDeListaArreglo(puntos);
	 return creaEntrada(2000*elementosNivelFinal,40*arbol.getElementos())+
			dibujoDelArbol + uneLasLineas(arregloDePuntos)+salto+ creaSalida();
      
	}
}
	       

	
	
	@SuppressWarnings("unchecked")
	public String graficaGrafica(Grafica grafica) {
		
	int elementos=listaGrafica.getElementos();
	String s =dibujaVertices(elementos,calculaAngulo(elementos),listaGrafica);
	VerticeGrafica[] arreglo= (Graficador<T>.VerticeGrafica[]) Array.newInstance(new VerticeGrafica(1,2).getClass(), listaGrafica.getElementos());
	int[] adyasce=new int[auxiliar.getElementos()];
	int k=0;
	for(Integer y:auxiliar){
		adyasce[k++]=y;
	}
	Lista<Punto> w=creaPuntosApartirDeEnteros(adyasce);
	return pintaLineas(w,listaGrafica)+s+aristasGrafica(arreglo,adyascencias);
	}
	
	public String graficaArbolRojinegro(ArbolRojinegro arbol) {
		if(arbol.elementos==0){
			return creaEntrada(20,20)+ creaSalida();
		}else{
	   int elementosNivelFinal=calculaElementosEnElUltimoNivel(arbol.profundidad());
		 Cola<VerticeRojinegro> cola= new Cola <VerticeRojinegro>();
		 String dibujoDelArbol =dibujaArbolRojinegro(arbol.profundidad(),BFS(arbol,cola));
		 Punto[] arregloDePuntos =pasarDeListaArreglo(puntos);
		
		 return creaEntrada(2000*elementosNivelFinal,40*arbol.getElementos())+
				dibujoDelArbol + uneLasLineas(arregloDePuntos)+salto+ creaSalida();
	      
		}
	}

private String pintaLineas(
			mx.unam.ciencias.edd.Lista<Graficador<T>.Punto> w,
			mx.unam.ciencias.edd.Lista<Graficador<T>.VerticeGrafica> listaGrafica2) {
		
	String resultado ="";
	for(Punto punto :w){
		Punto a=new Punto(0,0);
		Punto b=new Punto(0,0);
		
			for(VerticeGrafica v: listaGrafica2){
			if(v.elemento==punto.x){
				a=v.punto;
			}	
			}
            for(VerticeGrafica v: listaGrafica2){
            	if(v.elemento==punto.y){
    				b=v.punto;
    			}	
				
			}
            resultado+=dibujaLinea(a.x,a.y,b.x,b.y);
			
		}
	return resultado;
	}

private Lista<Punto> creaPuntosApartirDeEnteros(int[] a)  {
	Lista<Punto> lista =new Lista<Punto>();
	
	for(int i=0 ;i<a.length-1;i+=2){
		Punto punto = new Punto(a[i],a[i+1]);
		
			lista.agrega(punto);
		}
	return lista;
	}

private String aristasGrafica(Graficador<T>.VerticeGrafica[] arreglo,
			mx.unam.ciencias.edd.Lista<Graficador<T>.Punto> adyascencias2) {
	
	
				return salto;
		
	}

private double calculaAngulo(int elementos) {
	double e =elementos;
	double dos = 2;
    return  Math.PI/(e/dos);
	
}

private String dibujaVertices(int n, double angulo, mx.unam.ciencias.edd.Lista<Graficador<T>.VerticeGrafica> listaGrafica2) {
	String resultado = "";
	int i =1;
	for(Graficador<T>.VerticeGrafica a:listaGrafica2){
	Punto punto=new Punto(calculaCoordenadaX(angulo*i),calculaCoordenadaY(angulo*i));	
	resultado+= dibujaVertice(punto.x,punto.y,String.valueOf(a.elemento));
	a.punto=punto;
	puntos.agrega(punto);
	i++;
	}
	return resultado;
}

private int calculaCoordenadaY(double d) {
return (int) ((Math.cos(d)*300)+330);
}

private int calculaCoordenadaX(double d) {
return (int) ((Math.sin(d)*300)+330);
}

private String creaEntrada(int ancho,int largo){
	return "<?x"+"ml version='1.0' encoding='UTF-8' ?>"+salto+
				 "<svg width='"+ancho+"' height='"+largo+"'>"+salto+"<g>";
		}
	
private String creaSalida(){
		return   salto+" </g>"+salto+ " </svg>"+salto;
		
		
	}
	
	private String dibujaLinea(int x1,int y1, int x2, int y2){
		return "<line x1='"+String.valueOf(x1)+"'"
				+ " y1='"+String.valueOf(y1)+"'"
				+ " x2='"+String.valueOf(x2)+"'"
				+ " y2='"+String.valueOf(y2)+"' "
				+ "stroke='black' stroke-width='1' />";
		
		
	}
	private String dibujaFlecha(int posicion){
		
		return dibujaLinea(70+posicion,12,55+posicion,12)+salto+
				dibujaLinea(70+posicion,15,72+posicion,12)+salto+
				dibujaLinea(70+posicion,9,72+posicion,12)+salto+
				dibujaLinea(55+posicion,15,53+posicion,12)+salto+
				dibujaLinea(55+posicion,9,53+posicion,12)
				;
		
		
	}
	
	private String dibujaCuadrado(int x1,int y1, int x2, int y2,int x3,int y3, int x4, int y4){
		return dibujaLinea(x1,y1,x2,y2)+salto+
				dibujaLinea(x1,y1,x3,y3)+salto+
			   dibujaLinea(x3,y3,x4,y4)+salto+
			   dibujaLinea(x2,y2,x4,y4)+salto
				;
		
		
	}
	
	private String dibujaCuadrado(int posicion){
		return dibujaCuadrado(posicion,0,50+posicion,0,posicion,25,50+posicion,25) ;
		
		
	}
	private String dibujaCirculo(int x,int y){
		return "<circle cx='"+x+"' cy='"+y+"' r='10' stroke='black' stroke-width='1' fill='white' />"; 
		
		
	}
	private String dibujaCirculo(int x,int y,int radio){
		return "<circle cx='"+x+"' cy='"+y+"' r='"+radio+"' stroke='black' stroke-width='1' fill='white' />"; 
		
		
	}
	private String dibujaCirculoNegro(int x,int y){
		return "<circle cx='"+x+"' cy='"+y+"' r='10' stroke='black' stroke-width='1' fill='black' />"; 
		
		
	}
	private String dibujaCirculoRojo(int x,int y){
		return "<circle cx='"+x+"' cy='"+y+"' r='10' stroke='red' stroke-width='1' fill='red' />"; 
		
		
	}
	
	
	
	private String dibujaElemento(int x,int y ,int tamano , String color,String elemento){
		

		return "<text fill='"+color+"' font-family='sans-serif'"
                + " font-size='13'"
                + " x='"+x+"' "
                + "y='"+y+"'" +salto+
               "text-anchor='middle'>"+elemento+"</text>" ;
		
		
	}
	private String dibujaVertice(int i, int j, String string2) {
		return dibujaCirculo(i,j)+salto+dibujaElemento(i,j+5,14,"black",string2) ;
		}
	private String dibujaArbol(int nivel,Cola<VerticeArbolBinario<Integer>> bfs) {
	    int i =nivel;
	  
	    String resultado="";
		while (i>=0){
		resultado+=graficaNivel(12*(calculaNumero(i)),12+(45*(nivel-i)),25*calculaNumero2(i),calculaElementosEnElUltimoNivel(nivel-i),bfs);
		i--;
		}
		
		return resultado;
	    }

	
	private  boolean esNumero(char i){
    	if(String.valueOf(i).equals("0")||String.valueOf(i).equals("1")||
    	   String.valueOf(i).equals("2")||String.valueOf(i).equals("3")||
    	   String.valueOf(i).equals("4")||String.valueOf(i).equals("5")||
    	   String.valueOf(i).equals("6")||String.valueOf(i).equals("7")||
    	   String.valueOf(i).equals("8")||String.valueOf(i).equals("9")){
    		return true;
    	}
    	
    	
    	return  false;
    }
	


	private int calculaNumero(int i) {
		return (int)((Math.pow(2,i))-1);
	}
	
	private int calculaNumero2(int i) {
		return (int)((Math.pow(2,i)));
	}
	
		
		
		private static int calculaElementosEnElUltimoNivel(int profundidad) {
		return (int) (Math.pow(2, profundidad));
	}

		
		
	
		

	private String graficaNivel(int x, int y, int separacion, int elementosNivelFinal, Cola<VerticeArbolBinario<Integer>> bfs) {
		String resultado="";
		x+=19;
		Lista<Punto> listaPunto = new Lista<Punto>();
	
		
		for(int i = 0;i<elementosNivelFinal;i++){
		VerticeArbolBinario verticeArbolBinario =bfs.saca();
		if(verticeArbolBinario!=null){
        resultado+=dibujaVertice(x,y,verticeArbolBinario.toString());
        listaPunto.agregaFinal(new Punto(x,y));
        puntos.agregaFinal(new Punto(x,y));
        
		}else{
		 listaPunto.agregaFinal(new Punto(-1,-1));
		 puntos.agregaFinal(new Punto(-1,-1));
		}
        x+=separacion;
        }
		
		
		lista.agrega(listaPunto);
		return resultado;
	
	
}
	
	
	
	private Cola<VerticeArbolBinario<Integer>> BFS(ArbolBinario<Integer> arbol, Cola<VerticeArbolBinario<Integer>> cola){
	
		VerticeArbolBinario<Integer> vertice = arbol.raiz();
		Cola<VerticeArbolBinario<Integer>> resultado=new Cola <VerticeArbolBinario<Integer>>();
		cola.mete(vertice);
		
		while(!cola.esVacia()){
			
			vertice=cola.saca();
			resultado.mete(vertice);
			
			if(vertice!=null){
				
				if(vertice.hayIzquierdo()){cola.mete(vertice.getIzquierdo());
				}else{cola.mete(null);}
				if(vertice.hayDerecho()){cola.mete(vertice.getDerecho());
				}else{cola.mete(null);}
		     
			}else{
			    cola.mete(null);
			    cola.mete(null);
				
			}
			if(resultado.elementos>calculaNumero(arbol.elementos)-1){
				break;
				
			}
			
		}
		
		
	
		return resultado;
			
		

	
}
	private String uneLasLineas(Graficador<T>.Punto[] arreglo) {
		String resultado="";
		int longitud = arreglo.length;
		Punto punto;
		Punto punto1;
		
		for(int i = 0;i<longitud;i++){
			if(((2*i)+1)<longitud){
				if(arreglo[(2*i)+1].x!=-1){
					punto=arreglo[i];
					punto1=arreglo[(2*i)+1];
					resultado+=dibujaLinea(punto.x-5,punto.y+8,punto1.x,punto1.y-10);
				}
			}
			if(((2*i)+2)<longitud){
                if(arreglo[(2*i)+2].x!=-1){
                	punto=arreglo[i];
					punto1=arreglo[(2*i)+2];
					resultado+=dibujaLinea(punto.x+5,punto.y+8,punto1.x,punto1.y-10);
					
				}
				
			}
		}
		return resultado;
		
	}
	


	private Graficador<T>.Punto[] pasarDeListaArreglo(
			mx.unam.ciencias.edd.Lista<Graficador<T>.Punto> puntos2) {
		 @SuppressWarnings("unchecked")
		Punto[] arregloDePuntos = (Punto[]) Array.newInstance(new Punto(1,2).getClass(), puntos.getElementos());
		
		int i =0;
		 for(Punto puntw:puntos){
			 arregloDePuntos[i++]=puntw;
		 }
		return arregloDePuntos;
	}
	private String dibujaVerticeRojinegro(int i, int j, String string2, String string) {
		if(string.equals("Black")){
		return dibujaCirculoNegro(i,j)+salto+dibujaElemento(i,j+5,14,"white",string2) ;
		}else{
		return dibujaCirculoRojo(i,j)+salto+dibujaElemento(i,j+5,14,"white",string2) ;	
		}
	}

	
	private String dibujaArbolRojinegro(int nivel, Cola<VerticeRojinegro> bfs) {
		int i =nivel;
		  
	    String resultado="";
		while (i>=0){
		resultado+=graficaNivelRojiNegro(12*(calculaNumero(i)),12+(45*(nivel-i)),25*calculaNumero2(i),calculaElementosEnElUltimoNivel(nivel-i),bfs);
		i--;
		}
		
		return resultado;
		
	}

	private String graficaNivelRojiNegro(int x, int y, int separacion,
			int elementosNivelFinal, Cola<VerticeRojinegro> bfs) {
		String resultado="";
		x+=19;
		Lista<Punto> listaPunto = new Lista<Punto>();
	
		
		for(int i = 0;i<elementosNivelFinal;i++){
		VerticeRojinegro verticeArbolBinario =bfs.saca();
		if(verticeArbolBinario!=null){
        if(verticeArbolBinario.color==Color.NEGRO){
		resultado+=dibujaVerticeRojinegro(x,y,verticeArbolBinario.toString(),"Black");
        }else{
        resultado+=dibujaVerticeRojinegro(x,y,verticeArbolBinario.toString(),"Red");	
        	
        }
        listaPunto.agregaFinal(new Punto(x,y));
        puntos.agregaFinal(new Punto(x,y));
        
		}else{
		 listaPunto.agregaFinal(new Punto(-1,-1));
		 puntos.agregaFinal(new Punto(-1,-1));
		}
        x+=separacion;
        }
		
		
		lista.agrega(listaPunto);
		return resultado;
	
	
	}

	private Cola<VerticeRojinegro> BFS(
			mx.unam.ciencias.edd.ArbolRojinegro arbol,
			Cola<VerticeRojinegro> cola) {
		
		VerticeRojinegro vertice = (VerticeRojinegro) arbol.raiz();
		Cola<VerticeRojinegro> resultado=new Cola <VerticeRojinegro>();
		cola.mete(vertice);
		
		while(!cola.esVacia()){
			
			vertice=cola.saca();
			resultado.mete(vertice);
			
			if(vertice!=null){
				
				if(vertice.hayIzquierdo()){cola.mete((VerticeRojinegro) vertice.getIzquierdo());
				}else{cola.mete(null);}
				if(vertice.hayDerecho()){cola.mete((VerticeRojinegro) vertice.getDerecho());
				}else{cola.mete(null);}
		     
			}else{
			    cola.mete(null);
			    cola.mete(null);
				
			}
			if(resultado.elementos>calculaNumero(arbol.elementos)-1){
				break;
				
			}
			
		}
		
		
	
		return resultado;
			
		

		
	}

	
	
	


	public static void main(String []args) throws Exception{
		Graficador graficador;
		String nombreDeEstructura ="Grafica";
		   String direccion ="C:\\Users\\Antonio\\Desktop\\"+nombreDeEstructura+".html";
		   graficador =new Graficador(nombreDeEstructura);
			graficador.generadorDeColeccion("1 2 3 4 55 23 22","1 2 3 4 55 1");
		   System.out.println("Ya se genero tu "+nombreDeEstructura+" en "+direccion);
			//System.out.println(graficador.coleccion);
			generaArchivo(direccion,graficador.toString());
	
		
		}
	
	
  
}
