package com.nkey.desafio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DesafioApplicationTests {

    @Test
    public void testMain() {
    	DesafioApplication main = new DesafioApplication();
        String [] args = { "one" };
        main.main(args);
    }
    
}


