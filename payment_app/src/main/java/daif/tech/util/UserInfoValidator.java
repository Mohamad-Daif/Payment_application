package daif.tech.util;

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
         Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z._]+");
         boolean isValid = USERNAME_PATTERN.matcher(username).matches();
         if(isValid) return true;
         else{
             System.out.println("Username can only hold characters with . or _");
             return false;
         }
    }

    public static boolean validatePasswordLength(String password) {
        if(password.length() < 8){
            System.out.println("Password length should be 8 or more");
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
