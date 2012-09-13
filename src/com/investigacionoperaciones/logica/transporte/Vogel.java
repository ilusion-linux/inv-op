package com.investigacionoperaciones.logica.transporte;

public class Vogel extends MatrizTransporte
{
	private int [] intFilaTemp;						
	private int [] intColumnaTemp;					
//---------------------------------------------------------------------------------------------------------------
	public Vogel(int [][] matriz)
	{
		super(matriz);								
	}
//---------------------------------------------------------------------------------------------------------------
	public int siguiente()
	{
		if(calcularPenalizaciones()==0)
		{
			obtenerPuntoVogel();
			consumirOfertaDemanda();
			return 0;
		}
		return 1;
	}
	public void resolver()
	{
		if(siguiente()==0)
		{
			resolver();
		}
	}
	private int calcularPenalizaciones()
	{
		int intEstado=1;
		
		for(int f=0; f<(intTotalFila-1); f++)
		{
			intFilPen[f]=-1;
			if(intSolucion[f][intTotalColumna-1]>0)
			{
				intEstado=0;
				intFilPen[f]=obtenerPenalizacionFila(f);
			}
		}
		
		for(int c=0; c<(intTotalColumna-1); c++)
		{	
			intColPen[c]=-1;
			if(intSolucion[intTotalFila-1][c]>0)
			{
				intEstado=0;
				intColPen[c]=obtenerPenalizacionColumna(c);
			}
		}
		
		return intEstado;
	}
	private int obtenerPenalizacionFila(int fila)
	{
		int aux=0;
		
		for(int c=0; c<(intTotalColumna-1); c++)
		{
			if(intSolucion[(intTotalFila-1)][c]>0)
			{
				++aux;
			}
		}
		
		intFilaTemp=new int[aux];
		aux=0;
		
		for(int c=0; c<(intTotalColumna-1); c++)
		{
			if(intSolucion[(intTotalFila-1)][c]>0)
			{
				intFilaTemp[aux]=intMatriz[fila][c];
				++aux;
			}
		}
		
		if(aux==1)
		{
			aux=intFilaTemp[0];
		}
		else
		{
			ordenar(intFilaTemp);
			aux=intFilaTemp[1]-intFilaTemp[0];
		}
		
		return aux;
		
	}
	private int obtenerPenalizacionColumna(int columna)
	{
		int aux=0;
		
		for(int f=0; f<(intTotalFila-1); f++)
		{
			if(intSolucion[f][(intTotalColumna-1)]>0)
			{
				++aux;
			}
		}
		
		intColumnaTemp=new int[aux];
		aux=0;
		
		for(int f=0; f<(intTotalFila-1); f++)
		{
			if(intSolucion[f][intTotalColumna-1]>0)
			{
				intColumnaTemp[aux]=intMatriz[f][columna];
				++aux;
			}
		}
		
		if(aux==1)
		{
			aux=intColumnaTemp[0];
		}
		else
		{
			ordenar(intColumnaTemp);
			aux=intColumnaTemp[1]-intColumnaTemp[0];
		}
		
		return aux;
	}
//---------------------------------------------------------------------------------------------------------------
}