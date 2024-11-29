package com.sf.sso.app.dtos;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

    private final String fullname;
    private final String email;
    
    private CustomUserDetails(Builder builder) {
        super(builder.username, builder.password, builder.authorities);
        this.fullname = builder.fullname;
        this.email = builder.email;
        
    }

    

    public String getFullname() {
        return fullname;
    }



    public String getEmail() {
        return email;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        CustomUserDetails that = (CustomUserDetails) o;
        return fullname.equals(that.fullname)  && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fullname, email);
    }

    public static class Builder {

        private String fullname;
        private String email;
        private String username;
        private String password;

        private Collection<? extends GrantedAuthority> authorities;


        public Builder withFullname(String fullname){
            this.fullname = fullname;
            return this;
        }

        public Builder withUsername(String username){
            this.username = username;
            return this;
        }

        public Builder withEmail(String email){
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public CustomUserDetails build() {
            return new CustomUserDetails(this);
        }

    }

    
}