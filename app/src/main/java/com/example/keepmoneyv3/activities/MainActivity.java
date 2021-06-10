package com.example.keepmoneyv3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.keepmoneyv3.R;
import com.example.keepmoneyv3.database.*;
import com.example.keepmoneyv3.utility.Keys;
import com.example.keepmoneyv3.utility.User;


/**
 * This is the entry point of the application. It performs login and the access in the
 * RegistrationActivity and in the NavigationActivity
 *
 * @author Michelangelo De Pascale
 *
 * @see NavigationActivity
 * @see RegistrationActivity
 * */
public class MainActivity extends AppCompatActivity {

    private DbManager dbManager;

    /**
     * This method describes what happens when the Activity is created
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DbManager(getApplicationContext());
        addPredefinedCategories(); // method to add the predefined categories if the table is empty
    }

    /**
     * This method performs the login action, by checking with a query if the user's credentials
     * are valid
     *
     * @author Michelangelo De Pascale
     * @see NavigationActivity
     * @see com.example.keepmoneyv3.ui.dashboard.DashboardFragment
     *
     * @param view      the actual view*/
    public void loginAction(View view){

        User user;

        EditText txtFromUsernameBox = findViewById(R.id.txtUsernameLog);
        EditText txtFromPasswordBox = findViewById(R.id.txtPasswordLog);

        String strUsername = txtFromUsernameBox.getText().toString();
        String strPassword = txtFromPasswordBox.getText().toString();

        if(strUsername.equals("")||strPassword.equals("")) {
            Toast.makeText(getApplicationContext(),"Errore, uno o più campi non compilati correttamente",Toast.LENGTH_LONG).show();
        } else {
            Cursor cursor = dbManager.queryCheckUserLogin(strUsername, strPassword);
            if(cursor != null) {
                while(cursor.moveToNext()) {
                    String strLoggedUser_Email = cursor.getString(cursor.getColumnIndex(DbStrings.TableUsersField.USERS_EMAIL));
                    String strLoggedUser_Name = cursor.getString(cursor.getColumnIndex(DbStrings.TableUsersField.USERS_NAME));
                    String strLoggedUser_Surname = cursor.getString(cursor.getColumnIndex(DbStrings.TableUsersField.USERS_SURNAME));
                    String strLoggedUser_Username = cursor.getString(cursor.getColumnIndex(DbStrings.TableUsersField.USERS_ID));
                    String strLoggedUser_Password = cursor.getString(cursor.getColumnIndex(DbStrings.TableUsersField.USERS_PWD));
                    float fltLoggedUser_Total = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbStrings.TableUsersField.USERS_TOT)));
                    Bundle loginScreenMainActivityBundle = new Bundle();
                    user = new User(strLoggedUser_Username,strLoggedUser_Password,strLoggedUser_Name,strLoggedUser_Surname,strLoggedUser_Email,fltLoggedUser_Total);
                    loginScreenMainActivityBundle.putSerializable(Keys.SerializableKeys.USER_KEY,user);
                    newActivityRunning(NavigationActivity.class, loginScreenMainActivityBundle);
                }
            }
        }
    }

    /**
     * Used to switch to the RegistrationActivity
     *
     * @see RegistrationActivity*/
    public void fabRegistrationAction(View view){
        newActivityRunning(RegistrationActivity.class, null);
    }


    /**
     * Switches activity
     *
     * @author Michelangelo De Pascale
     *
     * @param s                 the class of the activity
     * @param additionalData    optional bundle to pass as extras*/
    private void newActivityRunning(@SuppressWarnings("rawtypes") Class s, Bundle additionalData){
        Intent intent = new Intent(this,s);

        if (additionalData != null){
            intent.putExtras(additionalData);
        }

        startActivity(intent);//start a new activity
    }

    /**
     * A method used to add the predefined categories
     * */
    private void addPredefinedCategories(){
        Cursor cursor = dbManager.countQuery(DbStrings.TableCategoriesFields.TABLE_NAME);
        int numRows; // rows number of the category table
        if(cursor != null) {
            // insert the categories if the table is empty (no records)
            while (cursor.moveToNext()) {
                numRows = cursor.getInt(cursor.getColumnIndex("numRows"));
                if (numRows == 0) {
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_TECH_ID, getString(R.string.tech), R.drawable.ic_baseline_computer_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_FOOD_ID, getString(R.string.food), R.drawable.ic_baseline_fastfood_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_FAMILY_ID, getString(R.string.family), R.drawable.ic_baseline_family_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_FUN_ID, getString(R.string.fun), R.drawable.ic_baseline_emoji_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_HOME_ID, getString(R.string.home), R.drawable.ic_baseline_home_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_HOLIDAY_ID, getString(R.string.holiday), R.drawable.ic_baseline_beach_access_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_SALARY_ID, getString(R.string.salary), R.drawable.ic_baseline_attach_money_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_PRESENT_ID, getString(R.string.present), R.drawable.ic_baseline_card_giftcard_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_BET_ID, getString(R.string.bet), R.drawable.ic_baseline_casino_24);
                    dbManager.insertCategories(Keys.PredefinedCategoriesId.CAT_HEALTH_ID, getString(R.string.health), R.drawable.ic_baseline_healing_24);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}