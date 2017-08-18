package mx.unam.ciencias.edd;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.Normalizer;

import mx.unam.ciencias.edd.Graficador.VerticeGrafica;


public class Analiza {
	int y =0;
	String direccion;
	Lista<Palabra> palabras=new Lista <Palabra>();
	double numeroDePalabras=0;
	private Diccionario<String, Palabra> diccionario=new Diccionario<String, Palabra>();
	
     public Analiza(String d,int p){
    	 this.direccion=d;
    	 this.y=p;
    	 creaListaDePalabras();
     }
     
     
     
     
     public static void main(String[] args) throws Exception{
    	 
    
		if(args.length==0){
			 BufferedReader	bf = new BufferedReader(new InputStreamReader(System.in));	
			 Analiza analiza = new Analiza(bf.readLine(),0);
			 String resultado =creaEntradaHTML("","FFFFFF")+creaEntrada(1000000 ,1000000)+
		     dibujaElemento(470, 75,43 , "blue","Proyecto 3 Analisis de Texto");
			 resultado+=analiza.toString();
			 resultado+=creaSalida()+creaSalidaHTML();
			 generaArchivo("index.html",resultado.toString());
		
		}else{

			
			Analiza[] arregloDeAnaliza = new Analiza[args.length];  
			int i =0;
			int centro=0;
			
			for(String string :args){
			arregloDeAnaliza[i++]=new Analiza(string,centro);
			centro+=1200;
			}
			
			String resultado =creaEntradaHTML("","FFFFFF")+creaEntrada(1000000 ,1000000)
					+ dibujaElemento(800, 75,40, "blue","Proyecto 3 Analisis de Texto");
			
			for(int o=0;o<arregloDeAnaliza.length;o++ ){
			resultado+= dibujaElemento(800, 185+o*34,23,"black",String.valueOf(o)+": "+args[o]);
			}
			
			@SuppressWarnings("unchecked")
			Diccionario<String, Palabra> [] arreglo= (Diccionario<String, Palabra>[] ) Array.newInstance(new Diccionario<String,Palabra>().getClass(),args.length);
			i=0;
			
			for(Analiza analiza :arregloDeAnaliza){
			resultado+=analiza.toString();
			arreglo[i++]=analiza.diccionario;
			}
			
			Grafica<Integer> a=new Grafica<Integer>();
			String numeros = "";
			for(i =0 ;i<args.length;i++){
			a.agrega(i);
			numeros+=String.valueOf(i)+" ";
			}
			
		
			
			String adyascencias="";
			for(int k=0;k<arreglo.length;k++){
				for(int t=0;t<arreglo.length;t++){
					if(k!=t){
						for(Palabra palabra:arreglo[k]){
							if(!a.sonVecinos(k, t)){
								if(arreglo[t].contiene(palabra.palabra)){
								a.conecta(k, t);
								adyascencias+=String.valueOf(k)+" "+String.valueOf(t)+" ";
								}
							}
						}
					}
				}
			}
			
			Graficador graficador = new Graficador("Grafica");
			graficador.generadorDeColeccion(numeros,adyascencias);
			resultado+=graficador.toString();
			resultado+=creaSalida()+creaSalidaHTML();
			
			generaArchivo("index.html",resultado.toString());	
			
			
    		}
		
		
	}
	
	
	
	protected class Punto {
		    int x; int y;
		    	
		    public Punto(int x, int y){
		    this.x=x;
		    this.y=y;
		    }
		    
		    public String toString(){
		    return"("+String.valueOf(x)+","+String.valueOf(y)+")";
		    }
		    	
		    }
	
	private  class Palabra implements Comparable<Palabra>{
     int repeticiones=1;
     String palabra="";
     
     	public Palabra(String palabra){
     		this.palabra=palabra;
     	}
     	
     	public String toString(){
     	 String s=this.palabra+ " se repite " +this.repeticiones;
     	 if(this.repeticiones==1){s+=" vez";
     	 }else{s+=" veces";}
     	 return s;
     	}
		@Override
		public int compareTo(Palabra palabra) {
			return (this.repeticiones==palabra.repeticiones)?this.palabra.compareTo(palabra.palabra)
					:palabra.repeticiones-this.repeticiones ;
		}
	}
	
	private class HTML {
		String texto="";
		public HTML(String s){
			texto+=new SVG().texto +creaSalidaHTML();
		}
		
