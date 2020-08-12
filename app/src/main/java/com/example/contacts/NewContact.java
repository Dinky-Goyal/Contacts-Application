package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class NewContact extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;


    Button btnNewContact;
    EditText etName,etMail,etNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        btnNewContact = findViewById(R.id.btnNewContact);
        etName = findViewById(R.id.etName);
        etMail = findViewById(R.id.etMail);
        etNumber = findViewById(R.id.etNumber);
        btnNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(etName.getText().toString().isEmpty() ||etMail.getText().toString().isEmpty()||etNumber.getText().toString().isEmpty())
               {
                   Toast.makeText(NewContact.this, "Please enter all the details!", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   String name = etName.getText().toString().trim();
                   String email = etMail.getText().toString().trim();
                   String number = etNumber.getText().toString().trim();
                   Contact contact = new Contact();
                   contact.setName(name);
                   contact.setEmail(email);
                   contact.setNumber(number); //for every contact that we create here,we can also link one of our users that's currently
                   // logged into that context so that every person with his/her own account will have his/her own set of contacts
                   contact.setUserEmail(ApplicationClass.user.getEmail());

                   showProgress(true);
                   tvLoad.setText("Creating new contact....Please wait!");

                   Backendless.Persistence.save(contact, new AsyncCallback<Contact>() { //here first parameter is table name
                       @Override
                       public void handleResponse(Contact response) {

                           Toast.makeText(NewContact.this, "New Contact saved successfully!", Toast.LENGTH_SHORT).show();
                           showProgress(false);
                           etName.setText("");
                           etMail.setText("");
                           etNumber.setText("");
                       }

                       @Override
                       public void handleFault(BackendlessFault fault) {
                           Toast.makeText(NewContact.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                           showProgress(false);
                       }
                   });
               }
            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}