package daif.tech.util;

import daif.tech.exception.InvalidEnteredAge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoValidator {

    public static boolean validatePhoneNumber(String phoneNumber) {
        String egyptianNumberRegex = "^(010|011|012|015)\\d{8}$";
        if(!phoneNumber.matches(egyptianNumberRegex)){
            System.out.println("Phone number should be 11 digit starting with 010, 011, 012 or 015");
            return false;
        }
        return true;
    }

    public static boolean validateEnteredUserName(String username) {
        // Username should only contain characters with either . or _
        Pattern USERNAME_PATTERN =
                Pattern.compile("^[A-Z][a-zA-Z._]{6,}$");
         boolean isValid = USERNAME_PATTERN.matcher(username).matches();
         if(isValid) return true;
         else{
             System.out.println("""
                     ########################################################################################
                     Invalid username. Please make sure your username meets the following rules:
                     1. The first character must be an uppercase letter (A-Z).
                     2. Minimum length of 7 characters.
                     3. Only letters (A-Z, a-z), dots (.), or underscores (_) are allowed in the rest of the username.
                     #########################################################################################
                     """);
             return false;
         }
    }

    public static boolean validatePassword(String password) {
        String PASSWORD_PATTERN =
                "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        if(!password.matches(PASSWORD_PATTERN)){
            System.out.println("""
                    ##############################################################################
                    Invalid password. Please make sure your password meets all the following rules:
                    1. Minimum length of 8 characters.
                    2. Contains at least one letter (a-z, A-Z).
                    3. Contains at least one number (0-9).
                    4. Contains at least one special character from @$!%*#?&.
                    5. Only letters, numbers, and the allowed special characters are permitted.
                    ###############################################################################
                    """);
            return false;
        }
        return true;
    }

    public static boolean isValidOptionCharacter(String character){
        if(character.equalsIgnoreCase("y") || character.equalsIgnoreCase("n")){
            return true;
        }else{
            System.out.println("Kindly enter either Y or N");
            return false;
        }
    }
}
