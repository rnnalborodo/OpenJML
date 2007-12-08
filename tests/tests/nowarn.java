package tests;

import org.jmlspecs.openjml.JmlTree.JmlClassDecl;
import org.jmlspecs.openjml.JmlTree.JmlCompilationUnit;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCErroneous;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCModifiers;

public class nowarn extends ParseBase {


    @Override
    public void setUp() throws Exception {
//        noCollectDiagnostics = true;
//        jmldebug = true;
        super.setUp();
    }
    
    public void testNowarn() {
        checkCompilationUnit("/*@ nowarn Z; */package t; //@ nowarn X; \n/*@ nowarn Y; */ class A{}",
                JmlCompilationUnit.class,16,
                JCIdent.class, 24,
                JmlClassDecl.class, 59,
                JCModifiers.class, -1);
        checkMessages();
    }
    
    public void testNowarn2() {
        checkCompilationUnit("package /*@ nowarn Z ZZ ZZZ; */t; //@ nowarn X,A,B; \n/*@ nowarn Y,,YY,,,YYY; */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 31,
                JmlClassDecl.class, 80,
                JCModifiers.class, -1);
        checkMessages();
    }
    
    public void testNowarn3() {
        checkCompilationUnit("package /*@ nowarn Z ; nowarn Q; */t; //@ nowarn X,A,B; \n/*@ nowarn Y,,YY,,,YYY; */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 35,
                JmlClassDecl.class, 84,
                JCModifiers.class, -1);
        checkMessages();
    }
    
    public void testNowarn4() {
        checkCompilationUnit("package /*@ nowarn Z  */t; //@ nowarn X\n/*@ nowarn ; */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 24,
                JmlClassDecl.class, 56,
                JCModifiers.class, -1);
        checkMessages("/TEST.java:1: warning: A nowarn pragma must end with a semicolon",13,
                "/TEST.java:1: warning: A nowarn pragma must end with a semicolon",32);
    }
    
    public void testNowarn5() {
        checkCompilationUnit("package /*@ nowarn Z  */t; //@ nowarn X\n/*@ nowarn ; */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 24,
                JmlClassDecl.class, 56,
                JCModifiers.class, -1);
        checkMessages("/TEST.java:1: warning: A nowarn pragma must end with a semicolon",13,
                "/TEST.java:1: warning: A nowarn pragma must end with a semicolon",32);
    }
    
    public void testNowarn6() {
        checkCompilationUnit("package /*@ nowarn nowarn */t; \n class /*@ nowarn ; */A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 28,
                JmlClassDecl.class, 33,
                JCModifiers.class, -1);
        checkMessages("/TEST.java:1: warning: A nowarn pragma must end with a semicolon",13);
    }
    
    
    public void testNowarnA() {
        checkCompilationUnit("package t; \n/*@ pure nowarn Y; */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 8,
                JmlClassDecl.class, 34,
                JCModifiers.class, 34,
                JCAnnotation.class, 16,
                JCFieldAccess.class, 16,
                JCFieldAccess.class, 16,
                JCFieldAccess.class, 16,
                JCIdent.class, 16
        );
        checkMessages();
    }
    
    public void testNowarnB() {
        checkCompilationUnit("package t; \n/*@ pure nowarn Y */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 8,
                JmlClassDecl.class, 33,
                JCModifiers.class, 33,
                JCAnnotation.class, 16,
                JCFieldAccess.class, 16,
                JCFieldAccess.class, 16,
                JCFieldAccess.class, 16,
                JCIdent.class, 16
        );
        checkMessages("/TEST.java:2: warning: A nowarn pragma must end with a semicolon",10);
    }
    
    public void testNowarnC() {
        checkCompilationUnit("package t; \n/*@ pure nowarn C; nullable_by_default */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 8,
                JmlClassDecl.class, 54,
                JCModifiers.class, 54,
                JCAnnotation.class, 16,
                JCFieldAccess.class, 16,
                JCFieldAccess.class, 16,
                JCFieldAccess.class, 16,
                JCIdent.class, 16,
                JCAnnotation.class, 31,
                JCFieldAccess.class, 31,
                JCFieldAccess.class, 31,
                JCFieldAccess.class, 31,
                JCIdent.class, 31
        );
        checkMessages();
    }
    
    public void testNowarnD() {
        checkCompilationUnit("package t; \n/*@ pure nowarn C nullable            */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 8,
                JmlClassDecl.class, 53,
                JCModifiers.class, 53,
                JCAnnotation.class, 16,
                JCFieldAccess.class, 16,
                JCFieldAccess.class, 16,
                JCFieldAccess.class, 16,
                JCIdent.class, 16
                );
        checkMessages("/TEST.java:2: warning: A nowarn pragma must end with a semicolon",10);
    }
  
    public void testNowarnE() {  // TODO - the error recovery could be improved here
        checkCompilationUnit("package t; \n/*@  nowarn C! ; */ class A{}",
                JmlCompilationUnit.class,0,
                JCIdent.class, 8,
                JCErroneous.class, 27,
                JmlClassDecl.class, 32,
                JCModifiers.class, -1);
        checkMessages("/TEST.java:2: An illegal character found in a nowarn pragma",14,
                "/TEST.java:2: class, interface, or enum expected",16);
    }
}
