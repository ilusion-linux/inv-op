package com.investigacionoperaciones.logica.transporte;

public class CostoMinimo extends MatrizTransporte
{
	public CostoMinimo(int [][] matriz)
	{
		super(matriz);
	}
//---------------------------------------------------------------------------------------------------------------
	public int siguiente()
	{
		if(obtenerPuntoCostoMinimo()==0)
		{
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
//---------------------------------------------------------------------------------------------------------------
}