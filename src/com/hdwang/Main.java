package com.hdwang;

import java.util.Scanner;
import static com.hdwang.Calculator.*;

public class Main {

    public static void main(String[] args) {
	    // write your code here


        Scanner scanner = new Scanner(System.in);

        boolean loop = true;

        while (loop) {
            System.out.println("请输入第一个数：");
            String numStr1 = scanner.nextLine();
            if (!numberValid(numStr1)) {
                System.out.println(String.format("%s不合法", numStr1));
                continue;
            }

            System.out.println("请输入第二个数：");
            String numStr2 = scanner.nextLine();
            if (!numberValid(numStr2)) {
                System.out.println(String.format("%s不合法", numStr2));
                continue;
            }

            System.out.println();

            try {
                System.out.println("加法直接计算：" + (Double.parseDouble(numStr1) + Double.parseDouble(numStr2)));
            }catch (Exception ex){
                System.out.println("加法直接计算："+ex.getClass().getName());
            }
            long nt1 = System.nanoTime();
            String r1 = addNumber(numStr1, numStr2);
            System.out.println(String.format("大数加法计算：\n %s\n+%s\n=%s\n", numStr1, numStr2,r1));


            try {
                System.out.println("减法直接计算：" + (Double.parseDouble(numStr1) - Double.parseDouble(numStr2)));
            }catch (Exception ex){
                System.out.println("减法直接计算："+ex.getClass().getName());
            }
            nt1 = System.nanoTime();
            String r2 = subtractNumber(numStr1, numStr2);
            System.out.println(String.format("大数减法计算：\n %s\n-%s\n=%s\n", numStr1,numStr2, r2));


            try {
                System.out.println("乘法直接计算：" + (Double.parseDouble(numStr1) * Double.parseDouble(numStr2)));
            }catch (Exception ex){
                System.out.println("乘法直接计算："+ex.getClass().getName());
            }
            nt1 = System.nanoTime();
            String r3 = multiplyNumber(numStr1, numStr2);
            System.out.println(String.format("大数乘法计算：\n %s\n*%s\n=%s\n", numStr1,  numStr2, r3));

            try {
                System.out.println("除法直接计算：" + ((double)Double.parseDouble(numStr1) / (double) Double.parseDouble(numStr2)));
            }catch (Exception ex){
                System.out.println("除法直接计算："+ex.getClass().getName());
            }
            try {
                nt1 = System.nanoTime();
                String r4 = divideNumber(numStr1, numStr2);
                System.out.println(String.format("大数除法计算：\n %s\n/%s\n=%s\n", numStr1,numStr2, r4));
            }catch (Exception ex){
                System.out.println("大数除法计算："+ex.getClass().getName());
            }

            System.out.println("退出输入q，否则继续");
            String line = scanner.nextLine();
            if(line.equalsIgnoreCase("Q")){
                loop = false;
            }else{
                loop  = true;
            }
        }

    }
}
