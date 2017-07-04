package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios ordenados. */
	private class Iterador implements Iterator<T> {

	    /* Pila para emular la pila de ejecucion. */
	    private Pila<ArbolBinario<T>.Vertice> pila;

	    /* Construye un iterador con el vertice recibido. */
	    public Iterador(ArbolBinario<T>.Vertice vertice) {
	      pila = new Pila<ArbolBinario<T>.Vertice>();
	      if(vertice!=null){
	    	  Vertice vertice1=vertice;
	    	  while(vertice1!=null){
	    		  pila.mete(vertice1);
	    		  vertice1=vertice1.izquierdo;
	    	  }
	       }
	    }

	    /* Nos dice si hay un siguiente elemento. */
	    @Override public boolean hasNext() {
			return !pila.esVacia();
	    }

	    /* Regresa el siguiente elemento del árbol en orden. */
	    @Override public T next() {
	    	if(pila.esVacia()){return null;
	    	}
	    	
	    	Vertice vertice = pila.saca();
	    	
	    	T elemento = vertice.elemento;
	    	vertice = vertice.derecho;
	    	while(vertice != null){
	    		pila.mete(vertice);
	    		vertice = vertice.izquierdo;
	    	}
	    	return elemento;
	    }

	    /* No lo implementamos: siempre lanza una excepcion. */
	    @Override public void remove() {
	        throw new UnsupportedOperationException();
	    }
	}

    /**
     * Constructor sin parámetros. Sencillamente ejecuta el constructor sin
     * parámetros de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de un árbol binario. El
     * árbol binario ordenado tiene los mismos elementos que el árbol recibido,
     * pero ordenados.
     * @param arbol el árbol binario a partir del cuál creamos el
     *        árbol binario ordenado.
     */
    public ArbolBinarioOrdenado(ArbolBinario<T> arbol) {
        // Aquí va su código.
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {

      if(this.raiz==null){
    	  this.raiz=nuevoVertice(elemento);
    	  ultimoAgregado=this.raiz;
    	 
      }else{
      Vertice auxiliar = raiz;
      while(true){
    	  if(elemento.compareTo(auxiliar.elemento)>=0){
    		  if(auxiliar.hayDerecho()){
    			  auxiliar=auxiliar.derecho;
    		  }else{
    		      auxiliar.derecho=nuevoVertice(elemento);
    			  ultimoAgregado=auxiliar.derecho;
    			  ultimoAgregado.padre=auxiliar;
    			  break;
    		  }
    	  }else{
    		  if(auxiliar.hayIzquierdo()){
    			  auxiliar=auxiliar.izquierdo;
    		  }else{
    			  auxiliar.izquierdo=nuevoVertice(elemento);
    			  ultimoAgregado=auxiliar.izquierdo;
    			  ultimoAgregado.padre=auxiliar;
    			  break;
    		  }
    	  }
        }
      }
      elementos++;
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
     private boolean tieneHijos(Vertice vertice){
    	 if(vertice.izquierdo==null&&vertice.derecho==null){
    		 return false;
    	 }
    	 return true;
         }
     
     private boolean tieneUnicoHijo(Vertice vertice){
    	 if(vertice.izquierdo==null&&vertice.derecho==null||vertice.izquierdo!=null&&vertice.derecho!=null){
    		 return false;
    	 }
    	 return true;
         }
     
     private boolean esIzquierdo(Vertice vertice){
    	 
    	 if(!vertice.hayPadre()){
    		 return false;
    	 }
    	 else if(vertice.padre.hayIzquierdo()){
    		if(vertice.padre.izquierdo==vertice){
    			return true;
    		}
    	
    	}
    	return false;
     }
    
    
     @Override public void elimina(T elemento) {
      if(raiz!=null&&this.contiene(elemento)){
    	  elementos--;
    	
    	  Vertice aEliminar=busca(raiz,elemento);
    	  
       if(!tieneHijos(aEliminar)){
    		  
    		  if(aEliminar.elemento.equals(raiz.elemento)){
    			  raiz=null;
    		  }
    		  else if(esIzquierdo(aEliminar)){
    			  aEliminar.padre.izquierdo=null;
    			  aEliminar.padre=null;
    		   }else{
    		   aEliminar.padre.derecho=null;
    		   aEliminar.padre=null;
    		   }
    	  }else if(tieneUnicoHijo(aEliminar)){
    		 
    		  if(aEliminar==raiz){
    			
    			  if(aEliminar.hayIzquierdo()){
    				 
    				  
    				  raiz=aEliminar.izquierdo;
    				  raiz.padre=null;
    				  
    				  aEliminar.padre=null;
    				  aEliminar.izquierdo=null;
    				  
    				 
    				 }else{
    			  
    			  
    			  raiz=aEliminar.derecho;
    			  raiz.padre=null;
    			  
    		     aEliminar.padre=null;
    			 aEliminar.derecho=null; 
    				 }

    		  }else{
    			  if(esIzquierdo(aEliminar)){
    			      if(aEliminar.hayIzquierdo()){
    				  aEliminar.izquierdo.padre=aEliminar.padre;
    				  aEliminar.padre.izquierdo=aEliminar.izquierdo;
    			      }else{
    			      aEliminar.derecho.padre=aEliminar.padre;
    			      aEliminar.padre.izquierdo=aEliminar.derecho;
    				  }
    			     
    				  
    			  }else{
    				  if(aEliminar.hayIzquierdo()){
        			  aEliminar.izquierdo.padre=aEliminar.padre;
        			  aEliminar.padre.derecho=aEliminar.izquierdo;
        			  }else{
        			  aEliminar.derecho.padre=aEliminar.padre;
        			  aEliminar.padre.derecho=aEliminar.derecho;
        			  }
    				  
    				  
    			  }
    			  
    			  
    		  }
    		   
    		   
    	   }else{
    		   Vertice sustituto=maximoEnSubarbol(aEliminar.izquierdo);
    		     aEliminar.elemento=sustituto.elemento;
    		     
    		     if(sustituto.hayIzquierdo()){
    		    	 
    		     }
    		     
    		    
    			   if(sustituto.padre.hayDerecho()){
    				   if(sustituto.padre.derecho.elemento.equals(sustituto.elemento)){
    					   if(sustituto.hayIzquierdo()){
    						   sustituto.padre.derecho=sustituto.izquierdo;
    						   sustituto.izquierdo.padre=sustituto.padre;
    						   
    					   }else{
    					   sustituto.padre.derecho=null;
    					   sustituto.padre=null;
    					   }
    				   }else{
    					   
    					   if(sustituto.hayIzquierdo()){
    						   sustituto.padre.izquierdo=sustituto.izquierdo;
    						   sustituto.izquierdo.padre=sustituto.padre;
    						   
    					   }else{
    						   sustituto.padre.izquierdo=null;
    						   sustituto.padre=null;
    					   }
    				   
    				   }
    			   }
    		   }
    		   
    		   
    	   }
      }
    

    /**
     * Nos dice si un elemento está contenido en el árbol.
     * @param elemento el elemento que queremos ver si está en el árbol.
     * @return <code>true</code> si el elemento está contenido en el árbol,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	if(busca(raiz,elemento)==null){
		return false;
	}
	
	return true;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
		return busca(raiz,elemento);
    }

    /**
     * Busca recursivamente un elemento, a partir del vértice recibido.
     * @param vertice el vértice a partir del cuál comenzar la búsqueda. Puede
     *                ser <code>null</code>.
     * @param elemento el elemento a buscar a partir del vértice.
     * @return el vértice que contiene el elemento a buscar, si se encuentra en
     *         el árbol; <code>null</code> en otro caso.
     */
    @Override protected Vertice busca(Vertice vertice, T elemento) {
		Vertice auxiliar=vertice;
		
		if(auxiliar==null){
			return null;
		}else if(auxiliar.elemento.equals(elemento)){
			return auxiliar;
	    }else if(auxiliar.elemento.compareTo(elemento)<0){
			return busca(vertice.derecho,elemento);
	    }else{
			return busca(vertice.izquierdo,elemento);
		}
		
    }

    /**
     * Regresa el vértice máximo en el subárbol cuya raíz es el vértice que
     * recibe.
     * @param vertice el vértice raíz del subárbol del que queremos encontrar el
     *                máximo.
     * @return el vértice máximo el subárbol cuya raíz es el vértice que recibe.
     */
    
	protected Vertice maximoEnSubarbol(Vertice maximo) {
		if(maximo==null){
			return null;
		}
    	
    	while(maximo.derecho!=null){
    		
    			maximo=maximo.derecho;
    		
    		
    		
    	}
		return maximo;
    	
    	
	
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador(raiz);
    }

    private void actualizaPadreCasoGiraDerecha(Vertice v) {
   	 if(v.padre==null){
          	 raiz=v.izquierdo;
          	 v.izquierdo.padre=null;
          	
          	 }else if(esIzquierdo(v)){
          	 v.padre.izquierdo=v.izquierdo;
          	 v.izquierdo.padre=v.padre;
          	 
          	 }else{
            v.padre.derecho=v.izquierdo;
            v.izquierdo.padre=v.padre;
            }
   
   }
   
   
    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
    	Vertice v = vertice(vertice);
        giraDerecha(v);
    }

    
     
     
    /* Gira el árbol a la derecha sobre el vértice recibido. */
    private void giraDerecha(Vertice v) {
    if(v.hayIzquierdo()){
     Vertice b=v.izquierdo.derecho;
   
    actualizaPadreCasoGiraDerecha(v);
    v.izquierdo.derecho=v;
    v.padre=v.izquierdo;;
       	
    v.izquierdo=b;
    if(b!=null){b.padre=v;}
     }
    
    
   }
          
           
    private void actualizaPadreCasoGiraIzquierda(Vertice vertice) {
    	 if(vertice.padre==null){
	       	 raiz=vertice.derecho;
	       	vertice.derecho.padre=null;
	       	
	       	 }else if(esIzquierdo(vertice)){
	       		vertice.padre.izquierdo=vertice.derecho;
	       		vertice.derecho.padre=vertice.padre;
	         }else{
	            //vertice es derecho
	        	 vertice.padre.derecho=vertice.derecho;
	        	 vertice.derecho.padre=vertice.padre;
	         }
      
      }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice v = vertice(vertice);
        giraIzquierda(v);
    }

    /* Gira el árbol a la izquierda sobre el vértice recibido. */
  
    private void giraIzquierda(Vertice v) {
    	  if(v.hayDerecho()){
        Vertice a=v.derecho.izquierdo; 
    	actualizaPadreCasoGiraIzquierda(v);
    	v.derecho.izquierdo=v;
    	v.padre=v.derecho;
    	v.derecho=a;
    	if(a!=null){a.padre=v;}
    	}
  }
    

    
    public static void main(String[]args){
    	
    	ArbolBinarioOrdenado<Integer> a= new ArbolBinarioOrdenado<Integer>();
    	
 
    	a.agrega(30);
    	a.agrega(20);
      	a.agrega(40);
    	a.agrega(10);
    	a.agrega(25);
      	a.agrega(35);
   



    	

   
      	
      	System.out.println(a);
    	
    }
    
 
   
}
