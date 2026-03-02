package daif.tech.model;

import java.math.BigDecimal;

public class User {
    private UserKey userKey;
    private String password;
    private BigDecimal balance;
    private int age;

    public User(String userName, String password, String phoneNumber,int age, BigDecimal balance) {
        this.userKey = new UserKey(phoneNumber,userName);
        this.password = password;
        this.age = age;
        this.balance = balance;
    }

    public int getAge() {
        return age;
    }

    public UserKey getUserKey() {
        return userKey;
    }

    public String getUserName() {
        return userKey.getUserName();
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return userKey.getPhoneNumber();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount){
        if(amount.doubleValue()<0){
            System.out.println("You can't add amount with negative value");
        }else balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        if(balance.subtract(amount).doubleValue()<0){
            System.out.println("The available balance is not enough to proceed");
        }else{
            balance = balance.subtract(amount);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userKey=" + userKey +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", age=" + age +
                '}';
    }
}
