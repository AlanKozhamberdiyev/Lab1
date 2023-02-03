package com.example.lab1;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private EditText plainTextInput;
    private EditText secretInput;
    private EditText passwordInput;
    private TextView resultView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plainTextInput = findViewById(R.id.plain_text_input);
        secretInput = findViewById(R.id.secret_input);
        passwordInput = findViewById(R.id.password_input);
        resultView = findViewById(R.id.result_text);
    }

    public void onEncryptClick(View view) {
        try {
            String plainText = plainTextInput.getText().toString();
            String secret = secretInput.getText().toString();
            String password = passwordInput.getText().toString();
            byte[] key = (secret + password).getBytes("UTF-8");
            key = java.util.Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            resultView.setText(Base64.encodeToString(encrypted, Base64.DEFAULT));
        } catch (Exception e) {
            resultView.setText("Error: " + e.getMessage());
        }
    }

    public void onDecryptClick(View view) {
        try {
            String encodedText = resultView.getText().toString();
            byte[] encrypted = Base64.decode(encodedText, Base64.DEFAULT);
            String secret = secretInput.getText().toString();
            String password = passwordInput.getText().toString();
            byte[] key = (secret + password).getBytes("UTF-8");
            key = java.util.Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(encrypted);
            resultView.setText(new String(decrypted, "UTF-8"));
        } catch (Exception e) {
            resultView.setText("Error: " + e.getMessage());
        }
    }
}

