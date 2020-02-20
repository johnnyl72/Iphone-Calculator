package org.openjfx;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.control.Button;
public class FXMLController {
    @FXML
    private Label result;
    private double num1 = 0, num2 = 0, ans = 0;;
    private String operator = "";
    private boolean initial_state = true;
    private boolean include = false;
    Glow glow;
    public void initialize() {
        result.setText("0");
    }
    @FXML
    public void hit(MouseEvent e) {
        this.glow = new Glow();
        glow.setLevel(.5);
        ((Button)e.getSource()).setEffect(glow);
    }
    public void release(MouseEvent e) {

        this.glow = new Glow();
        glow.setLevel(0);
        ((Button)e.getSource()).setEffect(glow);
    }
    @FXML
    public void pressNum(ActionEvent e) {
        if(initial_state) {
            result.setText("");
            initial_state = false;
        }
        String val = ((Button)e.getSource()).getText();
        if(val.equals(".")) {
            if(!include) {
                include = true;
                result.setText(result.getText() + val);
            }
            else
                return;
        }
        else
            result.setText(result.getText() + val);

    }
    @FXML
    public void pressOp(ActionEvent e) {
        include = false; //Reset to allow for decimal numbers after hitting operator
        String val = ((Button)e.getSource()).getText();
        //CLEAR
        if(val.equals("C")) {
            num1 = 0;
            num2 = 0;
            ans = 0;
            operator = "";
            result.setText("0");
            initial_state = true;
            return;
        }
        else if(val.equals("+/-")) {
            num1 = Double.parseDouble(result.getText());
            operator = val;
            ans = calc(num1, 0, operator);
            result.setText(String.valueOf(ans));
            operator = "";
            initial_state = true;
        }

        //NOT COMPUTING
        else if(!val.equals("=")) {
            //CONTINUE
            if(!operator.isEmpty()) {
                num2= Double.parseDouble(result.getText());
                ans = calc(num1, num2, operator);
                result.setText(String.valueOf(ans));
                num1 =  Double.parseDouble(result.getText());
                operator = val;
                initial_state = true;
                return;
            }
            //INITIAL OP
            else {
                num1 = Double.parseDouble(result.getText());
                operator = val;
                initial_state = true;
            }
        }
        //EQUAL
        else {
            //PRESS EQUAL WHEN NOTHING
            if(operator.isEmpty()) {
                initial_state = true;
                return;
            }
            //ACTUAL COMPUTE OF EQUAL
            else {
                num2= Double.parseDouble(result.getText());
                ans = calc(num1, num2, operator);
                result.setText(String.valueOf(ans));
                operator = "";
                initial_state = true;
            }
        }
    }

    public static double calc(double num1, double num2, String operator) {
        switch(operator) {
            case "+/-":
                return num1 *= -1;
            case "%":
                return num1 % num2;
            case "÷":
                return num1 / num2;
            case "×":
                return num1 * num2;
            case "-":
                return num1 - num2;
            case "+":
                return num1 + num2;
            default:
                return 0;
        }
    }//end calc
}
