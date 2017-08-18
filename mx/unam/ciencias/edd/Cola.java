package mx.unam.ciencias.edd;


/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {

    Nodo nodo = new Nodo(elemento);
     if(this.cabeza==null){
    	 cabeza=rabo=nodo;
    	 
     }else{
     rabo.siguiente=nodo;
      rabo=nodo;
     }
     elementos++;
    }
    
  public String toString() {
    	
        String letras = "[";
        Nodo nodo = this.cabeza;
        
        
        while (nodo!= null){
        if(nodo.siguiente!=null){	
        if(nodo.elemento!=null){	
        letras += nodo.elemento.toString()+", ";
        nodo=nodo.siguiente;
        }else{
        	 letras +="null, ";
             nodo=nodo.siguiente;
         }
        
        }
        else{
        	if(nodo.elemento!=null){
        letras += nodo.elemento.toString();	
        nodo=nodo.siguiente;
        	}else{
        		 letras += "null";	
        	        nodo=nodo.siguiente;
        	}
        }
        }
        letras += "]";
         
        return letras;
     
        
        }
  
  
  

	
    
}
