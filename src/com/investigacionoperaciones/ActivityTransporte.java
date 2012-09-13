package com.investigacionoperaciones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ActivityTransporte extends Activity implements OnClickListener, OnItemSelectedListener
{
	private int intVariable;
	private int intRestriccion;
	private int [][] intMatriz;
	private String strMetodo;
	
	private EditText txtVariable;
	private EditText txtRestriccion;
	private Button btnSolicitar;
	private Button btnLimpiar;
	private Button btnResolver;
	private Spinner spnTransportes;
	private TableLayout lytMatriz;
	
	private EditText [][] txtMatriz;
	private TextView [][] txtSeparador;
	private TableRow [] rowLayout;
	private AlertDialog alert;
//---------------------------------------------------------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.transporte);
		
		txtVariable=(EditText)this.findViewById(R.id.variable);
		txtRestriccion=(EditText)this.findViewById(R.id.restriccion);
		spnTransportes=(Spinner)this.findViewById(R.id.transportes);
		btnSolicitar=(Button)this.findViewById(R.id.solicitar);
		btnLimpiar=(Button)this.findViewById(R.id.limpiar);
		btnResolver=(Button)this.findViewById(R.id.resolver);
		lytMatriz=(TableLayout)this.findViewById(R.id.layout_matriz);
		
		btnSolicitar.setOnClickListener(this);
		btnLimpiar.setOnClickListener(this);
		btnResolver.setOnClickListener(this);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.metodos_transporte,
			android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnTransportes.setAdapter(adapter);
		spnTransportes.setOnItemSelectedListener(this);
	}
	public void onClick(View v)
	{
		if(v.getId()==btnSolicitar.getId())
		{
			solicitarDatos();
		}
		else if(v.getId()==btnLimpiar.getId())
		{
			txtVariable.setText("");
			txtRestriccion.setText("");
			spnTransportes.setSelection(0);
			lytMatriz.removeAllViews();
		}
		else if(v.getId()==btnResolver.getId())
		{
			copiarMatriz();
			Bundle param=new Bundle();
			
			for(int f=0; f<intRestriccion; f++)
			{
				param.putIntArray(Integer.toString(f), intMatriz[f]);
			}
			
			param.putString("metodo", strMetodo);
			
			param.putInt("variable", intVariable);
			param.putInt("restriccion", intRestriccion);
			
			if(cuadraMatriz()==0)
			{
				try
				{
					Class<?> clase=Class.forName("com.investigacionoperaciones.ActivityTransporteResolucion");
					Intent intent=new Intent(this, clase);
					intent.putExtras(param);
					this.startActivity(intent);
					this.finish();
				}
				catch(ClassNotFoundException error)
				{
					error.printStackTrace();
				}
			}
			else
			{
				mensaje("Error", "La sumatoria de Oferta y Demanda deben ser iguales.");
			}
		}
	}
	public void onItemSelected(AdapterView<?> adap, View view, int sel, long ident)
	{
		strMetodo=adap.getItemAtPosition(sel).toString();
	}
	public void onNothingSelected(AdapterView<?> arg0){}
//---------------------------------------------------------------------------------------------------------------	
	private void solicitarDatos()
	{
		try
		{
			intVariable=Integer.parseInt(txtVariable.getText().toString());
			intRestriccion=Integer.parseInt(txtRestriccion.getText().toString());
			
			if(intVariable>0 && intRestriccion>0)
			{
				crearMatriz();
			}
			else
			{
				mensaje("Error", "Debe ingresar un valor para variables y restricciones");
			}
		}
		catch(Exception e)
		{
			intVariable=0;
			intRestriccion=0;
			e.printStackTrace();
		}
	}
	private void crearMatriz()
	{
		intVariable=intVariable+1;
		intRestriccion=intRestriccion+1;
		
		txtMatriz=new EditText[intRestriccion][intVariable];
		txtSeparador=new TextView[intRestriccion][intVariable];
		rowLayout=new TableRow[intRestriccion];
		
		for(int f=0; f<intRestriccion; f++)
		{
			rowLayout[f]=new TableRow(this);
			rowLayout[f].setBackgroundColor(Color.BLACK);
			rowLayout[f].setPadding(3, 3, 0, 0);
			
			for(int c=0; c<intVariable; c++)
			{
				txtMatriz[f][c]=new EditText(this);
				txtMatriz[f][c].setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
				txtMatriz[f][c].setBackgroundColor(Color.WHITE);
				txtMatriz[f][c].setTextSize(16);
				txtMatriz[f][c].setTextColor(Color.BLACK);
				txtMatriz[f][c].setEms(4);
				txtMatriz[f][c].setMaxEms(4);
				txtMatriz[f][c].setMinEms(4);
				
				rowLayout[f].addView(txtMatriz[f][c]);
				
				txtSeparador[f][c]=new TextView(this);
				txtSeparador[f][c].setWidth(3);
				
				rowLayout[f].addView(txtSeparador[f][c]);
			}
		}
		
		for(int x=0; x<intRestriccion; x++)
		{
			txtMatriz[x][(intVariable-1)].setBackgroundColor(Color.YELLOW);
		}
		
		for(int x=0; x<intVariable; x++)
		{
			txtMatriz[(intRestriccion-1)][x].setBackgroundColor(Color.YELLOW);
		}
		
		txtMatriz[intRestriccion-1][intVariable-1].setEnabled(false);
		txtMatriz[intRestriccion-1][intVariable-1].setBackgroundColor(Color.BLACK);
		
		for(int x=0; x<intRestriccion; x++)
		{
			lytMatriz.addView(rowLayout[x]);
		}
		
		lytMatriz.setBackgroundColor(Color.BLACK);
		lytMatriz.setPadding(0, 0, 0, 3);
		lytMatriz.requestLayout();
	}
	private void copiarMatriz()
	{
		intMatriz=new int[txtMatriz.length][txtMatriz[0].length];
		
		for(int f=0; f<txtMatriz.length; f++)
		{
			for(int c=0; c<txtMatriz[f].length; c++)
			{
				try
				{
					intMatriz[f][c]=Integer.parseInt(txtMatriz[f][c].getText().toString());
				}
				catch(Exception e)
				{
					intMatriz[f][c]=0;
				}
			}
		}
	}
	private int cuadraMatriz()
	{
		int intCuadre=0;
		
		for(int f=0; f<intRestriccion; f++)
		{
			intCuadre=intCuadre+intMatriz[f][intVariable-1];
		}
		
		for(int c=0; c<intVariable; c++)
		{
			intCuadre=intCuadre-intMatriz[intRestriccion-1][c];
		}
		
		return intCuadre;
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