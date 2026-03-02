package daif.tech.model;

import java.util.Objects;

public class UserKey {
    private String phoneNumber;

    private String userName;

    public UserKey(String phoneNumber, String userName) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserKey userKey)) return false;
        return Objects.equals(phoneNumber, userKey.phoneNumber) || Objects.equals(userName, userKey.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, userName);
    }

    @Override
    public String toString() {
        return "UserKey{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
