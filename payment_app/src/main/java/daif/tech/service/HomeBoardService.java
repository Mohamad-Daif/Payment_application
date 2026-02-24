package daif.tech.service;

import daif.tech.model.Transaction;
import daif.tech.model.User;
import daif.tech.repo.TransactionDB;
import daif.tech.repo.UserDB;
import daif.tech.util.UserInfoValidator;

import java.math.BigDecimal;

public class HomeBoardService {

    private UserDB userDB = new UserDB();
    TransactionDB transactionDB = new TransactionDB();

    private User user;

    public void logout(){
        System.out.println("User Logged out successfully");
        user = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean checkIfUserExists(String phoneNumber)throws IllegalArgumentException{

        boolean isValidReceiverPhoneNumber = UserInfoValidator.validatePhoneNumber(phoneNumber);

        if(!isValidReceiverPhoneNumber){
            throw new IllegalArgumentException("Invalid Receiver phone number");
        }else if(user.getPhoneNumber().equals(phoneNumber)){
            throw new IllegalArgumentException("User can't transfer for him self");
        }

        return userDB.isUserExists(phoneNumber);
    }

    public void deposit(BigDecimal amount) throws IllegalArgumentException{
        if(amount.doubleValue() < 0){
            throw new IllegalArgumentException("Amount can't be negative value");
        }
        userDB.deposit(user.getPhoneNumber(),amount);
        System.out.println("Deposited successfully");
        transactionDB.logNewTransaction(new Transaction(
                String.format("User with user name : \"%s\" is depositing %.2f",user.getUserName(),amount.doubleValue())
        ));
    }

    public void withdraw(BigDecimal amount)throws IllegalArgumentException{
        if(amount.doubleValue() < 0){
            throw new IllegalArgumentException("Amount can't be negative value");
        }
        userDB.withdraw(user.getPhoneNumber(),amount);
        System.out.println(String.format("Amount withdrawn successfully",amount.doubleValue()));
        transactionDB.logNewTransaction(new Transaction(
                String.format("User with user name : \"%s\" is withdrawing %.2f",user.getUserName(),amount.doubleValue())
        ));
    }

    public double getBalance(){
        return userDB.getBalance(user.getPhoneNumber());
    }

    public void transfer(String receiverPhoneNumber,BigDecimal amount){

        userDB.transferToUser(user.getPhoneNumber(),receiverPhoneNumber,amount);
        System.out.println("Transfer process is done successfully");
        transactionDB.logNewTransaction(new Transaction(
                String.format("User with user name : \"%s\" is transferring %.2f to \"%s\"",user.getUserName(),amount.doubleValue(),receiverPhoneNumber)
        ));
    }
}
