package com.investigacionoperaciones;

import com.investigacionoperaciones.logica.transporte.CostoMinimo;
import com.investigacionoperaciones.logica.transporte.EsquinaNorOeste;
import com.investigacionoperaciones.logica.transporte.Vogel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ActivityTransporteResolucion extends Activity implements OnClickListener
{
	private Vogel vogel;					
	private CostoMinimo costo;				
	private EsquinaNorOeste esquina;		

	private int [][] intMatriz;				
	private int [][] intCoordenadaAnterior;	
	private String strMetodo;				
	private int intVariable;				
	private int intRestriccion;				
	private int intEstado;					
	
	private TextView txtTitulo;
	private TableLayout lytMatriz;
	private LinearLayout lytLinear;
	private Button btnSiguiente;
	private Button btnFinalizar;
	private Button btnCerrar;
	
	private TextView txtResultado;
	private EditText [][] txtMatriz;
	private EditText [][] auxA;
	private TableRow [] rowLayout;
	private LinearLayout [][] auxLayout;
	private LinearLayout [][] temLayout;
	private AlertDialog alert;
//---------------------------------------------------------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.transporteresolucion);
		
		txtTitulo=(TextView)this.findViewById(R.id.titulo);
		lytMatriz=(TableLayout)this.findViewById(R.id.layout_matriz);
		lytLinear=(LinearLayout)this.findViewById(R.id.layout_linear);
		btnSiguiente=(Button)this.findViewById(R.id.siguiente);
		btnFinalizar=(Button)this.findViewById(R.id.finalizar);
		btnCerrar=(Button)this.findViewById(R.id.cerrar);
		
		btnSiguiente.setOnClickListener(this);
		btnFinalizar.setOnClickListener(this);
		btnCerrar.setOnClickListener(this);
		
		leerParametros(this.getIntent().getExtras());
		intEstado=0;
	}
	public void onClick(View v)
	{
		if(v.getId()==btnSiguiente.getId())
		{
			clickSiguiente();
		}
		else if(v.getId()==btnFinalizar.getId())
		{
			clickFinalizar();
		}
		else if(v.getId()==btnCerrar.getId())
		{
			this.finish();
		}
	}
