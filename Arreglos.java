package mx.unam.ciencias.edd;

/**
 * Clase para manipular arreglos genericos de elementos comparables.
 */
public class Arreglos {

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T>
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     * 
     * 
     */
	

   
	
	
	
	
	private static <T extends Comparable<T>> void intercambia(T a[], int b, int c){
		T temporal =a[b];
		a[b]=a[c];
		a[c]=temporal;
	     
	}
	     
	
	
	
 private static <T extends Comparable<T>> void quickSort(T []a, int ini, int fin) {
	 
	 if(ini>=fin){
		return; 
	 }
	 
	 int i=ini+1;
	 int j=fin;
	 
	 while(i<j){
		 
		 if(a[i].compareTo(a[ini])>0&&a[j].compareTo(a[ini])<0){
			
		    intercambia(a,i++,j--);

		 } else if(a[i].compareTo(a[ini])<=0){
			 i++;
		
		 } else{
			 j--;
			 
			
		 }
	 }
	 if(a[i].compareTo(a[ini])>0){
		 i--;
		 
	 }
	 
	 intercambia(a,ini,i);
	 quickSort(a,ini,i-1);
	 quickSort(a,i+1,fin);
	     
	}


  public static <T extends Comparable<T>> void quickSort(T[] a) {

	  quickSort(a,0,a.length-1);
	
      }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     */
    
    
    
    public static <T extends Comparable<T>> void selectionSort(T[] a) {
    	
   	T min ,temp;
    int tamano = a.length; 
  
    for(int q =0;q<tamano;q++){
    	min=a[q];
    	   for(int w = q+1;w<tamano;w++){
    		     if(min.compareTo(a[w])>0){
    			    temp=min;
    			    min=a[w];
    			    a[w]=temp;
    		   }
    		    a[q]=min;
    		   
    	   }
    	
    }
    }

    /**
     * Hace una busqueda binaria del elemento en el arreglo. Regresa el indice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a el arreglo donde buscar.
     * @param e el elemento a buscar.
     * @return el indice del elemento en el arreglo, o -1 si no se encuentra.
     */
    

    
    
    public static <T extends Comparable<T>> int busquedaBinaria(T[] a, T e) {
   
    int primero= 0;
    int ultimo  = a.length-1;
    int mitad= (primero+ultimo)/2;
   
   if(e.compareTo(a[primero])<0||e .compareTo(a[ultimo])>0){
     return -1;
    	
         }else{
    	
                while(primero<=ultimo){
    	
                     if(e.compareTo(a[mitad])==0){
	                 return mitad;
	
	                 }else if(e.compareTo(a[mitad])<0){
    	             ultimo=mitad-1;
                     mitad=(primero+ultimo)/2;
    	
    	             }else if(e.compareTo(a[mitad])>0){
                 	 primero=mitad+1;
    	             mitad=(primero+ultimo)/2;
    }
    }
    }
	return -1;
    }
    
    public static void main(String[]gashj){
    	String[]b={"2", "2" ,"9" ,"6" ,"4" ,"5", "1"};
    	//selectionSort(b);
    	quickSort(b);
    	
    	for(int z = 0;z<b.length;z++){
    	System.out.print(b[z]+" ");
    	}
    	System.out.println();
    	
    	System.out.println(busquedaBinaria(b,"4"));
    	
    	
    }
}