		private String creaSalidaHTML() {
			return "</BODY></HTML>";
		}
		
		
		
		
	}
	
	private  class SVG{
		
		 String salto="\n";
		String texto="";
		Punto centro=new Punto(645,1000+y);
		
		int radio=150;
		
		public SVG(){
			texto+=dibujaElemento(new Punto(centro.x,centro.y-225),45,"blue",direccion)+ dibujaGraficaDePastel(palabras)+ dibujaHistograma(palabras)+dibujaElemento(new Punto(centro.x+5,centro.y+265),40 ,"black",String.valueOf((int)numeroDePalabras+" palabras"))  +dibujaCirculo(centro,radio);	
		}
		
		public String dibujaGraficaDePastel(Lista<Palabra> palabras) {

			String resultado = "";
			int n=palabras.getElementos();
			double angle=0.06283185307179587;
			
		    
		    
		    if(n==0){ return "";
		    }
		   
		    if(n==1){return dibujaCirculo(centro,radio,"red")
		    		+dibujaInfo(palabras.getPrimero(),new Punto 
		    		(centro.x-495,centro.y-205), "red");}
		    
		   
			if(n%2!=0){
			n=n*2;  int i=1;  int num=0;
			
			
			for(Palabra palabra:palabras){
				
				int porcentaje = 0;
				resultado+=dibujaInfo(palabra,new Punto(centro.x-495,centro.y-205+15*i),getColor(i));	
				double rep=palabra.repeticiones;
				
					if(palabra.palabra.equals("otros")){
						porcentaje=100;
					}
					porcentaje=(int) (rep*100.0/numeroDePalabras);
					porcentaje+=num;
				
				while(num<porcentaje){
						resultado+=dibujaPath(calculaPunto(angle*num),centro,calculaPunto(angle*(num+1)),getColor(i));
						num++;
					}
				
				
				i+=2;
				if(i>=n){break;}
			}
			
			return resultado;
			
			}else{
				int i=0; int num=0;
			
				for(Palabra palabra:palabras){
					int porcentaje=0;	
			
					resultado+=dibujaInfo(palabra,new Punto (centro.x-495,centro.y-205+25*i),getColor(i));
					porcentaje=(int) (palabra.repeticiones*100.0/numeroDePalabras);
					porcentaje+=num;
		    
					if(palabra.palabra.equals("otros")){porcentaje=100;}
			
					while(num<porcentaje){
						resultado+=dibujaPath(calculaPunto(angle*num),centro,calculaPunto(angle*(num+1)),getColor(i));
						num++;
					}
					i++;
					if(i>=n){break;}
				}
	 	}
			return resultado;
			
		}
	
		
		public String dibujaHistograma(Lista<Palabra> palabras) {
			
			String resultado ="";
			resultado+=dibujaLinea(new Punto(500 , centro.y+750) ,new Punto(1000 , centro.y+750),2)+
					dibujaLinea(new Punto(500 , centro.y+750) ,new Punto(500 , centro.y+350),2)
					+ dibujaElemento(new Punto(centro.x+100 , centro.y+780) , 20, "black" , "Palabras" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+380+65) , 24, "black" , "R" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+400+65) , 24, "black" , "e" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+420+65) , 24, "black" , "p" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+440+65) , 24, "black" , "e" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+460+65) , 24, "black" , "t" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+480+65) , 24, "black" , "i" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+500+65) , 24, "black" , "c" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+520+65) , 24, "black" , "i" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+540+65) , 24, "black" , "o" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+560+65) , 24, "black" , "n" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+580+65) , 24, "black" , "e" )
					+ dibujaElemento(new Punto(centro.x-230 , centro.y+600+65) , 24, "black" , "s" )
					+ dibujaElemento(new Punto(centro.x-160 , centro.y+750) , 20, "black" , "0")
					+ dibujaElemento(new Punto(centro.x-160 , centro.y+650) , 20, "black" , "25")
					+ dibujaElemento(new Punto(centro.x-160 , centro.y+550) , 20, "black" , "50")
					+ dibujaElemento(new Punto(centro.x-160 , centro.y+457) , 20, "black" , "75")
					+ dibujaElemento(new Punto(centro.x-165 , centro.y+365) , 20, "black" , "100")
			     	;
			
			int i =0;
             for(Palabra palabra:palabras){
            	 resultado+=dibujaInfo(palabra,new Punto(centro.x-495,centro.y+350+35*i),getColor(i));	
            	 resultado+=dibujaLinea(new Punto(550+i*50 , centro.y+750) ,new Punto(550+i*50,centro.y+350+4*(100-getPorcentaje(palabra.repeticiones)))
            	 ,10,getColor(i));
				i++;
				
			}
			return resultado;
			
			
		}
	
		private String getColor(int i){
          switch(i%14){
          case 0:return"#FFFF00"; 
          case 1:return"#64FE2E"; 
          case 2:return"#FF00FF"; 
          case 3:return"#FF0000"; 
          case 4:return"#00BFFF"; 
          case 5:return"#01A9DB"; 
          case 6:return"#FF8000"; 
          case 7:return"#FFBF00"; 
          case 8:return"#FF4000"; 
          case 9:return"#00FF80"; 
          case 10:return"#FFFF00"; 
          case 11:return"#FFFF00"; 
          case 12:return"#FE2E64"; 
          case 13:return"#29088A"; 
         
          }
		return "#29088A";
		
			
			
			
		}
			
			public String dibujaLinea(Punto punto1, Punto punto2, int i){
				return "<line x1='"+String.valueOf(punto1.x)+"'"
						+ " y1='"+String.valueOf(punto1.y)+"'"
						+ " x2='"+String.valueOf(punto2.x)+"'"
						+ " y2='"+String.valueOf(punto2.y)+"' "
						+ "stroke='black' stroke-width='"+String.valueOf(i)+"' />";
				
				
			}
			
			public String dibujaLinea(Punto punto1, Punto punto2, int i, String s){
				return "<line x1='"+String.valueOf(punto1.x)+"'"
						+ " y1='"+String.valueOf(punto1.y)+"'"
						+ " x2='"+String.valueOf(punto2.x)+"'"
						+ " y2='"+String.valueOf(punto2.y)+"' "
						+ "stroke='"+ s+"' stroke-width='"+String.valueOf(i)+"' />";
				
				
			}
			
			
			
			
		
			
			private String dibujaRectangulo(Punto punto, String color){
				
			return "<rect x=\""+punto.x+"\" y=\""+punto.y+"\" width=\"20\" height=\"20\" style=\"fill:"+color+";stroke:"+color+";stroke-width:1\" />" ;
				 }
			
			
		
			private String dibujaCirculo(Punto punto,int radio){
				
				return "<circle cx='"+punto.x+"' cy='"+punto.y+"' r="
						+ "'"+182+"' stroke='white' stroke-width='60'"
						+ " fill=\" none\"  />"; 
				
				
			}
			
			private String dibujaCirculo(Punto punto,int radio,String color){
			return "<circle cx='"+punto.x+"' cy='"+punto.y+"' r='"+182+"' str"
					+ "oke='white' stroke-width='60' fill=\" "+color+"\"  />"; 
				
				
			}

		
				private Punto calculaPunto(double d) {
					return new Punto( (int) ((Math.sin(d)*radio)+centro.x),(int) ((Math.cos(d)*radio)+centro.y));
					}
					
				
				
				
				

			
				

				private String dibujaInfo(Palabra palabra, Punto punto, String color) {
					
					Punto auxiliar =new Punto(punto.x+62+palabra.palabra.length()*3,punto.y+15);
					double a=palabra.repeticiones;
			    	double b=numeroDePalabras;
			    	return dibujaRectangulo(punto,""+color+"")
			    		+dibujaElemento(auxiliar
			    			,16,"black",palabra.palabra +" "+String.valueOf(round((a/b)*100)) +"% ");
				}     

				private String dibujaPath(Punto punto, Punto centro2,Punto punto1 ,String color) {
					
						return " <path d=\"M "+String.valueOf(punto.x)+" "+String.valueOf(punto.y)+""
								+ " L "+String.valueOf(centro.x)+" "+String.valueOf(centro.y)+""
								+ " L "+String.valueOf(punto1.x)+" "+String.valueOf(punto1.y)+
								" A 20 20 0 1 1 "+String.valueOf(punto.x)+" "+String.valueOf(punto.y)+
								" Z\" stroke=\""+color+"\"stroke-width=\"1\" fill=\""+color+ "\" />";
						 
				}
				
				private String dibujaElemento(Punto punto ,int tamano , String color,String elemento){
			
				return "<text fill='"+color+"' font-family='sans-serif'"
			                + " font-size='"+tamano+"'"
			                + " x='"+punto.x+"' "
			                + "y='"+punto.y+"'" +salto+
			               "text-anchor='middle'>"+elemento+"</text>" ;
				}
				
				private double round(double d) {
				    BigDecimal bd = new BigDecimal(d);
				    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				    return bd.doubleValue();
				}
				
				private int getPorcentaje(int n) {
					return (int)((n*100)/numeroDePalabras);
				}
				
				
			
			
			
			
		
	}
	
	private static String creaEntradaHTML(String titulo,String color) {
		
		return "<HTML>\n<HEAD>\n</HEAD> \n <TITLE>Analisis de Texto"
				+ "</TITLE>\n<center><h1><FONT FACE=\"Arial\" S"
				+ "IZE=\"50\" COLOR=\"0000FF\">"+titulo+"</FONT>"
				+ "</h1></center> \n<BODY BGCOLOR=\""+color+"\">";
		 
	}
	private static String creaSalidaHTML() {
		return "</BODY></HTML>";
	}
	private static String creaEntrada(int ancho,int largo){
		return "<?x"+"ml version='1.0' encoding='UTF-8' ?>"+
					 "<svg width='"+ancho+"' height='"+largo+"'>"+"<g>";}
	
	private static String creaSalida(){return   " </g></svg>";}
	 
	 public String remove(String input) {
	    	return Normalizer
	         .normalize(input, Normalizer.Form.NFD)
	         .replaceAll("[^\\p{ASCII}]", "");
	    }
	    	
	 private static String dibujaElemento(int x, int y,int tamano , String color,String elemento){
			
			return "<text fill='"+color+"' font-family='sans-serif'"
		                + " font-size='"+tamano+"'"
		                + " x='"+x+"' "
		                + "y='"+y+"'" +
		               "text-anchor='middle'>"+elemento+"</text>" ;
				
				
			}
	
	 
	 
	    public  void creaListaDePalabras(){
			String contenido="";
			
			
			try{
				BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(direccion)));
				String linea=null;
				while((linea=in.readLine())!=null){
				
				contenido+= remove(linea).replaceAll("[^\\p{L}\\p{Nd}]+"," ")+" ";
				}
				in.close();
					
				}catch(IOException ioe){
					ioe.printStackTrace();
					System.err.println("Ocurrio un error al tratar de leer "+direccion+".");
					System.exit(1);
					
				}catch(NumberFormatException ioe){
					ioe.printStackTrace();
					System.exit(1);
					
				}
				
				String[]ps =contenido.split(" ");
				numeroDePalabras=ps.length;
				
				
				Diccionario<String,Palabra> diccionario = new Diccionario <String,Palabra>();
				for(String palabra:ps){
					if(!palabra.equals("")){
						if(diccionario.contiene(palabra)){
							diccionario.get(palabra).repeticiones++;
						}else{
							diccionario.agrega(palabra, new Palabra(palabra));
						}
					}
				}
				this.diccionario=diccionario;
				
			palabras=Lista.mergeSort(diccionario.valores()); 
			if(palabras.getLongitud()>8){
				Lista<Palabra> temporal= new Lista<Palabra>();
				int i =0;
				int conteo=0;;
				for(Palabra palabra:palabras){
					temporal.agrega(palabra);
					conteo+=palabra.repeticiones;
					i++;
					
					if(i>=9){
						Palabra otros = new Palabra("otros");
						otros.repeticiones=(int) (numeroDePalabras-conteo);
					    temporal.agrega(otros);
						break;
						
					}
				}
				
				palabras=temporal;
				
			}
		   
		    }
	    
	    
	    /**
	     * Función que elimina acentos y caracteres especiales de
	     * una cadena de texto.
	     * @param input
	     * @return cadena de texto limpia de acentos y caracteres especiales.
	     */
	  
	  
	  public static void generaArchivo(String direccion,String texto){
	 	   try {
	        	File archivo = new File(direccion);
	            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
	            bw.write(texto);
	            bw.close();
	            
	           
	            
	        } catch(IOException e) {
	            System.err.println("No se ha podido crear el archivo " +e);
	        }
	 	   
	    }
	
	public String toString(){
		return new HTML(this.direccion).texto;}

}
