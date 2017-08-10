/* newly installed root password is null. So, login with root then
   first create schema and then user. Finally don't forget to change password of root and destroy password from the script.
*/

CREATE DATABASE  IF NOT EXISTS `twealthbookdev` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `twealthbookdev`;

-- CREATE USER 'analyst'@'localhost' IDENTIFIED BY 'Analyst703';
GRANT ALL ON twealthbookdev.* TO 'devuser'@'localhost' IDENTIFIED BY 'Dev703';


