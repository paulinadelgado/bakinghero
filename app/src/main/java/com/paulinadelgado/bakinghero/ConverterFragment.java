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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ConverterFragment extends Fragment {
    private EditText unit;
    private Spinner touni, fromuni;
    private TextView res,titulo;


    private double con;
    private double x;
    private double aux;
    private int y;
    private final double wlb=453.50;
    private final double woz=28.34;
    private final double wkg=1000; //weight
    private final double lt=1000;
    private final double lcup=240;
    private final double ltbsp=15;
    private final double ltsp=5;
    private final double floz=29.57;
    private final double gal=3785.41;
    private final double qt=946.35;
    private final double pt=473.17; //Liquids
    private final double pcup=125;
    private final double ptbsp=10;
    private final double ptsp=3.13; //fine powders
    private final double cup=227;
    private final double oz=28.34;
    private final double stk=113.4;
    private final  double tbsp=14.18;
    private final double tsp=4.73; //butter




    public ConverterFragment() {

    }



    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_converter, container, false);


        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(getActivity());
        adView.setAdSize(AdSize.SMART_BANNER);





        setHasOptionsMenu(true);


        unit = (EditText) rootView.findViewById(R.id.editUnit);
        Button convert = (Button) rootView.findViewById(R.id.btn_convert);
        res = (TextView) rootView.findViewById(R.id.txtCon);
        titulo = (TextView) rootView.findViewById(R.id.txtTitulo);
        touni=  (Spinner) rootView.findViewById(R.id.to_spinner);
        fromuni=  (Spinner) rootView.findViewById(R.id.from_spinner);

        // Spinner Drop down elements
        final List<String> units = new ArrayList<>();
        units.add(getString(R.string.select));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromuni.setAdapter(adapter);
        touni.setAdapter(adapter);



        convert.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if(unit.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getActivity(), R.string.emptyU,
                            Toast.LENGTH_SHORT).show();
                    return;

                }

                String s = touni.getSelectedItem().toString();
                if(s.equals(getString(R.string.select)))
                {
                    Toast.makeText(getActivity(), R.string.selcuni,
                            Toast.LENGTH_SHORT).show();
                    return;

                }
                else
                {
                    x= Double.parseDouble(String.valueOf(unit.getText()));
                }


                String st = fromuni.getSelectedItem().toString();
                if(st.equals(getString(R.string.select)))
                {
                    Toast.makeText(getActivity(), R.string.selcuni,
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



                res.setText(String.format(Locale.US,"%.2f", con));
            }





        });

        ImageButton but = (ImageButton) rootView.findViewById(R.id.btnButter);
        ImageButton wei = (ImageButton) rootView.findViewById(R.id.btnWeight);
        ImageButton flo = (ImageButton) rootView.findViewById(R.id.btnFlour);
        ImageButton tem = (ImageButton) rootView.findViewById(R.id.btnTemp);
        ImageButton liq = (ImageButton) rootView.findViewById(R.id.btnLiq);





        wei.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                titulo.setText(R.string.weight);
                y=1;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add(getString(R.string.select));
                units.add("Lb");
                units.add("Oz");
                units.add("Kg");
                units.add("gm");




            }

        });

        but.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                titulo.setText(R.string.butter);
                y=2;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add(getString(R.string.select));
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
                titulo.setText(R.string.temp);
                y=3;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add(getString(R.string.select));
                units.add("C");
                units.add("F");

            }

        });

        flo.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                titulo.setText(R.string.fine);
                y=4;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add(getString(R.string.select));
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
                titulo.setText(R.string.liquids);
                y=5;
                unit.setText("");
                res.setText("");
                units.clear();
                fromuni.setSelection(0);
                touni.setSelection(0);
                units.add(getString(R.string.select));
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
                
                dialogFragment.show(fm, String.valueOf(R.string.action_info));

                break;

            default:
                break;
        }

        return true;
    }


    //////////////////////////////////////CONVERSIONES*********************************

    private void toButter(){
        String s = touni.getSelectedItem().toString();

        switch (s){
            case "Oz":
                con = (aux * x) / oz;
                break;
            case "gm":
                con = (aux * x);
                break;
            case "Stick":
                con = (aux * x) / stk;
                break;
            case "Tbsp":
                con = (aux * x) / tbsp;
                break;
            case "Tsp":
                con = (aux * x) / tsp;
                break;
            case "Cup":
                con = (aux * x) / cup;
                break;
            default:
                break;
        }


    }
    private void fromButter(){
        String s = fromuni.getSelectedItem().toString();

        switch (s){


            case "Cup":
                aux = cup;
                toButter();
                break;
            case "Oz":
                aux = oz;
                toButter();
                break;
            case "gm":
                aux = 1;
                toButter();
                break;
            case "Stick":
                aux = stk;
                toButter();
                break;
            case "Tbsp":
                aux = tbsp;
                toButter();
                break;
            case "Tsp":
                aux = tsp;
                toButter();
                break;
            default:
                break;
        }

    }


