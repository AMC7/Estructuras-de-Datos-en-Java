package mx.unam.ciencias.edd;
import java.lang.Math;
import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para arboles binarios genericos.</p>
 *
 * <p>La clase proporciona las operaciones basicas para arboles binarios, pero
 * deja la implementacion de varias en manos de las clases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vertices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vertice. */
        public T elemento;
        /** El padre del vertice. */
        public Vertice padre;
        /** El izquierdo del vertice. */
        public Vertice izquierdo;
        /** El derecho del vertice. */
        public Vertice derecho;

        /**
         * Constructor unico que recibe un elemento.
         * @param elemento el elemento del vertice.
         */
        public Vertice(T elemento) {
           this.elemento=elemento;
        }

        /**
         * Regresa una representacion en cadena del vertice.
         * @return una representacion en cadena del vertice.
         */
        public String toString() {
           return this.elemento.toString();
        }

        /**
         * Nos dice si el vertice tiene un padre.
         * @return <tt>true</tt> si el vertice tiene padre,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayPadre() {
        return padre!=null?true:false;
        }

        /**
         * Nos dice si el vertice tiene un izquierdo.
         * @return <tt>true</tt> si el vertice tiene izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
        	  return izquierdo!=null?true:false;
        }

        /**
         * Nos dice si el vertice tiene un derecho.
         * @return <tt>true</tt> si el vertice tiene derecho,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayDerecho() {
        	  return derecho!=null?true:false;
        }

        /**
         * Regresa el padre del vertice.
         * @return el padre del vertice.
         * @throws NoSuchElementException si el vertice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> getPadre() {
			if(hayPadre()){
				return padre;
			}
         
			throw new NoSuchElementException();
        }

        /**
         * Regresa el izquierdo del vertice.
         * @return el izquierdo del vertice.
         * @throws NoSuchElementException si el vertice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> getIzquierdo() {
           if(hayIzquierdo()){
        	   return izquierdo;
           }
           throw new NoSuchElementException();
        }

        /**
         * Regresa el derecho del vertice.
         * @return el derecho del vertice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> getDerecho() {
          if(hayDerecho()){
        	  return derecho;
          }
          throw new NoSuchElementException();
        }

        /**
         * Regresa el elemento al que apunta el vertice.
         * @return el elemento al que apunta el vertice.
         */
        @Override public T get() {
			return elemento;
        
        }

        /**
         * Compara el vertice con otro objeto. 
         * La comparacion es <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el metodo {@link Vertice#equals}.
         * 
         * @param o el objeto con el cual se comparara el vertice.
         * 
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de este
         *         vertice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)o;
            
            Vertice vertice1=this;	
            
            if(this==null||vertice==null){
            	return false;
            }
            
            if(!vertice.elemento.equals(vertice1.elemento)){
            	return false;
            }else{
            
            if(hayIzquierdo()||hayDerecho()){
            
            	  if(vertice1.hayIzquierdo()&&!vertice.hayIzquierdo()||!vertice1.hayIzquierdo()&&vertice.hayIzquierdo()){
                  	return false;
                  }
                  
                  else if(vertice1.hayDerecho()&&!vertice.hayDerecho()||!vertice1.hayDerecho()&&vertice.hayDerecho()){
                  	return false;
                   }
            	  
                  else{
                	  if(hayIzquierdo()){
                		  vertice=vertice.izquierdo;
                		  vertice1=vertice.izquierdo;
                		  vertice.elemento.equals(vertice1.elemento);
                	  } 
                	  
                	  else if(hayDerecho()){
                		  vertice=vertice.derecho;
                		  vertice1=vertice.derecho;
                		  vertice.elemento.equals(vertice1.elemento);
                	  } 
                  }
              }
            
            
            
            	
            }
			return true;
        }

    }

    /** La raiz del arbol. */
    protected Vertice raiz;
    /** El numero de elementos */
    protected int elementos;
    /** El vertice del ultimo elemento agegado. */
    protected Vertice ultimoAgregado;

    /**
     * Construye un nuevo vertice, usando una instancia de {@link Vertice}. Para
     * crear vertices se debe utilizar este metodo en lugar del operador
     * <code>new</code>, para que las clases herederas de esta puedan
     * sobrecargarlo y permitir que cada estructura de arbol binario utilice
     * distintos tipos de vertices.
     * @param elemento el elemento dentro del vertice.
     * @return un nuevo vertice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
     return new Vertice(elemento);
    }

    /**
     * Regresa la profundidad del arbol. La profundidad de un arbol es la
     * longitud de la ruta mas larga entre la raiz y una hoja.
     * @return la profundidad del arbol.
     */
    private int profundidad(Vertice vertice){
  
    	if(vertice==null){return -1;}
    	else{
		return 1+Math.max(profundidad(vertice.izquierdo),profundidad(vertice.derecho));
		}
    }
    
    public int profundidad() {
    
    	return profundidad(raiz);	
	 }

    /**
     * Regresa el numero de elementos que se han agregado al árbol.
     * @return el numero de elementos en el árbol.
     */
    public int getElementos() {
		return elementos;
     }

    /**
     * Agrega un elemento al arbol.
     * @param elemento el elemento a agregar al arbol.
     */
    @Override public abstract void agrega(T elemento);

    /**
     * Regresa el vertice que contiene el ultimo elemento agregado al árbol.
     * @return el vertice que contiene el ultimo elemento agregado al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
		return ultimoAgregado;
        }

    /**
     * Elimina un elemento del árbol.
     * @param elemento el elemento a eliminar.
     */
    public abstract void elimina(T elemento);

    /**
     * Busca un elemento en el árbol. Si lo encuentra, regresa el vértice que lo
     * contiene; si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    
   
    
    
    public VerticeArbolBinario<T> busca(T elemento) {
        /* Busca recursivamente. */
        return busca(raiz, elemento);
    }

    /**
     * Busca recursivamente un elemento, a partir del vértice recibido.
     * @param vertice el vértice a partir del cuál comenzar la búsqueda. Puede
     *                ser <code>null</code>.
     * @param elemento el elemento a buscar a partir del vértice.
     * @return el vértice que contiene el elemento a buscar, si se encuentra en
     *         el árbol; <code>null</code> en otro caso.
     */
    protected Vertice busca(Vertice vertice, T elemento) {
    	   if(raiz==null){
    		   return null;
    	   }
   
    		Cola<ArbolBinario<T>.Vertice> cola = new Cola<ArbolBinario<T>.Vertice>();
    		cola.mete(vertice);
    		try{		
    	    while(!false){
    		    if(vertice==null){
    		    	return null;
    		    }
    	    	vertice=cola.saca();
    			if(vertice.elemento.equals(elemento)){
    		    return vertice;
    			}else{
    		    cola.mete(vertice.izquierdo);
    			cola.mete(vertice.derecho);
    			        
    			}
    		}
    		}catch(Exception e){
    			return null;
    		}
    	 }
    
              
    	
      
    

    /**
     * Regresa el vertice que contiene la raiz del arbol.
     * @return el vertice que contiene la raiz del arbol.
     * @throws NoSuchElementException si el arbol es vacio.
     */
    public VerticeArbolBinario<T> raiz() {
		
    	if(raiz != null){
    		return raiz;
    	}
    	
    	throw new NoSuchElementException();
        
    }

    /**
     * Compara el árbol con un objeto.
     * @param o el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") ArbolBinario<T> arbol = (ArbolBinario<T>)o;
        if (elementos != arbol.elementos)
            return false;
        if (raiz == null && arbol.raiz == null)
            return true;
        if (raiz == null)
            return false;
        return raiz.equals(arbol.raiz);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        if (elementos == 0)
            return "";
        /* Necesitamos la profundidad para saber cuántas ramas puede haber. */
        int p = profundidad() + 1;
        /* true == dibuja rama, false == dibuja espacio. */
        boolean[] rama = new boolean[p];
        for (int i = 0; i < p; i++)
            /* Al inicio, no dibujamos ninguna rama. */
            rama[i] = false;
        String s = aCadena(raiz, 0, rama);
        return s.substring(0, s.length()-1);
    }

    /* Método auxiliar recursivo que hace todo el trabajo. */
    private String aCadena(Vertice vertice, int nivel, boolean[] rama) {
        /* Primero que nada agregamos el vertice a la cadena. */
        String s = vertice + "\n";
        /* A partir de aquí, dibujamos rama en este nivel. */
        rama[nivel] = true;
        if (vertice.izquierdo != null && vertice.derecho != null) {
            /* Si hay vertice izquierdo Y derecho, dibujamos ramas o
             * espacios. */
            s += espacios(nivel, rama);
            /* Dibujamos el conector al hijo izquierdo. */
            s += "├─›";
            /* Recursivamente dibujamos el hijo izquierdo y sus
               descendientes. */
            s += aCadena(vertice.izquierdo, nivel+1, rama);
            /* Dibujamos ramas o espacios. */
            s += espacios(nivel, rama);
            /* Dibujamos el conector al hijo derecho. */
            s += "└─»";
            /* Como ya dibujamos el último hijo, ya no hay rama en este
               nivel. */
            rama[nivel] = false;
            /* Recursivamente dibujamos el hijo derecho y sus descendientes. */
            s += aCadena(vertice.derecho, nivel+1, rama);
        } else if (vertice.izquierdo != null) {
            /* Dibujamos ramas o espacios. */
            s += espacios(nivel, rama);
            /* Dibujamos el conector al hijo izquierdo. */
            s += "└─›";
            /* Como ya dibujamos el último hijo, ya no hay rama en este
               nivel. */
            rama[nivel] = false;
            /* Recursivamente dibujamos el hijo izquierdo y sus
               descendientes. */
            s += aCadena(vertice.izquierdo, nivel+1, rama);
        } else if (vertice.derecho != null) {
            /* Dibujamos ramas o espacios. */
            s += espacios(nivel, rama);
            /* Dibujamos el conector al hijo derecho. */
            s += "└─»";
            /* Como ya dibujamos el último hijo, ya no hay rama en este
               nivel. */
            rama[nivel] = false;
            /* Recursivamente dibujamos el hijo derecho y sus descendientes. */
            s += aCadena(vertice.derecho, nivel+1, rama);
        }
        return s;
    }

    /* Dibuja los espacios (incluidas las ramas, de ser necesarias) que van
       antes de un vértice. */
    private String espacios(int n, boolean[] rama) {
        String s = "";
        for (int i = 0; i < n; i++)
            if (rama[i])
                /* Rama: dibújala. */
                s += "│  ";
            else
                /* No rama: dibuja espacio. */
                s += "   ";
        return s;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        /* No necesitamos suprimir advertencias porque Vertice no es
         * genérica. */
        Vertice v = (Vertice)vertice;
        return v;
    }
}
