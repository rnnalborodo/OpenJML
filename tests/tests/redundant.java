package tests;

public class redundant extends TCBase {


    @Override
    public void setUp() throws Exception {
//        noCollectDiagnostics = true;
//        jmldebug = true;
        super.setUp();
    }

    public void testRedundant() {
        helpTCF("A.java","public class A { /*@ ensures true; implies_that  ensures false; for_example ensures true; */\n void m(){} }");
    }

    public void testRedundant1() {
        helpTCF("A.java","public class A { /*@ ensures true; implies_that behavior ensures false; for_example example ensures true; */\n void m(){} }");
    }

    public void testRedundant1a() {
        helpTCF("A.java","public class A { /*@ ensures true; implies_that normal_behavior ensures false; for_example normal_example ensures true; */\n void m(){} }");
    }

    public void testRedundant1b() {
        helpTCF("A.java","public class A { /*@ ensures true; implies_that exceptional_behavior signals_only Exception; for_example exceptional_example requires true; */\n void m(){} }");
    }

    public void testRedundant2() {
        expectedExit = 0;
        helpTCF("A.java","public class A { /*@ ensures true; implies_that example ensures false; for_example behavior ensures true; */\n void m(){} }"
                ,"/A.java:1: warning: A example specification case must appear in a for_example section",49
                ,"/A.java:1: warning: A behavior specification case must not appear in a for_example section",84
            );
    }

    public void testRedundant3() {
        helpTCF("A.java","public class A { /*@ ensures true; implies_that  ensures false; also requires true; for_example ensures true; also normal_example ensures false; */\n void m(){} }");
    }

    public void testRedundant4() {
        helpTCF("A.java","public class A { /*@ ensures true; implies_that public behavior ensures false; */\n void m(){} }");
    }
    
    public void testRedundant4b() {
        helpTCF("A.java","public class A { /*@ ensures true; for_example public normal_example ensures false; */\n void m(){} }");
    }

}
