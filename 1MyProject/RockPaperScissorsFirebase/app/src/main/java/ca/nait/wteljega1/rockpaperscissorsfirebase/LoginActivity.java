package ca.nait.wteljega1.rockpaperscissorsfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {
    EditText logEmail, logPassword;
    Button logInButton;
    TextView returnRegister;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logEmail = findViewById(R.id.log_in_email);
        logPassword = findViewById(R.id.log_in_password);
        logInButton = findViewById(R.id.log_into_account_button);
        returnRegister = findViewById(R.id.return_register);

        fAuth = FirebaseAuth.getInstance();

        logInButton.setOnClickListener(this);

        returnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.log_into_account_button:
            {
                String email = logEmail.getText().toString().trim();
                String password = logPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    logEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    logPassword.setError("Password is Required");
                    return;
                }
                if  (password.length() < 8)
                {
                    logPassword.setError("Password must have 8 or more Characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this);
            }
            case R.id.return_register:
            {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task)
    {
        if (task.isSuccessful())
        {
            Toast.makeText(this, "User Logged In", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else
        {
            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Register your account if you are not registered yet" , Toast.LENGTH_LONG).show();
        }
    }
}
