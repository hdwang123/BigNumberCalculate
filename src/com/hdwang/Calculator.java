package com.hdwang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 大数四则运算（超出long型的大数（64位：18446744073709551615））
 * Created by hdwang on 2017/10/9.
 */
public class Calculator {

    /**
     * 两非负整数相加
     * @param numStr1 数1
     * @param numStr2 数2
     * @return 结果
     */
    public static String add(String numStr1, String numStr2){

        int numLen1 = numStr1.length();
        int numLen2 = numStr2.length();

        int[] numArray1 = new int[numLen1]; //数字数组
        int[] numArray2 = new int[numLen2];



        // "12345"-> [5,4,3,2,1]
        for(int i=0;i<numLen1;i++){
            String c = numStr1.substring(i,i+1);
            numArray1[numLen1-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }
        for(int i=0;i<numLen2;i++){
            String c = numStr2.substring(i,i+1);
            numArray2[numLen2-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }


        int minLen = 0; //取长度小的数位数
        int maxLen = 0; //取长度大的数位数
        int[] maxArray = null; //长度大的数
        if(numLen1<numLen2){
            minLen = numLen1;
            maxLen = numLen2;
            maxArray = numArray2;
        }else{
            minLen = numLen2;
            maxLen = numLen1;
            maxArray = numArray1;
        }

        int[] resultArray = new int[maxLen+1]; //考虑到可能会进位，多给一个元素空间

        //两数长度相同的部分，同位相加，超出9进1
        int added = 0;
        int i=0;
        for(;i<minLen;i++){
            int t = numArray1[i]+numArray2[i]+added; //两数相加，再加进位
            if(t>9){
                added = 1; //进1
                resultArray[i] = t-10; //当前位计算结果
            }else{
                added = 0; //不进位
                resultArray[i] = t; //当前位计算结果
            }
        }
        //长度超出部分累加
        for(;i<maxLen;i++){
            int t = maxArray[i]+added; //多余位数加上进位
            if(t>9){
                added = 1; //进1
                resultArray[i] = t-10; //当前位计算结果
            }else{
                added = 0; //不进位
                resultArray[i] = t; //当前位计算结果
            }
        }
        resultArray[i] = added; //最高位

        //拼接结果 [1,4,8,2,0] -> 2841
        StringBuilder builder = new StringBuilder();
        for(int n=resultArray.length-1;n>=0;n--){
            //如果最高位为0,移除
            if(n==resultArray.length-1 && resultArray[resultArray.length-1]==0){
                continue; //跳过
            }else{
                builder.append(resultArray[n]);
            }
        }

        return builder.toString();
    }

    /**
     * 两非负整数相减
     * @param numStr1 数1
     * @param numStr2 数2
     * @return 结果
     */
    public static String subtract(String numStr1,String numStr2){
        int numLen1 = numStr1.length();
        int numLen2 = numStr2.length();

        int[] numArray1 = new int[numLen1]; //数字数组
        int[] numArray2 = new int[numLen2];



        // "12345"-> [5,4,3,2,1]
        for(int i=0;i<numLen1;i++){
            String c = numStr1.substring(i,i+1);
            numArray1[numLen1-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }
        for(int i=0;i<numLen2;i++){
            String c = numStr2.substring(i,i+1);
            numArray2[numLen2-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }


        int minLen = 0; //取长度小的数位数
        int maxLen = 0; //取长度大的数位数
        int[] maxArray = null; //数值大的数
        if(numLen1<numLen2){
            minLen = numLen1;
            maxLen = numLen2;
            maxArray = numArray2;
        }else{
            minLen = numLen2;
            maxLen = numLen1;
            maxArray = numArray1;
            if(numLen1 == numLen2){ //等于
                maxArray = getMaxNumber(numArray1,numArray2);
            }
        }
        int[] minArray = maxArray==numArray1?numArray2:numArray1; //数值小的数

        int[] resultArray = new int[maxLen];

        //大数-小数，同位相减，小于0借位
        int subtracted = 0;
        int i=0;
        for(;i<minLen;i++){
            int t = maxArray[i] - minArray[i] - subtracted; //两数相减，再减借位
            if(t<0){
                subtracted = 1; //向高位借1，暂存起来
                resultArray[i] = t+10; //当前位计算结果（借1相当于借了10）
            }else{
                subtracted = 0; //不借位
                resultArray[i] = t; //当前位计算结果
            }
        }
        //大数超出部分减掉借位
        for(;i<maxLen;i++){
            int t = maxArray[i]-subtracted; //多余位数减掉借位
            if(t<0){
                subtracted = 1; //进1
                resultArray[i] = t+10; //当前位计算结果
            }else{
                subtracted = 0; //不借位
                resultArray[i] = t; //当前位计算结果
            }
        }

        //拼接结果 [1,4,8,2,0] -> 2841
        StringBuilder builder = new StringBuilder();
        boolean highBitNotEqualZero = false; //存在高位不为0的情况，低位0保留
        for(int n=resultArray.length-1;n>=0;n--){
            //如果高位为0,移除
            if(resultArray[n]==0 && !highBitNotEqualZero && n!=0){ //高位无用的0去除
                continue; //跳过
            }else{
                highBitNotEqualZero = true; //找到不为0的位
                builder.append(resultArray[n]);
            }
        }

        if(maxArray == numArray1){ //第一个数大或相等

        }else{  //第一个数小于第二个数，相减为负数
            builder.insert(0,"-");
        }

        return builder.toString();
    }

    /**
     * 两非负整数相乘
     * @param numStr1 数1
     * @param numStr2 数2
     * @return 结果
     */
    public static String multiply(String numStr1,String numStr2){
        int numLen1 = numStr1.length();
        int numLen2 = numStr2.length();

        int[] numArray1 = new int[numLen1]; //数字数组
        int[] numArray2 = new int[numLen2];



        // "12345"-> [5,4,3,2,1]
        for(int i=0;i<numLen1;i++){
            String c = numStr1.substring(i,i+1);
            numArray1[numLen1-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }
        for(int i=0;i<numLen2;i++){
            String c = numStr2.substring(i,i+1);
            numArray2[numLen2-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }


        int minLen = 0; //取长度小的数位数
        int maxLen = 0; //取长度大的数位数
        int[] maxArray = null; //长度大的数
        int[] minArray = null; //长度小的数
        if(numLen1<numLen2){
            minLen = numLen1;
            maxLen = numLen2;
            minArray = numArray1;
            maxArray = numArray2;
        }else{
            minLen = numLen2;
            maxLen = numLen1;
            minArray = numArray2;
            maxArray = numArray1;
        }

        //二维数组存储结果，例如：23*23 ->[[6,9],[4,6]] ,内部括号（低维）存某位的相乘结果，高维低位存个位,十位...
        int[][] resultArray = new int[minLen][maxLen+1];

        //长度大的数*长度小的数的每一位，分别存到相应数组中，然后累加
        for(int h=0;h<minLen;h++){ //高维
            int l=0;
            int added = 0;
            for(;l<maxLen;l++){ //低维
                int t = maxArray[l]*minArray[h]+added; //长度大的数的每一位*长度小的数的个位、十位...
                if(t>9){
                    added = t/10; //进位
                    resultArray[h][l] = t%10; //当前位计算结果
                }else{
                    added = 0; //不进位
                    resultArray[h][l] = t; //当前位计算结果
                }
            }
            resultArray[h][l] = added; //个位、十位...的计算结果的最高位
        }

        //对结果补位（左移），个位不动，十位补0，百位补00...，然后累加
        int[] sum = null; //最终累加结果
        int[] lowBitResult = null; //低位补0结果（前一位）
        for(int h=0;h<minLen;h++){
            int[] bitResult =  resultArray[h];
            int[] r;  //个位、十位...的补0结果
            if(h==0){ //个位
                r  = bitResult;
                sum = r;
                lowBitResult = r; //记录下来，待下次循环累加
            }else{ //十位...的计算结果
                r = new int[resultArray[h].length+h]; //初始化默认就是0的
                int rLen = r.length-1;
                for(int i=bitResult.length-1;i>=0;i--){ //从高位开始复制到新数组
                    r[rLen--] = bitResult[i];
                }
                //累加之前的数
                sum = new int[r.length+1]; //取高位长度+1，可能进位

                //================加法核心算法====================
                //两数长度相同的部分，同位相加，超出9进1
                int added = 0;
                int i=0;
                for(;i<lowBitResult.length;i++){
                    int t = lowBitResult[i]+r[i]+added; //两数相加，再加进位
                    if(t>9){
                        added = 1; //进1
                        sum[i] = t-10; //当前位计算结果
                    }else{
                        added = 0; //不进位
                        sum[i] = t; //当前位计算结果
                    }
                }
                //长度超出部分累加
                for(;i<r.length;i++){
                    int t = r[i]+added; //多余位数加上进位
                    if(t>9){
                        added = 1; //进1
                        sum[i] = t-10; //当前位计算结果
                    }else{
                        added = 0; //不进位
                        sum[i] = t; //当前位计算结果
                    }
                }
                sum[i] = added; //最高位
                //===============================================

                lowBitResult = sum; //记录下来，待下次循环累加
            }
        }

        //拼接结果 [1,4,8,2,0] -> 2841
        StringBuilder builder = new StringBuilder();
        boolean existHighNotZero = false; //高位存在不为0的，这个0就不能移除
        for(int n=sum.length-1;n>=0;n--){
            //移除高位无效的0，保留最后一个0
            if(sum[n]==0 && !existHighNotZero && n!=0){
                continue; //跳过
            }else{
                existHighNotZero = true;
                builder.append(sum[n]);
            }
        }

        return builder.toString();
    }

    /**
     * 两非负整数相除
     * @param numStr1 数1(被除数)
     * @param numStr2 数2(除数,不能超过long型)
     * @return 结果
     */
    @Deprecated
    public static String divide(String numStr1,String numStr2){
        //除数不能为0
        if("0".equals(numStr2)){
            return "NaN";
        }

        int numLen1 = numStr1.length();
        int numLen2 = numStr2.length();

        int[] numArray1 = new int[numLen1]; //数字数组
        int[] numArray2 = new int[numLen2];



        // "12345"-> [5,4,3,2,1]
        for(int i=0;i<numLen1;i++){
            String c = numStr1.substring(i,i+1);
            numArray1[numLen1-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }
        for(int i=0;i<numLen2;i++){
            String c = numStr2.substring(i,i+1);
            numArray2[numLen2-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }

        int effectiveNum = (numLen1 >= numLen2 ? numLen1:numLen2)+16; //有效位数: 默认大数长度+16
        int[] resultArray = new int[effectiveNum]; //高位存高位

        //将被除数的每一位除以除数，取整为该位结果，取余暂存借给低位(除数不能大过long型，除非除法转换为减法)
        long yu = 0;
        int resultIndex = effectiveNum-1;
        for(int i=numArray1.length-1;i>=0;i--){
            long num = yu * 10 + numArray1[i]; //被除数该位为：余数*10+自己
            int r= (int)(num / Long.parseLong(numStr2)); //取整
            yu = num % Long.parseLong(numStr2); //取余
            resultArray[resultIndex--] = r;
        }
        int decimalPoint = effectiveNum-numArray1.length-1; //小数点位置
        if(yu!=0){
            int decimal = decimalPoint; //小数
            for(int i=0;i<effectiveNum-numArray1.length;i++){
                long num = yu * 10 + 0; //小数部分被除数补0
                int r= (int)(num / Long.parseLong(numStr2)); //取整
                yu = num % Long.parseLong(numStr2); //取余
                resultArray[decimal--] = r;
                if(yu==0){
                    break; //余数为0，提前退出
                }
            }
        }

        //拼接结果
        StringBuilder builder = new StringBuilder();
        boolean existHighNotZero = false;
        for(int i=effectiveNum-1;i>=0;i--){
            if(i==decimalPoint){
                builder.append(".");
            }
            if(resultArray[i]==0){
                if(!existHighNotZero && i>decimalPoint+1){ //跳过高位无用的0
                    continue;
                }
            }else{
                existHighNotZero = true;
            }
            builder.append(resultArray[i]);
        }
        String result = builder.toString();
        //去除尾部无用的0
        int endIndex = result.length();
        for(int i=result.length()-1;i>=0;i--){
            char c = result.charAt(i);
            if(c!='0'){
                endIndex = i+1;
                break;
            }
        }
        //去除多余的小数点
        if(result.charAt(endIndex-1)=='.'){
            endIndex = endIndex-1;
        }
        result = result.substring(0,endIndex);
        return result;
    }

    /**
     * 两非负整数相除（增强版，除数、被除数都可以超过long型）
     * @param numStr1 数1(被除数)
     * @param numStr2 数2(除数)
     * @return 结果
     */
    public static String divideEnhanced(String numStr1,String numStr2){

        //除数不能为0
        if("0".equals(numStr2)){
            return "NaN";
        }

        int numLen1 = numStr1.length();
        int numLen2 = numStr2.length();

        int[] numArray1 = new int[numLen1]; //数字数组
        int[] numArray2 = new int[numLen2];



        // "12345"-> [5,4,3,2,1]
        for(int i=0;i<numLen1;i++){
            String c = numStr1.substring(i,i+1);
            numArray1[numLen1-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }
        for(int i=0;i<numLen2;i++){
            String c = numStr2.substring(i,i+1);
            numArray2[numLen2-i-1] = Integer.parseInt(c); //低位存字符串尾部数字
        }

        int effectiveNum = (numLen1 >= numLen2 ? numLen1:numLen2)+16; //有效位数: 默认大数长度+16
        int[] resultArray = new int[effectiveNum]; //高位存高位

        //将被除数的每一位除以除数，取整为该位结果，取余暂存借给低位
        String yu = "0";
        int resultIndex = effectiveNum-1;
        for(int i=numArray1.length-1;i>=0;i--){
            String num = "0".equals(yu)?numArray1[i]+"":add(yu+"0",numArray1[i]+""); //被除数该位为：余数*10+自己
            DivideResult result = getDivideResult(num,numStr2);
            String r= result.getR() ; //取整
            yu = result.getYu(); //取余
            resultArray[resultIndex--] = Integer.parseInt(r); //某位上的结果肯定小于10
        }
        int decimalPoint = effectiveNum-numArray1.length-1; //小数点位置
        if(!"0".equals(yu)){
            int decimal = decimalPoint; //小数
            for(int i=0;i<effectiveNum-numArray1.length;i++){
                String num = yu+"0"; //小数部分被除数补0
                DivideResult result = getDivideResult(num,numStr2);
                String r= result.getR() ; //取整
                yu = result.getYu(); //取余
                resultArray[decimal--] = Integer.parseInt(r);
                if("0".equals(yu)){
                    break; //余数为0，提前退出
                }
            }
        }

        //拼接结果
        StringBuilder builder = new StringBuilder();
        boolean existHighNotZero = false;
        for(int i=effectiveNum-1;i>=0;i--){
            if(i==decimalPoint){
                builder.append(".");
            }
            if(resultArray[i]==0){
                if(!existHighNotZero && i>decimalPoint+1){ //跳过高位无用的0
                    continue;
                }
            }else{
                existHighNotZero = true;
            }
            builder.append(resultArray[i]);
        }
        String result = builder.toString();
        //去除尾部无用的0
        int endIndex = result.length();
        for(int i=result.length()-1;i>=0;i--){
            char c = result.charAt(i);
            if(c!='0'){
                endIndex = i+1;
                break;
            }
        }
        //去除多余的小数点
        if(result.charAt(endIndex-1)=='.'){
            endIndex = endIndex-1;
        }
        result = result.substring(0,endIndex);
        return result;
    }


    /**
     * 两非负小数相加
     *
     * 核心思路：小数部分小的补齐和另一个数一致，然后去掉小数点运算，然后对运算结果添加小数点。
     * 例如： 2.5+3 -> 25+30;  2.35+2.4->235+240;
     * @param decimal1 小数1
     * @param decimal2 小数2
     * @return 结果
     */
    public static String addDecimal(String decimal1,String decimal2){
        int decimalLen1 = 0; //小数部分长度
        int decimalLen2 = 0;
        int decimalPointIndex1 = decimal1.lastIndexOf(".");
        if(decimalPointIndex1 == -1){ //找不到小数点（整数）

        }else{
            decimalLen1 = decimal1.length()-1 - decimalPointIndex1;
        }
        int decimalPointIndex2 = decimal2.lastIndexOf(".");
        if(decimalPointIndex2 == -1){ //找不到小数点（整数）

        }else{
            decimalLen2 = decimal2.length()-1 - decimalPointIndex2;
        }

        //小数部分长度（最长的）
        int maxDecimalLen = decimalLen1 >= decimalLen2 ? decimalLen1:decimalLen2;

        //提升数值（乘10 或者 补0）
        if(decimalLen1 == maxDecimalLen){ //第一个数小数部分长度最长
            decimal1 = decimal1.replace(".",""); //直接去除小数点

            StringBuilder builder = new StringBuilder(decimal2.replace(".","")); //去点
            for(int i=0;i<decimalLen1-decimalLen2;i++){ //多出的小数位数
                builder.append("0"); //补0
            }
            decimal2 = builder.toString();
        }else{
            decimal2 = decimal2.replace(".",""); //直接去除小数点

            StringBuilder builder = new StringBuilder(decimal1.replace(".","")); //去点
            for(int i=0;i<decimalLen2-decimalLen1;i++){ //多出的小数位数
                builder.append("0"); //补0
            }
            decimal1 = builder.toString();
        }

        //调用非负整数加法运算
        String result = add(decimal1,decimal2);

        //结果添加小数点
        StringBuilder builder = new StringBuilder(result);
        if(maxDecimalLen != 0){ //不是整数
            builder.insert(result.length()-maxDecimalLen,".");
        }
        return  builder.toString();
    }

    /**
     * 两非负小数相减(和小数加法的思路一致)
     *
     * 核心思路：小数部分小的补齐和另一个数一致，然后去掉小数点运算，然后对运算结果添加小数点。
     * 例如： 2.5-3 -> 25-30;  2.35-2.4->235-240;
     * @param decimal1 小数1
     * @param decimal2 小数2
     * @return 结果
     */
    public static String subtractDecimal(String decimal1,String decimal2){
        int decimalLen1 = 0; //小数部分长度
        int decimalLen2 = 0;
        int decimalPointIndex1 = decimal1.lastIndexOf(".");
        if(decimalPointIndex1 == -1){ //找不到小数点（整数）

        }else{
            decimalLen1 = decimal1.length()-1 - decimalPointIndex1;
        }
        int decimalPointIndex2 = decimal2.lastIndexOf(".");
        if(decimalPointIndex2 == -1){ //找不到小数点（整数）

        }else{
            decimalLen2 = decimal2.length()-1 - decimalPointIndex2;
        }

        //小数部分长度（最长的）
        int maxDecimalLen = decimalLen1 >= decimalLen2 ? decimalLen1:decimalLen2;

        //提升数值（乘10 或者 补0）
        if(decimalLen1 == maxDecimalLen){ //第一个数小数部分长度最长
            decimal1 = decimal1.replace(".",""); //直接去除小数点

            StringBuilder builder = new StringBuilder(decimal2.replace(".","")); //去点
            for(int i=0;i<decimalLen1-decimalLen2;i++){ //多出的小数位数
                builder.append("0"); //补0
            }
            decimal2 = builder.toString();
        }else{
            decimal2 = decimal2.replace(".",""); //直接去除小数点

            StringBuilder builder = new StringBuilder(decimal1.replace(".","")); //去点
            for(int i=0;i<decimalLen2-decimalLen1;i++){ //多出的小数位数
                builder.append("0"); //补0
            }
            decimal1 = builder.toString();
        }

        //调用非负整数减法运算
        String result = subtract(decimal1,decimal2);
        boolean isNegative = false;
        //结果添加小数点
        StringBuilder builder = new StringBuilder();
        if(maxDecimalLen != 0) { //不是整数
            //两数相减可能出现负号，特殊处理一下
            if(result.startsWith("-")){
                isNegative = true;
                result = result.replace("-","");
            }
            //两数相减可能使长度减小，特殊处理一下
            if(result.length()<= maxDecimalLen){
                String padLeft = "";
                for(int i=0;i< maxDecimalLen-result.length()+1;i++){
                    padLeft+="0";
                }
                result = padLeft + result; //左补0，方可让小数点插入
            }
            builder.append(result);
            builder.insert(result.length() - maxDecimalLen, ".");

            //负号还原
            if(isNegative){
                builder.insert(0,"-");
            }
        }
        return  builder.toString();
    }

    /**
     * 两非负小数相乘
     *
     * 核心思路：去掉小数点运算，然后对运算结果添加小数点。
     * 例如： 2.5*3 -> 25*3;  2.35*2.4->235*24;
     * @param decimal1 小数1
     * @param decimal2 小数2
     * @return 结果
     */
    public static String multiplyDecimal(String decimal1,String decimal2){
        int decimalLen1 = 0; //小数部分长度
        int decimalLen2 = 0;
        int decimalPointIndex1 = decimal1.lastIndexOf(".");
        if(decimalPointIndex1 == -1){ //找不到小数点（整数）

        }else{
            decimalLen1 = decimal1.length()-1 - decimalPointIndex1;
        }
        int decimalPointIndex2 = decimal2.lastIndexOf(".");
        if(decimalPointIndex2 == -1){ //找不到小数点（整数）

        }else{
            decimalLen2 = decimal2.length()-1 - decimalPointIndex2;
        }

        //小数位数为两个数的小数位数之和
        int sumDecimalLen = decimalLen1 + decimalLen2;

        //去掉小数点
        decimal1 = decimal1.replace(".","");
        decimal2 = decimal2.replace(".","");

        //去除高位无用0
        if(decimal1.startsWith("0")){
            decimal1 = removeHighZero(decimal1);
        }
        if(decimal2.startsWith("0")){
            decimal2 = removeHighZero(decimal2);
        }

        //调用非负整数乘法运算
        String result = multiply(decimal1,decimal2);

        //结果添加小数点
        StringBuilder builder = new StringBuilder();
        if(sumDecimalLen != 0) { //不是整数
            //两数相乘可能使长度减小，特殊处理一下（如:0.1*1.1->01 * 11 -> 011 -> 11 -> .11 ,所以要补0 ）
            if(result.length()<= sumDecimalLen){
                String padLeft = "";
                for(int i=0;i< sumDecimalLen-result.length()+1;i++){
                    padLeft+="0";
                }
                result = padLeft + result; //左补0，方可让小数点插入
            }
            builder.append(result);
            builder.insert(result.length() - sumDecimalLen, ".");
        }
        return  builder.toString();
    }

    /**
     * 两非负小数相除
     *
     * 核心思路：提升数值以去掉小数点，然后运算即可。
     * 例如： 2.5/3 -> 25/30;  2.35/2.4->235/240;
     * @param decimal1 小数1
     * @param decimal2 小数2
     * @return 结果
     */
    public static String divideDecimal(String decimal1,String decimal2){
        int decimalLen1 = 0; //小数部分长度
        int decimalLen2 = 0;
        int decimalPointIndex1 = decimal1.lastIndexOf(".");
        if(decimalPointIndex1 == -1){ //找不到小数点（整数）

        }else{
            decimalLen1 = decimal1.length()-1 - decimalPointIndex1;
        }
        int decimalPointIndex2 = decimal2.lastIndexOf(".");
        if(decimalPointIndex2 == -1){ //找不到小数点（整数）

        }else{
            decimalLen2 = decimal2.length()-1 - decimalPointIndex2;
        }

        //小数部分长度（最长的）
        int maxDecimalLen = decimalLen1 >= decimalLen2 ? decimalLen1:decimalLen2;

        //提升数值（乘10 或者 补0）
        if(decimalLen1 == maxDecimalLen){ //第一个数小数部分长度最长
            decimal1 = decimal1.replace(".",""); //直接去除小数点

            StringBuilder builder = new StringBuilder(decimal2.replace(".","")); //去点
            for(int i=0;i<decimalLen1-decimalLen2;i++){ //多出的小数位数
                builder.append("0"); //补0
            }
            decimal2 = builder.toString();
        }else{
            decimal2 = decimal2.replace(".",""); //直接去除小数点

            StringBuilder builder = new StringBuilder(decimal1.replace(".","")); //去点
            for(int i=0;i<decimalLen2-decimalLen1;i++){ //多出的小数位数
                builder.append("0"); //补0
            }
            decimal1 = builder.toString();
        }

        //去除高位无用0
        if(decimal1.startsWith("0")){
            decimal1 = removeHighZero(decimal1);
        }
        if(decimal2.startsWith("0")){
            decimal2 = removeHighZero(decimal2);
        }


        //调用非负整数除法运算
        String result = divideEnhanced(decimal1,decimal2);
        return  result;
    }


    /**
     * 两数相加（可带负号，可是小数）
     * @param num1 数1
     * @param num2 数2
     * @return 结果
     */
    public static String addNumber(String num1,String num2){
        boolean num1Negative = num1.startsWith("-");
        boolean num2Negative = num2.startsWith("-");
        //去除符号
        num1 = num1.replace("-","");
        num2 = num2.replace("-","");
        if(num1Negative){ //数1是负数
            if(num2Negative){ //数2是负数
                // -a + -b = -(a+b)
                return "-" + addDecimal(num1,num2);
            }else{
                // -a + b = b-a
                return subtractDecimal(num2,num1);
            }
        }else{
            if(num2Negative){
                // a + -b = a-b
                return subtractDecimal(num1,num2);
            }else{
                // a + b
                return  addDecimal(num1,num2);
            }
        }
    }

    /**
     * 两数相减（可带负号，可是小数）
     * @param num1 数1
     * @param num2 数2
     * @return 结果
     */
    public static String subtractNumber(String num1,String num2){
        boolean num1Negative = num1.startsWith("-");
        boolean num2Negative = num2.startsWith("-");
        //去除符号
        num1 = num1.replace("-","");
        num2 = num2.replace("-","");
        if(num1Negative){ //数1是负数
            if(num2Negative){ //数2是负数
                // -a - -b = b-a
                return subtractDecimal(num2,num1);
            }else{
                // -a - b = -(a+b)
                return "-"+addDecimal(num1,num2);
            }
        }else{
            if(num2Negative){
                // a - -b = a+b
                return addDecimal(num1,num2);
            }else{
                // a - b
                return  subtractDecimal(num1,num2);
            }
        }
    }

    /**
     * 两数相乘（可带负号，可是小数）
     * @param num1 数1
     * @param num2 数2
     * @return 结果
     */
    public static String multiplyNumber(String num1,String num2){
        boolean num1Negative = num1.startsWith("-");
        boolean num2Negative = num2.startsWith("-");
        //去除符号
        num1 = num1.replace("-","");
        num2 = num2.replace("-","");
        if(num1Negative){ //数1是负数
            if(num2Negative){ //数2是负数
                // -a * -b = a*b
                return multiplyDecimal(num1,num2);
            }else{
                // -a * b = -(a*b)
                return "-"+multiplyDecimal(num1,num2);
            }
        }else{
            if(num2Negative){
                // a * -b = -(a*b)
                return "-"+multiplyDecimal(num1,num2);
            }else{
                // a * b
                return  multiplyDecimal(num1,num2);
            }
        }
    }

    /**
     * 两数相除（可带负号，可是小数）
     * @param num1 数1
     * @param num2 数2
     * @return 结果
     */
    public static String divideNumber(String num1,String num2){
        boolean num1Negative = num1.startsWith("-");
        boolean num2Negative = num2.startsWith("-");
        //去除符号
        num1 = num1.replace("-","");
        num2 = num2.replace("-","");
        if(num1Negative){ //数1是负数
            if(num2Negative){ //数2是负数
                // -a / -b = a/b
                return divideDecimal(num1,num2);
            }else{
                // -a / b = -(a/b)
                return "-"+divideDecimal(num1,num2);
            }
        }else{
            if(num2Negative){
                // a / -b = -(a/b)
                return "-"+divideDecimal(num1,num2);
            }else{
                // a / b
                return  divideDecimal(num1,num2);
            }
        }
    }



    /**
     * 校验非负整数是否合法
     * @param numStr 数字字符串
     * @return 是否合法
     */
    public static boolean integerValid(String numStr){
        Pattern pattern = Pattern.compile("^[1-9]\\d*$|0");
        Matcher matcher = pattern.matcher(numStr);
        return matcher.matches();
    }

    /**
     * 校验非负数是否合法
     * @param numStr 数字字符串
     * @return 是否合法
     */
    public static boolean decimalValid(String numStr){
        //正整数+0+非0开头小数+0开头小数
        Pattern pattern = Pattern.compile("^[1-9]\\d*$|0|^[1-9]\\d*\\.?\\d+$|0\\.\\d+");
        Matcher matcher = pattern.matcher(numStr);
        return matcher.matches();
    }

    /**
     * 校验数是否合法
     * @param numStr 数字字符串
     * @return 是否合法
     */
    public static boolean numberValid(String numStr){
        //正整数+0+非0开头小数+0开头小数
        Pattern pattern = Pattern.compile("^-?[1-9]\\d*$|0|^-?[1-9]\\d*\\.?\\d+$|-?0\\.\\d+");
        Matcher matcher = pattern.matcher(numStr);
        return matcher.matches();
    }

    /**
     * 计算大数
     * @param numArray1 数1
     * @param numArray2 数2
     * @return 大数
     */
    public static int[] getMaxNumber(int[] numArray1, int[] numArray2) {
        for(int i=numArray1.length-1;i>=0;i--){
            if(numArray1[i]>numArray2[i]){
                return numArray1;
            }else{
                if(numArray1[i]==numArray2[i]){
                    continue; //待继续比较
                }else{
                    return numArray2;
                }
            }
        }
        return numArray1; //全部相等，返回第一个
    }

    /**
     * 除法转换为减法
     * @param numStr1 数1（被除数）
     * @param numStr2 数2（除数）
     * @return 除的结果
     */
    public static DivideResult getDivideResult(String numStr1,String numStr2){
        DivideResult result = new DivideResult();
        String r = "";
       // String times = "0";
        int times = 0; //取整不会大于9的（被除数（余数+某位）/除数(肯定大于余数)这个过程是，被除数逐渐增大到可以除以除数为止，此时被除数>=除数，刚刚好，所以被除数最多比除数多1位，两数相差肯定小于10倍）
        while (true){
            r=subtract(numStr1,numStr2);
           // times = add(times,"1"); //次数递增
            times++;
            if("0".equals(r)){ //除尽了
                result.setYu("0");
                result.setR(times+"");
                break;
            }else if(r.startsWith("-")){ //负数，多减了一次
                result.setYu(numStr1); //上次减下来多余的数值，就是余数
               // result.setR(subtract(times,"1"));
                result.setR((times-1)+"");
                break;
            }
            numStr1 = r; //被减数重置为剩余的数值
        }
        return result;
    }

    /**
     * 去除字符串头部0
     * @param str 字符串
     * @return 结果
     */
    public static String removeHighZero(String str){
        if("0".equals(str)){ //特殊情况
            return str;
        }
        StringBuilder builder = new StringBuilder();
        boolean exsitHighNotZero = false;
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if(c!='0'){
                exsitHighNotZero = true; //不为0的出现了，之后的0就要保留了
            }
            if(c=='0' && !exsitHighNotZero){ //高位为0的
                continue; //跳过
            }
            builder.append(c);
        }
        return builder.toString();
    }
}
