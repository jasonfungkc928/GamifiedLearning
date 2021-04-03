package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Define table name
@Entity(tableName = "table_name")
public class NotesData implements Serializable {

    //Create id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //Create text column
    @ColumnInfo(name = "text")
    private String text;

    //Generate getter and setter
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

