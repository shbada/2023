package me.whiteship.chapter05.item26.genericdao;

public class Account implements Entity {

    private Long id;

    private String username;

    public Account(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }
}
