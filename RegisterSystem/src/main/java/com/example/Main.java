package com.example;

import java.util.Scanner;

import com.example.ui.FormUi;

public class Main {

    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + """
                    ====================================
                    | Where will the data be stored?   |
                    | 1. In file                       |
                    | 2. In memory                     |
                    | Enter : """);
            String fromFile = sc.nextLine();
            if (fromFile.equals("1")) {
                var f = new FormUi(true);
                f.start();
                break;
            } else if (fromFile.equals("2")) {
                var f = new FormUi(false);
                f.start();
                break;
            }
           
        }

    }

}
