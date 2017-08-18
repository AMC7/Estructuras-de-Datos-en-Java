/**Metodos que estan mal 
 * Elimina
 * Elimina Ultimo
 * Reversa
 * Iterator
 * Iterador Lista
 * to String*/

package mx.unam.ciencias.edd;
import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * <p>Clase generica para lis
 * tas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten 
 * agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista,
 * comprobar si un elemento esta o no en la lista,
 * y otras operaciones basicas.</p>
 *
 * <p>Las instancias de la clase Lista implementan la interfaz {@link
 * Coleccion}, y por lo tanto tambien la interfaz {@link Iterator}, por lo que
 * el recorrerlas es muy sencillo:</p>
 *
<pre>
    for (String s : l)
        System.out.println(s);
</pre>
 *
 * <p>Ademas, se le puede pedir a una lista una instancia de {@link
 * IteradorLista} para recorrerla en ambas direcciones.</p>
 */
public class Lista<T> implements Coleccion<T> {

  	/* Clase Nodo privada para uso interno de la clase Lista. */
   
 private class Nodo {

        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con el elemento especificado. */
        public Nodo(T elemento) {
         this.elemento = elemento;
         }
        
    }
	
	/* Clase Iterador privada para iteradores. */
   
	private class Iterador<T> implements IteradorLista<T> {

        /* La lista a iterar. */
        Lista<T> lista;
        /* Elemento anterior. */
        private Lista<T>.Nodo anterior;
        /* Elemento siguiente. */
        private Lista<T>.Nodo siguiente;
        
        
        
        /* El constructor recibe una lista 
         * para inicializar su siguiente iterador
         * con la
         * cabeza. */
       
       
		public Iterador(Lista<T> lista) {
        	
        	this.lista = lista;
        	this.siguiente= lista.cabeza;
        	this.anterior=null;
        	
			
            }
        /* Existe un siguiente elemento, si siguiente no es nulo. */
        @Override public boolean hasNext() {
		 return this.siguiente == null ? false : true;
        }

        /* Regresa el elemento del siguiente, a menos que sea nulo, en cuyo caso
         * lanza la excepcion NoSuchElementException. */
        
       
		@Override public T next() {
    
			
			if (hasNext()){
			/**	Lista<T>.Nodo nodo=this.siguiente;
				this.anterior=nodo;
				this.siguiente=nodo.siguiente;
			   return nodo.elemento;*/
			T elemento = this.siguiente.elemento;
			this.anterior=this.siguiente;
			this.siguiente=this.siguiente.siguiente;
			return elemento;
			}
			else{throw new NoSuchElementException();}
        }

        /* Existe un elemento anterior, si anterior no es nulo. */
        @Override public boolean hasPrevious() {
        
         return this.anterior != null ? true : false;
           }
        
        
              /* Regresa el elemento del anterior, a menos que sea nulo, en cuyo caso
         * lanza la excepcion NoSuchElementException. */
         
       
        @Override public T previous() {
			
        	if(hasPrevious()){
        		Lista<T>.Nodo nodo = this.anterior;
    			this.anterior=nodo.anterior;
    			this.siguiente=nodo;
        		return nodo.elemento;}
        	else{throw new NoSuchElementException();}
        	
        }

        /* No implementamos el metodo remove(); sencillamente lanzamos la
         * excepcion UnsupportedOperationException. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }

        /* Mueve el iterador al inicio de la lista; despues de llamar este
         * metodo, y si la lista no es vacia, hasNext() regresa verdadero y
         * next() regresa el primer elemento. */
        @Override public void start() {
        	
        	if (longitud != 0){
        	this.siguiente=lista.cabeza;
        	this.anterior=null;
        	}else{
        	throw new UnsupportedOperationException();
        	}
            
        }

