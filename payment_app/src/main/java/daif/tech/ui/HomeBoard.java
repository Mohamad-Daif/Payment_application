package daif.tech.ui;

import daif.tech.exception.UserNotFoundException;
import daif.tech.model.User;
import daif.tech.service.HomeBoardService;
import daif.tech.util.UserInfoValidator;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HomeBoard {

    private boolean isLoggedIn = false;

    private static HomeBoard homeBoard;

    private HomeBoard(){

    }

    public static HomeBoard getInstance(){
        if(homeBoard == null){
            homeBoard = new HomeBoard();
        }
        return homeBoard;
    }

    HomeBoardService homeBoardService = new HomeBoardService();

    public void showHomeBoard(User user){

        isLoggedIn = true;

        // the all operations which are listed within home page are related to this passed user.
        homeBoardService.setUser(user);

        while (isLoggedIn){
            System.out.println("""
                Enter the number of the action that you want to perform : 
                1. Deposit.
                2. Withdraw.
                3. viewBalance.
                4. Transfer.
                5. showAccountDetails.
                6. changePassword.
                7.  Logout.""");
            int choice = new Scanner(System.in).nextInt();
            switch (choice){
                case 1 -> showDepositBoard();
                case 2 -> showWithdrawBoard();
                case 3 -> viewBalance();
                case 4 -> showTransferBoard();
                case 5 -> showAccountDetails();
                case 6 -> changePassword();
                case 7 -> logoutAndBackToMainBoard();
            }
        }
    }

    private void changePassword() {
        System.out.println("Enter current password: ");
        String currentPassword = new Scanner(System.in).nextLine();
        boolean isValidNewPassword = false;
        String newEnteredPassword = "";
        if(homeBoardService.isCurrentPassword(currentPassword)){
            while (!isValidNewPassword){
                System.out.println("Enter new password: ");
                newEnteredPassword = new Scanner(System.in).nextLine();
                if(currentPassword.equals(newEnteredPassword)) throw new IllegalArgumentException("New password should not match the current one");
                isValidNewPassword = UserInfoValidator.validatePassword(newEnteredPassword);
            }
        }else{
            System.out.println("Invalid Password");
        }

        if(isValidNewPassword){
            homeBoardService.changePassword(newEnteredPassword);
            System.out.println("Password changed successfully");
        }
    }

    private void showAccountDetails() {
        homeBoardService.showAccountDetails();
    }

    private void viewBalance() {
        System.out.println("Your balance is : "+homeBoardService.getBalance());
    }

    private void logoutAndBackToMainBoard() {
        homeBoardService.logout();
        isLoggedIn = false;
        PagesContext.MAIN_BOARD.showMainBoard();
    }

    private void showTransferBoard() {
        boolean isPhoneNumberExists = false;

        while (!isPhoneNumberExists){
            System.out.println("Enter the phone number that you want to transfer to : ");
            String receiverPhoneNumber = new Scanner(System.in).nextLine();
            try{
                isPhoneNumberExists = homeBoardService.checkIfUserExists(receiverPhoneNumber);
                if(isPhoneNumberExists){
                    System.out.println("Enter amount you want to transfer : ");
                    try{
                        BigDecimal amount = new Scanner(System.in).nextBigDecimal();
                        homeBoardService.transfer(receiverPhoneNumber,amount);
                    }catch (InputMismatchException e){
                        System.out.println("Kindly enter a valid positive numeric value");
                    }catch (UserNotFoundException userNotFoundException){
                        System.out.println(userNotFoundException.getMessage());
                    }
                }else{
                    System.out.println("There is no user with this phone number");
                }
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }

    }

    private void showWithdrawBoard() {
        System.out.println("Enter the amount that you want to withdraw : ");
        try{
            BigDecimal amount = new Scanner(System.in).nextBigDecimal();
            homeBoardService.withdraw(amount);
        }catch (InputMismatchException e){
            System.out.println("Please enter a valid positive numeric value");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void showDepositBoard() {
        System.out.println("Enter the amount that you want to deposit : ");
        try{
            BigDecimal amount = new Scanner(System.in).nextBigDecimal();
            homeBoardService.deposit(amount);
        }catch (InputMismatchException e){
            System.out.println("Please enter a valid positive numeric value");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }


}
