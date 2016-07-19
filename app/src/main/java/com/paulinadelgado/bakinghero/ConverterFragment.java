package com.paulinadelgado.bakinghero;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ConverterFragment extends Fragment {
    private EditText unit;
    private Button convert;
    private Spinner touni, fromuni;
    private TextView res,titulo;
    private ImageButton but, wei,flo,tem,liq;


    double con,x,aux;
    int y;
    double wlb=453.50, woz=28.34,wkg=1000; //weight
    double lt=1000, lcup=240, ltbsp=15,ltsp=5,floz=29.57,gal=3785.41, qt=946.35, pt=473.17; //Liquids
    double pcup=125,ptbsp=10,ptsp=3.13; //fine powders
    double cup=227,oz=28.34,stk=113.4,tbsp=14.18,tsp=4.73; //butter





    public ConverterFragment() {

    }



    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_converter, container, false);



        setHasOptionsMenu(true);


        unit = (EditText) rootView.findViewById(R.id.editUnit);
        convert = (Button) rootView.findViewById(R.id.btn_convert);
        res = (TextView) rootView.findViewById(R.id.txtCon);
        titulo = (TextView) rootView.findViewById(R.id.txtTitulo);
        touni=  (Spinner) rootView.findViewById(R.id.to_spinner);
        fromuni=  (Spinner) rootView.findViewById(R.id.from_spinner);

        // Spinner Drop down elements
        final List<String> units = new ArrayList<String>();
        units.add("Select");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromuni.setAdapter(adapter);
        touni.setAdapter(adapter);



        convert.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if(unit.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getActivity(), "Empty Unit Field",
                            Toast.LENGTH_SHORT).show();
                    return;

                }

                String s = touni.getSelectedItem().toString();
                if(s.equals("Select"))
                {
                    Toast.makeText(getActivity(), "Select Conversion Unit",
                            Toast.LENGTH_SHORT).show();
                    return;

                }
                else
                {
                    x= Double.parseDouble(String.valueOf(unit.getText()));
                }


                String st = fromuni.getSelectedItem().toString();
                if(st.equals("Select"))
                {
                    Toast.makeText(getActivity(), "Select Conversion Unit",
                            Toast.LENGTH_SHORT).show();
                    return;

                }
                else
                {
                    x= Double.parseDouble(String.valueOf(unit.getText()));
                }


                   if (y==1){
                       fromWeight();
                   } else if (y==2) {
                       fromButter();
                   } else if (y==3){
                       conTemp();
                   } else if (y==4){
                       fromPowder();
                   } else if (y==5){
                       fromLiquid();
                   }



                res.setText(String.format("%.2f", con));
            }





        });

        but = (ImageButton) rootView.findViewById(R.id.btnButter);
        wei = (ImageButton) rootView.findViewById(R.id.btnWeight);
        flo = (ImageButton) rootView.findViewById(R.id.btnFlour);
        tem = (ImageButton) rootView.findViewById(R.id.btnTemp);
        liq = (ImageButton) rootView.findViewById(R.id.btnLiq);





        wei.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                titulo.setText(String.format("Weight Conversions"));
                y=1;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add("Select");
                units.add("Lb");
                units.add("Oz");
                units.add("Kg");
                units.add("gm");




            }

        });

        but.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                titulo.setText(String.format("Butter Conversions"));
                y=2;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add("Select");
                units.add("Cup");
                units.add("Oz");
                units.add("gm");
                units.add("Stick");
                units.add("Tbsp");
                units.add("Tsp");



            }

        });

        tem.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                titulo.setText(String.format("Temperature Conversions"));
                y=3;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add("Select");
                units.add("C");
                units.add("F");

            }

        });

        flo.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                titulo.setText(String.format("Fine Powder Conversions"));
                y=4;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add("Select");
                units.add("Cup");
                units.add("Oz");
                units.add("gm");
                units.add("Tbsp");
                units.add("Tsp");



            }

        });

        liq.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                titulo.setText(String.format("Liquid Conversions"));
                y=5;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add("Select");
                units.add("Lt");
                units.add("ml");
                units.add("Gal");
                units.add("Qt");
                units.add("Pt");
                units.add("FlOz");
                units.add("Cup");
                units.add("Tbsp");
                units.add("Tsp");

            }

        });








        return rootView;
	}






    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_convert, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_info:
                FragmentManager fm = getFragmentManager();
                android.app.DialogFragment dialogFragment = new DialogFragment ();
                
                dialogFragment.show(fm, "Information");

                break;

            default:
                break;
        }

        return true;
    }


    //////////////////////////////////////CONVERSIONES*********************************

    public void toButter(){
        String s = touni.getSelectedItem().toString();
        if (s.equals("Oz")) {
            con = (aux * x) / oz;
        } else if (s.equals("gm")) {
            con = (aux * x);
        } else if (s.equals("Stick")) {
            con = (aux * x) / stk;
        } else if (s.equals("Tbsp")) {
            con = (aux * x) / tbsp;
        } else if (s.equals("Tsp")) {
            con = (aux * x) / tsp;
        } else if (s.equals("Cup")) {
            con = (aux * x) / cup;
        }



    }
    public void fromButter(){
        String s = fromuni.getSelectedItem().toString();
        if (s.equals("Cup")) {
            aux = cup;
            toButter();
        } else if (s.equals("Oz")) {
            aux = oz;
            toButter();
        } else if (s.equals("gm")) {
            aux = 1;
            toButter();
        } else if (s.equals("Stick")) {
            aux = stk;
            toButter();
        } else if (s.equals("Tbsp")) {
            aux = tbsp;
            toButter();
        } else if (s.equals("Tsp")) {
            aux = tsp;
            toButter();
        }

    }
