package com.example.calculatorgit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calculator.R;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, input;
    MaterialButton button_AC, button_percent, button_divide;
    MaterialButton button_seven, button_eight, button_nine, button_multiply;
    MaterialButton button_four, button_five, button_six, button_minus;
    MaterialButton button_one, button_two, button_three, button_add;
    MaterialButton button_zero, button_dot, button_back, button_equal;
    MaterialButton button_sqrt, button_inverse, button_square, button_openParen, button_closeParen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        input = findViewById(R.id.input);

        assignId(button_AC, R.id.button_AC);
        assignId(button_percent, R.id.button_percent);
        assignId(button_divide, R.id.button_divide);
        assignId(button_seven, R.id.button_seven);
        assignId(button_eight, R.id.button_eight);
        assignId(button_nine, R.id.button_nine);
        assignId(button_multiply, R.id.button_multiply);
        assignId(button_four, R.id.button_four);
        assignId(button_five, R.id.button_five);
        assignId(button_six, R.id.button_six);
        assignId(button_minus, R.id.button_minus);
        assignId(button_one, R.id.button_one);
        assignId(button_two, R.id.button_two);
        assignId(button_three, R.id.button_three);
        assignId(button_add, R.id.button_add);
        assignId(button_dot, R.id.button_dot);
        assignId(button_zero, R.id.button_zero);
        assignId(button_back, R.id.button_back);
        assignId(button_equal, R.id.button_equal);
        assignId(button_sqrt, R.id.button_sqrt);
        assignId(button_inverse, R.id.button_inverse);
        assignId(button_square, R.id.button_square);
        assignId(button_openParen, R.id.button_openParen);
        assignId(button_closeParen, R.id.button_closeParen);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        if (btn != null) {
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalc = input.getText().toString();

        if (buttonText.equals("AC")) {
            input.setText("");
            result.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            String finalResult = getResult(dataToCalc);
            if (!finalResult.equals("Err")) {
                result.setText(finalResult);
                input.setText("");
            }
            return;
        }

        // clear button
        if (buttonText.equals("⌫"))
        {
            if (!dataToCalc.isEmpty())
            {
                dataToCalc = dataToCalc.substring(0, dataToCalc.length() - 1);
            }
        }
        // square button
        else if (buttonText.equals("x²"))
        {
            if (!dataToCalc.isEmpty())
            {
                dataToCalc = "Math.pow(" + dataToCalc + ",2)";
            }
        }
        // Handling Square Root Button
        else if (buttonText.equals("√")) { // Handling Square Root Button
            if (!dataToCalc.isEmpty()) {
                dataToCalc = "Math.sqrt(" + dataToCalc + ")";
            }
        }else if (buttonText.equals("x⁻¹")) {
            dataToCalc = "1/(" + dataToCalc + ")";
        } else if (buttonText.equals("(")) {
            dataToCalc += "(";
        } else if (buttonText.equals(")")) {
            dataToCalc += ")";
        } else {
            dataToCalc += buttonText;
        }

        input.setText(dataToCalc);
    }


    String getResult(String data) {
        try {
            if (data.isEmpty()) return "0";
            data = data.replace("×", "*").replace("÷", "/");

            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();

            // Evaluate the JavaScript expression
            String finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();
            context.exit();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }

}