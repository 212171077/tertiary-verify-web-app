package com.pc.common;

import com.pc.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@SpringBootApplication
@ComponentScan({"com.pc"})
@EntityScan("com.pc.entities")
@EnableJpaRepositories("com.pc.repositories")
public class SpringPrimeFacesApplication implements CommandLineRunner {

    @Autowired
    MailSender mailSender;

    public static void main(String[] args) {
        SpringApplication.run(SpringPrimeFacesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //detecting the file type
	     /* BodyContentHandler handler = new BodyContentHandler();
	      
	      Metadata metadata = new Metadata();
	      FileInputStream inputstream = new FileInputStream(new File(
	         "C:/tika/excelExample.xlsx"));
	      
	      ParseContext pcontext = new ParseContext();

	      //OOXml parser
	      OOXMLParser  msofficeparser = new OOXMLParser ();
	      
	      msofficeparser.parse(inputstream, handler, metadata,pcontext);
	      System.out.println("Contents of the document:" + handler.toString());
	      System.out.println("Metadata of the document:");
	      String[] metadataNames = metadata.names();

	      for(String name : metadataNames) {
	         System.out.println(name + ": " + metadata.get(name));
	      }*/
		/*Mail mail=new Mail();
		
		mail.setContent("Email Content");
		mail.setFrom("christoph@gmail.com");
		String[] to={"christoph@dajotechnologies.com"};;
		mail.setTo(to);
		mail.setSubject("Test Email");
		mail.setCc(to);
		mailSender.sendHtmlEmil(mail);*/

    }
	
	/*CREATE SCHEMA IF NOT EXISTS TERTIARY_VERIFY;

	SET SCHEMA TERTIARY_VERIFY;

	CREATE TABLE TERTIARY_VERIFY.SPRING_SESSION (

	PRIMARY_ID CHAR(36) NOT NULL,

	SESSION_ID CHAR(36) NOT NULL,

	CREATION_TIME BIGINT NOT NULL,

	LAST_ACCESS_TIME BIGINT NOT NULL,

	MAX_INACTIVE_INTERVAL INT NOT NULL,

	EXPIRY_TIME BIGINT NOT NULL,

	PRINCIPAL_NAME VARCHAR(100),

	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)

	);

	CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON TERTIARY_VERIFY.SPRING_SESSION (SESSION_ID);

	CREATE INDEX SPRING_SESSION_IX2 ON TERTIARY_VERIFY.SPRING_SESSION (EXPIRY_TIME);

	CREATE INDEX SPRING_SESSION_IX3 ON TERTIARY_VERIFY.SPRING_SESSION (PRINCIPAL_NAME);

	CREATE TABLE TERTIARY_VERIFY.SPRING_SESSION_ATTRIBUTES (

	SESSION_PRIMARY_ID CHAR(36) NOT NULL,

	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,

	ATTRIBUTE_BYTES BLOB NOT NULL,

	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),

	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES TERTIARY_VERIFY.SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE

	);*/


}