//Peso
    public void toWeight(){
        String s = touni.getSelectedItem().toString();



        if (s.equals("Lb")) {
            con = (aux * x) / wlb;
        } else if (s.equals("Oz")) {
            con = (aux * x)/woz;
        } else if (s.equals("Kg")) {
            con = (aux * x) /wkg;
        } else if (s.equals("gm")) {
            con = (aux * x) ;
        }


    }

    public void fromWeight(){
        String s = fromuni.getSelectedItem().toString();
        if (s.equals("Lb")) {
            aux = wlb;
            toWeight();
        } else if (s.equals("Oz")) {
            aux = woz;
            toWeight();
        } else if (s.equals("gm")) {
            aux = 1;
            toWeight();
        } else if (s.equals("Kg")) {
            aux = wkg;
            toWeight();
        }

    }

    //Temperaturas
    public void conTemp() {
        String s = touni.getSelectedItem().toString();
        String s2 = fromuni.getSelectedItem().toString();
        if (s2.equals("C")){
            if (s.equals("F")){
                con=(x*1.8)+32;
            }
            else
                con=x;
        }
        if (s2.equals("F")){
            if (s.equals("C")){
                con=(x-32)/1.8;
            }
            else
                con=x;

        }
    }



    public void toLiquid(){
        String s = touni.getSelectedItem().toString();
        if (s.equals("Lt")) {
            con = (aux * x) / lt;
        } else if (s.equals("ml")) {
            con = (aux * x);
        } else if (s.equals("Cup")) {
            con = (aux * x)/lcup;
        } else if (s.equals("Gal")) {
            con = (aux * x) /gal;
        } else if (s.equals("Qt")) {
            con = (aux * x)/qt ;
        } else if (s.equals("Pt")) {
            con = (aux * x)/pt ;
        } else if (s.equals("FlOz")) {
            con = (aux * x)/floz ;
        } else if (s.equals("Tbsp")) {
            con = (aux * x)/ltbsp ;
        } else if (s.equals("Tsp")) {
            con = (aux * x)/ltsp ;
        }
    }

    public void fromLiquid(){
        String s = fromuni.getSelectedItem().toString();
        if (s.equals("Lt")) {
            aux = lt;
            toLiquid();
        } else if (s.equals("ml")) {
            aux = 1;
            toLiquid();
        } else if (s.equals("Gal")) {
            aux = gal;
            toLiquid();
        } else if (s.equals("Qt")) {
            aux = qt;
            toLiquid();
        } else if (s.equals("Pt")) {
            aux = pt;
            toLiquid();
        } else if (s.equals("FlOz")) {
            aux = floz;
            toLiquid();
        } else if (s.equals("Tbsp")) {
            aux = ltbsp;
            toLiquid();
        } else if (s.equals("Tsp")) {
            aux = ltsp;
            toLiquid();
        } else if (s.equals("Cup")) {
            aux =lcup;
            toLiquid();
        }
    }

    public void toPowder(){
        String s = touni.getSelectedItem().toString();
        if (s.equals("Cup")) {
            con = (aux * x) / pcup;
        } else if (s.equals("Oz")) {
            con = (aux * x)/oz;
        } else if (s.equals("gm")) {
            con = (aux * x);
        } else if (s.equals("Tbsp")) {
            con = (aux * x) /ptbsp;
        } else if (s.equals("Tsp")) {
            con = (aux * x) /ptsp;
        }
    }

    public void fromPowder(){
        String s = fromuni.getSelectedItem().toString();
        if (s.equals("Cup")) {
            aux = pcup;
            toPowder();
        } else if (s.equals("Oz")) {
            aux = oz;
            toPowder();
        } else if (s.equals("gm")) {
            aux = 1;
            toPowder();
        } else if (s.equals("Tbsp")){
            aux= ptbsp;
            toPowder();
        } else if (s.equals("Tsp")){
            aux= ptsp;
            toPowder();
        }
    }


}
