package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, permitiendo (en general, dependiendo de qué tan bueno
 * sea su método para generar huellas digitales) agregar, eliminar, y buscar
 * valores en tiempo <i>O</i>(1) (amortizado) en cada uno de estos casos.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Clase privada para iteradores de diccionarios. */
    private class Iterador implements Iterator<V> {

        /* En qué lista estamos. */
        private int indice;
        /* Diccionario. */
        private Diccionario<K,V> diccionario;
        /* Iterador auxiliar. */
        private Iterator<Diccionario<K,V>.Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador(Diccionario<K,V> dicc) {
            this.diccionario=dicc;
            while(indice<diccionario.entradas.length&&diccionario.entradas[indice]==null){
          	  indice++;
            }
            if(indice<diccionario.entradas.length){
          		  iterador=diccionario.entradas[indice].iterator();
            }
            

          		  
          }

        /* Nos dice si hay un siguiente elemento. */
        public boolean hasNext() {
        	if(iterador==null){
        		return false;
        	}if(iterador.hasNext()){
        		return true;
        	}indice++;
        	while(indice<diccionario.entradas.length&&diccionario.entradas[indice]==null) {
        		indice++;
        	}
        	    if(indice<diccionario.entradas.length){
          	  iterador=diccionario.entradas[indice].iteradorLista();
        	    }else{
        	    	return false;
        	    }
		return iterador!=null;
        }

        /* Regresa el siguiente elemento. */
        public V next() {
        	if(iterador==null){
        	throw new NoSuchElementException();
        	}
        	if(iterador.hasNext()){
        		return iterador.next().valor;
        	}
			return null;
        	
        }

        /* No lo implementamos: siempre lanza una excepción. */
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

    /* Tamaño mínimo; decidido arbitrariamente a 2^6. */
    private static final int MIN_N = 64;
   /* Máscara para no usar módulo. */
    private int mascara;
    /* Huella digital. */
    private HuellaDigital<K> huella;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores*/
    private int elementos;

    /* Clase para las entradas del diccionario. */
    private class Entrada {
        public K llave;
        public V valor;
        public Entrada(K llave, V valor) {
            this.llave = llave;
            this.valor = valor;
        }
    }

  
    /**
     * Construye un diccionario con un tamaño inicial y huella digital
     * predeterminados.
     */
    public Diccionario() {
    
    	this.mascara=calculaMascara(MIN_N);
    	this.entradas= nuevoArreglo(this.mascara+1);
    	this.huella=(s)->{return s.hashCode();};
    	
    
    }

    /**
     * Construye un diccionario con un tamaño inicial definido por el usuario, y
     * una huella digital predeterminada.
     * @param tam el tamaño a utilizar.
     */
    public Diccionario(int tam) {
    	this.mascara=calculaMascara(tam);
    	this.entradas= nuevoArreglo(this.mascara+1);
    	this.huella=(s)->{return s.hashCode();};
    }

    /**
     * Construye un diccionario con un tamaño inicial predeterminado, y una
     * huella digital definida por el usuario.
     * @param huella la huella digital a utilizar.
     */
    public Diccionario(HuellaDigital<K> huella) {
    	this.mascara=calculaMascara(MIN_N);;
    	this.entradas= nuevoArreglo(this.mascara+1);
    	this.huella=huella;
    	
    	
    }

    /**
     * Construye un diccionario con un tamaño inicial, y un método de huella
     * digital definidos por el usuario.
     * @param tam el tamaño del diccionario.
     * @param huella la huella digital a utilizar.
     */
    public Diccionario(int tam, HuellaDigital<K> huella) {
    	this.mascara=calculaMascara(tam);
    	this.entradas= nuevoArreglo(this.mascara+1);
    	this.huella=huella;
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     */
    public void agrega(K llave, V valor) {
       int indice = indice(llave);
       Lista <Entrada> lista =getLista(indice,true);
       Entrada entrada=buscaEntrada(llave,lista);
       if(entrada!=null){
    	   entrada.valor=valor;
       }else{
    	   entrada=new Entrada(llave,valor);
    	   lista.agregaFinal(entrada);
    	   elementos++;
       }
     
       if(carga()>MAXIMA_CARGA){
      creceArreglo();
       }
      }

  

	/**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
    	  Lista<Entrada> lista =this.entradas[indice(llave)];
    	    if(lista!=null){
    	   for(Entrada entrada:lista){
    		   if(entrada.llave.equals(llave)){
    			   return entrada.valor;
    		   }
    	   }
    	   }
    	throw new NoSuchElementException();
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <tt>true</tt> si la llave está en el diccionario,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(K llave) {
       Lista<Entrada> lista =this.entradas[indice(llave)];
  	   if(lista!=null){
  	   for(Entrada entrada:lista){
  		   if(entrada.llave.equals(llave)){
  			   return true;
  		   }
  	   }
  	   }
  	return false;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
    	  int indice = indice(llave);
          Lista <Entrada> lista =getLista(indice,false);
          if(lista==null){
        	  throw new NoSuchElementException();
          }
          Entrada entrada=buscaEntrada(llave,lista);
          if(entrada!=null){
       	   lista.elimina(entrada);
       	   if(lista.getLongitud()==0){
       	   entradas[indice]=null;
       	   }
       	   elementos--;
       	   }else{
       	   throw new NoSuchElementException();
          }
    }

    /**
     * Regresa una lista con todas las llaves con valores asociados en el
     * diccionario. La lista no tiene ningún tipo de orden.
     * @return una lista con todas las llaves.
     */
    public Lista<K> llaves() {
		Lista<K> listaDeLlaves =new Lista<K>();
		for(Lista<Entrada> listaDeEntradas : this.entradas){
			if(listaDeEntradas!=null){
			for(Entrada entrada:listaDeEntradas){
				listaDeLlaves.agrega(entrada.llave);
			}
			}
		}
		return listaDeLlaves;
    }

    /**
     * Regresa una lista con todos los valores en el diccionario. La lista no
     * tiene ningún tipo de orden.
     * @return una lista con todos los valores.
     */
    public Lista<V> valores() {
    	Lista<V> listaDeValores =new Lista<V>();
		for(Lista<Entrada> listaDeEntradas : this.entradas){
			if(listaDeEntradas!=null){
			for(Entrada entrada:listaDeEntradas){
			listaDeValores.agrega(entrada.valor);}
			}
		}
		return listaDeValores;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
    	int i=0;
	for(Lista<Entrada> lista:this.entradas){
		if(lista!=null){
			if(lista.getLongitud()>i){
				i=lista.getLongitud()-1;
			}
		}
	}
	return i;
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
    	
    	int colisiones =0;
		for(Lista<Entrada> listaDeEntradas : this.entradas){
			if(listaDeEntradas!=null){
			colisiones+=(listaDeEntradas.getLongitud()-1);
			}
		}
		
		return colisiones;
		
		
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
    double elem = (double) elementos;
    double tam = (double) this.entradas.length; 
    return elem/tam;
       
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
		return elementos;
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar el diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new Iterador(this);
    }
    
    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
    Java implementa sus genéricos; de otra forma obtenemos advertencias del
    compilador. */
 @SuppressWarnings("unchecked") private Lista<Entrada>[] nuevoArreglo(int n) {
     Lista[] arreglo = new Lista[n];
     return (Lista<Entrada>[])arreglo;
 }

    
    private int calculaMascara(int n ){
    	int i =1;
    	while((i*=2)<n);
    	return (i*2)-1;
		
    	}
  private void creceArreglo() {
    	
    	Diccionario<K,V> nuevo=new Diccionario<K,V>(this.entradas.length*2,this.huella);
		for(Lista<Entrada> listaDeEntradas : this.entradas){
			if(listaDeEntradas!=null){
			for(Entrada entrada:listaDeEntradas){
				nuevo.agrega(entrada.llave,entrada.valor);}
			}
		}
	
			this.entradas=nuevo.entradas;
			this.mascara=nuevo.mascara;
			this.elementos=nuevo.elementos;
			
		}

	private Diccionario<K, V>.Entrada buscaEntrada(K llave,
			Lista<Diccionario<K, V>.Entrada> lista) {
		for(Entrada s :lista){
			if(s.llave.equals(llave)){
				return s;
			}
		}
		return null;
	}

	private Lista<Diccionario<K, V>.Entrada> getLista(int indice, boolean b) {
		if(b){
			if(this.entradas[indice]==null){
			this.entradas[indice]=new Lista<Diccionario<K, V>.Entrada>();}
			return this.entradas[indice];
            }else{
		   return this.entradas[indice];
		  }
	}

	private int indice(K llave) {
      return huella.huellaDigital(llave)&mascara;
	}
 
    
}
