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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {
    EditText registerUsername, registerEmail, registerPassword;
    Button registerButton;
    TextView returnLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.register_user_name);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        returnLogin = findViewById(R.id.return_log_in);
        registerButton = findViewById(R.id.create_new_account_button);

        // Declare Fire base Authentication
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        registerButton.setOnClickListener(this);

        returnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.create_new_account_button:
            {
                String email = registerEmail.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    registerEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    registerPassword.setError("Password is Required");
                    return;
                }
                if  (password.length() < 8)
                {
                    registerPassword.setError("Password must have 8 or more Characters");
                    return;
                }

                // register user in fire base
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this);
            }
            case R.id.return_log_in:
            {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task)
    {
        if (task.isSuccessful())
        {
            Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else
        {
            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
