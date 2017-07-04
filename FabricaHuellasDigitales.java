package mx.unam.ciencias.edd;

/**
 * Clase para fabricar generadores de huellas digitales.
 */
public class FabricaHuellasDigitales {

    /**
     * Identificador para fabricar la huella digital de Bob
     * Jenkins para cadenas.
     */
    public static final int BJ_STRING   = 0;
    /**
     * Identificador para fabricar la huella digital de GLib para
     * cadenas.
     */
    public static final int GLIB_STRING = 1;
    /**
     * Identificador para fabricar la huella digital de XOR para
     * cadenas.
     */
    public static final int XOR_STRING  = 2;
    
   
    


private static int huella_bj(byte[] k, int n) {
	 /* Guardamos estas variables en registros, para rapidez. */
    int a, b, c, l;

    /* Estado interno. */
    l = n;
    a = b = 0x9e3779b9;  /* "Golden ratio"; es arbitrario. */
    c = 0xffffffff;      /* Valor inicial. */
    int i=0;
    /* Dividimos la llave en 3 enteros (grupos de 4 bytes), y los
       mezclamos. Hacemos esto mientras tengamos grupos de 3 enteros
       disponibles en la llave. */
    while (l >= 12) {
            a += (k[i+0] + (k[i+1] << 8) + (k[i+2]  << 16) + (k[i+3]  << 24));
            b += (k[i+4] + (k[i+5] << 8) + (k[i+6]  << 16) + (k[i+7]  << 24));
            c += (k[i+8] + (k[i+9] << 8) + (k[i+10] << 16) + (k[i+11] << 24));
           
            a -= b; a -= c; a ^= (c >>> 13);   
            b -= c; b -= a; b ^= (a << 8);    
            c -= a; c -= b; c ^= (b >>> 13);   
            a -= b; a -= c; a ^= (c >>> 12);   
            b -= c; b -= a; b ^= (a << 16);   
            c -= a; c -= b; c ^= (b >>> 5);    
            a -= b; a -= c; a ^= (c >>> 3);    
            b -= c; b -= a; b ^= (a << 10);   
            c -= a; c -= b; c ^= (b >>> 15);   
            
            
            
            i += 12;
        
            l -= 12;
            
            
    }
    
    /* El switch lidia con los Ãºltimos 11 bytes. */
    c += k.length;
    switch (l) {
            /* No hay breaks; se ejecutan los casos desde el primero
               encontrado hasta el Ãºltimo. */
    case 11: c += (k[i+10] << 24);
    case 10: c += (k[i+9]  << 16);
    case  9: c += (k[i+8]  <<  8);
            /* El primer byte de c se reserva para la longitud. */
    case  8: b += (k[i+7]  << 24);
    case  7: b += (k[i+6]  << 16);
    case  6: b += (k[i+5]  <<  8);
    case  5: b += k[i+4];
    case  4: a += (k[i+3]  << 24);
    case  3: a += (k[i+2]  << 16);
    case  2: a += (k[i+1]  <<  8);
    case  1: a += k[i+0];
            /* Caso 0: Nada que hacer, se acabaron los bytes. */
    }

    /* Mezclamos una Ãºltima vez. */
    a -= b; a -= c; a ^= (c >>> 13);   
    b -= c; b -= a; b ^= (a << 8);    
    c -= a; c -= b; c ^= (b >>> 13);   
    a -= b; a -= c; a ^= (c >>> 12);   
    b -= c; b -= a; b ^= (a << 16);   
    c -= a; c -= b; c ^= (b >>> 5);    
    a -= b; a -= c; a ^= (c >>> 3);    
    b -= c; b -= a; b ^= (a << 10);   
    c -= a; c -= b; c ^= (b >>> 15);   
    
    return c;

}




    /**
     * Regresa una instancia de {@link HuellaDigital} para cadenas.
     * @param identificador el identificador del tipo de huella
     *        digital que se desea.
     * @return una instancia de {@link HuellaDigital} para cadenas.
     * @throws IllegalArgumentException si recibe un identificador
     *         no reconocido.
     */
    public static HuellaDigital<String> getInstanciaString(int identificador) {
         
    	
    
    	switch(identificador){
    	case 0:{
    		return (s)->{  
    			return huella_bj(s.getBytes(), s.length());
    			
           };
    		
    	}
    	case 1:{
    		return (a)->{  byte[] k = a.getBytes();
    		               int longitud = k.length;
    		               int h=5381;
    		               
    		               for(int i =0;i<longitud;i++){
    			            h=h*33+k[i];
    			           }
    		               
    		               return h;
    		              };
    		              
    	}
        case 2:{
        	
        return (s)->{ byte[] k = s.getBytes();
        	          int longitud=k.length;
        	          int r = 0;
        	         int i=0;
        	          while(longitud>=4){
                      r^=(k[i]<<24)|(k[i+1]<<16)|(k[i+2]<<8)|(k[i+3]);
        	          longitud-=4;
        	          i+=4;
        	          }
        	  
        	          int t=0;
        	  
        	          switch(longitud){
        	          case 3 :t|=k[i+2]<<8;
        	          case 2 :t|=k[i+1]<<16;
        	          case 1 :t|=k[i]<<24;
        	          }
        	          r^=t;
        	          return r;
        	  
           };
        	
        }
    			
    		
    	}
    	throw new  IllegalArgumentException();
    	
    		
        }
    
}


