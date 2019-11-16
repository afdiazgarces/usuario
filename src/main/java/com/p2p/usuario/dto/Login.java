/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.p2p.usuario.dto;

/**
 *
 * @author Felipe Diaz
 */
public class Login {
    
    String correo;
    String password;

    @Override
    public String toString() {
        return "Login{" + "correo=" + correo + ", password=" + password + '}';
    }
    
    

    public Login(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
