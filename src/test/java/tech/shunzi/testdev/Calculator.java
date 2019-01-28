package tech.shunzi.testdev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator {
    public static void main(String[] args) {
        new Interface();
    }
}

class Interface extends JFrame {
    ArrayList<String> numberList = new ArrayList<String>();//用来存该运算中的数，以及运算结果，由文本编辑框得到
    ArrayList<String> operatorList = new ArrayList<String>();//用来存该运算中使用了的运算符，当点击运算符按钮存起集合lists
    int count = 0; //计数运算符，用来作为集合中各元素的下标
    JPanel inputPanel;//GUI组件的布局包括如下三个过程：1。创建面板，确定面板中的布局管理
    JTextField inputField; //                     2.给面板添加标签
    JButton button;  //                     3.为面板添加按钮，文本编辑框等组件
    JButton numberButton7, btn2, btn3, btn4;
    JButton btn5, btn6, btn7, btn8;
    JButton btn9, btn10, btn11, btn12;
    JButton btn13, btn14, btn15, btn16;

    public Interface() {
        inputPanel = new JPanel();
        inputField = new JTextField(9); //文本框长度为9             
        button = new JButton("清零");
        inputPanel.add(inputField);
        inputPanel.add(button);
        this.add(inputPanel);
        JPanel panel = new JPanel(new GridLayout(4, 4));//设置panel面板的布局管理为GridLayout管理器，为一个4*4的网格
        numberButton7 = new JButton("7");
        btn2 = new JButton("8");
        btn3 = new JButton("9");
        btn4 = new JButton("+");
        btn5 = new JButton("4");
        btn6 = new JButton("5");
        btn7 = new JButton("6");
        btn8 = new JButton("-");
        btn9 = new JButton("1");
        btn10 = new JButton("2");
        btn11 = new JButton("3");
        btn12 = new JButton("×");
        btn13 = new JButton("0");
        btn14 = new JButton(".");
        btn15 = new JButton("=");
        btn16 = new JButton("÷"); //定义Button按钮
        panel.add(numberButton7);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);
        panel.add(btn5);
        panel.add(btn6);
        panel.add(btn7);
        panel.add(btn8);
        panel.add(btn9);
        panel.add(btn10);
        panel.add(btn11);
        panel.add(btn12);
        panel.add(btn13);
        panel.add(btn14);
        panel.add(btn15);
        panel.add(btn16); //将按钮添加进panel面板
        numberButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "7");
            }

        });
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "8");
            }
        });
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "9");
            }
        });
        btn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "4");
            }
        });
        btn6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "5");
            }
        });
        btn7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "6");
            }
        });
        btn9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "1");
            }
        });
        btn10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "2");
            }
        });
        btn11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "3");
            }
        });

        btn13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + "0");
            }
        });
        btn14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText(inputField.getText() + ".");
            }
        }); //为数字按钮和小数点按钮添加点击事件，点击之后按钮所代表数字显示在文本编辑框
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberList.add(inputField.getText()); //运算的实现，获取在文本编辑框上显示的文本，并存入集合list中
                operatorList.add("+"); //获取运算符，存入lists中
                count++;  //标记运算符的个数，也就是要进行运算的次数 
                inputField.setText("");  //将编辑框置为空的，方便下一个数据的额输入
            }
        });
        btn8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberList.add(inputField.getText());
                operatorList.add("-");
                count++;
                inputField.setText("");
            }
        });
        btn12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberList.add(inputField.getText());
                operatorList.add("×");
                count++;
                inputField.setText("");
            }

        });
        btn16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberList.add(inputField.getText());
                operatorList.add("÷");
                count++;
                inputField.setText("");
            }
        });
        btn15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberList.add(inputField.getText()); //点击按钮=时，把此事编辑框显示的数据存入list
                inputField.setText(Operator(operatorList.get(count - 1)));//把运算得到的结果存入list，作为下一个运算符运算的前一个数
            }
        });
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");
                numberList.clear(); //将list和lists集合保存的上一次运算的各要素清空
                operatorList.clear();
                count = 0; //把cout的值还原
            }
        });
        this.add(panel);
        this.setTitle("计算器");
        this.setSize(185, 205);
        this.setLayout(new FlowLayout());
        this.setVisible(true);//GUI窗体
    }

    public String Operator(String str) {
        Double result = 0.0;
        Double a = Double.parseDouble(numberList.get(2 * count - 2));//将list中记录的第一个运算符前一个数据转化为Double类型
        Double b = Double.parseDouble(numberList.get(2 * count - 1));//将list中记录的第一个运算符后一个数据转化为Double类型
        switch (str) {//cout表示第几个字符
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "×":
                result = a * b;
                break;
            case "÷":
                result = a / b;
                break;
        }
        String res = String.valueOf(result);
        return res; //通过switch语句得到各种运算之后的返回值并转化为String类型
    }
}
