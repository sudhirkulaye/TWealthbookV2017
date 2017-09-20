package com.twealthbook.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeneratePassword {
    public static void main(String[] arg){
        System.out.println("8767599725/Tuna1980 :"+ passwordEncoder().encode("Tuna1980"));
        /*
        System.out.println("9004078268/Pagnis502 :"+ passwordEncoder().encode("Pagnis502"));
        System.out.println("Admin/Admin703 :"+ passwordEncoder().encode("Admin703"));
        System.out.println("9930174850/Madhavi1985 :"+ passwordEncoder().encode("Madhavi1985"));
        System.out.println("9987508953/Rohini0304 :"+ passwordEncoder().encode("Rohini0304"));
        System.out.println("9819156287/Yatin218 :"+ passwordEncoder().encode("Yatin218"));
        System.out.println("9967020963/Yogesh1980 :"+ passwordEncoder().encode("Yogesh1980"));
        System.out.println("9833539125/Pawar702 :"+ passwordEncoder().encode("Pawar702"));
        System.out.println("9833539299/Rahul111 :"+ passwordEncoder().encode("Rahul111"));
        System.out.println("9869483091/Prashant222 :"+ passwordEncoder().encode("Prashant222"));
        System.out.println("9820518100/Sachin504 :"+ passwordEncoder().encode("Sachin504"));
        System.out.println("9820116406/Girish1110 :"+ passwordEncoder().encode("Girish1110"));
        System.out.println("9821312266/NishaAneeket123 :"+ passwordEncoder().encode("NishaAneeket123"));
        System.out.println("9823433623/JayeshManu111 :"+ passwordEncoder().encode("JayeshManu111"));*/

        /*
        8767599725/Tuna1980 :$2a$10$prUa6b7WsfhN33u8khVx8.qrLNympG0bbmuqXbGHUpvPKcHOdTB.O
        9004078268/Pagnis502 :$2a$10$5ufphFNQmDWNy45omuaj4e2/4OuKLsIounmLhhkbT8uEb4D4n9QXu
        Admin/Admin703 :$2a$10$yLrtIAznr3IwyyGv4Wj2yO2rm67gn3MchuK0Fs.UuIQdWowkemxiS
        9930174850/Madhavi1985 :$2a$10$Nuz9Mn4SovtiHMwsRiqD3eW5DHncbQIjop2vBeJA9dKDdDdImuK6C
        9987508953/Rohini0304 :$2a$10$ZDtcgbzTbieQdqX5E.0qDuob5G8cYmYokFxQVGHwXmT7OV9mqzs92
        9819156287/Yatin218 :$2a$10$T2.zn.raKEGFqchMr.2zLusIa/V65CBxesC52cN7bMcrRmY/ca3me
        9967020963/Yogesh1980 :$2a$10$z/Q8TVh.fAXUHoo9wwj.cO46bNeS.SWik0w.oFeUBsSywr3LS4SoK
        9833539125/Pawar702 :$2a$10$G7HGj356utx0mpUczSKq9OJYeuFgS8XqTuyOJefe2EY0a32lbrcNC
        9833539299/Rahul111 :$2a$10$D2zaA138F0IU1.Cf7j4kQO.mAROnXoANlVZRprbsjbQKNYCXSiKhy
        9869483091/Prashant222 :$2a$10$abwcYXmedFI5yKVorc9n2O2IwxUeLz0s636nXnmdjxGkWDDFbztpW
        9820518100/Sachin504 :$2a$10$lWnzSvQBPXvToLpWFPWDE.6TgA6/xTNBiadl.oKcDhtfSjVQbnISO
        9820116406/Girish1110 :$2a$10$hO4NGE6qGX0XPz5XtVIbqeik9R4Si70VVA/or0NTIUGNfTXj9SVhy
        9821312266/NishaAneeket123 :$2a$10$dmaIWfYNdmVSdJHgmYMuiOlCI4c55cV1Yujg5eAily9UHEqTq9HvS
        9823433623/JayeshManu111 :$2a$10$bOyflnmzOFtmtqt9wGrYy.B6ZrKiWmOXQ9T0x4NxRVU9f.PzxKcyO
         */

    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
