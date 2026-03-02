package daif.tech.repo;

import daif.tech.exception.InvalidCredentialsException;
import daif.tech.exception.UserAlreadyExistsException;
import daif.tech.exception.UserNotFoundException;
import daif.tech.model.User;
import daif.tech.model.UserKey;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDB {

    private static volatile Map<UserKey, User> usersMap = new HashMap<>();

    public User getUser(String username, String password) throws InvalidCredentialsException {

        Optional<UserKey> userKey = fetchUserKeyOfReceiverByUserName(username);
        User user;
        if(userKey.isPresent()){
            user =  usersMap.get(userKey.get());
            if (password.equals(user.getPassword())) return user;
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }

    public void addUser(User user) throws UserAlreadyExistsException {

        UserKey newUserKey = new UserKey(user.getPhoneNumber(), user.getUserName());

        boolean userNameOrPhoneNumberAlreadyexists = usersMap.keySet().stream()
                .anyMatch(key ->
                        key.getPhoneNumber().equals(user.getPhoneNumber()) ||
                                key.getUserName().equals(user.getUserName())
                );

        if (userNameOrPhoneNumberAlreadyexists) {
            throw new UserAlreadyExistsException("User already exists");
        }

        usersMap.put(newUserKey, user);
    }

    public boolean isUserExists(UserKey userKey){
        return usersMap.containsKey(userKey);
    }

    public void transferToUser(UserKey senderUserKey, String receiverNumber, BigDecimal amount) throws UserNotFoundException {
        usersMap.get(senderUserKey).withdraw(amount);

        Optional<UserKey> receiverUserKey = fetchUserKeyOfReceiverByPhoneNumber(receiverNumber);
        if (receiverUserKey.isPresent()) {
            usersMap.get(receiverUserKey.get()).deposit(amount);
        }else{
            throw new UserNotFoundException("There is no user with this phone number");
        }
    }

    public void deposit(UserKey userKey,BigDecimal amount){
        usersMap.get(userKey).deposit(amount);
    }

    public void withdraw(UserKey userKey,BigDecimal amount){
        usersMap.get(userKey).withdraw(amount);
    }


    public double getBalance(UserKey userKey){
        return usersMap.get(userKey).getBalance().doubleValue();
    }


    private Optional<UserKey> fetchUserKeyOfReceiverByUserName(String username) {
        return usersMap
                .keySet()
                .stream().filter(userKey -> userKey.getUserName().equals(username)).findFirst();

    }

    private Optional<UserKey> fetchUserKeyOfReceiverByPhoneNumber(String phoneNumber) {
        return usersMap
                .keySet()
                .stream().filter(userKey -> userKey.getPhoneNumber().equals(phoneNumber)).findFirst();

    }
}
