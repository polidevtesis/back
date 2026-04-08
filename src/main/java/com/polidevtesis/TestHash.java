package com.polidevtesis;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String passwordPlano = "1234"; // 👈 aquí cambias la contraseña

        String hash = encoder.encode(passwordPlano);

        System.out.println("Hash generado:");
        System.out.println(hash);
    }
}
