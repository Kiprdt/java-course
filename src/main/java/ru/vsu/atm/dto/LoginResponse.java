package ru.vsu.atm.dto;

public class LoginResponse {
    private Long userId;
    private String login;

    public LoginResponse(Long userId, String login) {
        this.userId = userId;
        this.login = login;
    }
    public Long getUserId() { return userId; }
    public String getLogin() { return login; }
}