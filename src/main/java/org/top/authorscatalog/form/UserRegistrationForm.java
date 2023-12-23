package org.top.authorscatalog.form;

// UserRegistrationForm - объект формы регистрации пользователя
public class UserRegistrationForm {
    private String login;
    private String password;
    private String passwordConfirmation;
    private String role;

    // конструктор
    public UserRegistrationForm() {

    }

    // геттеры и сеттеры
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
