package daif.tech.ui;

import daif.tech.exception.InvalidEnteredAge;
import daif.tech.service.RegisterBoardService;
import daif.tech.util.UserInfoValidator;

import java.math.BigDecimal;
import java.util.*;

public class RegisterBoard {

    private static RegisterBoard registerBoard;

    private RegisterBoard(){

    }

    public static RegisterBoard getInstance(){
        if(registerBoard == null){
            registerBoard = new RegisterBoard();
        }
        return registerBoard;
    }

    RegisterBoardService registerBoardService = new RegisterBoardService();

    public void showRegisterBoard(){

        Scanner get = new Scanner(System.in);

        boolean isValidUserName = false,
                isValidPhoneNumber = false,
                isValidBalanceOptionChoice = false,
                isValidPassword = false,
                isValidAge = false;

        int age = 0;
        while (!isValidAge){
            System.out.print("Please enter your age: ");
            try{

                String input = get.nextLine();

                // will fail in case the user entered anything rather than digits.
                // Also decimals are declined.
                isValidAge = validateEnteredAge(input);
                age = Integer.parseInt(input);
            }catch (InputMismatchException | InvalidEnteredAge  iea){
                System.out.println(iea.getMessage());
            }
        }

        String username = "";
        while (!isValidUserName) {
            System.out.println("Enter your preferred username : ");
            username = get.nextLine();
            isValidUserName = UserInfoValidator.validateEnteredUserName(username);
        }

        String phoneNumber = "";
        while (!isValidPhoneNumber) {
            System.out.println("Enter your phone number : ");
            phoneNumber = get.nextLine();
            isValidPhoneNumber = UserInfoValidator.validatePhoneNumber(phoneNumber);
        }

        String password = "";
        while (!isValidPassword){
            System.out.println("Enter your password : ");
            password = get.nextLine();
            isValidPassword = UserInfoValidator.validatePassword(password);
        }

        BigDecimal initialBalance = new BigDecimal(0);
        while (!isValidBalanceOptionChoice) {
            System.out.println("Do you want to initialize your account with balance ? Y/N");
            String choice = get.nextLine();
            choice = choice.trim();
            boolean isValidCharacterOption = UserInfoValidator.isValidOptionCharacter(choice);
            try{
                if (isValidCharacterOption) {
                    if (choice.equalsIgnoreCase("y")) {
                        System.out.println("Enter amount : ");
                        initialBalance = get.nextBigDecimal();
                        isValidBalanceOptionChoice = true;
                    } else if (choice.equalsIgnoreCase("n")) {
                        initialBalance = new BigDecimal(0);
                        isValidBalanceOptionChoice = true;
                    }
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid positive numeric value");
                get.nextLine();
            }
        }

        registerBoardService.registerUser(username,password,phoneNumber,age,initialBalance);

    }

    private boolean validateEnteredAge(String ageInString)throws InvalidEnteredAge{
        if(ageInString.chars().anyMatch(character -> !Character.isDigit(character))){
            throw new InvalidEnteredAge();
        }

        int age = Integer.parseInt(ageInString);

        if(age < 18 ){
            throw new InvalidEnteredAge();
        }
        return true;
    }


}
