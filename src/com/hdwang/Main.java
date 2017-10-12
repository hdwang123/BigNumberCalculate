package com.hdwang;

import java.math.BigDecimal;
import java.util.Scanner;
import static com.hdwang.Calculator.*;

public class Main {

    public static void main(String[] args) {
	    // write your code here

        testBigDecimal();

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

    /**
     * compare the BigDecimal and my algorithm
     *
     * the conclusion is：
     *
     * advantage
     * easily to understand.
     *
     * shortcoming
     * cost time more.
     *
     */
    private static void testBigDecimal(){
        String num1 = "-143425465345654765746864856785967896967967098670698598756951434254653456547657468648567859678969679670986706985987569514342546534565476574686485678596789696796709867069859875695.22222222222222222222";
        String num2 = "425363764765875875867980987675623542542542653653737373377331434254653456547657468648567859678969679670986706985987569514342546534565476574686485678596789696796709867069859875695.11111111111111111111";


        long time1 = System.nanoTime();
        BigDecimal b1 = new BigDecimal(num1);
        BigDecimal b2 = new BigDecimal(num2);
        System.out.println(b1.add(b2));
        System.out.println(b1.subtract(b2));
        System.out.println(b1.multiply(b2));
        System.out.println(b1.divide(b2,16,BigDecimal.ROUND_HALF_UP)); //保留10位，四舍五入
        System.out.println("cost time:"+(System.nanoTime()-time1)); //cost time:6565140ns

        //自己的计算(效率低了7倍，哎！)
        time1 = System.nanoTime();
        System.out.println(addNumber(num1,num2));
        System.out.println(subtractNumber(num1,num2));
        System.out.println(multiplyNumber(num1,num2));
        System.out.println(divideNumber(num1,num2));
        System.out.println("cost time:"+(System.nanoTime()-time1)); //cost time:41036076ns
    }
}
