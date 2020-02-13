package Phase_02.Contents_03;

import java.util.regex.Pattern;

class RomanNumerals {
    private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    public static boolean isRomanNumeral(String input) {
        return ROMAN.matcher(input).matches();
    }
}
