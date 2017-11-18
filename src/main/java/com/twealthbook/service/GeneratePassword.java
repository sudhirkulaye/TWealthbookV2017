package com.twealthbook.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeneratePassword {
    public static void main(String[] arg){
        System.out.println("Demo/Demo123 :"+ passwordEncoder().encode("Demo123"));


        /*
        Demo/Demo123 :$2a$10$C9Ez4CWi8s3DJ2c618gZfOHA064CDWNzHhLHQptfaH8.rIRlzNiHe
        Admin/Admin703 :$2a$10$yLrtIAznr3IwyyGv4Wj2yO2rm67gn3MchuK0Fs.UuIQdWowkemxiS
        1001/Madhavi1985 :$2a$10$Nuz9Mn4SovtiHMwsRiqD3eW5DHncbQIjop2vBeJA9dKDdDdImuK6C
        1002/Rohini0304 :$2a$10$ZDtcgbzTbieQdqX5E.0qDuob5G8cYmYokFxQVGHwXmT7OV9mqzs92
        1003/Yatin218 :$2a$10$T2.zn.raKEGFqchMr.2zLusIa/V65CBxesC52cN7bMcrRmY/ca3me
        1004/Yogesh1980 :$2a$10$z/Q8TVh.fAXUHoo9wwj.cO46bNeS.SWik0w.oFeUBsSywr3LS4SoK
        1005/Pawar702 :$2a$10$G7HGj356utx0mpUczSKq9OJYeuFgS8XqTuyOJefe2EY0a32lbrcNC
        1007/Rahul111 :$2a$10$D2zaA138F0IU1.Cf7j4kQO.mAROnXoANlVZRprbsjbQKNYCXSiKhy
        1008/Prashant222 :$2a$10$abwcYXmedFI5yKVorc9n2O2IwxUeLz0s636nXnmdjxGkWDDFbztpW
        1010/Sachin504 :$2a$10$lWnzSvQBPXvToLpWFPWDE.6TgA6/xTNBiadl.oKcDhtfSjVQbnISO
        1014/Girish1110 :$2a$10$hO4NGE6qGX0XPz5XtVIbqeik9R4Si70VVA/or0NTIUGNfTXj9SVhy
        1016/NishaAneeket123 :$2a$10$dmaIWfYNdmVSdJHgmYMuiOlCI4c55cV1Yujg5eAily9UHEqTq9HvS
        1017/JayeshManu111 :$2a$10$bOyflnmzOFtmtqt9wGrYy.B6ZrKiWmOXQ9T0x4NxRVU9f.PzxKcyO
        1020/Pagnis502 :$2a$10$5ufphFNQmDWNy45omuaj4e2/4OuKLsIounmLhhkbT8uEb4D4n9QXu
        1022/Tuna1980 :$2a$10$prUa6b7WsfhN33u8khVx8.qrLNympG0bbmuqXbGHUpvPKcHOdTB.O
        1023/Paresh111 :$2a$10$N0VLkKh9vm5uh8QsABFM.OEgTCKSGfIJp2HK1bgn.Ff/OdjVmH8p6
        1026/SJP111 :$2a$10$uscbfv0eT8cMcHmdy6cf4OQM8lsSLUBCPW2JUkYvQXGJIQDZXiGU6
         */

    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
