package com.investigacionoperaciones.logica.transporte;

public class EsquinaNorOeste extends MatrizTransporte
{
	public EsquinaNorOeste(int [][] matriz)
	{
		super(matriz);
	}
//---------------------------------------------------------------------------------------------------------------
	public int siguiente()
	{
		if(obtenerPuntoEsquinaNorOeste()==0)
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
