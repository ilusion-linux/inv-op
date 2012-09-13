package com.investigacionoperaciones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityInvestigacionOperaciones extends Activity implements OnClickListener
{
	Button btnSimplex;
	Button btnTransporte;
	Button btnAsignacion;
	Button btnInformacion;
//---------------------------------------------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        
        btnSimplex=(Button)(this.findViewById(R.id.simplex));
        btnTransporte=(Button)(this.findViewById(R.id.transporte));
        btnAsignacion=(Button)(this.findViewById(R.id.asignacion));
        btnInformacion=(Button)(this.findViewById(R.id.informacion));
        
        btnSimplex.setOnClickListener(this);
        btnTransporte.setOnClickListener(this);
        btnAsignacion.setOnClickListener(this);
        btnInformacion.setOnClickListener(this);
    }
	public void onClick(View v)
	{
		if(v.getId()==btnSimplex.getId())
		{
			
		}
		else if(v.getId()==btnTransporte.getId())
		{
			try
			{
				Class<?> clase=Class.forName("com.investigacionoperaciones.ActivityTransporte");
				Intent intent=new Intent(this, clase);
				this.startActivity(intent);
			}
			catch(ClassNotFoundException error)
			{
				error.printStackTrace();
			}
		}
		else if(v.getId()==btnAsignacion.getId())
		{
			
		}
		else if(v.getId()==btnInformacion.getId())
		{
			try
			{
				Class<?> clase=Class.forName("com.investigacionoperaciones.ActivityInformacion");
				Intent intent=new Intent(this, clase);
				this.startActivity(intent);
			}
			catch(ClassNotFoundException error)
			{
				error.printStackTrace();
			}
		}
	}
//---------------------------------------------------------------------------------------------------------------
}