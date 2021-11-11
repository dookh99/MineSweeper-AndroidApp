package com.example.minesweeper;
import android.content.Context;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;

public class BlockButton extends AppCompatButton {
    int x;
    int y;
    boolean mine = false;
    boolean flag = false;
    int neighborMines = 0;
    static int mines = 10; //남은 지뢰 수
    static int blocks = 81; //남은 블럭 수
    static boolean flagMode = false;
    static boolean [][] mineLocation; //지뢰의 위치를 모아놓은 배열
    static boolean [][] uncovered; //이미 눌린 블럭들 모음
    static BlockButton[][] buttons; //블럭들을 모아놓은 배열

    public BlockButton(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
        neighborMines = checkNeighbor(this.x, this.y);

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagMode) { //플래그 모드일때
                    if (flag) {
                        flag = false;
                        setText("");
                        return;
                    } else {
                        flag = true;
                        setText("F");
                        return;
                    }
                } else { //플래그 모드가 아닐 때
                    if (flag) { //플래그인 경우
                        return;
                    } else if (Uncover()) { //지뢰인 경우
                        //게임 종료...
                    } else { //지뢰가 아닌 경우
                        return;
                    }
                }
            }
        });
    }

    private void flag() {

    }

    private boolean Uncover() {
        if (flag) //플래그상태면 리턴
            return false;
        this.setEnabled(false); //클릭 안되게 바꾼다
        uncovered[this.x][this.y] = true;
        blocks -= 1; //남은 블록수 -1

        if (mine) { //지뢰일 경우
            setText("M");
            return true;
        } else { //지뢰가 아닌 경우
            if (neighborMines == 0) { //주변에 지뢰 없음
                getBackground().setAlpha(0); //완전 투명(0~255)
                uncoverCascade(this.x, this.y);
            } else { //주변에 지뢰 있음
                setText(Integer.toString(neighborMines));
                getBackground().setAlpha(0);
            }
            return false;
        }
    }

    private int checkNeighbor(int x, int y) {
        int count = 0;
        try {
            for(int i = x-1; i < x+2; i++) {
                if(i < 0 || i >= mineLocation.length)
                    continue;
                for(int j = y-1; j < y+2; j++) {
                    if(j < 0 || j >= mineLocation.length || (i == x && j == y))
                        continue;
                    else if(mineLocation[i][j])
                        count += 1;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        return count;
    }

    private void uncoverCascade(int x, int y) {
        try {
            for(int i = x-1; i < x+2; i++) {
                if(i < 0 || i >= buttons.length)
                    continue;
                for(int j = y-1; j < y+2; j++) {
                    if(j < 0 || j >= buttons.length || uncovered[i][j] || (i == x && j == y))
                        continue;
                    buttons[i][j].Uncover();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}