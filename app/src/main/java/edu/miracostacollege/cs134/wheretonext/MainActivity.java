package edu.miracostacollege.cs134.wheretonext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import edu.miracostacollege.cs134.wheretonext.model.College;
import edu.miracostacollege.cs134.wheretonext.model.JSONLoader;

public class MainActivity extends AppCompatActivity {

    //private DBHelper db;
    private List<College> collegesList;
    private CollegeListAdapter collegesListAdapter;
    private ListView collegesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.deleteDatabase(DBHelper.DATABASE_NAME);
       // db = new DBHelper(this);

        // DONE: Comment this section out once the colleges below have been added to the database,
        // DONE: so they are not added multiple times (prevent duplicate entries)
        /*db.addCollege(new College("UC Berkeley", 42082, 14068, 4.7, "ucb.png"));
        db.addCollege(new College("UC Irvine", 31551, 15026.47, 4.3, "uci.png"));
        db.addCollege(new College("UC Los Angeles", 43301, 25308, 4.5, "ucla.png"));
        db.addCollege(new College("UC San Diego", 33735, 20212, 4.4, "ucsd.png"));
        db.addCollege(new College("CSU Fullerton", 38948, 6437, 4.5, "csuf.png"));
        db.addCollege(new College("CSU Long Beach", 37430, 6452, 4.4, "csulb.png"));*/

        // DONE:  Fill the collegesList with all Colleges from the JSON
        try {
            collegesList = JSONLoader.loadJSONFromAsset(this);
        } catch (IOException e) {
            Log.e("Where to Next", e.getMessage());
        }

        // TODO:  Connect the list adapter with the list
        collegesListView = findViewById(R.id.collegeListView);
        collegesListAdapter = new CollegeListAdapter(this, R.layout.colleges_list_item, collegesList);


        // TODO:  Set the list view to use the list adapter
        collegesListView.setAdapter(collegesListAdapter);
    }

    public void viewCollegeDetails(View view) {

        // TODO: Implement the view college details using an Intent
        Intent collegesIntent = new Intent(this, CollegeDetailsActivity.class);

        //extract the position from the tag
        int pos = (int) view.getTag();
        College selectedCollege = collegesList.get(pos);

        collegesIntent.putExtra("Name", selectedCollege.getName());
        collegesIntent.putExtra("Population", selectedCollege.getPopulation());
        collegesIntent.putExtra("Tuition", selectedCollege.getTuition());
        collegesIntent.putExtra("Rating", selectedCollege.getRating());
        collegesIntent.putExtra("ImageName", selectedCollege.getImageName());

        startActivity(collegesIntent);



    }

    public void addCollege(View view) {

        // TODO: Implement the code for when the user clicks on the addCollegeButton
    }

}
