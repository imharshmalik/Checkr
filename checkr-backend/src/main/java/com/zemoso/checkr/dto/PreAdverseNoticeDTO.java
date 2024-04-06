package com.zemoso.checkr.dto;

public class PreAdverseNoticeDTO {
    private String fromEmail;
    private String toEmail;
    private String emailSubject;
    private String emailContent;
    private boolean checkbox1;
    private boolean checkbox2;
    private boolean checkbox3;

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public boolean isCheckbox1() {
        return checkbox1;
    }

    public void setCheckbox1(boolean checkbox1) {
        this.checkbox1 = checkbox1;
    }

    public boolean isCheckbox2() {
        return checkbox2;
    }

    public void setCheckbox2(boolean checkbox2) {
        this.checkbox2 = checkbox2;
    }

    public boolean isCheckbox3() {
        return checkbox3;
    }

    public void setCheckbox3(boolean checkbox3) {
        this.checkbox3 = checkbox3;
    }

    public PreAdverseNoticeDTO() {
    }

    public PreAdverseNoticeDTO(String fromEmail, String toEmail, String emailSubject, String emailContent, boolean checkbox1, boolean checkbox2, boolean checkbox3) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.emailSubject = emailSubject;
        this.emailContent = emailContent;
        this.checkbox1 = checkbox1;
        this.checkbox2 = checkbox2;
        this.checkbox3 = checkbox3;
    }
}
