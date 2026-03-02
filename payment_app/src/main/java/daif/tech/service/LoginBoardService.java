package daif.tech.service;

import daif.tech.exception.InvalidCredentialsException;
import daif.tech.model.Transaction;
import daif.tech.model.User;
import daif.tech.repo.TransactionDB;
import daif.tech.repo.UserDB;

public class LoginBoardService {

    private UserDB userDB = new UserDB();
    private TransactionDB transactionDB = new TransactionDB();

    public User login(String username, String password) throws InvalidCredentialsException {
        User user;
        user = userDB.getUser(username, password);
        System.out.println("Welcome back, " + user.getUserName());

        transactionDB.logNewTransaction(new Transaction(
                String.format("User with user name : \"%s\" is logging in", user.getUserName())
        ));
        return user;
    }
}