        /* Mueve el iterador al final de la lista; despues de llamar este
         * metodo, y si la lista no es vacia, hasPrevious() regresa verdadero y
         * previous() regresa el ultimo elemento. */
        @Override public void end() {

        	if (longitud != 0){
        	this.anterior=lista.rabo;
        	this.siguiente=null;
        	}else{
        	throw new UnsupportedOperationException();
        	}
        	
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Ultimo elemento de la lista. */
    private Nodo rabo;
    /* Numero de elementos en la lista. */
    private int longitud;

    /**
     * Regresa el numero de elementos en la lista. El metodo es identico a
     * {@link getLongitud}.
     * @return el numero de elementos en la lista.
     */
    @Override public int getElementos() {
		return longitud;
	}

    /**
	 * Regresa la longitud de la lista. El metodo es identico a {@link
	 * #getElementos}.
	 * @return la longitud de la lista, el numero de elementos que contiene.
	 */
	public int getLongitud() {
		return longitud;
	  }

	/**
     * Agrega un elemento al final de la lista. El metodo es identico a {@link
     * #agregaFinal}.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
    	Nodo nodoFinal = new Nodo(elemento);
    	if (longitud == 0){
    	cabeza = rabo = nodoFinal ;
    	}else{
    	nodoFinal.anterior= this.rabo;
    	this.rabo.siguiente=nodoFinal;
    	rabo=nodoFinal;	
        }
    	longitud ++;
      }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar sera el primero y el ultimo a la vez.
     * @param elemento el elemento a agregar.
     */
    public void agregaFinal(T elemento) {
    	Nodo nodoFinal = new Nodo(elemento);
    	if (longitud == 0){
    	cabeza = rabo = nodoFinal ;
    	}else{
    	nodoFinal.anterior= this.rabo;
    	this.rabo.siguiente=nodoFinal;
    	rabo=nodoFinal;	
        }
    	longitud ++;
      }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar sera el primero y el ultimo a la vez.
     * @param elemento el elemento a agregar.
     */
    public void agregaInicio(T elemento) {
    	
        Nodo nodoInicio = new Nodo(elemento);
    	if (longitud == 0){
    	cabeza = rabo = nodoInicio ;
    	}else{
    	this.cabeza.anterior = nodoInicio;
    	nodoInicio.siguiente= this.cabeza;
    	
    	cabeza=nodoInicio;	
    	}
    	longitud ++;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no esta contenido en la
     * lista, el metodo no hace nada. Si el elemento aparece varias veces en la
     * lista, el metodo elimina el primero.
     * @param elemento el elemento a eliminar.
     * 
     * 
     */
    
    private Nodo getNodo(T elemento){
    	
	 Nodo nodo = this.cabeza;
	 while(nodo != null){
	     if (nodo.elemento.equals(elemento)){	
	  
	    	 
	     return nodo;
	     }else{
	     nodo=nodo.siguiente;
	     }
	     }
	 return null;
     }
    
    
    
    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param l la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     * 
     * 
     */
    private static <T extends Comparable<T>>  Lista<T> mezcla(Lista<T> li,Lista<T> ld) {
    	Lista<T> resultado = new Lista<T>();
    	
    	Lista<T>.Nodo nodoLi= li.cabeza;
    	Lista<T>.Nodo nodoLd= ld.cabeza;
    
    	while(nodoLi!=null||nodoLd!=null){
        
    		if(nodoLi==null&&nodoLd!=null){
    			resultado.agregaFinal(nodoLd.elemento);
    			nodoLd=nodoLd.siguiente;
    		}
    		
    		else if(nodoLi!=null&&nodoLd==null){
    			resultado.agregaFinal(nodoLi.elemento);
    			nodoLi=nodoLi.siguiente;
    		}
    		
    		else if(nodoLi.elemento.compareTo(nodoLd.elemento)<=0){
    			resultado.agregaFinal(nodoLi.elemento);
    			nodoLi=nodoLi.siguiente;
    		}else{
    			resultado.agregaFinal(nodoLd.elemento);
    			nodoLd=nodoLd.siguiente;
    		}
    		
    		
    		
    	}
    
		return resultado;
    	
    }
    
    
    
    
    
    public static <T extends Comparable<T>>  Lista<T> mergeSort(Lista<T> l) {
    	
		if(l.longitud<2){
			return l.copia();
		}else{
			Lista<T>.Nodo temp =l.cabeza;
			
			Lista<T> li= new Lista<T>();
			Lista<T> ld= new Lista<T>();
			
			
			for(int i=0;i<l.longitud/2;i++){
				li.agrega(temp.elemento);
				temp=temp.siguiente;
			}
			

			for(int j=l.longitud/2;j<l.longitud;j++){
				ld.agrega(temp.elemento);
				temp=temp.siguiente;
			}
			
			
			li=mergeSort(li);
			ld=mergeSort(ld);
			
			return mezcla(li,ld);
			
			
			//return l;
		}

    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param l la lista donde se buscará.
     * @param e el elemento a buscar.
     * @return <tt>true</tt> si e está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>> boolean busquedaLineal(Lista<T> l, T e) {
    	Lista<T>.Nodo nodo = l.cabeza;
  	  while(nodo!=null){
  		  if(nodo.elemento.compareTo(e)>0){
  			  return false;
  		  }
  		 else if (nodo.elemento.equals(e)){
         return true;
         }else{
          nodo=nodo.siguiente;
          	}
            }
          	return false;
           }
    /**
     * Elimina un elemento de la lista. Si el elemento no esta contenido en la
     * lista, el metodo no hace nada. Si el elemento aparece varias veces en la
     * lista, el metodo elimina el primero.
     * @param elemento el elemento a eliminar.
     */
  
  @Override public void elimina(T elemento) {
    	
    	if(this.contiene(elemento)){
    		
    		if(this.cabeza.elemento.equals(elemento)){
    		this.eliminaPrimero();
    	}
    	else{
    		Nodo nodo = getNodo(elemento);
    	
    		
    		if(nodo.siguiente==null){
    			this.eliminaUltimo();
            
    		}else{
    			Nodo nodoAnterior = nodo.anterior;
        		Nodo nodoSiguiente = nodo.siguiente;
    			 nodoAnterior.siguiente=nodoSiguiente;
        		 nodoSiguiente.anterior=nodoAnterior;
        		
        		longitud--;
    		}
    	
    	}
    	}
        }
        

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacia.
     */
  
	public T eliminaPrimero() {
		
	Nodo nodoRetornable=new Nodo(null);
		
	if (longitud > 2){ 
		
	nodoRetornable=this.cabeza;
	cabeza =this.cabeza.siguiente;
	longitud --;
    return nodoRetornable.elemento;
    
		
	}if(longitud ==2){
	nodoRetornable=this.cabeza;
	cabeza=rabo=this.cabeza.siguiente;
	longitud --;
    return nodoRetornable.elemento;
		
	}
	
	if(longitud ==1){
		
	nodoRetornable=this.cabeza;	
	cabeza=rabo=null;
	longitud=0;
	
	return nodoRetornable.elemento;
			
	}
	
	else{
	
	throw new NoSuchElementException();
	
	}
	
		
    }

    /**
     * Elimina el ultimo elemento de la lista y lo regresa.
     * @return el ultimo elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacia.
     */
    public T eliminaUltimo() {
    	
    	
    	
    	if (longitud > 2){ 
    		
    	Nodo nodoRetornable=this.rabo;
    	nodoRetornable.anterior.siguiente=null;
    	rabo=this.rabo.anterior;
    	longitud--;
    	
    	return nodoRetornable.elemento;
    			
    	}else if(longitud ==2){
    	
    	Nodo nodoRetornable=this.rabo;
    	cabeza.siguiente=cabeza.anterior=null;
    	rabo=cabeza;
    	longitud--;
    	
    	return nodoRetornable.elemento;
    	
    	
    	}
    	
    	else if(longitud ==1){
    	Nodo nodoRetornable=this.rabo;	
    	cabeza=rabo=null;
    	longitud=0;
    	return nodoRetornable.elemento;
    				
    	}
    		
    	else{
    		
    	throw new NoSuchElementException();
    		
    	}
    		
    }

    /**
     * Nos dice si un elemento esta en la lista.
     * @param elemento el elemento que queremos saber si esta en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> esta en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
    	
              Nodo nodo = this.cabeza;
        	  while(nodo!=null){
              if (nodo.elemento.equals(elemento) ){
              return true;
               }else{
                nodo=nodo.siguiente;
                	}
                    }
                	return false;
                	}
        	 
	 /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa de la que manda llamar el
     *         metodo.
     */
    public Lista<T> reversa() {
   /** Nodo nodo = this.cabeza;
    Lista <T> lista= new Lista<T>();	
    
    while (nodo!= null){
    lista.agregaInicio(nodo.elemento);
    nodo=nodo.siguiente;
    }
    
    return lista;*/
    	 Nodo nodo = this.rabo;
    	    Lista <T> lista= new Lista<T>();	
    	    	    
    	    while (nodo!= null){
    	    lista.agregaFinal(nodo.elemento);
    	    nodo=nodo.anterior;
    	    }
    	    return lista;
    
    }
    
    

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el metodo, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
    
    Nodo nodo = this.cabeza;
    Lista <T> lista= new Lista<T>();	
    	    
    while (nodo!= null){
    lista.agregaFinal(nodo.elemento);
    nodo=nodo.siguiente;
    }
    return lista;
    }

    /**
     * Limpia la lista de .elementos. El llamar este metodo es equivalente a
     * eliminar todos los elementos de la lista.
     */
    public void limpia() {
     this.cabeza=this.rabo=null;
     longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacia.
     */
    public T getPrimero() {
    	if (longitud !=0){
			return cabeza.elemento;
		}else{
			throw new NoSuchElementException();
		}
    }
    

    

	/**
	 * Regresa el indice del elemento recibido en la lista.
	 * @param elemento el elemento del que se busca el indice.
	 * @return el indice del elemento recibido en la lista, o -1 si el elemento
	 *         no esta contenido en la lista.
	 */
	public int indiceDe(T elemento) {
		int i=0;
		if(!this.contiene(elemento)){
			return -1;
		}else{
			
			Nodo nodo = this.cabeza;
			while(nodo != null){
				if (nodo.elemento.equals(elemento)){
					return i;
				}else{
					nodo=nodo.siguiente;
					i++;
				}
			}
		}
		return i;
	}

	/**
     * Regresa el ultimo elemento de la lista.
     * @return el ultimo elemento de la lista.
     * @throws NoSuchElementException si la lista es vacia.
     */
    public T getUltimo() {
		if (longitud !=0){
			return rabo.elemento;
		}else{
			throw new NoSuchElementException();
		}
    }

    /**
     * Regresa el <em>i</em>-esimo elemento de la lista.
     * @param i el indice del elemento que queremos.
     * @return el <em>i</em>-esimo elemento de la lista, si <em>i</em> es mayor
     *         o igual que cero y menor que el numero de elementos en la lista.
     * @throws ExcepcionIndiceInvalido si el indice recibido es menor que cero,
     *         o mayor que el numero de elementos en la lista menos uno.
     */
    public T get(int i) {
    	Nodo nodo = this.cabeza;
 
    	if(i<0||i>=this.longitud){
    		throw new ExcepcionIndiceInvalido();
    	}else{
    		for(int x=0;x<i;x++){
    			nodo=nodo.siguiente;
    		}
    		
    		
    	}
		return nodo.elemento;


    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
    	 if (o == null){
             return false;
         }
    	 if (getClass() != o.getClass()){
             return false;
          }
         else{
            @SuppressWarnings("unchecked")
 			Lista<T> lista = (Lista<T>)o;
            
         
        	 if(lista.longitud!=this.longitud){
        	 return false;
        	
        	 } else{
        		 Nodo nodo = this.cabeza;
        		 Nodo nodo1 = lista.cabeza;
                 
                 while(nodo!=null){
                	 
                	 if (nodo.elemento.equals(nodo1.elemento) ){
                		 
                		 nodo=nodo.siguiente;
                		 nodo1=nodo1.siguiente;
                		 }else{
                		 return false;
                	 }
                  }
        	    }
        	 }
		return true;
         }
    	
    	
    /**
     * Regresa una representacion en cadena de la lista.
     * @return una representacion en cadena de la lista.
     */
        @Override 
        public String toString() {
        	
        String letras = "[";
        Nodo nodo = this.cabeza;
        
        
        while (nodo!= null){
        if(nodo.siguiente!=null){	
        letras += nodo.elemento.toString()+", ";
        nodo=nodo.siguiente;
        }
        else{
        letras += nodo.elemento.toString();	
        nodo=nodo.siguiente;
        }
        }
        letras += "]";
         
        return letras;
     
        
        }
    

    /**
     * Regresa un iterador para recorrer la lista.
     * @return un iterador para recorrer la lista.
     */
    @Override public Iterator<T> iterator() {
    	
        return  iteradorLista();
        
    }

    /** 
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador<T>(this);
    }
    
 /**  public static void main(String[]args){
    	
    	Lista<Integer> a = new Lista<Integer>();
        a.agrega(1);
        a.agrega(2);
        a.agrega(4);
        a.agrega(4);
        a.agrega(9);
    	
    	System.out.println(a);
    	a.elimina(9);
    	System.out.println(a);
    	
    	
    	
    	
    
    }*/
    

}































