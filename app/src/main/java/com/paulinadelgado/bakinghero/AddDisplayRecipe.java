package com.paulinadelgado.bakinghero;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class AddDisplayRecipe extends Fragment implements View.OnClickListener {

    TextView titleName;
    TextView name;
    Spinner category;
    TextView ingredients;
    TextView instructions;
    TextView notes;
    int USER_ID;
    String valid_user_id = "";
    String Toast_msg = null;
    DatabaseHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.add_edit_recipe, container, false);

        setHasOptionsMenu(true);
        dbHandler = new DatabaseHandler(getActivity());

        List<String> cat = Arrays.asList(getResources().getStringArray(R.array.cat));



        category = (Spinner) rootView.findViewById(R.id.spinnerCat);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,cat);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);


        name = (TextView) rootView.findViewById(R.id.editTextName);
        ingredients = (TextView) rootView.findViewById(R.id.editTextIng);
        instructions = (TextView) rootView.findViewById(R.id.editTextInst);
        notes = (TextView) rootView.findViewById(R.id.editTextNotes);


        Button b = (Button) rootView.findViewById(R.id.btnSave);
        b.setOnClickListener(this);



            //VIEW DATA
            Bundle bundle = this.getArguments();
            int viewUser = bundle.getInt("USER_ID");
            String called_from = bundle.getString("called");

        if (viewUser>0) {
                Recipe c = dbHandler.Get_Recipe(viewUser);


                    //cargador del spinner de categoria
                    int index = 0;
                    for (int i = 0; i < category.getCount(); i++) {
                        if (category.getItemAtPosition(i).equals(c.getCategory())) {
                            index = i;
                        }
                    }


                    category.setSelection(index);
                    name.setText(c.getName());
                    ingredients.setText(c.getIngredients());
                    instructions.setText(c.getInstructions());
                    notes.setText(c.getNotes());

                    switch (called_from) {
                        case "view":


                            b.setVisibility(View.INVISIBLE);
                            category.setEnabled(false);

                            name.setFocusable(false);
                            name.setClickable(false);

                            ingredients.setFocusable(false);
                            ingredients.setClickable(false);

                            instructions.setFocusable(false);
                            instructions.setClickable(false);

                            notes.setFocusable(false);
                            notes.setClickable(false);

                            break;
                        case "edit":

                            b.setVisibility(View.VISIBLE);
                            b.setText(R.string.update);
                            EditButton();
                            break;

                    }
                }

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        Bundle extras = this.getArguments();
        if (extras != null) {
            int Value = extras.getInt("USER_ID");
            if (Value > 0) {
                inflater.inflate(R.menu.menu_display_recipe, menu);
                super.onCreateOptionsMenu(menu, inflater);
            }
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Edit_Recipe:



                Button b = (Button) getActivity().findViewById(R.id.btnSave);
                b.setVisibility(View.VISIBLE);
                b.setText(R.string.update);


                EditButton();

                return true;
            case R.id.Delete_Recipe:

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle(R.string.deleteTitle);
                adb.setMessage(R.string.deleteDes);
                Bundle extras = this.getArguments();
                final int user_id = extras.getInt("USER_ID");
                adb.setNegativeButton(R.string.cancel, null);
                adb.setPositiveButton(R.string.ok,
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {


                                DatabaseHandler dBHandler = new DatabaseHandler(
                                        getActivity().getApplicationContext());
                                dBHandler.Delete_Recipe(user_id);
                                FragmentTransaction t = getFragmentManager().beginTransaction();
                                Fragment mFrag = new RecipesFragment();
                                t.replace(R.id.content_frame, mFrag);
                                t.commit();

                            }
                        });
                adb.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View view) {
        Bundle extras = this.getArguments();
        if (extras != null) {
            int Value = extras.getInt("USER_ID");

            if(category.getSelectedItem().toString().matches("Select"))
            {
                Toast.makeText(getActivity().getApplicationContext(),R.string.emCat,  Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(name.getText().toString())){
                Toast.makeText(getActivity().getApplicationContext(), R.string.emNam, Toast.LENGTH_SHORT).show();
                return;

            }
            if (Value > 0) {

                dbHandler.Update_Recipe(new Recipe(Value, category.getSelectedItem().toString(), name.getText().toString(), ingredients.getText().toString(), instructions.getText().toString(), notes.getText().toString()));
                Toast.makeText(getActivity().getApplicationContext(), R.string.sucUp, Toast.LENGTH_LONG).show();
                dbHandler.close();
                FragmentTransaction t = getFragmentManager().beginTransaction();
                Fragment mFrag = new RecipesFragment();
                t.replace(R.id.content_frame, mFrag);
                t.commit();
            } else {



                dbHandler.Add_Recipe(new Recipe(category.getSelectedItem().toString(), name.getText().toString(), ingredients.getText().toString(), instructions.getText().toString(), notes.getText().toString()));

                Toast.makeText(getActivity().getApplicationContext(), R.string.sucAd, Toast.LENGTH_SHORT).show();
                FragmentTransaction t = getFragmentManager().beginTransaction();
                Fragment mFrag = new RecipesFragment();
                t.replace(R.id.content_frame, mFrag);
                t.commit();
            }
        }
    }



    public void EditButton(){
        category.setEnabled(true);
        category.setFocusableInTouchMode(true);
        category.setClickable(true);

        name.setEnabled(true);
        name.setFocusableInTouchMode(true);
        name.setClickable(true);

        ingredients.setEnabled(true);
        ingredients.setFocusableInTouchMode(true);
        ingredients.setClickable(true);

        instructions.setEnabled(true);
        instructions.setFocusableInTouchMode(true);
        instructions.setClickable(true);

        notes.setEnabled(true);
        notes.setFocusableInTouchMode(true);
        notes.setClickable(true);
    }




    }



