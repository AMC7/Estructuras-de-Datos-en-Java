package mx.unam.ciencias.edd;

import java.util.Random;





/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros son autobalanceados, y por lo tanto las operaciones de
 * inserción, eliminación y búsqueda pueden realizarse en <i>O</i>(log
 * <i>n</i>).
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {
	
	  private static  Random random;
	

    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
     */
    protected class VerticeRojinegro extends ArbolBinario<T>.Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }
        
        public String toString() {
        	if (this.elemento==null){
        		return "null"+this.color;
        	}
            return this.elemento.toString()+this.color;
          
            
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeRojinegro vertice = (VerticeRojinegro)o;
            return color == vertice.color && super.equals(o);
        }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * VerticeRojinegro}). Método auxililar para hacer esta audición en un único
     * lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice
     *                rojinegro.
     * @return el vértice recibido visto como vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos la altura.
     * @return la altura del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = verticeRojinegro(vertice);
        return v.color;
    }
    
    private boolean esNegro(VerticeRojinegro v) {
    if(v==null){
    return true;
    }
    if(getColor(v)==Color.NEGRO){
    return true;
    }
    return false;
    }
    
    
    private boolean esRojo(VerticeRojinegro v) {
    if(v==null){
    	return false;
    }
    if(getColor(v)==Color.ROJO){
    return true;
    }
    return false;
    }
    
   
    private void pintaNegro(VerticeRojinegro v) {
   if(v!=null){
    v.color=Color.NEGRO;
   }
    }
   
    
    private void pintaRojo(  VerticeRojinegro v) {
	v.color=Color.ROJO;
    }
    
    
    private boolean esIzquierdo(VerticeRojinegro vertice){
    Vertice padre=vertice.padre;
   	if(!vertice.hayPadre()){
    return false;
   	}
   	else if(padre.hayIzquierdo()){
   	  if(verticeRojinegro(padre.getIzquierdo())==verticeRojinegro(vertice)){
   		return true;
   		}
   	}
   	return false;
    }
    
     /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
 
    @Override public void agrega(T elemento) {
      super.agrega(elemento);
      VerticeArbolBinario<T> v=ultimoAgregado;
      VerticeRojinegro vertice= verticeRojinegro(v);
      pintaRojo(vertice);
      rebalancea(this,vertice);
    }
    
    private void rebalancea(ArbolRojinegro<T> arbolRojinegro,VerticeRojinegro vertice) {
    	//Caso 1
        if(!vertice.hayPadre()){
        pintaNegro(vertice);
        return;
        }
        
        //Caso 2
        if(esNegro(verticeRojinegro(vertice.padre))){
        return;   
        }
       
        //Caso 3
        if(tioEsRojo(vertice)){
     	pintaNegro(verticeRojinegro(vertice.padre));
     	pintaNegro(getTio(vertice));
        pintaRojo(verticeRojinegro(vertice.padre.padre));
     	VerticeRojinegro abuelo = verticeRojinegro(vertice.padre.padre);
     	rebalancea(this,abuelo);
     	return;
     	 
     	 
     	//Caso 4
     	}if(estanCruzados(vertice)){
        	
        	 if(esIzquierdo(verticeRojinegro(vertice.padre))){
     		 arbolRojinegro.giraIzquierda(vertice.padre);
    		 vertice=getHijo(vertice);
    	    }else{
    		 arbolRojinegro.giraDerecha(vertice.padre);
    		 vertice=getHijo(vertice);
    		 }
    	  }
         //Caso 5
         pintaNegro(verticeRojinegro(vertice.padre));
         pintaRojo(verticeRojinegro(vertice.padre.padre));
         if(esIzquierdo(verticeRojinegro(vertice.padre))){
        arbolRojinegro.giraDerecha(vertice.padre.padre);
     	 }else{
     	arbolRojinegro.giraIzquierda(vertice.padre.padre);
     	 }
         return;
         
      }
    
      private boolean tioEsRojo( VerticeRojinegro vertice) {
        if( getTio(vertice)!=null){
      	  if(esRojo(getTio(vertice))){
      		 return true;
      	 }
        }
        return false;
      }
      
      private boolean estanCruzados(ArbolRojinegro<T>.VerticeRojinegro vertice) {
    	   	 if(vertice.padre==raiz){
    	   		 return false;
    	   	 }else{
    	   		 if(esIzquierdo(verticeRojinegro(vertice.padre))&&!esIzquierdo(vertice)
    	   		 ||!esIzquierdo(verticeRojinegro(vertice.padre))&&esIzquierdo(vertice)){
    	   		 return true;
    	   		}
    	   	 }
    	   	 return false;
    	}
    
    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
    	//Caso 1
        if(!this.contiene(elemento)){
        return;
        }else{
        	
        VerticeRojinegro v= verticeRojinegro(this.busca(elemento));
       
        //Caso 2
        if(v.hayIzquierdo()){
        VerticeRojinegro u=maximo(v.izquierdo);	
        intercambia(u,v);
        v=u;
     
        }
        
        //Caso 3
        if(noTieneHijos(v)){
        VerticeRojinegro fantasma=new VerticeRojinegro(null);
        v.izquierdo=fantasma;
        fantasma.padre=v;
        pintaNegro(fantasma);
        }
        
        //Caso 4
        
        if(tieneSoloUnHijo(v)){
        sube(getHijo(v));
        }
        
        //Caso 5
        if(esRojo(getHijo(v))){
        pintaNegro(getHijo(v));
        elementos--;
        return;
        }
        
        //Caso 6
        if(esNegro(v)&&esNegro(getHijo(v))){
        rebalanceaElimina(getHijo(v));
        }
        
        //Caso 7
        
         eliminaFantasma(verticeRojinegro(v.padre));
         elementos--;
       
        }
    }
    
   
     private void intercambia(VerticeRojinegro u,VerticeRojinegro v) {
     T temporal=u.elemento;
     u.elemento=v.elemento;
     v.elemento=temporal;
     }
     
     private VerticeRojinegro maximo(Vertice maximo) {
     while(maximo.derecho!=null){
     maximo=maximo.derecho;
     }
     return verticeRojinegro(maximo);
     }
    	  	
    
     private void sube(ArbolRojinegro<T>.VerticeRojinegro hijo) {
	 Vertice vertice=hijo.padre;
	
	if(esIzquierdo(verticeRojinegro(vertice))){
	vertice.padre.izquierdo=hijo;	
	hijo.padre=vertice.padre;
	  
	 }else{
		 
		 if(vertice.padre==null){
			 
	    raiz=hijo;
	    hijo.padre=null;
	   
	   
		 }else{
		 vertice.padre.derecho=hijo;
		 hijo.padre=vertice.padre;
		 }
	  
	    }
		
	}
    
  	private void eliminaFantasma(ArbolRojinegro<T>.VerticeRojinegro v) {  
  		if(v==null){
  			raiz=null;
  			return;
  		}

      if(v.izquierdo!=null){
  		 if(v.izquierdo.elemento==null){
  		  v.izquierdo.padre=null;	 
  		  v.izquierdo=null;
  		 return;
          }
        }
        if(v.derecho!=null){
  		  if(v.derecho.elemento==null){
  		  v.derecho.padre=null;	  
  		  v.derecho=null;
  	      return;
           }
         }
        	 
         
		
	}

	private void rebalanceaElimina(ArbolRojinegro<T>.VerticeRojinegro v) {
  		//Caso 1
  		if(!v.hayPadre()){
  		return;
  		}
  	   //Caso 2
  		if(esRojo(getHermano(v))){
  			
  		pintaNegro(getHermano(v));
  		pintaRojo(verticeRojinegro(v.padre));
  			
  		if(esIzquierdo(v)){this.giraIzquierda(v.padre);}
  		else{this.giraDerecha(v.padre);}
  		
  		}
  	 //Caso 3
  		if(esNegro(verticeRojinegro(v.padre))&&esNegro(getHermano(v))&&sobrinosSonNegros(v)){
  			
  			pintaRojo(getHermano(v));
  			rebalanceaElimina(verticeRojinegro(v.padre));
  			return;
  		}
  	//Caso 4
  		if(esNegro(getHermano(v))&&sobrinosSonNegros(v)&&esRojo(verticeRojinegro(v.padre))){
  			
  			pintaRojo(getHermano(v));
  			pintaNegro(verticeRojinegro(v.padre));
  			return;
  		}
  	//Caso 5
  		if(sobrinosSonBicoloresCruzados(v)){
  			pintaNegro(getSobrinoRojo(v));
  			pintaRojo(getHermano(v));
  			if(esIzquierdo(v)){
  				giraDerecha(getHermano(v));
  			}else{
  				giraIzquierda(getHermano(v));
  			}
  			
  		}
  		//Caso 6
  		if(getHermano(v)!=null){
  		getHermano(v).color=getColor(verticeRojinegro(v.padre));
  		}
	    pintaNegro(verticeRojinegro(v.padre));
  	
	   if(esIzquierdo(v)){
		   if(getHermano(v)!=null){
  			pintaNegro(verticeRojinegro(getHermano(v).derecho));
		   }
  			giraIzquierda(v.padre);
  			
  		}else{
  			if(getHermano(v)!=null){
  			pintaNegro(verticeRojinegro(getHermano(v).izquierdo));
  			}
  			giraDerecha(v.padre);
  			
  		}
		
		
	}
	
	private ArbolRojinegro<T>.VerticeRojinegro getSobrinoRojo(
			ArbolRojinegro<T>.VerticeRojinegro v) {
	if(esRojo(verticeRojinegro(getHermano(v).izquierdo))){
		return verticeRojinegro(getHermano(v).izquierdo);
	}if(esRojo(verticeRojinegro(getHermano(v).derecho))){
		return verticeRojinegro(getHermano(v).derecho);
	}else{
		return null;
	}
		
	}
	
 
	private ArbolRojinegro<T>.VerticeRojinegro getHermano (ArbolRojinegro<T>.VerticeRojinegro v) {
		if(v.hayPadre()){
			if(esIzquierdo(v)){
				if(v.padre.hayDerecho()){
					return verticeRojinegro(v.padre.derecho);
				}
			}else{
				if(v.getPadre().hayIzquierdo()){
					return verticeRojinegro(v.padre.izquierdo);
					
				}
			}
		}
		return null;
		}

		private ArbolRojinegro<T>.VerticeRojinegro getHijo(ArbolRojinegro<T>.VerticeRojinegro v) {
		if(v.hayDerecho()){
		return verticeRojinegro(v.derecho);
		}if(v.hayIzquierdo()){
		return verticeRojinegro(v.izquierdo);
		}else{
		return null;	
		 }
			
		}
	    private  VerticeRojinegro getTio( VerticeRojinegro v) {
	    return (getHermano(verticeRojinegro(v.padre)));
	    }
	    
	    private boolean sobrinosSonNegros(ArbolRojinegro<T>.VerticeRojinegro v) {
			if(getHermano(v)!=null){
	  	  if(esNegro(verticeRojinegro(getHermano(v).izquierdo))&&
	  		esNegro(verticeRojinegro(getHermano(v).derecho))){
	  		return true;
	  		}
			}
	  		return false;
	  		
	  	}
	  	
	  	private boolean sobrinosSonBicoloresCruzados(ArbolRojinegro<T>.VerticeRojinegro v) {
	  		if(getHermano(v)!=null){
	  		if(esIzquierdo(verticeRojinegro(v))){
	  			if((esNegro(verticeRojinegro(getHermano(v).derecho)))&&
	  					(esRojo(verticeRojinegro(getHermano(v).izquierdo)))){
	  				return true;
	  			}
	  		}else{
	  			if((esRojo(verticeRojinegro(getHermano(v).derecho)))&&
	  					(esNegro(verticeRojinegro(getHermano(v).izquierdo)))){
	  				return true;
	  			}
	  			
	  		}
	  		}
	  		return false;
	  	}
	  	
	  	private boolean tieneSoloUnHijo(ArbolRojinegro<T>.VerticeRojinegro v) {
	      return (v.hayDerecho()||v.hayIzquierdo())&&!(tieneDosHijos(v));
	  	}

	  	private boolean noTieneHijos(VerticeRojinegro vertice) {
	    	return !vertice.hayIzquierdo()&&!vertice.hayDerecho();
	      }
	  	
	  	private boolean tieneDosHijos(VerticeRojinegro vertice) {
	    	return vertice.hayIzquierdo()&&vertice.hayDerecho();
	      }
	  	
	  	
}
