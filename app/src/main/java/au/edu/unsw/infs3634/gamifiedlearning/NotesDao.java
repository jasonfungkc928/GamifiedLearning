package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class NotesDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    abstract void insert(NotesData notesData);

    //Delete query
    @Delete
    abstract void delete(NotesData notesData);

    //Delete all query
    @Delete
    abstract void reset(List<NotesData> notesData);

    //Update query
    @Query("UPDATE table_name SET text = :sText WHERE ID = :sID")
    abstract void update(int sID, String sText);

    //Get all data query
    @Query("SELECT * FROM table_name")
    abstract List<NotesData> getAll();
}
