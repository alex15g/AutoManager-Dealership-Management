package ro.utcn.reprezentanta.model;

public class User {
    private int idUser;
    private String username;
    private String role;

    public User(int idUser, String username, String role) {
        this.idUser = idUser;
        this.username = username;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }
}
