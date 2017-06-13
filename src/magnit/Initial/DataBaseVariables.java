package magnit.Initial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBaseVariables {
    static String username, password, s;
    static int N;

    public static void setUsername(String username) {
        DataBaseVariables.username = username;
    }

    public static void setPassword(String password) {
        DataBaseVariables.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static int getN() {
        return N;
    }

    public static void setN(int n) {
        N = n;
    }

    public void enterData() {
        System.out.println("МАГНИТ");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите имя пользоватя MYSQL: ");
            setUsername(reader.readLine());
            System.out.println("Введите пароль пользоватя MYSQL: ");
            setPassword(reader.readLine());
            while (true) {
                System.out.println("Введите N - число полей в таблице test от 1 до 1000000: ");
                s = reader.readLine();
                if (checkWithRegExp(s)) {
                    N = Integer.parseInt(s);
                    if (N >= 1 && N <= 1000000) {
                        setN(N);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("В таблицу test будет добавлено " + getN() + " полей и подсчитана их сумма");
    }

    public static boolean checkWithRegExp(String userNameString) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }
}
