package daif.tech.ui;

import daif.tech.exception.InvalidCredentialsException;
import daif.tech.model.User;
import daif.tech.service.LoginBoardService;
import daif.tech.util.UserInfoValidator;

import java.util.Scanner;

public class LoginBoard {

    private static LoginBoard loginBoard;

    private LoginBoard(){

    }

    public static LoginBoard getInstance(){
        if (loginBoard == null){
            loginBoard = new LoginBoard();
        }
        return loginBoard;
    }

    LoginBoardService loginBoardService = new LoginBoardService();

    public void showLoginBoard(){
        int numberOfAttempts = 0;
        Scanner get = new Scanner(System.in);

        boolean isValidUserName = false,isValidPassword = false;
        String username = "",password = "";

        while(!isValidUserName){
            System.out.print("Enter your user name : ");
            username = get.nextLine();
            numberOfAttempts++;
            if(numberOfAttempts == 4) {
                System.out.println("You exceeded the number of attempts");
                System.exit(0);
            }
            isValidUserName = UserInfoValidator.validateEnteredUserName(username);
        }


        while (!isValidPassword){
            System.out.print("Enter your password : ");
            password = get.nextLine();
            numberOfAttempts++;
            if(numberOfAttempts == 4) {
                System.out.println("You exceeded the number of attempts");
                System.exit(0);
            }
            isValidPassword = UserInfoValidator.validatePassword(password);
        }

       try{
           User user = loginBoardService.login(username,password);
           PagesContext.HOME_BOARD.showHomeBoard(user);
       }catch (RuntimeException | InvalidCredentialsException e){
           System.out.println(e.getMessage());
       }
    }
}
