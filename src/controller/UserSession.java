package controller;

public class UserSession    {
    private static UserSession instance;
    private String Role;
    private String username;
    private String nom;
    private String prenom;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private UserSession() {
        // private constructor to prevent instantiation
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public String getRole() {
        return Role;
    }

    public void invalidate() {
        Role = null;
    }

    public boolean isLoggedIn() {
        return Role != null;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}