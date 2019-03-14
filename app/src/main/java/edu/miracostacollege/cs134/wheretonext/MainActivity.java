package edu.miracostacollege.cs134.wheretonext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import edu.miracostacollege.cs134.wheretonext.model.College;
import edu.miracostacollege.cs134.wheretonext.model.JSONLoader;

/**
 * Activity that lets user enter a new school and click on the schools from a ListView in order
 * to see more details about that school.
 *
 * @author Dennis La
 */
public class MainActivity extends AppCompatActivity {

    //private DBHelper db;
    private List<College> collegesList;
    private CollegeListAdapter collegesListAdapter;
    private ListView collegesListView;

    private EditText nameEditText;
    private EditText populationEditText;
    private EditText tuitionEditText;
    private RatingBar collegeRatingBar;

    /**
     * Provides user with interface to add a college. Also inflates the list view using the
     * custom adapter
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        populationEditText = findViewById(R.id.populationEditText);
        tuitionEditText = findViewById(R.id.tuitionEditText);
        collegeRatingBar = findViewById(R.id.collegeRatingBar);

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

        // DONE:  Connect the list adapter with the list
        collegesListAdapter = new CollegeListAdapter(this, R.layout.colleges_list_item, collegesList);


        // DONE:  Set the list view to use the list adapter
        collegesListView = findViewById(R.id.collegeListView);
        collegesListView.setAdapter(collegesListAdapter);

    }

    /**
     * Trigger by clicking a college list item. brings user to CollegeDetailsActivity using
     * an intent with data from the selected college
     *
     * @param view the list item clicked
     */
    public void viewCollegeDetails(View view) {

        // DONE: Implement the view college details using an Intent
        Intent collegesIntent = new Intent(this, CollegeDetailsActivity.class);

        //extract the position from the tag
        int pos = (int) view.getTag();
        College selectedCollege = collegesList.get(pos);

        collegesIntent.putExtra("Name", selectedCollege.getName());
        collegesIntent.putExtra("Population", selectedCollege.getPopulation());
        collegesIntent.putExtra("Tuition", (float) selectedCollege.getTuition());
        collegesIntent.putExtra("Rating", (float) selectedCollege.getRating());
        collegesIntent.putExtra("ImageName", selectedCollege.getImageName());

        startActivity(collegesIntent);



    }

    /**
     * Triggered by add college button. Checks if valid info is entered into the EditTexts
     * and then creates a new College and adds it to the List. Notifies the adapter that the
     * data set has changed in order to update the list view. If invalid data is entered, a toast
     * appears to tell the user to enter valid data.
     *
     *
     * @param view
     */
    public void addCollege(View view) {

        College collegeToAdd = new College();
        boolean validName = false;
        boolean validPop = false;
        boolean validTuition = false;

        // DONE: Implement the code for when the user clicks on the addCollegeButton
        //extract data from the edit texts
        if(!nameEditText.getText().toString().equals(""))
        {
            collegeToAdd.setName(nameEditText.getText().toString());
            validName = true;
        }

        if(!populationEditText.getText().toString().equals(""))
        {
            collegeToAdd.setPopulation(Integer.parseInt(populationEditText.getText().toString()));
            validPop = true;
        }

        if(!tuitionEditText.getText().toString().equals(""))
        {
            collegeToAdd.setTuition(Double.parseDouble(tuitionEditText.getText().toString()));
            validTuition = true;
        }

        collegeToAdd.setRating(collegeRatingBar.getRating());


        if(validName && validPop && validTuition)
        {
            collegesList.add(collegeToAdd);

            collegesListAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(this, getString(R.string.invalid_arguments),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
