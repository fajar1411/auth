package com.example.auth.dto;

public class ChangePassword {
    private String oldPassword;
    private String newPassword;
    private String confirmNew;

    public String getConfirmNew() {
        return confirmNew;
    }

    public void setConfirmNew(String confirmNew) {
        this.confirmNew = confirmNew;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ChangePassword() {
    }
    
}
