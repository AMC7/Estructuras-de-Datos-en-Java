package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 *  Podemos crear un montículo mínimo con <em>n</em> elementos 
 *  en tiempo <em>O</em>(<em>n</em>), y podemos
 * agregar y actualizar elementos en tiempo <em>O</em>(log <em>n</em>). Eliminar
 * el elemento mínimo también nos toma tiempo <em>O</em>(log <em>n</em>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T> {

    /* Clase privada para iteradores de montículos. */
    private class Iterador<T extends ComparableIndexable<T>>
        implements Iterator<T> {

        /* indice del iterador. */
        private int indice;
        /* El montículo mínimo. */
        private MonticuloMinimo<T> monticulo;

        /* Construye un nuevo iterador, auxiliándose del montículo mínimo. */
        public Iterador(MonticuloMinimo<T> monticulo) {
        	this.monticulo=monticulo;
        	indice=0;
            // Aquí va su código.
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
        	return indice!=siguiente;
        	
            // Aquí va su código.
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
        	if(hasNext()){
			return monticulo.get(indice++);
			 }
		    throw new NoSuchElementException();
		   }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* El siguiente índice dónde agregar un elemento. */
    private int siguiente;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] creaArregloGenerico(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Lista)}, pero se ofrece este constructor por completez.
     */

	public MonticuloMinimo() {
    	siguiente=0;
    	T[] a =creaArregloGenerico(1);
    	this.arbol= a;
       
    }

    /**
     * Constructor para montículo mínimo que recibe una lista. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param lista la lista a partir de la cuál queremos construir el
     *              montículo.
     */
    public  MonticuloMinimo(Lista<T> lista) {
    	T [] arreglo=creaArregloGenerico(lista.getElementos());
    	this.arbol=arreglo;
        for(T a:lista){
          this.agrega(a);
        }
      }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    
    @Override public void agrega(T elemento) {
    	
   	if(siguiente>=this.arbol.length){
    		
    	    T[] arreglo=creaArregloGenerico(siguiente*2);
    		int i=0;
    		for(T vertice:this.arbol){
    		  arreglo[i]=vertice;
    		  i++;
    		 }
    		this.arbol=arreglo;
    	}
    	
    	elemento.setIndice(siguiente);
    	this.arbol[siguiente]=elemento;
    	
    	
    		siguiente++;
    		antiMinHeapify(siguiente-1);
    	
       
       
    }
    
   

    private void minHeapify(int i) {
     	
    	 int izq=(2*i)+1;
    	 int der=(2*i)+2;
    	 
    	 int min=i;
    	 
    	 if(izq<siguiente){
    		 if(arbol[min].compareTo(arbol[izq])>0){
    		 min=izq;
    		 }
    	 }
    	 if(der<siguiente){
    		 if(arbol[min].compareTo(arbol[der])>0){
    		 min=der;
    	 }
    	 }
    	 	
    	 	if(min!=i){
    	 		intercambiar(min,i);
    	 		minHeapify(min);
    	 	}
    			
    			
    		}
    
 private void antiMinHeapify(int i) {
	 if(i==0){
		 return;
	 }
	 T padre=this.arbol[getPadre(i)];
	 T actual =this.arbol[i];
	 
	 if(padre.compareTo(actual)>0){
		intercambiar(getPadre(i),i);
		 
	 }else{
		 return;
	 }
	 
	 this.antiMinHeapify(getPadre(i));
    	
    
		
		
	}
	
 private void intercambiar(int i, int j) {
		T t = arbol[i];
		arbol[i]=arbol[j];
		arbol[j]=t;
		
		arbol[i].setIndice(i);
		arbol[j].setIndice(j);
 }

	

	private int getPadre(int indiceDelVertice) {
	  return (indiceDelVertice-1)/2;
		
	}

	/**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    public T elimina() {
    	
    	if(getElementos()!=0){
    	T minimo=get(0);
       	intercambiar(0,siguiente-1);
		arbol[siguiente-1]=null;
	    siguiente--;
		minHeapify(0);
		minimo.setIndice(-1);
		return minimo;
    	}
    	else{
    	 throw new IllegalStateException();
    	}
    	
    }
    

	
	
	
	/**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
	  @Override public void elimina(T elemento) {
	        int indice=elemento.getIndice();
	        
	        if(indice<0||indice>=siguiente||!arbol[indice].equals(elemento)){
	        	return ;
	        }
	        
	        while(elemento.getIndice()<siguiente){
	        	int izq=2*elemento.getIndice()+1;
	        	int der=2*elemento.getIndice()+2;
	        	
	        	if(izq<siguiente&&der<siguiente){
	        		
	        		if(arbol[izq].compareTo(arbol[der])<0){
	        			intercambiar(elemento.getIndice(),izq);
	            		
	            	}else{
	        		intercambiar(elemento.getIndice(),der);
	            	}
	        	}
	        	else if(izq<siguiente){
	        		intercambiar(elemento.getIndice(),izq);
	        		
	        	}else if(der<siguiente){
	        		intercambiar(elemento.getIndice(),der);
	        		indice=der;
	        	}else
	        		break;
	        	
	        	}
	        	
	        	
	        	indice=elemento.getIndice();
	        	int ultimo=siguiente-1;
	        	
	        	if(indice!=ultimo){
	        	    intercambiar(indice,ultimo);
	        		reordena(arbol[indice]);
	        	}
	        	
	        	arbol[ultimo]=null;
	        	siguiente--;
	        	elemento.setIndice(-1);
	        	
	        	
	        }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
		if(esMenor(elemento,0)&&esMayor(elemento,siguiente-1)){
			return false;
		}else{
			for(int i =0;i<siguiente;i++){
				if(sonIguales(elemento,i)){
					return true;
					
				}
				
			}
		}
		return false;
    }

    private boolean sonIguales(T elemento, int i) {
    return elemento.equals(this.arbol[i]);
	}

	private boolean esMenor(T elemento, int indice) {
    	if(elemento.compareTo(this.arbol[indice])<=0){
			return true;
		}
		return false;
	}
    
    private boolean esMayor(T elemento, int indice) {
    	if(elemento.compareTo(this.arbol[indice])>=0){
			return true;
		}
		return false;
	}

	/**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    public boolean esVacio() {
		return siguiente==0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    public void reordena(T elemento) {
    	
    	antiMinHeapify(elemento.getIndice());
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
		// Aquí va su código.
		return siguiente;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    public T get(int i) {
    	
    	if(i<0||i>=arbol.length){
    		throw new NoSuchElementException();
    		
    	}if(this.arbol[i]==null){
    		throw new NoSuchElementException();
    	}
    	else return this.arbol[i];
    		
    	
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador<T>(this);
    }
    
 
    
   

  
    
   
}
