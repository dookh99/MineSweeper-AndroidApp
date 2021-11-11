package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ToggleButton toggleButton;
    boolean mines [][];
    static BlockButton[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("test", "test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        mines = mineGenerator(9);
        buttons = new BlockButton[9][9];
        BlockButton.mineLocation = mines;
        BlockButton.buttons = this.buttons;
        BlockButton.uncovered = new boolean[9][9];

        TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f);

        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
                buttons[i][j].setLayoutParams(layoutParams);
                if (mines[i][j]) {
                    buttons[i][j].mine = true;
                }
                tableRow.addView(buttons[i][j]);
            }
            table.addView(tableRow);
        }

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Mines : " + Integer.toString(BlockButton.mines));

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) { //on
                    BlockButton.flagMode = true;
                } else { //off
                    BlockButton.flagMode = false;
                }
            }
        });
    }

    public boolean[][] mineGenerator(int j) {
        boolean[][] mines = new boolean[j][j];
        String [] numbers = new String[j+1];
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);

        for (int i = 0; i < j+1; i++) {
            int row = rand.nextInt(j); //0~j
            int column = rand.nextInt(j);
            String s = Integer.toString(row) + Integer.toString(column);
            if (Arrays.asList(numbers).contains(s)) { //중복이면 건너뛴다
                i--;
                continue;
            } else {
                numbers[i] = s;
                mines[row][column] = true;
            }
        }
        return mines;
    }

    public void onClick(View view) { //replay button listener
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
