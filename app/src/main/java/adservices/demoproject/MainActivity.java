package adservices.demoproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{
   private static int   SIGN_IN=0;
    private GoogleApiClient ac;
    private FirebaseAuth fa,femail;
    private FirebaseAuth.AuthStateListener mauth;

    EditText email,pass;
    Button login,reg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        reg=(Button)findViewById(R.id.reg);

        login.setOnClickListener(this);
        reg.setOnClickListener(this);


        fa=FirebaseAuth.getInstance();
        femail=FirebaseAuth.getInstance();
        mauth=new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){

                    Toast.makeText(MainActivity.this,"successfull"+user.getEmail(),Toast.LENGTH_LONG).show();
                }
                else {

                }
                }
            };
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        ac=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API)
                                                .build();
                    findViewById(R.id.gid).setOnClickListener(this);


        }

    @Override
    protected void onStart() {
        super.onStart();
        fa.addAuthStateListener(mauth);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mauth!=null){
            fa.removeAuthStateListener(mauth);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN){
            GoogleSignInResult gsr=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(gsr.isSuccess()){
                GoogleSignInAccount gsa=gsr.getSignInAccount();
                firebaseAuthWithGoogle(gsa);

            }
            else {
                Log.d("sign in","failed");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount ga){

        AuthCredential acr= GoogleAuthProvider.getCredential(ga.getIdToken(),null);
        fa.signInWithCredential(acr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("auth","complete"+task.isSuccessful());
            }
        });
    }
    private void signIn(){
        Intent i=Auth.GoogleSignInApi.getSignInIntent(ac);
        startActivityForResult(i,SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void login(){
        String mail=email.getText().toString();
        String pswd=pass.getText().toString();
        if(!mail.isEmpty()&&!pswd.isEmpty()) {
            femail.signInWithEmailAndPassword(mail, pswd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "sign in problem", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"login successfull",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(MainActivity.this,"fields are empty",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gid:
                signIn();
                break;
            case R.id.login:
                login();

                break;
            case R.id.reg:
                startActivity(new Intent(this,Register.class));
                break;

        }




    }
}
