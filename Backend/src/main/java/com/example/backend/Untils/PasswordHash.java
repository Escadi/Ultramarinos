package com.example.backend.Untils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {


    /**
     * HASHEAMOS CUALQUIER CONTRASEÑA
     *
     * */
    public static String passwordHash (String password){

        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * VEMOS SI LA CONTRASEÑA ENTRANDO ES IGUAL A LA QUE SE TIENE HASHEADA EN LA BASE DE DATOS
     *
     * */

    public static boolean checkPasswordHash(String password, String hashPassword){
        return BCrypt.checkpw(password,hashPassword);
    }

}
