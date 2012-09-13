package com.investigacionoperaciones.logica.transporte;

public abstract class Multiplicadores
{
	public int [][] intCoordenada;
	
	private int [] intU;
	private int [] intV;
	private int [] intValor;
//----------------------------------------------------------------------------------------------------
	public abstract int [][] optimizar();
	public abstract void terminar();
	public abstract int [][] recorrer(int [][] intRecorrer, int intLlegada);
	public abstract int [][] operarMultiplicador(int [][] intCoor);
	public void evaluarConValor(int [][] intMatriz, int [][] intSolucion)
	{
		int intAux=0;
		
		intU=new int[intMatriz.length-1];
		intV=new int[intMatriz[0].length-1];
		
		inicializar(intSolucion);
		
		for(int f=0; f<intSolucion.length-1; f++)
		{
			for(int c=0; c<intSolucion[0].length-1; c++)
			{
				if(intSolucion[f][c]>-1)
				{
					++intAux;
				}
			}
		}
		
		intValor=new int [intAux];
		intCoordenada=new int [intAux][2];
		
		intAux=0;
		for(int f=0; f<intSolucion.length-1; f++)
		{
			for(int c=0; c<intSolucion[0].length-1; c++)
			{
				if(intSolucion[f][c]>-1)
				{
					intCoordenada[intAux][0]=f;
					intCoordenada[intAux][1]=c;
					intValor[intAux]=intMatriz[f][c];
					++intAux;
				}
			}
		}
		
		operarUV();
	}
	public int evaluarSinValor(int [][] intMatriz, int [][] intSolucion)
	{
		int intMayor=-1;
		intCoordenada=new int[1][3];
		
		for(int f=0; f<intSolucion.length-1; f++)
		{
			for(int c=0; c<intSolucion[0].length-1; c++)
			{
				if(intSolucion[f][c]==-1)
				{
					int intAux=intU[f]+intV[c]-intMatriz[f][c];
					
					if((intAux>=0) && (intMayor<intAux))
					{
						intMayor=intU[f]+intV[c]-intMatriz[f][c];
						intCoordenada[0][0]=f;
						intCoordenada[0][1]=c;
						intCoordenada[0][2]=0;
					}
				}
			}
		}
		
		if(intMayor>-1)
		{
			intSolucion[intCoordenada[0][0]][intCoordenada[0][1]]=0;
		}
		
		return intMayor;
	}
	public int [][] separarEsquinas(int [][] intCoor)
	{
		int intAux=0;
		int intTempA=intCoor[0][0];
		int intTempB=intCoor[0][1];
		int intTempC=0;
		
		for(int x=1; x<intCoor.length; x++)
		{
			switch(intTempC)
			{
				case 0:
					if(intTempA==intCoor[x][0])
					{
						intTempC=1;
					}
					else if(intTempB==intCoor[x][1])
					{
						intTempC=2;
					}
				break;
				case 1:
					if(intTempA!=intCoor[x][0])
					{
						intAux=intAux+2;
						intTempC=0;
						intTempA=intCoor[x][0];
						intTempB=intCoor[x][1];
					}
				break;
				case 2:
					if(intTempB!=intCoor[x][1])
					{
						intAux=intAux+2;
						intTempC=0;
						intTempA=intCoor[x][0];
						intTempB=intCoor[x][1];
					}
				break;
			}
		}
		
		int [][] intTemp=new int[intAux+2][3];
		
		intAux=1;
		intTempC=0;
		intTempA=intCoor[0][0];
		intTempB=intCoor[0][1];

		intTemp[0][0]=intCoor[0][0];
		intTemp[0][1]=intCoor[0][1];
		intTemp[0][2]=intCoor[0][2];
		
		for(int x=1; x<intCoor.length; x++)
		{
			switch(intTempC)
			{
				case 0:
					if(intTempA==intCoor[x][0])
					{
						intTempC=1;
					}
					else if(intTempB==intCoor[x][1])
					{
						intTempC=2;
					}
				break;
				case 1:
					if(intTempA!=intCoor[x][0])
					{
						intTemp[intAux][0]=intCoor[x-1][0];
						intTemp[intAux][1]=intCoor[x-1][1];
						intTemp[intAux][2]=intCoor[x-1][2];
						++intAux;
						
						intTemp[intAux][0]=intCoor[x][0];
						intTemp[intAux][1]=intCoor[x][1];
						intTemp[intAux][2]=intCoor[x][2];
						++intAux;
						
						intTempC=0;
						intTempA=intCoor[x][0];
						intTempB=intCoor[x][1];
					}
				break;
				case 2:
					if(intTempB!=intCoor[x][1])
					{
						intTemp[intAux][0]=intCoor[x-1][0];
						intTemp[intAux][1]=intCoor[x-1][1];
						intTemp[intAux][2]=intCoor[x-1][2];
						++intAux;
						
						intTemp[intAux][0]=intCoor[x][0];
						intTemp[intAux][1]=intCoor[x][1];
						intTemp[intAux][2]=intCoor[x][2];
						++intAux;
						
						intTempC=0;
						intTempA=intCoor[x][0];
						intTempB=intCoor[x][1];
					}
				break;
			}
		}
		
		intTemp[intTemp.length-1][0]=intCoor[intCoor.length-1][0];
		intTemp[intTemp.length-1][1]=intCoor[intCoor.length-1][1];
		intTemp[intTemp.length-1][2]=intCoor[intCoor.length-1][2];
		
		return intTemp;
	}
	public int [][] operarCirculo(int [][] intCoor, int intMenor)
	{
		for(int f=0; f<intCoor.length; f++)
		{
			if((f%2)==0)
			{
				intCoor[f][2]=intCoor[f][2]+intMenor;
			}
			else
			{
				intCoor[f][2]=intCoor[f][2]-intMenor;
			}
		}
		
		return intCoor;
	}
//---------------------------------------------------------------------------------------------------------------
	private void operarUV()
	{
		for(int f=0; f<intValor.length; f++)
		{
			if(intU[intCoordenada[f][0]]==-1 && intV[intCoordenada[f][1]]==-1)
			{
				intU[intCoordenada[f][0]]=0;
				intV[intCoordenada[f][1]]=intValor[f];
			}
			else
			{
				if(intU[intCoordenada[f][0]]==-1)
				{
					intU[intCoordenada[f][0]]=intValor[f]-intV[intCoordenada[f][1]];
				}
				else
				{
					intV[intCoordenada[f][1]]=intValor[f]-intU[intCoordenada[f][0]];
				}
			}
			
		}
	}
	private void inicializar(int [][] intSolucion)
	{
		for(int f=0; f<intSolucion.length; f++)
		{
			for(int c=0; c<intSolucion[0].length; c++)
			{
				switch(intSolucion[f][c])
				{
					case 0:
						intSolucion[f][c]=-1;
					break;
					case -2:
						intSolucion[f][c]=0;
					break;
				}
			}
		}
		for(int x=0; x<intU.length; x++)
		{
			intU[x]=-1;
		}
		for(int x=0; x<intV.length; x++)
		{
			intV[x]=-1;
		}
	}
//---------------------------------------------------------------------------------------------------------------
}