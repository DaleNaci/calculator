package com.example.dalenash.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Widgets
    Button zero, one, two, three, four, five, six, seven, eight, nine, add, subtract, multiply, divide, sign, equal, period, percent, clear;
    public TextView box;
    public StringBuilder display;
    ArrayList<String> commands = new ArrayList<>();
    boolean afterSymbol;
    double answer = Integer.MAX_VALUE;
    boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        add = findViewById(R.id.add);
        subtract = findViewById(R.id.subtract);
        multiply = findViewById(R.id.multiply);
        divide = findViewById(R.id.divide);
        sign = findViewById(R.id.sign);
        equal = findViewById(R.id.equal);
        period = findViewById(R.id.period);
        percent = findViewById(R.id.percent);
        clear = findViewById(R.id.clear);
        box = findViewById(R.id.box);

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        add.setOnClickListener(this);
        subtract.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
        sign.setOnClickListener(this);
        equal.setOnClickListener(this);
        period.setOnClickListener(this);
        percent.setOnClickListener(this);
        clear.setOnClickListener(this);

        display = new StringBuilder("0");
        afterSymbol = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(200);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                box.setText(display.toString());
                            }
                        });
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.zero:     number("0");       break;
            case R.id.one:      number("1");       break;
            case R.id.two:      number("2");       break;
            case R.id.three:    number("3");       break;
            case R.id.four:     number("4");       break;
            case R.id.five:     number("5");       break;
            case R.id.six:      number("6");       break;
            case R.id.seven:    number("7");       break;
            case R.id.eight:    number("8");       break;
            case R.id.nine:     number("9");       break;
            case R.id.add:      operator("+");     break;
            case R.id.subtract: operator("-");     break;
            case R.id.multiply: operator("*");     break;
            case R.id.divide:   operator("/");     break;
            case R.id.sign:     changeSign();      break;
            case R.id.equal:    calculate();       break;
            case R.id.period:   decimal();         break;
            case R.id.percent: break;
            case R.id.clear:    clear(true, true); break;
        }
    }

    // Adds numbers to the display and commands
    public void number(String num) {
        add.setBackgroundResource(R.drawable.orange_button);
        subtract.setBackgroundResource(R.drawable.orange_button);
        multiply.setBackgroundResource(R.drawable.orange_button);
        divide.setBackgroundResource(R.drawable.orange_button);
        add.setTextColor(0xffffffff);
        subtract.setTextColor(0xffffffff);
        multiply.setTextColor(0xffffffff);
        divide.setTextColor(0xffffffff);
        if (afterSymbol)
            clear(false, false);
        if (display.length() < 8) {
            if (num.equals("0") && box.getText().toString().equals("0")) {
                ;
            } else if (box.getText().toString().equals("0")) {
                commands.add(num);
                display.deleteCharAt(0);
                display.append(num);
            } else {
                commands.add(num);
                display.append(num);
            }
            afterSymbol = false;
        }
    }
    public void decimal() {
        if (display.indexOf(".") == -1) {
            display.append(".");
            commands.add(".");
        }
    }

    // Clears the display and commands
    public void clear(boolean placeZero, boolean reset) {
        if (reset)
            commands = new ArrayList<>();
        display = placeZero ? new StringBuilder("0") : new StringBuilder();
        add.setBackgroundResource(R.drawable.orange_button);
        add.setTextColor(0xffffffff);
        subtract.setBackgroundResource(R.drawable.orange_button);
        subtract.setTextColor(0xffffffff);
        multiply.setBackgroundResource(R.drawable.orange_button);
        multiply.setTextColor(0xffffffff);
        divide.setBackgroundResource(R.drawable.orange_button);
        divide.setTextColor(0xffffffff);
    }

    // Operators
    public void operator(String op) {
        if (answer != Integer.MAX_VALUE && commands.size() == 0)
            commands.add(Double.toString(answer));
        if (afterSymbol) {
            commands.set(commands.size() - 1, op);
        }
        else {
            commands.add(op);
        }
        System.out.println(commands);
        if (op.equals("+")) {
            add.setBackgroundResource(R.drawable.white_button);
            subtract.setBackgroundResource(R.drawable.orange_button);
            multiply.setBackgroundResource(R.drawable.orange_button);
            divide.setBackgroundResource(R.drawable.orange_button);
            add.setTextColor(0xffff9400);
            subtract.setTextColor(0xffffffff);
            multiply.setTextColor(0xffffffff);
            divide.setTextColor(0xffffffff);
        }
        if (op.equals("-")) {
            add.setBackgroundResource(R.drawable.orange_button);
            subtract.setBackgroundResource(R.drawable.white_button);
            multiply.setBackgroundResource(R.drawable.orange_button);
            divide.setBackgroundResource(R.drawable.orange_button);
            add.setTextColor(0xffffffff);
            subtract.setTextColor(0xffff9400);
            multiply.setTextColor(0xffffffff);
            divide.setTextColor(0xffffffff);
        }
        if (op.equals("*")) {
            add.setBackgroundResource(R.drawable.orange_button);
            subtract.setBackgroundResource(R.drawable.orange_button);
            multiply.setBackgroundResource(R.drawable.white_button);
            divide.setBackgroundResource(R.drawable.orange_button);
            add.setTextColor(0xffffffff);
            subtract.setTextColor(0xffffffff);
            multiply.setTextColor(0xffff9400);
            divide.setTextColor(0xffffffff);
        }
        if (op.equals("/")) {
            add.setBackgroundResource(R.drawable.orange_button);
            subtract.setBackgroundResource(R.drawable.orange_button);
            multiply.setBackgroundResource(R.drawable.orange_button);
            divide.setBackgroundResource(R.drawable.white_button);
            add.setTextColor(0xffffffff);
            subtract.setTextColor(0xffffffff);
            multiply.setTextColor(0xffffffff);
            divide.setTextColor(0xffff9400);
        }
        afterSymbol = true;
    }

    public void changeSign() {
        if (display.charAt(0) == '-')
            display.deleteCharAt(0);
        else
            display.insert(0, '-');
    }

    public void calculate() {
        if (commands.size() == 0)
            error = true;
        else if (commands.get(commands.size()-1).equals("*") || commands.get(commands.size()-1).equals("/") || commands.get(commands.size()-1).equals("+") || commands.get(commands.size()-1).equals("-"))
            error = true;
        ArrayList<String> commands2 = new ArrayList<>();
        double finalNum = 0;
        boolean isDecimal = false;
        int intoDecimal = 0;
        System.out.println("Calculate()");
        System.out.println(commands);
        for (String command : commands) {
            System.out.println("Command: "+command);
            if (isNumber(command)) {
                currentNum = Double.parseDouble(command);
                if (isDecimal) {
                    finalNum += currentNum / Math.pow(10, intoDecimal);
                    intoDecimal++;
                }
                else
                    finalNum = finalNum*10 + currentNum;
            }
            if (command.equals(".")) {
                isDecimal = true;
                intoDecimal++;
            }
            else if (!isNumber(command))
                commands2.add(Double.toString(finalNum));
            if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
                commands2.add(command);
                finalNum = 0;
                isDecimal = false;
                intoDecimal = 0;
            }
        }
        commands2.add(Double.toString(finalNum));
        System.out.println("Commands2: " + commands2);

        for (int i = 0; i < commands2.size(); i++) {
            if (commands2.get(i).equals("*")) {
                double x = Double.parseDouble(commands2.get(i-1));
                double y = Double.parseDouble(commands2.get(i+1));
                commands2.remove(i+1);
                commands2.remove(i);
                commands2.set(i-1, Double.toString(x*y));
                i--;
            }
            if (commands2.get(i).equals("/")) {
                double x = Double.parseDouble(commands2.get(i-1));
                double y = Double.parseDouble(commands2.get(i+1));
                if (y == 0) {
                    System.out.println("DIVIDE BY 0 ERROR");
                    error = true;
                } else {
                    commands2.remove(i + 1);
                    commands2.remove(i);
                    commands2.set(i - 1, Double.toString(x / y));
                    i--;
                }
            }
        }
        System.out.println("After M/D: " + commands2);
        for (int i = 0; i < commands2.size(); i++) {
            if (commands2.get(i).equals("+")) {
                double x = Double.parseDouble(commands2.get(i-1));
                double y = Double.parseDouble(commands2.get(i+1));
                commands2.remove(i+1);
                commands2.remove(i);
                commands2.set(i-1, Double.toString(x+y));
                i--;
            }
            if (commands2.get(i).equals("-")) {
                double x = Double.parseDouble(commands2.get(i-1));
                double y = Double.parseDouble(commands2.get(i+1));
                commands2.remove(i+1);
                commands2.remove(i);
                commands2.set(i-1, Double.toString(x-y));
                i--;
            }
        }
        answer = Double.parseDouble(commands2.get(0));
        if (error) {
            display = new StringBuilder("Error");
            error = false;
        } else if (answer % 1 == 0) {
            System.out.println("Answer (int): " + (int)answer);
            if ((int)answer > 99999999) {
                System.out.println("MAX VALUE");
                DecimalFormat f = new DecimalFormat("0.##E0");
                System.out.println("DecimalFormat: " + f.format(answer));
                display = new StringBuilder(f.format(answer));
            }
            else {
                display = new StringBuilder(Integer.toString((int) answer));
            }
        }
        else {
            System.out.println(commands2.get(0).length());
            if (commands2.get(0).length() >= 15) { // POST-PROJECT: 15 TO 8
                int count = 0;
                int temp = (int)Double.parseDouble(commands2.get(0));
                while (temp > 0) {
                    temp /= 10;
                    count++;
                }
                System.out.println("Count: " + count);
                if (count > 7) {
                    display = new StringBuilder(Double.toString(round(Double.parseDouble(commands2.get(0)), 0)));
                    System.out.println("Rounded: " + round(Double.parseDouble(commands2.get(0)), 0));
                }
                else {
                    display = new StringBuilder(Double.toString(round(Double.parseDouble(commands2.get(0)), 7 - count)));
                }
            }
            else {
                display = new StringBuilder(Double.toString(round(Double.parseDouble(commands2.get(0)), 8 - commands2.get(0).length())));
            }
        }
        System.out.println("After A/S: " + commands2);
        commands = new ArrayList<>();
    }

    public static boolean isNumber(String s) {
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