//Peso
private void toWeight(){
        String s = touni.getSelectedItem().toString();

        switch (s){
            case "Lb":
                con = (aux * x) / wlb;
                break;
            case "Oz":
                con = (aux * x)/woz;
                break;
            case "Kg":
                con = (aux * x) /wkg;
                break;
            case "gm":
                con = (aux * x) ;
                break;
        }

    }

    private void fromWeight(){
        String s = fromuni.getSelectedItem().toString();
        switch (s){
            case "Lb":
                aux = wlb;
                toWeight();
                break;
            case "Oz":
                aux = woz;
                toWeight();
                break;
            case "gm":
                aux = 1;
                toWeight();
                break;
            case "Kg":
                aux = wkg;
                toWeight();
                break;
            default:
                break;
        }


    }

    //Temperaturas
    private void conTemp() {
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



    private void toLiquid(){
        String s = touni.getSelectedItem().toString();
        switch (s){
            case "Lt":
                con = (aux * x) / lt;
                break;
            case "ml":
                con = (aux * x);
                break;
            case "Cup":
                con = (aux * x)/lcup;
                break;
            case "Gal":
                con = (aux * x) /gal;
                break;
            case "Qt":
                con = (aux * x)/qt ;
                break;
            case "Pt":
                con = (aux * x)/pt ;
                break;
            case "FlOz":
                con = (aux * x)/floz ;
                break;
            case "Tbsp":
                con = (aux * x)/ltbsp ;
                break;
            case "Tsp":
                con = (aux * x)/ltsp ;
                break;
            default:
                break;

        }

    }

    private void fromLiquid(){
        String s = fromuni.getSelectedItem().toString();
        switch (s){
            case "Lt":
                aux = lt;
                toLiquid();
                break;
            case "ml":
                aux = 1;
                toLiquid();
                break;
            case "Gal":
                aux = gal;
                toLiquid();
                break;
            case "Qt":
                aux = qt;
                toLiquid();
                break;
            case "Pt":
                aux = pt;
                toLiquid();
                break;
            case "FlOz":
                aux = floz;
                toLiquid();
                break;
            case  "Tbsp":
                aux = ltbsp;
                toLiquid();
                break;
            case "Tsp":
                aux = ltsp;
                toLiquid();
                break;
            case "Cup":
                aux =lcup;
                toLiquid();
                break;

        }


    }

    private void toPowder(){
        String s = touni.getSelectedItem().toString();
        switch (s){
            case "Cup":
                con = (aux * x) / pcup;
                break;
            case "Oz":
                con = (aux * x)/oz;
                break;
            case "gm":
                con = (aux * x);
                break;
            case "Tbsp":
                con = (aux * x) /ptbsp;
                break;
            case "Tsp":
                con = (aux * x) /ptsp;
                break;

        }

    }

    private void fromPowder(){
        String s = fromuni.getSelectedItem().toString();

        switch (s){
            case "Cup":
                aux = pcup;
                toPowder();
                break;
            case "Oz":
                aux = oz;
                toPowder();
                break;
            case "gm":
                aux = 1;
                toPowder();
                break;
            case "Tbsp":
                aux= ptbsp;
                toPowder();
                break;
            case "Tsp":
                aux= ptsp;
                toPowder();
                break;
            default:
                break;


        }

    }


}
