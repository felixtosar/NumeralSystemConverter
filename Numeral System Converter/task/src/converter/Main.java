package converter;

import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String strSourceRadix = scanner.nextLine();
        if (testRadix(strSourceRadix)) {
            System.out.println("error");
            exit(0);
        }
        int sourceRadix = Integer.parseInt(strSourceRadix);

        String sourceNumber = scanner.nextLine();
        if (!test(sourceNumber, sourceRadix)) {
            System.out.println("error");
            exit(0);
        }

        String strTargetRadix = scanner.nextLine();
        if (testRadix(strTargetRadix)) {
            System.out.println("error");
            exit(0);
        }
        int targetRadix = Integer.parseInt(strTargetRadix);

        String[] number = sourceNumber.split("\\.");
        if (number.length == 1 || sourceRadix == 1) {
            System.out.println(convertIntegerPart(number[0], sourceRadix, targetRadix));
        } else {
            String integerPart = convertIntegerPart(number[0], sourceRadix, targetRadix);
            String fractionalPart = convertFractionalPart(number[1], sourceRadix, targetRadix);
            System.out.printf("%s.%s", integerPart, fractionalPart);
        }
    }

    private static String convertIntegerPart(String sourceNumber, int sourceRadix, int targetRadix) {
        int number;
        // Convert it to decimal
        if (sourceRadix != 10) {
            if (sourceRadix == 1) {
                number = sourceNumber.length();
            } else {
                number = Integer.parseInt(sourceNumber, sourceRadix);
            }
        } else {
            // if it's decimal
            number = Integer.parseInt(sourceNumber);
        }
        // convert it to another base
        if (targetRadix == 1) {
            return "1".repeat(Math.max(0, number));
        }
        return Integer.toString(number, targetRadix);
    }

    private static String convertFractionalPart(String sourceNumber, int sourceRadix, int targetRadix) {
        double decimalValue = 0;

        // convert it to base 10
        if (sourceRadix == 10) {
            decimalValue = Double.parseDouble("0." + sourceNumber);
        } else {
            char character;
            for (int i = 0; i < sourceNumber.length(); i++) {
                character = sourceNumber.charAt(i);
                decimalValue += Character.digit(character, sourceRadix) / Math.pow(sourceRadix, i + 1);
            }
        }
        // convert it to another base
        StringBuilder result = new StringBuilder();
        int decimal;
        for (int i = 0; i < 5; i++) {
            double aux = decimalValue * targetRadix;
            decimal = (int) aux;
            result.append(Long.toString(decimal, targetRadix));
            decimalValue = aux - decimal;
        }
        return result.toString();
    }

    private static boolean testRadix(String radix) {
        if (isInteger(radix)) {
            int num = Integer.parseInt(radix);
            return num <= 0 || num > Character.MAX_RADIX;
        }
        return true;
    }

    private static boolean test(String str, int radix) {
        if (str == null || str.length() == 0) {
            return false;
        }

        int countPoint = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                countPoint++;
                if (countPoint > 1) {
                    return false;
                }
            } else if (!Character.isDigit(str.charAt(i))
                    && (str.charAt(i) < 97 || str.charAt(i) > 86 + radix)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isInteger(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
