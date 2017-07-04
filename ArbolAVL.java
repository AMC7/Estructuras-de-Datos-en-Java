package mx.unam.ciencias.edd;

/**
 *p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho es al menos -1, y a lo más
 * 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {
	

    /**
     * Clase interna protegida para vértices de árboles AVL. La única diferencia
     * con los vértices de árbol binario, es que tienen una variable de clase
     * para la altura del vértice.
     */
    protected class VerticeAVL extends ArbolBinario<T>.Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
        	super(elemento);
        	altura=0;
        }
        public String toString() {
        	return this.elemento.toString()+" "+this.altura;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)o;
            return altura == vertice.altura && super.equals(o);
        }
    }

	

	/**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
    	return new VerticeAVL(elemento);
    }
    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * VerticeAVL}). Método auxililar para hacer esta audición en un único
     * lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice AVL.
     * @return el vértice recibido visto como vértice AVL.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeAVL}.
     */
    protected VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice) {
        VerticeAVL v = (VerticeAVL)vertice;
        return v;
    }
 
 
    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario. La complejidad en tiempo del método es <i>O</i>(log
     * <i>n</i>) garantizado.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
      super.agrega(elemento);
      VerticeAVL a = verticeAVL(this.ultimoAgregado); 
     if(a.hayPadre()){
      actualizaAlturas(getPadre(verticeAVL(ultimoAgregado)));
      rebalancea(getPadre(verticeAVL(ultimoAgregado)));
      }
      
    }

    

	/**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo. La
     * complejidad en tiempo del método es <i>O</i>(log <i>n</i>) garantizado.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        Vertice v = busca(raiz, elemento);
        if (v == null)
            return;
        elementos--;
        VerticeAVL p = elimina(v);
        actualizaAlturas(p);
        rebalancea(p);
    }


    private VerticeAVL elimina(Vertice vertice) {
        Vertice padre = null;
        Vertice anterior = maximoEnSubarbol(vertice.izquierdo);
        if (anterior == null) {
            padre = vertice.padre;
            if (vertice.padre == null) {
                raiz = vertice.derecho;
                if (vertice.derecho != null)
                    vertice.derecho.padre = null;
            } else {
                if (vertice.padre.izquierdo == vertice)
                    vertice.padre.izquierdo = vertice.derecho;
                else
                    vertice.padre.derecho = vertice.derecho;
                if (vertice.derecho != null)
                    vertice.derecho.padre = vertice.padre;
            }
        } else {
            padre = anterior.padre;
            vertice.elemento = anterior.elemento;
            if (anterior.padre.izquierdo == anterior)
                anterior.padre.izquierdo = anterior.izquierdo;
            else
                anterior.padre.derecho = anterior.izquierdo;
            if (anterior.izquierdo != null)
                anterior.izquierdo.padre = anterior.padre;
        }
        return padre != null ? verticeAVL(padre) : null;
    }




    
   
    
	/**
     * Regresa la altura del vértice AVL.
     * @param vertice el vértice del que queremos la altura.
     * @return la altura del vértice AVL.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeAVL}.
     */
    public int getAltura(VerticeArbolBinario<T> vertice) {
    	if(vertice==null){
   		 return -1;
   	     } 
    	VerticeAVL a = new VerticeAVL(null);
    	if(a.getClass() != vertice.getClass()){
    	throw new ClassCastException();
    	 }
    	 else{
		return verticeAVL(vertice).altura ;
    	 }
       
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada. Una vez hecho el giro, las
     * alturas de los vértices se recalculan. Este método no debe ser llamado
     * por los usuarios de la clase; puede desbalancear el árbol.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> v) {
    	 if(v.hayIzquierdo()){
    	VerticeAVL h=getIzquierdo(verticeAVL(v));
    	 super.giraDerecha(verticeAVL(v));
    	 actualizaAlturaIndividual(verticeAVL(v));
    	 
          actualizaAlturas(getPadre(verticeAVL(v)));
          
          actualizaAlturaIndividual(getDerecho(verticeAVL(h)));
    	  actualizaAlturaIndividual(getDerecho(verticeAVL(v)));
         }
      }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada. Una vez hecho el giro, las
     * alturas de los vértices se recalculan. Este método no debe ser llamado
     * por los usuarios de la clase; puede desbalancear el árbol.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
    	if(vertice.hayDerecho()){
    	VerticeAVL h=getDerecho(verticeAVL(vertice));
  	    super.giraIzquierda(vertice);
  	 
  	    actualizaAlturaIndividual(verticeAVL(vertice));
  	  
  	    actualizaAlturas(getPadre(verticeAVL(vertice)));
  	    
  	    actualizaAlturaIndividual(getIzquierdo(verticeAVL(vertice)));
     	actualizaAlturaIndividual(getIzquierdo(h));
  	    }
      }
    
    private void actualizaAlturaIndividual(Vertice v){
    	VerticeAVL vertice=verticeAVL(v);
    if(vertice!=null){
          vertice.altura=calculoAltura(vertice);
          }
    	
    }
    
    
   


    
    private void actualizaAlturas(ArbolAVL<T>.VerticeAVL v) {
    	if(v!=null){
    	ArbolAVL<T>.VerticeAVL a = v;
     while(a!=null){
    	a.altura=calculoAltura(a);
    	a=getPadre(a);
    }
    }
	}
    
	private int calculoAltura(ArbolAVL<T>.VerticeAVL v) {

		return Math.max(getAltura(getIzquierdo(v)),getAltura(getDerecho(v)))+1;
	}
	
	
	 



    
    

	    private void rebalancea(VerticeAVL vertice) {
	          if (vertice == null)
	              return;
	          int balance = getAltura(getIzquierdo(verticeAVL(vertice))) - getAltura(getDerecho(verticeAVL(vertice)));
	          if (balance == 0) {
	              rebalancea(getPadre(verticeAVL(vertice)));
	              return;
	          } else if (balance == -2) {
	              VerticeAVL d = getDerecho(vertice);
	              if (getAltura(getIzquierdo(d)) - getAltura(getDerecho(d)) == 1)
	                  giraDerecha(d);
	              giraIzquierda(vertice);
	          } else if (balance == 2) {
	              VerticeAVL i = getIzquierdo(verticeAVL(vertice));
	              if (getAltura(getIzquierdo(i)) - getAltura(getDerecho(i)) == -1)
	                  giraIzquierda(i);
	              giraDerecha(vertice);
	          }
	          rebalancea(getPadre(vertice));
	      }

    private ArbolAVL<T>.VerticeAVL getPadre(ArbolAVL<T>.VerticeAVL v) {
    	return verticeAVL(v.padre);
	}

	private ArbolAVL<T>.VerticeAVL getDerecho(ArbolAVL<T>.VerticeAVL v) {
	return verticeAVL(v.derecho);
}

	private ArbolAVL<T>.VerticeAVL getIzquierdo(ArbolAVL<T>.VerticeAVL v) {
	return verticeAVL(v.izquierdo);
}
	 
	
	
}
