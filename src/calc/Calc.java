/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calc;

/**
 *
 * @author Sasha
 */
public class Calc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String str = "55 + 44 * (4 / 2 - 3 / 5) + 2 * 2";
        Calculator calc = new Calculator();
        String workStr = str.replace(" ", "");
        double res      = calc.solution(workStr);
        System.out.println("Результат 1: " + res);

        str     = "55 + 44 * 4 / 2 - 3 / 5 + 2 * 2";
        workStr = str.replace(" ", "");
        res = calc.solution(workStr);
        System.out.println("Результат 2:" + res);
    }
    
    static public class Calculator
    {
        public Calculator() {}

        public double solution(String str)
        {
            double retVal = 0.;
            int i         = 0;
            int endInd    = 0;

            while (str.contains("(") && str.contains(")"))
            {
                i      = str.indexOf('(', i);
                endInd = str.indexOf(')', i);
                retVal = solution(str.substring(i + 1, endInd));
                // почему endIndex = str.length(), а не str.length() ????
                str = str.substring(0, i) + retVal + str.substring(endInd + 1, str.length());
                i = endInd;
            }

            boolean ok = false;
            try {
                retVal  = Double.parseDouble(str);
                ok = true;
            }
            catch(NumberFormatException exeption) { }

            if (ok)
                return retVal;

            // Иначе
            String[] strs;
            strs = str.split("\\+");
            if (strs.length == 1)
            {
                strs = str.split("\\-");
                if (strs.length == 1)
                {
                    strs = str.split("\\*");
                    if (strs.length == 1)
                    {
                        strs = str.split("\\/");
                        if (strs.length == 1)
                        {
                            // Такое бы отсеялось ещё на строчке
                            // if(ok) return retVal;
                            // ну да ладно. Можно exception выдавать при некорректно
                            // введённом числе
                            retVal  = Double.parseDouble(str);
                        }
                        else
                        {
                            retVal = solution(strs[0]);
                            for (i = 1; i < strs.length; i++)
                            {
                                retVal /= solution(strs[i]);
                            }
                        }
                    }
                    else
                    {
                        retVal = solution(strs[0]);
                        for (i = 1; i < strs.length; i++)
                        {
                            retVal *= solution(strs[i]);
                        }
                    }
                }
                else
                {
                    retVal = solution(strs[0]);
                    for (i = 1; i < strs.length; i++)
                    {
                        retVal -= solution(strs[i]);
                    }
                }
            }
            else
            {
                retVal = solution(strs[0]);
                for (i = 1; i < strs.length; i++)
                {
                    retVal += solution(strs[i]);
                }
            }
            return retVal;
        }
    }
}
