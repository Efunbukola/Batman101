package saboor.testexlist;

/**
 * Created by Saboor Salaam on 7/23/2014.
 */
public class UserAccount {

    String name;
    String email;
    String password;

    public UserAccount(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserAccount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
