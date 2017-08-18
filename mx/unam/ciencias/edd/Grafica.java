package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase privada para iteradores de gráficas. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Grafica<T>.Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose del diccionario de
         * vértices. */
        public Iterador(Grafica<T> grafica) {
        	 iterador=grafica.vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
        	return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
        	 return iterador.next().elemento;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* Aristas para gráficas; para poder guardar el peso de las aristas. */
    private class Arista {

        /* El vecino del vértice. */
        public Grafica<T>.Vertice vecino;
        /* El peso de arista conectando al vértice con el vecino. */
        public double peso;

        /* Construye una nueva arista con el vértice recibido como vecino y el
         * peso especificado. */
        public Arista(Grafica<T>.Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }
    }

    /* Vertices para gráficas; implementan la interfaz ComparableIndexable y
     * VerticeGrafica */
    private class Vertice implements ComparableIndexable<Vertice>,
        VerticeGrafica<T> {

        /* Iterador para las vecinos del vértice. */
        private class IteradorVecinos implements Iterator<VerticeGrafica<T>> {

            /* Iterador auxiliar. */
            private Iterator<Grafica<T>.Arista> iterador;

            /* Construye un nuevo iterador, auxiliándose del diccionario de
             * aristas. */
            public IteradorVecinos(Iterator<Grafica<T>.Arista> iterador) {
            	this.iterador=iterador;
            }

            /* Nos dice si hay un siguiente vecino. */
            @Override public boolean hasNext() {
            	return iterador.hasNext();
            }

            /* Regresa el siguiente vecino. */
            @Override public VerticeGrafica<T> next() {
            	return iterador.next().vecino;
            }

            /* No lo implementamos: siempre lanza una excepción. */
            @Override public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* El diccionario de aristas que conectan al vértice con sus vecinos. */
        public Diccionario<Grafica<T>.Vertice, Grafica<T>.Arista> aristas;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
        	 this.elemento=elemento;
             aristas=new Diccionario<Grafica<T>.Vertice, Grafica<T>.Arista>();
             color=color.NINGUNO;
        }

        /* Regresa el elemento del vértice. */
        @Override public T getElemento() {
        	return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
        	return aristas.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
        	return color;
        }

        /* Define el color del vértice. */
        @Override public void setColor(Color color) {
        	  this.color=color;
        }

        /* Regresa un iterador para los vecinos. */
        @Override public Iterator<VerticeGrafica<T>> iterator() {
            return new IteradorVecinos(aristas.iterator());
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
        	 this.indice=indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
        	 return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override
		public int compareTo(Grafica<T>.Vertice o) {
			if(this.distancia>o.distancia){
				return -1;
			}
			if(this.distancia<o.distancia){
				return 1;
			}
			return 0;
		}
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
    	aristas=0;
    	vertices=new Diccionario<T, Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
    	return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
    	return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
    	
        if(vertices.contiene(elemento)){
        throw new IllegalArgumentException();
        }else{
        vertices.agrega(elemento, new Vertice(elemento));
        }
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
    	Vertice uno=getVertice(a);
    	Vertice dos=getVertice(b);
    
    	if(!vertices.contiene(a)||!vertices.contiene(b)){
    		throw new NoSuchElementException();
    	}if(estanConectados(uno,dos)||uno==dos){
    		throw new IllegalArgumentException();
    	}else{
    		
    		Arista arista1=new Arista(uno, 1);
    		Arista arista2=new Arista(dos, 1);
    		
    		uno.aristas.agrega(dos,arista2);
    		dos.aristas.agrega(uno,arista1);
    		aristas++;
    		
    	}
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva arista.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b, double peso) {
    	Vertice uno=getVertice(a);
    	Vertice dos=getVertice(b);
    
    	if(!vertices.contiene(a)||!vertices.contiene(b)){
    		throw new NoSuchElementException();
    	}if(estanConectados(uno,dos)||uno==dos){
    		throw new IllegalArgumentException();
    	}else{
    		
    		Arista arista1=new Arista(uno, peso);
    		Arista arista2=new Arista(dos, peso);
    		
    		uno.aristas.agrega(dos,arista2);
    		dos.aristas.agrega(uno,arista1);
    		aristas++;
    		
    	}
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
     	Vertice uno=getVertice(a);
    	Vertice dos=getVertice(b);
    	if(!vertices.contiene(a)||!vertices.contiene(b)){
    		throw new NoSuchElementException();
    	}if(!estanConectados(uno,dos)||uno==dos){
    		throw new IllegalArgumentException();
    	}else{
    		Arista arista1 =encuentra(uno,dos.aristas);
    		Arista arista2 =encuentra(dos,uno.aristas);
    		
    		uno.aristas.elimina(arista2.vecino);
    		dos.aristas.elimina(arista1.vecino);
    		aristas--;
    	}
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
    	if(vertices.contiene(elemento)){
    		return true;
    		
    	}
    	return false;
    }

   
    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
      	Vertice aEliminar=getVertice(elemento);
        if(vertices.contiene(elemento)){
    	vertices.elimina(elemento);
    	  for (Vertice s : vertices)
    		  if(s.aristas.contiene(aEliminar)){
    			 
    	        s.aristas.elimina(aEliminar);
    	  		aristas--;
    		  }
        }
       
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
    	Vertice primerVertice =getVertice(a);
    	Vertice segundoVertice =getVertice(b);
    	if(!vertices.contiene(a)||!vertices.contiene(b)){
    		throw new NoSuchElementException();
    	}
		if(encuentra(segundoVertice,primerVertice.aristas)!=null&&
			encuentra(primerVertice,segundoVertice.aristas)!=null){
			return true;
		}else{
		
		    
		return false;
		}
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos, o -1 si los elementos no están
     *         conectados.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public double getPeso(T a, T b) {
    	Vertice vertice1 =getVertice(a);
		Vertice vertice2 =getVertice(b);
		
		return encuentra(vertice2,vertice1.aristas).peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
		return vertices.get(elemento);
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
    	 for(Vertice v:vertices){
        	 accion.actua(v);
         }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
    	  if(!vertices.contiene(elemento)){
        	  throw new NoSuchElementException();
          }else{
        	  Vertice vertice =getVertice(elemento);
        	  Cola<Vertice> cola=new Cola<Vertice>();
        	  recorre(accion,cola,vertice);
          }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
   	 if(!vertices.contiene(elemento)){
      	  throw new NoSuchElementException();
        }else{
         
      	  Vertice vertice =getVertice(elemento);
      	  Pila<Vertice> pila=new Pila<Vertice>();
      	 recorre(accion,pila,vertice);
      	  
        }
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
    	  return new Iterador(this);  // Aquí va su código.
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {

    	Lista<VerticeGrafica<T>> trayectoria;
    	
    	
    	if(getVertice(origen)==null||getVertice(destino)==null){
    		throw new NoSuchElementException();
    	}else{
    	
    	
    	trayectoria=new Lista<VerticeGrafica<T>>();
    	Cola <Vertice> cola=new Cola<Vertice>();
    	
    	
    	for(Vertice v:vertices){v.distancia=-1;}
    	Vertice inicial =getVertice(origen);
    	inicial.distancia=0;
    	
    	for(Vertice v:vertices){
    	cola.mete(v);
    	}
    	while(!cola.esVacia()){
    		Vertice a =cola.saca();
    		
    		for(Arista arista :a.aristas){
    			if(arista.vecino.distancia==-1){
    			arista.vecino.distancia=a.distancia+1;}
    		}
    	}
    	
    	Vertice terminal =getVertice(destino);
    	Vertice actual =terminal;
    	trayectoria.agregaInicio(actual);
    	Vertice a =getVertice(origen);
    	
    	while(actual.distancia>0){
    		
    		for(Arista arista :actual.aristas){
    			if(arista.vecino.distancia==actual.distancia-1){
    				actual=arista.vecino;
    				trayectoria.agregaInicio(actual);
    				break;
    		  }
           }
    	}
      }
		return trayectoria;
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
Lista<VerticeGrafica<T>> trayectoria;
    	
    	
    	if(getVertice(origen)==null||getVertice(destino)==null){
    		throw new NoSuchElementException();
    	}else{
    	
    	
    	trayectoria=new Lista<VerticeGrafica<T>>();
       
    	for(Vertice v:vertices){v.distancia=-1;}
    	Vertice inicial =getVertice(origen);
    	inicial.distancia=0;
    	 MonticuloMinimo <Vertice> monti=new MonticuloMinimo<Vertice>(vertices.valores());
    	
    	while(!monti.esVacio()){
    		Vertice a =monti.elimina();
    		
    		for(Arista arista :a.aristas){
    			if(arista.vecino.distancia==-1){
    			monti.elimina(arista.vecino);
    			arista.vecino.distancia=a.distancia+arista.peso;
    			monti.agrega(arista.vecino);
    			}
    			else if(arista.vecino.distancia>a.distancia+arista.peso){
    			monti.elimina(arista.vecino);
        		arista.vecino.distancia=a.distancia+arista.peso;
        		monti.agrega(arista.vecino);
        		}
    		}
    		
    	}
    	
    	Vertice terminal =getVertice(destino);
    	Vertice actual =terminal;
    	trayectoria.agregaInicio(actual);
    	Vertice a =getVertice(origen);
    	
    	while(actual.distancia>0){
    		
    		for(Arista arista :actual.aristas){
    			if(arista.vecino.distancia==actual.distancia-arista.peso){
    				actual=arista.vecino;
    				trayectoria.agregaInicio(actual);
    				break;
    		  }
           }
    	}
      }
		return trayectoria;
    }
    
    private boolean estanConectados(Grafica<T>.Vertice uno,
			Grafica<T>.Vertice dos) {
    	if(esVecino(uno,dos)&&esVecino(dos,uno)){
    		
    		return true;
    	}
		return false;
	
    }
    
    /**Nos dice si dos es vecino de uno*/
    
    private boolean esVecino(Grafica<T>.Vertice uno, Grafica<T>.Vertice dos) {
		for(Arista a :uno.aristas){
			if(a.vecino==dos){
				return true;
			}
			
		}
		return false;
	}

	private Vertice getVertice(T elemento) {
		for(Vertice vertice:vertices){
  			if(vertice.elemento.equals(elemento)){
  				return vertice;
  			}
  		}
  		
  		throw new NoSuchElementException();
  	
      }

 
    
	private Grafica<T>.Arista encuentra(Grafica<T>.Vertice dos,
			Diccionario<Grafica<T>.Vertice, Grafica<T>.Arista> aristas2) {
		for(Arista a :aristas2){
			if(a.vecino==dos){
				return a ;
			}
			
		}
		return null;
	}
	
	  private void recorre(AccionVerticeGrafica<T>  accion,
	            MeteSaca<Vertice>        meteSaca,
	            Vertice                  vertice) {
	if (vertices.getElementos() == 0)
	return;
	for (Vertice n : vertices)
	n.setColor(Color.ROJO);
	vertice.color = Color.NEGRO;
	meteSaca.mete(vertice);
	while (!meteSaca.esVacia()) {
	vertice = meteSaca.saca();
	accion.actua(vertice);
	for ( Arista arista:vertice.aristas) {
	   if (arista.vecino.color == Color.ROJO) {
	       arista.vecino.color = Color.NEGRO;
	       meteSaca.mete(arista.vecino);
	   }
	}
	}
	for (Vertice nn : vertices)
	nn.setColor(Color.NINGUNO);
	}
}
