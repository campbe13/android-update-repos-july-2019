package ca.campbell.roomwordsample.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "pizzatext_table",
        foreignKeys = @ForeignKey(entity = Pizza.class,
                parentColumns = "pid",
                childColumns = "pizzaNameId",
                // delete me if parent (pizza name) is deleted
                onDelete = ForeignKey.CASCADE))
        //indices = {@Index("title"), @Index("directorId")})
public class PizzaText {
        // autoGenerate optional
        // must be int, Integer, long or Long
        // @PrimaryKey(autoGenerate = true)
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="textId")
        private int id;
        @NonNull
        @ColumnInfo(name="pizzatext")
        private String pizzaText;
        // foreign key
        @ColumnInfo(name="pizzaNameId")
        private int pizzaNameId;

        public PizzaText(@NonNull String pizzaText, int pizzaNameId) {
                this.pizzaText = pizzaText;
                this.pizzaNameId = pizzaNameId;
        }

        public String getText(){return this.pizzaText;}
        public int getId(){return this.id;}
}
