package Model;

import java.util.HashMap;

public class User {
    private final String username;
    private final String password;
    private final String firstName;

    private static final HashMap<String, String> loginMember = new HashMap<>();

    //add a blank constructor so you can call this class for login purposes
    public User(){
        this.username = "";
        this.password = "";
        this.firstName = "";
    }

    public User(String username, String password, String firstName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
    }

    //method to access hashmap to another class
    public HashMap<String, String> getMemberMap() {
        return loginMember;
    }

    public String getUsername() {
        return this.username + "";
    }

    public String getPassword() {
        return this.password + "";
    }

    public String getFirstName() {
        return this.firstName + "";
    }


    //something wrong with this method here
    public boolean isMemberExist(User user){
        if(loginMember.containsKey(user.getUsername()) && loginMember.containsValue(user.getPassword())){
            return true;
        } else {
            System.out.println("No member in the list!");
        }
        return false;
    }

    //something also wrong here
    public void register(User user) {
        loginMember.put(user.getUsername(),user.getPassword());
    }


    public void login(User user){
        if(isMemberExist(user)) {
            System.out.println("Hello " + user.getFirstName());
        } else {
            System.out.println("No member with username " +user.getUsername());
        }
    }
}