//---------------------------------------------------------------------------------------------------------------
	private void clickSiguiente()
	{
		if(intEstado==0)
		{
			crearMatriz();
			matrizOriginal();
			intEstado=1;
			mensaje("Aviso", "Matriz creada\npresione el bonto siguiente o el boton resolver");
			btnSiguiente.setBackgroundResource(R.drawable.siguiente);
		}
		else if(intEstado==1)
		{
			if(strMetodo.compareToIgnoreCase("vogel")==0)
			{
				if(vogel.siguiente()==0)
				{
					actualizarMatriz(vogel.intSolucion);
				}
				else
				{
					if(txtResultado==null)
					{
						escribirSolucion(vogel.resolverEcuacion());
					}
					intEstado=2;
					mensaje("Aviso", "Matriz resuelta\npresione el boton iniciar para resolver multiplicadores");
					btnSiguiente.setBackgroundResource(R.drawable.crear);
				}
			}
			else if(strMetodo.compareToIgnoreCase("costo minimo")==0)
			{
				if(costo.siguiente()==0)
				{
					actualizarMatriz(costo.intSolucion);
				}
				else
				{
					if(txtResultado==null)
					{
						escribirSolucion(costo.resolverEcuacion());
					}
					intEstado=2;
					mensaje("Aviso", "Matriz resuelta\npresione el boton iniciar para resolver multiplicadores");
					btnSiguiente.setBackgroundResource(R.drawable.crear);
				}
			}
			else if(strMetodo.compareToIgnoreCase("esquina nor oeste")==0)
			{
				if(esquina.siguiente()==0)
				{
					actualizarMatriz(esquina.intSolucion);
				}
				else
				{
					if(txtResultado==null)
					{
						escribirSolucion(esquina.resolverEcuacion());
					}
					intEstado=2;
					mensaje("Aviso", "Matriz resuelta\npresione el boton iniciar para resolver multiplicadores");
					btnSiguiente.setBackgroundResource(R.drawable.crear);
				}
			}
		}
		else if(intEstado==2)
		{
			btnSiguiente.setBackgroundResource(R.drawable.siguiente);
			int [][] intCoor;
			if(strMetodo.compareToIgnoreCase("vogel")==0)
			{
				if((intCoor=vogel.optimizar())==null)
				{
					intEstado=3;
					txtResultado.append("\n\n"+vogel.resolverEcuacion());
					mensaje("Aviso", "Ya no se puede optimizar mas.");
				}
				else
				{
					actualizarMultiplicadores(intCoor, vogel.intSolucion);
				}
			}
			else if(strMetodo.compareToIgnoreCase("costo minimo")==0)
			{
				if((intCoor=costo.optimizar())==null)
				{
					intEstado=3;
					txtResultado.append("\n\n"+costo.resolverEcuacion());
					mensaje("Aviso", "Ya no se puede optimizar mas.");
				}
				else
				{
					actualizarMultiplicadores(intCoor, costo.intSolucion);
				}
			}
			else if(strMetodo.compareToIgnoreCase("esquina nor oeste")==0)
			{
				if((intCoor=esquina.optimizar())==null)
				{
					intEstado=3;
					txtResultado.append("\n\n"+esquina.resolverEcuacion());
					mensaje("Aviso", "Ya no se puede optimizar mas.");
				}
				else
				{
					actualizarMultiplicadores(intCoor, esquina.intSolucion);
				}
			}
		}
	}
	private void clickFinalizar()
	{
		if(intEstado==2)
		{
			if(strMetodo.compareToIgnoreCase("vogel")==0)
			{
				vogel.terminar();
				actualizarMultiplicadores(vogel.intCoordenada, vogel.intSolucion);
			}
			else if(strMetodo.compareToIgnoreCase("costo minimo")==0)
			{
				costo.terminar();
				actualizarMultiplicadores(costo.intCoordenada, costo.intSolucion);
			}
			else if(strMetodo.compareToIgnoreCase("esquina nor oeste")==0)
			{
				esquina.terminar();
				actualizarMultiplicadores(esquina.intCoordenada, esquina.intSolucion);
			}
		}
		else
		{
			if(strMetodo.compareToIgnoreCase("vogel")==0)
			{
				vogel.resolver();
				actualizarMatriz(vogel.intSolucion);
			}
			else if(strMetodo.compareToIgnoreCase("costo minimo")==0)
			{
				costo.resolver();
				actualizarMatriz(costo.intSolucion);
			}
			else if(strMetodo.compareToIgnoreCase("esquina nor oeste")==0)
			{
				esquina.resolver();
				actualizarMatriz(esquina.intSolucion);
			}
		}
	}
	private void leerParametros(Bundle param)
	{
		intVariable=param.getInt("variable");
		intRestriccion=param.getInt("restriccion");
		strMetodo=param.getString("metodo");
		txtTitulo.setText("Resolucion por medio del metodo "+strMetodo);
		intMatriz=new int[intRestriccion][intVariable];
		
		for(int f=0; f<intRestriccion; f++)
		{
			int aux[]=param.getIntArray(Integer.toString(f));
			for(int c=0; c<intVariable; c++)
			{
				intMatriz[f][c]=aux[c];
			}
		}
		
		if(strMetodo.compareToIgnoreCase("vogel")==0)
		{
			vogel=new Vogel(intMatriz);
		}
		else if(strMetodo.compareToIgnoreCase("costo minimo")==0)
		{
			costo=new CostoMinimo(intMatriz);
		}
		else if(strMetodo.compareToIgnoreCase("esquina nor oeste")==0)
		{
			esquina=new EsquinaNorOeste(intMatriz);
		}
	}
	private void crearMatriz()
	{
		int fila=intRestriccion*2;
		int columna=intVariable*2;
		
		txtMatriz=new EditText[fila][columna];
		rowLayout=new TableRow[fila];
		auxA=new EditText[fila][columna];
		auxLayout=new LinearLayout[fila][columna];
		temLayout=new LinearLayout[fila][columna];
		
		for(int f=0; f<fila; f++)
		{
			rowLayout[f]=new TableRow(this);
			
			for(int c=0; c<columna; c++)
			{
				auxLayout[f][c]=new LinearLayout(this);
				txtMatriz[f][c]=new EditText(this);
				
				txtMatriz[f][c].setEnabled(false);
				txtMatriz[f][c].setBackgroundColor(Color.WHITE);
				txtMatriz[f][c].setTextColor(Color.BLACK);
				txtMatriz[f][c].setTextSize(16);
				txtMatriz[f][c].setEms(4);
				txtMatriz[f][c].setMaxEms(4);
				txtMatriz[f][c].setMinEms(4);
				
				auxLayout[f][c].addView(txtMatriz[f][c]);
				rowLayout[f].addView(auxLayout[f][c]);
				
				temLayout[f][c]=new LinearLayout(this);
				
				auxA[f][c]=new EditText(this);
				auxA[f][c].setWidth(3);
				auxA[f][c].setTextSize(16);
				auxA[f][c].setBackgroundColor(Color.WHITE);
				
				if((c%2)>0 && c<columna-1)
				{
					auxA[f][c].setBackgroundColor(Color.BLACK);
				}
				
				if((c%2)==0 && (f%2)==0 && f<fila-2 && c<columna-2)
				{
					auxA[f][c].setBackgroundColor(Color.BLACK);
				}
				
				temLayout[f][c].addView(auxA[f][c]);
				rowLayout[f].addView(temLayout[f][c]);
			}
		}
		
		for(int f=0; f<fila-2; f++)
		{
			for(int c=0; c<columna-1; c++)
			{
				if((f%2)==0 && (c%2)>0)
				{
					auxLayout[f][c].setPadding(0, 0, 0, 3);
					auxLayout[f][c].setBackgroundColor(Color.BLACK);
					
					temLayout[f][c-1].setPadding(0, 0, 0, 3);
					temLayout[f][c-1].setBackgroundColor(Color.BLACK);
					
					temLayout[f][c].setPadding(0, 0, 0, 3);
					temLayout[f][c].setBackgroundColor(Color.BLACK);
				}
			}
		}
		
		for(int x=0; x<fila; x++)
		{
			if((x%2)>0)
			{
				rowLayout[x].setPadding(0, 0, 0, 3);
				rowLayout[x].setBackgroundColor(Color.BLACK);
			}
			else
			{
				rowLayout[x].setBackgroundColor(Color.WHITE);
			}
			
			lytMatriz.addView(rowLayout[x]);
		}
		
		txtMatriz[fila-1][columna-1].setBackgroundColor(Color.BLACK);
		txtMatriz[fila-1][columna-2].setBackgroundColor(Color.BLACK);
		txtMatriz[fila-2][columna-1].setBackgroundColor(Color.BLACK);
		txtMatriz[fila-2][columna-2].setBackgroundColor(Color.BLACK);
		auxA[fila-1][columna-1].setBackgroundColor(Color.BLACK);
		auxA[fila-1][columna-2].setBackgroundColor(Color.BLACK);
		auxA[fila-2][columna-1].setBackgroundColor(Color.BLACK);
		auxA[fila-2][columna-2].setBackgroundColor(Color.BLACK);
		
		lytMatriz.setBackgroundColor(Color.BLACK);
		lytMatriz.setPadding(3, 3, 3, 0);
		lytMatriz.requestLayout();
	}
	private void matrizOriginal()
	{
		for(int f=0; f<(intRestriccion-1); f++)
		{
			for(int c=0; c<(intVariable-1); c++)
			{
				txtMatriz[(f*2)][(c*2+1)].setText(Integer.toString(intMatriz[f][c]));
			}
		}
		
		actualizarOfertaDemanda(intMatriz);		
	}
	private void actualizarMatriz(int [][] intSolucion)
	{
		for(int f=0; f<(intRestriccion-1); f++)
		{
			for(int c=0; c<(intVariable-1); c++)
			{
				if(intSolucion[f][c]>0)
				{
					txtMatriz[(f*2+1)][(c*2+1)].setText(Integer.toString(intSolucion[f][c]));
				}
			}
		}
		
		actualizarOfertaDemanda(intSolucion);
		
		for(int f=0; f<intRestriccion-1; f++)
		{
			if(intSolucion[f][intVariable-1]==0)
			{
				for(int c=0; c<(intVariable*2); c++)
				{
					txtMatriz[(f*2)][c].setBackgroundColor(Color.YELLOW);
					txtMatriz[(f*2+1)][c].setBackgroundColor(Color.YELLOW);
					
					if(((f*2)%2)==0)
					{
						if((c%2)==0)
						{
							auxLayout[(f*2)][c].setPadding(0, 0, 0, 3);
							auxLayout[(f*2)][c].setBackgroundColor(Color.YELLOW);
						}
					}
					
					auxLayout[(f*2+1)][c].setBackgroundColor(Color.YELLOW);
					
					if(((f*2+1)%2)>0)
					{
						if((c%2)==0)
						{
							auxA[(f*2+1)][c].setBackgroundColor(Color.YELLOW);
						}
					}
				}
				
				auxA[(f*2)][(intVariable*2)-1].setBackgroundColor(Color.YELLOW);
				auxA[(f*2)][(intVariable*2)-2].setBackgroundColor(Color.YELLOW);
				
				auxA[(f*2+1)][(intVariable*2)-1].setBackgroundColor(Color.YELLOW);
				
				auxLayout[(f*2)][(intVariable*2)-1].setPadding(0, 0, 0, 3);
				auxLayout[(f*2)][(intVariable*2)-1].setBackgroundColor(Color.YELLOW);
			}
		}
		
		for(int c=0; c<intVariable-1; c++)
		{
			if(intSolucion[intRestriccion-1][c]==0)
			{
				for(int f=0; f<(intRestriccion*2); f++)
				{
					txtMatriz[f][(c*2)].setBackgroundColor(Color.YELLOW);
					txtMatriz[f][(c*2+1)].setBackgroundColor(Color.YELLOW);
					
					if((f%2)==1)
					{
						if(((c*2)%2)==0)
						{
							auxA[f][(c*2)].setBackgroundColor(Color.YELLOW);
						}
					}
					else
					{
						if(((c*2)%2)==0 && f<((intRestriccion*2)-2))
						{
							auxLayout[f][(c*2)].setPadding(0, 0, 0, 3);
							auxLayout[f][(c*2)].setBackgroundColor(Color.YELLOW);
						}
					}					
				}
				
				auxA[(intRestriccion*2)-2][(c*2)].setBackgroundColor(Color.YELLOW);
			}
		}
	}
	private void actualizarOfertaDemanda(int [][] intAux)
	{
		for(int x=0; x<(intVariable-1); x++)
		{
			txtMatriz[intRestriccion*2-1][(x*2+1)].setText(Integer.toString(intAux[intRestriccion-1][x]));
		}
		
		for(int x=0; x<(intRestriccion-1); x++)
		{
			txtMatriz[(x*2+1)][(intVariable*2-1)].setText(Integer.toString(intAux[x][intVariable-1]));
		}
	}
	private void escribirSolucion(String solucion)
	{
		txtResultado=new TextView(this);
		txtResultado.setTextSize(20);
		txtResultado.setText(solucion);
		txtResultado.setBackgroundColor(Color.TRANSPARENT);
		txtResultado.setTextColor(Color.BLACK);
		txtResultado.setPadding(50, 5, 0, 0);
		lytLinear.addView(txtResultado);
	}
	private void actualizarMultiplicadores(int [][] intCoor, int [][] intSolucion)
	{
		if(intCoordenadaAnterior!=null)
		{
			for(int x=0; x<intCoordenadaAnterior.length; x++)
			{
				txtMatriz[(intCoordenadaAnterior[x][0]*2)][(intCoordenadaAnterior[x][1]*2)].setBackgroundColor(Color.YELLOW);
				txtMatriz[(intCoordenadaAnterior[x][0]*2)][(intCoordenadaAnterior[x][1]*2)+1].setBackgroundColor(Color.YELLOW);
				txtMatriz[(intCoordenadaAnterior[x][0]*2)+1][(intCoordenadaAnterior[x][1]*2)].setBackgroundColor(Color.YELLOW);
				txtMatriz[(intCoordenadaAnterior[x][0]*2)+1][(intCoordenadaAnterior[x][1]*2)+1].setBackgroundColor(Color.YELLOW);
			}
		}
		
		intCoordenadaAnterior=new int[intCoor.length][3];
		
		int intEstado=0;
		
		for(int x=0; x<intCoor.length; x++)
		{
			intCoordenadaAnterior[x][0]=intCoor[x][0];
			intCoordenadaAnterior[x][1]=intCoor[x][1];
			intCoordenadaAnterior[x][2]=intCoor[x][2];
						
			txtMatriz[(intCoor[x][0]*2)+1][(intCoor[x][1]*2)+1].setText(Integer.toString(intCoor[x][2]));
			intSolucion[intCoor[x][0]][intCoor[x][1]]=intCoor[x][2];
			if(intCoor[x][2]==0)
			{
				if(intEstado==0)
				{
					intSolucion[intCoor[x][0]][intCoor[x][1]]=-2;
					intEstado=1;
				}
			}
			
			txtMatriz[(intCoor[x][0]*2)][(intCoor[x][1]*2)].setBackgroundColor(Color.LTGRAY);
			txtMatriz[(intCoor[x][0]*2)][(intCoor[x][1]*2)+1].setBackgroundColor(Color.LTGRAY);
			txtMatriz[(intCoor[x][0]*2)+1][(intCoor[x][1]*2)].setBackgroundColor(Color.LTGRAY);
			txtMatriz[(intCoor[x][0]*2)+1][(intCoor[x][1]*2)+1].setBackgroundColor(Color.LTGRAY);
		}
	}
	private void mensaje(String strTitulo, String strMensaje)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(strTitulo);
		builder.setMessage(strMensaje);
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
			}
		});
		alert=builder.create();
		alert.show();
	}
//---------------------------------------------------------------------------------------------------------------
}