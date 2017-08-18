package mx.unam.ciencias.edd;



/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {
        Nodo nodo = new Nodo(elemento);
        nodo.siguiente=this.cabeza;
        if(nodo.siguiente==null){
        cabeza=rabo=nodo;
        }else{
        cabeza=nodo;
        }
        elementos++;
    }
    
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
    
    
 /**   public static void main(String[]args){
    	Pila<String> pila = new Pila();
    	pila.mete("a");
   
    	
    	
    	
    	
    }*/
}
