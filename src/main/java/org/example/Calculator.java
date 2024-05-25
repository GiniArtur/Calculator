package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Calculator {
    private static final int MIN_ARABIC_OPERAND_VALUE = 1;
    private static final int MAX_ARABIC_OPERAND_VALUE = 10;
    private static final Map<String, Integer> NumbersOfRoman = new HashMap<>() {{
        put("I", 1); // Добавляем ключ*I*-значение*1* в NumbersOfRoman
        put("II", 2); // Добавляем ключ*II*-значение*2* в NumbersOfRoman
        put("III", 3); // Добавляем ключ*III*-значение*3* в NumbersOfRoman
        put("IV", 4); // Добавляем ключ*IV*-значение*4* в NumbersOfRoman
        put("V", 5); // Добавляем ключ*V*-значение*5* в NumbersOfRoman
        put("VI", 6); // Добавляем ключ*VI*-значение*6* в NumbersOfRoman
        put("VII", 7); // Добавляем ключ*VII*-значение*7* в NumbersOfRoman
        put("VIII", 8); // Добавляем ключ*VIII*-значение*8* в NumbersOfRoman
        put("IX", 9); // Добавляем ключ*IX*-значение*9* в NumbersOfRoman
        put("X", 10); // Добавляем ключ*X*-значение*10* в NumbersOfRoman
        put("XX", 20); // Добавляем ключ*XX*-значение*20* в NumbersOfRoman
        put("XXX", 30); // Добавляем ключ*XXX*-значение*30* в NumbersOfRoman
        put("XL", 40); // Добавляем ключ*XL*-значение*40* в NumbersOfRoman
        put("L", 50); // Добавляем ключ*L*-значение*50* в NumbersOfRoman
        put("LX", 60); // Добавляем ключ*LX*-значение*60* в NumbersOfRoman
        put("LXX", 70); // Добавляем ключ*LXX*-значение*70* в NumbersOfRoman
        put("LXXX", 80); // Добавляем ключ*LXXX*-значение*80* в NumbersOfRoman
        put("XC", 90); // Добавляем ключ*XC*-значение*90* в NumbersOfRoman
        put("C", 100); // Добавляем ключ*С*-значение*100* в NumbersOfRoman
    }};

    private static final ArrayList<String> operators = new ArrayList<>() {{
        add("+");       // После создания экземпляра класса объект - *operators*, добавляем строку*+* в массив
        add("-");       // После создания экземпляра класса объект - *operators*, добавляем строку*-* в массив
        add("/");       // После создания экземпляра класса объект - *operators*, добавляем строку*/* в массив
        add("*");       // После создания экземпляра класса объект - *operators*, добавляем строку*_*_* в массив
    }};

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);        //Инициализируем объект класса Scanner для считывания данных с клавиатуры
        System.out.println("Введите арабскую цифру от 1 до 10, затем пробел, вид операции : (+,-,/,*),  снова пробел и арабскую цифру  ");
        System.out.println("Либо введите Римскую цифру от (I до X);  затем пробел, вид операции : (+,-,/,*),  снова пробел и Римскую цифру  ");
        System.out.println("Для того чтобы выйти из калькулятора пропиши слово 'ВЫХОД' ");

        String s;
        while (!(s = sc.nextLine()).equals("Выход")) {  //Цикл будет выполняться, пока условие вводим строки НЕ содержащие слово BYE
            try {                                    // Так как возможен выброс исключения, обязательно вызов метода *calculation*
                // обернуть в конструкцию try-catch
                System.out.println(calculation(s));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String calculation(String input) throws Exception {   // Метод должен бросать исключение, т.к. могут попасть данные,
        // не входящие в техническое задание(условие задачи)
        String[] expression = input.split(" ");                  // В объект *expression* - строковый массив закидываем строки, деля при этом их по ПРОБЕЛУ.

        if (!isCorrectExpression(expression))    //Если возвращенное значение метода TRUE - т.е. выражение не соответствует условию задачи, и
            throw new Exception("Введенная строка не является выражением," +      //сбрасывается исключение с данным сообщением!!!!!!
                    " либо выражение не соответствует заданному формату.");

        if (isCorrectRomanExpression(expression)) {              //Если условие выполниться, выражение соответствует условию по Римским цифрам
            return calculateRomanExpression(expression);        //будет возвращено вычисленное в методе *calculateRomanExpression*
        } else if (isCorrectArabicExpression(expression)) {         //Если условие выполниться, выражение соответствует условию по Арабским цифрам
            return calculateArabicExpression(expression);        //будет возвращено вычисленное в методе *calculateArabicExpression*
        } else {                                                    // Если ни одно из условий не выполнится, то
            throw new Exception("Операнды переданного выражения не подходят под условия," +    //Сброс исключения и соответственно - сообщение!!!!!
                    " либо операнды имеют разный тип.");
        }
    }
    private static boolean isCorrectExpression(String[] input) {         //Данный метод проверяет выражение на корректный ввод
        return (input.length == 3) && (operators.contains(input[1]));   // длина введенной строки должна быть равна 3, И - в веденной строке после преобразования
        // его в массив, вторым элементом должен быть символ из массива *operators*
        // {'+','-', '/','*']
    }

    private static boolean isCorrectRomanExpression(String[] expression) {       //Проверка условия, о том, чтобы в выражении были только Римские цифры
        if (!NumbersOfRoman.containsKey(expression[0]) || !NumbersOfRoman.containsKey(expression[2])) //Проверка условия, следующее - если преобразованное выражение *expression* НЕ!!! содержит
            // в себе в качестве первого элемента и третьего элемента
            //массива строку Римской цифры из коллекции *NumbersOfRoman*
            return false;                                                                               // метод возвращает значение False

        int firstOperand = NumbersOfRoman.get(expression[0]);    //Метод *get* используется для получения значения, связанного с заданным ключом-первым элементом массива expression[0]
        int secondOperand = NumbersOfRoman.get(expression[2]);   //Метод *get* используется для получения значения, связанного с заданным ключом-третьим элементом массива expression[0]

        return (inTargetRange(firstOperand)) && (inTargetRange(secondOperand));   //Операция логическое И- если результат первого метода inTargetRange равен TRUE и второго - TRUE, значит оба наших
        // числа ВХОДЯТ в ДОЛЖНЫЙ Диапазон от 1 до 10
    }
    private static boolean inTargetRange(int number) {                    //Проверяет число больше, равно минимального Арабского значения, в данном случае *1*
        // И меньше равно *10* -максимального значения в нашем случае
        return (number >= MIN_ARABIC_OPERAND_VALUE) && (number <= MAX_ARABIC_OPERAND_VALUE);
    }

    private static boolean isCorrectArabicExpression(String[] expression) {    //Проверка корректности Выражения в арабской форме записи ВВЕДЕнной строки
        int firstOperand;
        int secondOperand;
        try {
            firstOperand = Integer.parseInt(expression[0]);      // Статический метод  *parseInt* переводит первый элемент массива *expression[0]* в целочисленный тип.
            secondOperand = Integer.parseInt(expression[2]);     // Статический метод  *parseInt* переводит третий элемент массива *expression[2]* в целочисленный тип.
        } catch (
                NumberFormatException e) {                        // Ввозможен сброс ИСКЛЮЧЕНИЯ NumberFormatException, вернет значение False, если будет некорректное выражение при вводе строки с клавиатуры
            return false;
        }

        return (inTargetRange(firstOperand)) && (inTargetRange(secondOperand));      //Операция логическое И- если результат первого метода inTargetRange равен TRUE и второго - TRUE, значит оба наших
        // числа ВХОДЯТ в ДОЛЖНЫЙ Диапазон от 1 до 10
    }





    private static String calculateRomanExpression(String[] expression) throws Exception {
        int firstOperand = NumbersOfRoman.get(expression[0]);   //Метод *get* используется для получения значения, связанного с заданным ключом-первым элементом массива expression[0]
        int secondOperand = NumbersOfRoman.get(expression[2]);  //Метод *get* используется для получения значения, связанного с заданным ключом-третьим элементом массива expression[0]
        String operator = expression[1];                        // Оператору присваиваем второй элемент массива expression[1]

        int intResult = calculate(firstOperand, secondOperand, operator);   //Переменной присваивается возвращенное методом *calculate* значение

        if (intResult <= 0)                                                  //Если результат 0 или отрицательное
            throw new Exception("Результат выражения с римскими цифрами меньше либо равен нулю."); // выбрасывается исключение и выводится сообщение

        return toRomanNumber(intResult);
    }

    private static String calculateArabicExpression(String[] expression) {
        int firstOperand = Integer.parseInt(expression[0]);
        int secondOperand = Integer.parseInt(expression[2]);
        String operator = expression[1];

        return "" + calculate(firstOperand, secondOperand, operator);
    }

    private static int calculate(int a, int b, String operator) {     //Работа над цифрами
        return switch (operator) {          //В зависимости от *operator* будет возвращен результат операции:
            case "+" -> a + b;              // сложение
            case "-" -> a - b;              //разности
            case "/" -> a / b;              // деление(без остатка так как целые цисла - *int*
            case "*" -> a * b;              // умножение чисел
            default ->
                    throw new RuntimeException(); // Когда метод получает аргумент, который не соответствует ожидаемому диапазону или типу,
            // но при этом невозможно сразу определить корректное поведение без дополнительной проверки или исправления кода.
        };
    }

    private static String toRomanNumber(int intNumber) {       //На вход метода подается целочисленное значение
        int intResultTens = intNumber - intNumber % 10;       //Целая часть числа без хвостика, т.е. например *10,20,30,40... так далее*   т.е. кратно 10 без остатка!!!!
        int intResultUnits = intNumber % 10;                  // единицы (остаток от деления на 10)
        String romanResultTens = "";                          // Сначала инициализируем как пустая строка
        String romanResultUnits = "";                         // Сначала инициализируем как пустая строка

        for (Map.Entry<String, Integer> entry : NumbersOfRoman.entrySet()) {       //Интерфейс *Map.Entry*. entrySet- возвращает набор (set) элементов, пробегаем по всем элементам коллекции  *NumbersOfRoman*
            if (entry.getValue() == intResultTens) {   //Если значение элемента коллекции равна десятой части от числа(остаток от деления на 10)
                romanResultTens = entry.getKey();     // Переменной *romanResultTens*  присваиваем ключ из коллекции
            }
            if (entry.getValue() == intResultUnits) {   //Если значение элемента коллекции равна единице(целой при делении на 10)
                romanResultUnits = entry.getKey();      // Переменной *romanResultUnits*  присваиваем ключ из коллекции
            }
        }

        return romanResultTens + romanResultUnits;      //Возвращаем строку где будет объединено Первое целая часть (кратная 10) - в виде Римской цифры и остатка от деления (хвостик) - в виде Римской цифры
    }
}