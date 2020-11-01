package design.kfu.entity.dto;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static design.kfu.service.SignUpService.*;

public class PersonForm {

    private String email;
    private String name;
    private String password;
    private String pwd2;
    private String agree = null;
    private int songCount = 0;

    public PersonForm(String email, String name, String pwd1, String pwd2, String agree) {
        this.email = email;
        this.name = name;
        this.password = pwd1;
        this.pwd2 = pwd2;
        if (agree.equals("on"))
            this.agree = agree;
    }

    public PersonForm() {
    }

    public PersonForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPwd2() {
        return pwd2;
    }

    public String getAgree() {
        return agree;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String pwd1) {
        this.password = pwd1;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    @Override
    public String toString() {
        return "PersonForm{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", pwd1='" + password + '\'' +
                ", pwd2='" + pwd2 + '\'' +
                ", agree=" + agree +
                '}';
    }

    public int isValid() {
        if (agree == null)
            return AGREEMENT_FALSE;
        if (!password.equals(pwd2))
            return PWDS_NOT_EQUALS;
        if (password.length() < 6)
            return PWD_SIZE_SHORT;
        if (password.length() > 30)
            return PWD_SIZE_LONG;
        if (!validate(email))
            return EMAIL_INCORRECT;
        return SUCCESS;
    }

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


}