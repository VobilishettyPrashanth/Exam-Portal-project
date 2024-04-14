package com.csye6220.esdfinalproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;


    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "profile")
    private String profile;

    @OneToMany(mappedBy = "createdByUser", fetch = FetchType.EAGER)
    private Set<Category> CreateCategory;

    private Set<UserRole> userRoles = new HashSet<>();


    public User() {
        this.userRoles = new HashSet<>();
    }

    public User(long id, String firstName, String lastName, UserRole role, String email, String username, String password, String phone, String profile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.profile = profile;
    }

    public User(String firstName, String lastName, String username, String password, String email, UserRole role,String profile, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.profile = profile;
        this.userRoles = new HashSet<>(Collections.singletonList(role));
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if(this.userRoles != null) {
            for (UserRole role : this.userRoles) {
                authorities.add(new SimpleGrantedAuthority(role.name()));
            }
        }
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public Set<Category> getCreateCategory() {
        return CreateCategory;
    }

    public void setCreateCategory(Set<Category> createCategory) {
        CreateCategory = createCategory;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }

}

