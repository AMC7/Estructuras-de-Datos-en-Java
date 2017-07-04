package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase para arboles binarios completos.
 * </p>
 *
 * <p>
 * Un arbol binario completo agrega y elimina elementos de tal forma que el
 * arbol siempre es lo mas cercano posible a estar lleno.
 * </p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

	/* Clase privada para iteradores de arboles binarios completos. */
	private class Iterador implements Iterator<T> {

		private Cola<ArbolBinario<T>.Vertice> cola;

		/* Constructor que recibe la raiz del arbol. */
		public Iterador(ArbolBinario<T>.Vertice raiz) {
			cola = new Cola<ArbolBinario<T>.Vertice>();
			
			if (raiz != null) {
				cola.mete(raiz);
			}
		}

		/* Nos dice si hay un elemento siguiente. */
		@Override
		public boolean hasNext() {
		return !cola.esVacia();
        }

		/* Regresa el elemento siguiente. */
		@Override
		public T next() {
			
			if (cola.esVacia()) {
			throw new NoSuchElementException();
			}else{
				ArbolBinario<T>.Vertice vertice=cola.saca();
				if(vertice.hayIzquierdo()){
					cola.mete(vertice.izquierdo);
				}if(vertice.hayDerecho()){
					cola.mete(vertice.derecho);
				}
				
				return vertice.elemento;
			}
			
		}

		/* No lo implementamos: siempre lanza una excepcion. */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Agrega un elemento al árbol binario completo. El nuevo elemento se
	 * coloca a la derecha del ultimo nivel, o a la izquierda de un nuevo nivel.
	 * 
	 * @param elemento
	 *            el elemento a agregar al árbol.
	 */
	

	

	@Override
	public void agrega(T elemento) {
		//Si la raiz es null
	    if(this.raiz==null){
		raiz=nuevoVertice(elemento);
		ultimoAgregado=raiz;
	    elementos++;
		
		}else{
			Cola<ArbolBinario<T>.Vertice> cola = new Cola<ArbolBinario<T>.Vertice>();
			Vertice vertice =this.raiz; 
			cola.mete(vertice);
			
	      while(!false){
				vertice=cola.saca();
				
		  if(!vertice.hayIzquierdo()){
			vertice.izquierdo=nuevoVertice(elemento);
			vertice.izquierdo.padre=vertice;
		    ultimoAgregado=vertice.izquierdo;
		    elementos++;
		    return;
		  }
			
		  else if(!vertice.hayDerecho()){
			vertice.derecho=nuevoVertice(elemento);
			vertice.derecho.padre=vertice;
			ultimoAgregado=vertice.derecho;
			elementos++;
			return;
				
		  }else{
			cola.mete(vertice.izquierdo);
		    cola.mete(vertice.derecho);
		    }
		}
	  }
	}
		
	private boolean esIzquierdo(Vertice vertice){
	
		 if(vertice.padre.derecho!=null){
			if(vertice.padre.derecho.elemento.equals(vertice.elemento)){
				return false;
			}
			}
		return true;
		  }
	


	/**
	 * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
	 * el último elemento del árbol al recorrerlo por BFS, y entonces es
	 * eliminado.
	 * 
	 * @param element el elemento a eliminar.
	 */
	@Override
	public void elimina(T elemento) {
		
	  if(this.contiene(elemento)&&this.raiz!=null){
		
          if(this.raiz.izquierdo==null&&this.raiz.derecho==null){
			raiz=null;
		   elementos--;
		  }else {
		  //Si tiene mas de un elemento
		  Vertice aEliminar= busca(raiz,elemento);
	      Vertice sustituto = this.sustituto();
	     
	      elementos--;
		  aEliminar.elemento=sustituto.elemento;
				
		  if(esIzquierdo(sustituto)){
		  sustituto.padre.izquierdo=null;
		  }else{
		  sustituto.padre.derecho=null;
		 }
		}
		}
		}	
		
		
		
	

	private ArbolBinario<T>.Vertice sustituto() {;
		Vertice vertice = null;
		if(raiz!=null){
		vertice=raiz;
	    Cola<ArbolBinario<T>.Vertice> cola = new Cola<ArbolBinario<T>.Vertice>();
    	cola.mete(vertice);
    		
    	while(!cola.esVacia()){
    		vertice=cola.saca();
    		
    		if(vertice.hayIzquierdo()){cola.mete(vertice.izquierdo);}
    		if(vertice.hayDerecho()){cola.mete(vertice.derecho);}
    	
    	}
	  }
		
		return vertice;
    }

	/**
	 * Nos dice si un elemento está en el árbol binario completo.
	 * 
	 * @param elemento
	 *            el elemento que queremos comprobar si está en el árbol.
	 * @return <code>true</code> si el elemento está en el árbol;
	 *         <code>false</code> en otro caso.
	 */
	@Override
	public boolean contiene(T elemento) {
		if(this.raiz==null){
			return false;
		}
		else if(this.busca(elemento)==null){
			return false;
		}else{
			return true;
		}
		
	}

	/**
	 * Regresa un iterador para iterar el árbol. El árbol se itera en orden
	 * BFS.
	 * 
	 * @return un iterador para iterar el árbol.
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterador(raiz);
	}
	


	

}
