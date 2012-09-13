package com.investigacionoperaciones.logica.transporte;

public class MatrizTransporte extends Multiplicadores
{
	public int [][] intRecorrido;
	public int [][] intMatriz;						
	public int [][] intSolucion;					
	public int [] intFilPen;						
	public int [] intColPen;						
	public int intTotalFila;						
	public int intTotalColumna;						
	
	private int intFila;							 
	private int intColumna;							
	private int intResultado;						
	private final int intArriba=1;
	private final int intIzquierda=2;
	private final int intAbajo=3;
	private final int intDerecha=4;
//---------------------------------------------------------------------------------------------------------------
	public MatrizTransporte(int [][] matriz)
	{
		intMatriz=matriz;
		intTotalFila=intMatriz.length;
		intTotalColumna=intMatriz[0].length;
		intSolucion=new int[intTotalFila][intTotalColumna];
		
		for(int f=0; f<intTotalFila; f++)
		{
			intSolucion[f][intTotalColumna-1]=intMatriz[f][intTotalColumna-1];
		}
		
		for(int c=0; c<intTotalColumna; c++)
		{	
			intSolucion[intTotalFila-1][c]=intMatriz[intTotalFila-1][c];
		}
		
		intFilPen=new int[intTotalFila-1];
		intColPen=new int[intTotalColumna-1];
	}
//---------------------------------------------------------------------------------------------------------------
	public void obtenerPuntoVogel()
	{
		intFila=0;
		intColumna=-1;
		int intMayor=-1;
		int intMayorAux=-1;

		for(int x=0; x<(intTotalFila-1); x++)
		{
			if((intFilPen[x]>-1))
			{
				intFila=x;
				intMayor=intFilPen[x];
				intMayorAux=intMayor;
				x=intTotalFila;
			}
		}
		
		for(int x=0; x<(intTotalFila-1); x++)
		{
			if((intFilPen[x]>-1) && (intFilPen[x]>intMayor))
			{
				intFila=x;
				intMayor=intFilPen[x];
				intMayorAux=intMayor;
			}
			else if((intFilPen[x]>-1) && (intFilPen[x]==intMayor) && (x!=intFila))
			{
				int intAuxA=intFila;
				int intAuxB=intMayor;
				
				intFila=x;
				intMayor=intFilPen[x];
				int intAuxC=obtenerElementoVogel();
				
				if(intAuxC>intMayorAux)
				{
					intMayorAux=intAuxC;
				}
				else
				{
					intFila=intAuxA;
					intMayor=intAuxB;
				}
			}
		}
		
		for(int x=0; x<(intTotalColumna-1); x++)
		{
			if((intColPen[x]>-1) && (intColPen[x]>intMayor))
			{
				intColumna=x;
				intMayor=intColPen[x];
			}
			else if((intColPen[x]>-1) && (intColPen[x]==intMayor) && (x!=intColumna))
			{
				int intAuxA=intColumna;
				int intAuxB=intMayor;
				
				intColumna=x;
				intMayor=intColPen[x];
				int intAuxC=obtenerElementoVogel();
				
				if(intAuxC>intMayorAux)
				{
					intMayorAux=intAuxC;
				}
				else
				{
					intColumna=intAuxA;
					intMayor=intAuxB;
				}
			}
		}
		
		obtenerElementoVogel();
	}
	public int obtenerPuntoCostoMinimo()
	{
		int intRetorno=1;
		int intMenor=0;
		int intMayorAux=-1;
		
		for(int f=0; f<intTotalFila-1; f++)
		{
			for(int c=0; c<intTotalColumna-1; c++)
			{
				if(intSolucion[f][intTotalColumna-1]>0 && intSolucion[intTotalFila-1][c]>0)
				{
					intMenor=intMatriz[f][c];
					intFila=f;
					intColumna=c;
					f=intTotalFila;
					c=intTotalColumna;
					intRetorno=0;
				}
			}
		}
		
		if(intRetorno==0)
		{
			for(int f=0; f<intTotalFila-1; f++)
			{
				if(intSolucion[f][intTotalColumna-1]>0)
				{
					for(int c=0; c<intTotalColumna-1; c++)
					{
						if(intSolucion[intTotalFila-1][c]>0  && intMatriz[f][c]<intMenor)
						{
							intMenor=intMatriz[f][c];
							intFila=f;
							intColumna=c;
						}
						else if(intSolucion[intTotalFila-1][c]>0  && intMatriz[f][c]==intMenor)
						{
							int intAuxA=intMenor;
							int intAuxB=intFila;
							int intAuxC=intColumna;
							
							intMenor=intMatriz[f][c];
							intFila=f;
							intColumna=c;
							int intAuxD=simularOfertaDemanda();
							
							if(intAuxD>intMayorAux)
							{
								intMayorAux=intAuxD;
							}
							else
							{
								intMenor=intAuxA;
								intFila=intAuxB;
								intColumna=intAuxC;
							}
						}
					}
				}
			}
		}
		return intRetorno;
	}
	public int obtenerPuntoEsquinaNorOeste()
	{
		int intRetorno=1;
		intFila=0;
		intColumna=0;
		
		for(int f=0; f<intTotalFila; f++)
		{
			if(intSolucion[f][intTotalColumna-1]>0)
			{
				intFila=f;
				f=intTotalFila;
				for(int c=0; c<intTotalColumna; c++)
				{
					if(intSolucion[intTotalFila-1][c]>0)
					{
						intRetorno=0;
						intColumna=c;
						c=intTotalColumna;
					}
				}
			}
		}
		return intRetorno;
	}
	public void consumirOfertaDemanda()
	{
		if(intSolucion[intFila][intTotalColumna-1]==intSolucion[intTotalFila-1][intColumna])
		{
			intSolucion[intFila][intColumna]=intSolucion[intFila][intTotalColumna-1];
			intSolucion[intFila][intTotalColumna-1]=0;
			intSolucion[intTotalFila-1][intColumna]=0;
		}
		else if(intSolucion[intFila][intTotalColumna-1]>intSolucion[intTotalFila-1][intColumna])
		{
			intSolucion[intFila][intColumna]=intSolucion[intTotalFila-1][intColumna];
			intSolucion[intFila][intTotalColumna-1]=intSolucion[intFila][intTotalColumna-1]-intSolucion[intTotalFila-1][intColumna];
			intSolucion[intTotalFila-1][intColumna]=0;
		}
		else
		{
			intSolucion[intFila][intColumna]=intSolucion[intFila][intTotalColumna-1];
			intSolucion[intTotalFila-1][intColumna]=intSolucion[intTotalFila-1][intColumna]-intSolucion[intFila][intTotalColumna-1];
			intSolucion[intFila][intTotalColumna-1]=0;
		}
	}
	public String resolverEcuacion()
	{
		String retorno="resultado=";
		intResultado=0;
		for(int f=0; f<intTotalFila-1; f++)
		{
			for(int c=0; c<intTotalColumna-1; c++)
			{
				if(intSolucion[f][c]>0)
				{
					intResultado=intResultado+(intSolucion[f][c]*intMatriz[f][c]);
					retorno=retorno+Integer.toString(intSolucion[f][c])+"*"+Integer.toString(intMatriz[f][c])+"+";
				}
			}
		}
		retorno=retorno.substring(0, retorno.length()-1)+"\nresultado="+Integer.toString(intResultado);
		return retorno;
	}
	public void ordenar(int [] ordenar)
	{
		for(int x=0; x<ordenar.length; x++)
		{
			for(int y=0; y<ordenar.length; y++)
			{
				int aux;
				if(ordenar[y]>ordenar[x])
				{
					aux=ordenar[y];
					ordenar[y]=ordenar[x];
					ordenar[x]=aux;
				}
			}
		}
	}
	@Override
	public int [][] optimizar()
	{
		evaluarConValor(intMatriz, intSolucion);
		if(evaluarSinValor(intMatriz, intSolucion)>-1)
		{
			if((intCoordenada=recorrer(intCoordenada, 0))!=null)
			{
				intCoordenada=separarEsquinas(intCoordenada);
				intCoordenada=operarMultiplicador(intCoordenada);
				
				return intCoordenada;
			}
		}
		return null;
	}
	@Override
	public void terminar()
	{
		evaluarConValor(intMatriz, intSolucion);
		if(evaluarSinValor(intMatriz, intSolucion)>-1)
		{
			if((intCoordenada=recorrer(intCoordenada, 0))!=null)
			{
				intCoordenada=separarEsquinas(intCoordenada);
				intCoordenada=operarMultiplicador(intCoordenada);
				terminar();
			}
		}
	}
	@Override
	public int [][] recorrer(int [][] intRecorrer, int intLlegada)
	{
		int [][] intRetorno=null;

		if(intLlegada!=intArriba)
		{
			for(int f=(intRecorrer[(intRecorrer.length-1)][0]-1); f>=0; f--)
			{
				if(intSolucion[f][intRecorrer[(intRecorrer.length-1)][1]]>=0)
				{
					int [][] intAux=new int[(intRecorrer.length+1)][3];
					
					for(int f1=0; f1<intRecorrer.length; f1++)
					{
						intAux[f1][0]=intRecorrer[f1][0];
						intAux[f1][1]=intRecorrer[f1][1];
						intAux[f1][2]=intRecorrer[f1][2];
					}
					
					intAux[intRecorrer.length][0]=f;
					intAux[intRecorrer.length][1]=intRecorrer[(intRecorrer.length-1)][1];
					intAux[intRecorrer.length][2]=intSolucion[f][intRecorrer[(intRecorrer.length-1)][1]];
					
					if(intRecorrer[0][0]==intAux[(intAux.length-1)][0] && intRecorrer[0][1]==intAux[(intAux.length-1)][1])
					{
						return intRecorrer;
					}
					else if((intRetorno=recorrer(intAux, intAbajo))!=null)
					{
						return intRetorno;
					}
				}
			}
		}

		if(intLlegada!=intIzquierda)
		{
			for(int c=(intRecorrer[(intRecorrer.length-1)][1]+1); c<(intTotalColumna-1); c++)
			{
				if(intSolucion[intRecorrer[(intRecorrer.length-1)][0]][c]>=0)
				{
					int [][] intAux=new int[(intRecorrer.length+1)][3];
					
					for(int f1=0; f1<intRecorrer.length; f1++)
					{
						intAux[f1][0]=intRecorrer[f1][0];
						intAux[f1][1]=intRecorrer[f1][1];
						intAux[f1][2]=intRecorrer[f1][2];
					}
					
					intAux[intRecorrer.length][0]=intRecorrer[(intRecorrer.length-1)][0];
					intAux[intRecorrer.length][1]=c;
					intAux[intRecorrer.length][2]=intSolucion[intRecorrer[(intRecorrer.length-1)][0]][c];
										
					if(intAux[0][0]==intAux[(intAux.length-1)][0] && intAux[0][1]==intAux[(intAux.length-1)][1])
					{
						return intRecorrer;
					}
					else if((intRetorno=recorrer(intAux, intDerecha))!=null)
					{
						return intRetorno;
					}
				}
			}
		}
		
		if(intLlegada!=intAbajo)
		{
			for(int f=(intRecorrer[(intRecorrer.length-1)][0]+1); f<(intTotalFila-1); f++)
			{
				if(intSolucion[f][intRecorrer[(intRecorrer.length-1)][1]]>=0)
				{
					int [][] intAux=new int[(intRecorrer.length+1)][3];
					
					for(int f1=0; f1<intRecorrer.length; f1++)
					{
						intAux[f1][0]=intRecorrer[f1][0];
						intAux[f1][1]=intRecorrer[f1][1];
						intAux[f1][2]=intRecorrer[f1][2];
					}
					
					intAux[intRecorrer.length][0]=f;
					intAux[intRecorrer.length][1]=intRecorrer[(intRecorrer.length-1)][1];
					intAux[intRecorrer.length][2]=intSolucion[f][intRecorrer[(intRecorrer.length-1)][1]];
									
					if(intAux[0][0]==intAux[(intAux.length-1)][0] && intAux[0][1]==intAux[(intAux.length-1)][1])
					{
						return intRecorrer;
					}
					else if((intRetorno=recorrer(intAux, intArriba))!=null)
					{
						return intRetorno;
					}
				}
			}
		}
		
		if(intLlegada!=intDerecha)
		{
			for(int c=(intRecorrer[(intRecorrer.length-1)][1]-1); c>=0; c--)
			{
				if(intSolucion[intRecorrer[(intRecorrer.length-1)][0]][c]>=0)
				{
					int [][] intAux=new int[(intRecorrer.length+1)][3];
					
					for(int f1=0; f1<intRecorrer.length; f1++)
					{
						intAux[f1][0]=intRecorrer[f1][0];
						intAux[f1][1]=intRecorrer[f1][1];
						intAux[f1][2]=intRecorrer[f1][2];
					}
					
					intAux[intRecorrer.length][0]=intRecorrer[(intRecorrer.length-1)][0];
					intAux[intRecorrer.length][1]=c;
					intAux[intRecorrer.length][2]=intSolucion[intRecorrer[(intRecorrer.length-1)][0]][c];
					
					if(intAux[0][0]==intAux[(intAux.length-1)][0] && intAux[0][1]==intAux[(intAux.length-1)][1])
					{
						return intRecorrer;						
					}
					else if((intRetorno=recorrer(intAux, intIzquierda))!=null)
					{
						return intRetorno;
					}
				}
			}
		}
		
		return  intRetorno;
	}
	@Override
	public int [][] operarMultiplicador(int [][] intCoor)
	{
		int intMenor=intCoor[1][2];
		int intIndiceMenor=1;
		
		for(int f=0; f<intCoor.length; f++)
		{
			if((f%2==1) && (intCoor[f][2]<intMenor))
			{
				intMenor=intCoor[f][2];
				intIndiceMenor=f;
			}
		}
		
		if(intMenor==0)
		{
			intSolucion[intCoor[intIndiceMenor][0]][intCoor[intIndiceMenor][1]]=-1;
		}
		else
		{
			intCoor=operarCirculo(intCoor, intMenor);
		}
		
		return intCoor;
	}
//---------------------------------------------------------------------------------------------------------------
	private int obtenerElementoVogel()
	{
		int intMenor=0;

		if(intColumna>-1)
		{
			intFila=0;
			
			for(int x=0; x<intTotalFila; x++)
			{
				if(intSolucion[x][intTotalColumna-1]>0)
				{
					intFila=x;
					intMenor=intMatriz[x][intColumna];
					x=intTotalFila;
				}
			}
			
			for(int x=0; x<intTotalFila; x++)
			{
				if(intMatriz[x][intColumna]<intMenor && intSolucion[x][intTotalColumna-1]>0)
				{
					intFila=x;
					intMenor=intMatriz[x][intColumna];
				}
			}
		}
		else
		{
			intColumna=0;
			
			for(int x=0; x<intTotalColumna; x++)
			{
				if(intSolucion[intTotalFila-1][x]>0)
				{
					intColumna=x;
					intMenor=intMatriz[intFila][x];
					x=intTotalColumna;
				}
			}
			
			for(int x=0; x<intTotalColumna; x++)
			{
				if(intMatriz[intFila][x]<intMenor && intSolucion[intTotalFila-1][x]>0)
				{
					intColumna=x;
					intMenor=intMatriz[intFila][x];
				}
			}
		}
		return intMenor;
	}
	private int simularOfertaDemanda()
	{
		if(intSolucion[intFila][intTotalColumna-1]==intSolucion[intTotalFila-1][intColumna])
		{
			return intSolucion[intFila][intTotalColumna-1];
		}
		else if(intSolucion[intFila][intTotalColumna-1]>intSolucion[intTotalFila-1][intColumna])
		{
			return intSolucion[intTotalFila-1][intColumna];
		}
		else
		{
			return intSolucion[intFila][intTotalColumna-1];
		}
	}
//---------------------------------------------------------------------------------------------------------------
}
