package tests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class Channels extends TCBase {

    String eol = "\n";  
    
    @Before
    public void setUp() throws Exception {
        super.setUp();        
//        main.addOptions("-noPurityCheck");
//        main.addOptions("-jmltesting");
//        main.addOptions("-noInternalRuntime");
//        main.addOptions("-esc");
        jmldebug = true;
//    
        }

    @Test
    public void testOnMethod() throws IOException{
        helpTCF("A.java", caseFromStub("testOnMethod"));
    }
    
    @Test
    public void testOnFormalParameters() throws IOException{
        helpTCF("A.java", caseFromStub("testOnFormalParameters"));
    }
    
    @Test
    public void testOnField() throws IOException{
        helpTCF("A.java", caseFromStub("testOnField"));
    }
    
    // we don't allow this.
    @Test
    public void testOnLocalVariable() throws IOException{
        
        helpTCF("A.java", caseFromStub("testOnLocalVariable"),
                "/A.java:4: A channel assignment may only be used in the formal parameters to a method or as a function return type",
                21);
        
    }
    
    
}