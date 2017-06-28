/* newly installe root password is null. So, login with root then 
   first create schema and then user. Finally don't forget to change passowrd of root and destroy password from the script.
*/

CREATE DATABASE  IF NOT EXISTS `twealthbookdev` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `twealthbookdev`;

-- CREATE USER 'analyst'@'localhost' IDENTIFIED BY 'Analyst703';
GRANT ALL ON twealthbookdev.* TO 'devuser'@'localhost' IDENTIFIED BY 'Dev703';

-- On Productin database

CREATE DATABASE  IF NOT EXISTS `twealthbookprod` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `twealthbookprod`;

-- CREATE USER 'analyst'@'localhost' IDENTIFIED BY 'Analyst703';
GRANT ALL ON twealthbookprod.* TO 'appuser'@'localhost' IDENTIFIED BY 'AppU$ser703108';

---Create table, insert day0 goes here

revoke all on twealthbookprod.* from 'appuser'@'localhost';

grant select, insert, delete, update on twealthbookprod.* to 'appuser'@'localhost';

--Now make this change to your src/main/resources/application.properties
--spring.jpa.hibernate.ddl-auto=none 

