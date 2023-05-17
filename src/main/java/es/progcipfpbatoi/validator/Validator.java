package es.progcipfpbatoi.validator;

public class Validator {

    /**
     * xxxxxx@xxxxx.xx
     * xxx.xx@xx.xx.xx
     */
    private static final String EMAIL_REGEXP = "^[a-z0-9]+(\\.[a-z0-9]+)*@[a-z0-9-_]+(\\.[a-z0-9-_]+)*(\\.([a-z])+)$";

    /**
     * 000000000X
     */
    private static final String DNI_REGEXP = "^\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]$";

    /**
     * Debe empezar por el prefijo internacional: 0034, +34 o 34.
     * Seguida del número de móvil: 9 dígitos que empieza por 6 o 7
     */
    public static final String MOBILE_PHONE_REGEXP = "^((0034)|(\\+34)|(34))[67]\\d{8}$";

    /**
     * yyyy-MM-dd
     */
    public static final String ENGLISH_DATE_REGEXP =  "^\\d{4}\\-(0[1-9]|1[0-2])\\-(0[1-9]|[1-2][0-9]|3[0-1])$";;

    /**
     * 00000
     * Las 2 primeras cifras hacen referencia a la provincia y pueden y pueden valer desde el 01 al 52.
     * Las 3 últimas cifras hacen referencia al distrito y pueden ir desde el 000 al 999
     */
    public static final String ZIP_CODE_REGEXP = "^(0[1-9]|[1-4][0-9]|5[0-2])\\d{3}$";

    public static final String NUMERIC_REGEXP = "\\d+";

    /**
     * Cadena de texto entre 5 y 20 caracteres
     * Debe contener al menos letras mayúsculas, minúsculas y un carácter especial
     */
    private static final String PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{5,20}$";

    public static boolean isValidDate(String date) {
        return isNotEmptyOrNull(date) && date.matches(ENGLISH_DATE_REGEXP);
    }

    public static boolean isValidDNI(String dni) {
        return isNotEmptyOrNull(dni) &&  dni.matches(DNI_REGEXP);
    }

    public static boolean isValidZipCode(String zipCode) {
        return zipCode.matches(ZIP_CODE_REGEXP);
    }

    public static boolean isValidMobilePhone(String mobilePhone) {
        return isNotEmptyOrNull(mobilePhone) && mobilePhone.matches(MOBILE_PHONE_REGEXP);
    }

    public static boolean isValidNumeric(String number) {
        return number.matches(NUMERIC_REGEXP);
    }

    public static boolean isValidEmail(String email) {
        return isNotEmptyOrNull(email) && email.matches(EMAIL_REGEXP);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEXP);
    }

    public static boolean hasLength(String param, int min, int max){
        return isNotEmptyOrNull(param) && param.length() >= min  && param.length() <= max;
    }

    public static boolean hasLength(String param, int min){
        return isNotEmptyOrNull(param) && param.length() >= min;
    }

    private static boolean isNotEmptyOrNull(String param) {
        return param != null && !param.isEmpty();
    }
}

