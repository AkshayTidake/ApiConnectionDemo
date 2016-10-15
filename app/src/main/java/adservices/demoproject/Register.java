package adservices.demoproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText rmail,rmob,rfirst,rlast,rage,rpass;
    Button register;

    private FirebaseAuth fauth;
    private ProgressDialog pdg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fauth=FirebaseAuth.getInstance();
        pdg=new ProgressDialog(this);

        rmail=(EditText)findViewById(R.id.remail);
        rmob=(EditText)findViewById(R.id.rmob);
        rfirst=(EditText)findViewById(R.id.rfname);
        rlast=(EditText)findViewById(R.id.rlname);
        rage=(EditText)findViewById(R.id.rage);
        rpass=(EditText)findViewById(R.id.rpass);
        register=(Button)findViewById(R.id.regbtn);

        register.setOnClickListener(this);



    }
    private void registerUser()
    {

        String email = rmail.getText().toString().trim();
        String mob = rmob.getText().toString().trim();
        String fname = rfirst.getText().toString().trim();
        String lname = rlast.getText().toString().trim();
        String age=rage.getText().toString().trim();
        String pass = rpass.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this,"please enter email", Toast.LENGTH_SHORT).show();
            //stopping execution function
            return;
        }
        if(TextUtils.isEmpty(mob))
        {
            //email is empty
            Toast.makeText(this,"please enter mobile number", Toast.LENGTH_SHORT).show();
            //stopping execution function
            return;
        }
        if(TextUtils.isEmpty(fname))
        {
            //email is empty
            Toast.makeText(this,"please enter first name", Toast.LENGTH_SHORT).show();
            //stopping execution function
            return;
        }
        if(TextUtils.isEmpty(lname))
        {
            //email is empty
            Toast.makeText(this,"please enter last name", Toast.LENGTH_SHORT).show();
            //stopping execution function
            return;
        }
        if(TextUtils.isEmpty(age))
        {
            //email is empty
            Toast.makeText(this,"please enter correct age", Toast.LENGTH_SHORT).show();
            //stopping execution function
            return;
        }

        if (TextUtils.isEmpty(pass))
        {
            //password is empty
            Toast.makeText(this,"please enter password", Toast.LENGTH_SHORT).show();
            //stopping execution function
            return;
        }

        pdg.setMessage("Registering user....");
        pdg.show();

        fauth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            //task is successful register and login
                            // we will start the profile activity here
                            //right now lets display a toast only

                            Toast.makeText(Register.this,"Resisted successfully ", Toast.LENGTH_SHORT).show();
                            pdg.dismiss();
                            startActivity(new Intent(Register.this,MainActivity.class));
                        }else {
                            Toast.makeText(Register.this,"could not register. please try again. ", Toast.LENGTH_SHORT).show();
                            pdg.dismiss();
                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {
            switch(view.getId()){
                case R.id.regbtn:
                    registerUser();
                    break;
            }
    }
}
