package throne.feeder.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        userList.add(new User("BURAK"));
        userList.add(new User("BURAK"));
        System.out.println(userList.size());
        System.out.println(userList.contains(new User("BURAK")));

        HashSet<User> userListSet = new HashSet<>();
        userListSet.add(new User("BURAK"));
        userListSet.add(new User("BURAK"));
        System.out.println(userListSet.size());

    }
}
