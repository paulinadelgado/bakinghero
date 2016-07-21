package com.paulinadelgado.bakinghero;

/**
 * Created by pauparapapau on 29/06/16. ;D
 */


class Recipe {
    private int _id;
    private String _category;
    private String _name;
    private String _ingredients;
    private String _instructions;
    private String _notes;

    public Recipe(){
    }



    public Recipe(int id, String _category, String name, String _ingredients, String _instructions, String _notes) {
        this._id = id;
        this._category = _category;
        this._name = name;
        this._ingredients = _ingredients;
        this._instructions =_instructions;
        this._notes=_notes;

    }
    public Recipe(String _category, String name, String _ingredients, String _instructions, String _notes) {
        this._category = _category;
        this._name = name;
        this._ingredients = _ingredients;
        this._instructions =_instructions;
        this._notes=_notes;

    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id=id;
    }

    public String getCategory(){
        return this._category;
    }

    public void setCategory(String category){
        this._category=category;
    }

    public String getName(){
        return this._name;
    }
    public void setName(String name){
        this._name=name;
    }

    public String getIngredients(){
        return this._ingredients;
    }
    public void setIngredients(String ingredients){
        this._ingredients=ingredients;
    }

    public String getInstructions(){
        return this._instructions;
    }
    public void setInstructions(String instructions){
        this._instructions=instructions;
    }

    public String getNotes(){
        return this._notes;
    }
    public void setNotes(String notes){
        this._notes=notes;
    }
}
