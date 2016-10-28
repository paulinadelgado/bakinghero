package com.paulinadelgado.bakinghero;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipesFragment extends Fragment  {


    //

    private ListView Recipe_listview;
    private final List<Recipe> recipe_data = new ArrayList<>();
    private ListRecipeAdapter rAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        setHasOptionsMenu(true);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {


                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23
                );
            }
        }


        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(getActivity());
        adView.setAdSize(AdSize.SMART_BANNER);





        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction t = getFragmentManager().beginTransaction();
                Fragment mFrag = new AddDisplayRecipe();
                Bundle b = new Bundle();
                b.putInt("USER_ID", 0);
                b.putString("called", "new");
                mFrag.setArguments(b);
                t.addToBackStack(null);
                t.replace(R.id.content_frame, mFrag);
                t.commit();
            }
        });

        DatabaseHandler db;




        try {
            Recipe_listview = (ListView) rootView.findViewById(R.id.lstRecipes);
            Recipe_listview.setTextFilterEnabled(true);
            Recipe_listview.setItemsCanFocus(false);


            recipe_data.clear();

            db = new DatabaseHandler(getActivity());

            ArrayList<Recipe> recipe_array_from_db = db.Get_Recipes();

            for (int i = 0; i < recipe_array_from_db.size(); i++) {

                int tidno = recipe_array_from_db.get(i).getID();
                String name = recipe_array_from_db.get(i).getName();
                String category =recipe_array_from_db.get(i).getCategory();
                Recipe rcp = new Recipe();
                rcp.setID(tidno);
                rcp.setName(name);
                rcp.setCategory(category);

                recipe_data.add(rcp);
            }
            db.close();

            rAdapter = new ListRecipeAdapter(getActivity().getApplicationContext(), R.layout.listview_row,
                    recipe_data);
            Recipe_listview.setAdapter(rAdapter);
            rAdapter.notifyDataSetChanged();


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }




        return rootView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        MenuItem item = menu.add("Search");
        SearchView sv = new SearchView(getActivity());
        item.setActionView(sv);
        item.setIcon(R.mipmap.ic_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
                | MenuItem.SHOW_AS_ACTION_IF_ROOM);


        sv.setSubmitButtonEnabled(true);



        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                rAdapter.filter(newText.trim());
                Recipe_listview.invalidate();
                return true;

            }

        });


        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);



        switch (item.getItemId()) {

            case R.id.importDB:

                importDB();
                FragmentTransaction t = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new RecipesFragment();
                t.replace(R.id.content_frame, fragment);
                t.commit();

                return true;

            case R.id.exportDB:

                exportDB();



                return true;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }



    //importing database
    private void importDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "com.paulinadelgado.bakinghero"
                        + "//databases//" + "recipesManager";
                String backupDBPath  = "/BackupFolderBH/recipesManager";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getActivity().getBaseContext(),getString(R.string.impor) + backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getActivity().getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
    //exporting database
    private void exportDB() {
        // TODO Auto-generated method stub


        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "com.paulinadelgado.bakinghero"
                        + "//databases//" + "recipesManager";
                String backupDBPath  = "/BackupFolderBH/recipesManager";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getActivity().getBaseContext(),getString(R.string.expor)+ backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getActivity().getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }

    }





    public class ListRecipeAdapter extends ArrayAdapter implements Filterable{

        final List<Recipe> mlist;

        final ArrayList<Recipe> arraylist;

        public ListRecipeAdapter(Context context, int resource, List<Recipe> list) {
            super(context, resource);
            mlist=list;
            arraylist =new ArrayList<>();
            arraylist.addAll(mlist);

        }

         class LayoutHandler{
            TextView name,category;
            ImageButton view,edit,delete;

        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View mview = convertView;
            LayoutHandler layoutHandler;
            if(mview==null){
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mview = layoutInflater.inflate(R.layout.listview_row,parent,false);
                layoutHandler = new LayoutHandler();

                layoutHandler.name = (TextView) mview.findViewById(R.id.recipe_name_txt);
                layoutHandler.category =(TextView) mview.findViewById(R.id.recipe_category_txt);
                layoutHandler.view = (ImageButton) mview.findViewById(R.id.btn_view);
                layoutHandler.edit = (ImageButton) mview.findViewById(R.id.btn_edit);
                layoutHandler.delete = (ImageButton) mview.findViewById(R.id.btn_delete);


                mview.setTag(layoutHandler);
            }else {
                layoutHandler = (LayoutHandler) mview.getTag();
            }
            Recipe recipe = (Recipe) this.getItem(position);
            layoutHandler.name.setText(recipe.getName());
            layoutHandler.category.setText(recipe.getCategory());

            final int recId=recipe.getID();

            layoutHandler.view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    FragmentTransaction t = getActivity().getFragmentManager().beginTransaction();
                    Fragment fragment = new AddDisplayRecipe();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("called", "view");
                    dataBundle.putInt("USER_ID",recId );
                    fragment.setArguments(dataBundle);
                    t.addToBackStack(null);
                    t.replace(R.id.content_frame, fragment);

                    t.commit();




                }
            });

            layoutHandler.edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    FragmentTransaction t = getActivity().getFragmentManager().beginTransaction();
                    Fragment fragment = new AddDisplayRecipe();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("called", "edit");
                    dataBundle.putInt("USER_ID",recId );

                    fragment.setArguments(dataBundle);
                    t.addToBackStack(null);
                    t.replace(R.id.content_frame, fragment);

                    t.commit();




                }
            });

            layoutHandler.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                    adb.setTitle(R.string.deleteTitle);
                    adb.setMessage(R.string.deleteDes);

                    adb.setNegativeButton(R.string.cancel, null);
                    adb.setPositiveButton(R.string.ok,
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    DatabaseHandler dBHandler = new DatabaseHandler(
                                            getActivity().getApplicationContext());
                                    dBHandler.Delete_Recipe(recId);
                                    FragmentTransaction t = getActivity().getFragmentManager().beginTransaction();
                                    Fragment fragment = new RecipesFragment();
                                    t.replace(R.id.content_frame, fragment);
                                    t.commit();

                                }
                            });
                    adb.show();



                }
            });



            return mview;
        }

        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            mlist.clear();
            if (charText.length() == 0) {
                mlist.addAll(arraylist);

            } else {
                for (Recipe recipeDetail : arraylist) {
                    if (charText.length() != 0 && recipeDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        mlist.add(recipeDetail);
                    } else if (charText.length() != 0 && recipeDetail.getCategory().toLowerCase(Locale.getDefault()).contains(charText)) {
                        mlist.add(recipeDetail);
                    }
                }
            }
            notifyDataSetChanged();

        }


    }





}
