package br.com.clinica.cui;

import br.com.clinica.service.LoginService;

import java.util.Scanner;

public class LoginCUI {

    public void LogIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------");
        System.out.println("** Entrar **");
        System.out.println("--------------------------------------------------------------");
        boolean result;
        LoginService loginService = new LoginService();
        do {
            System.out.println("E-mail: ");
            String login = scanner.next();
            System.out.println("Senha: ");
            String senha = scanner.next();
            result = loginService.Logar(login, senha);
        } while (!result);
    }
}
