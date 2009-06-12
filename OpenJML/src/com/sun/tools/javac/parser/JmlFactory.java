package com.sun.tools.javac.parser;

import org.jmlspecs.openjml.Utils;

import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

/**
 * This class extends the Java Parser factory class to be able to produce
 * JML ast nodes as well. There is just one factory per context, but there
 * may be multiple instances of parsers.
 * 
 * @author David Cok
 */
public class JmlFactory extends ParserFactory {

    /** The context key for the parser factory. */
    protected static final Context.Key<JmlFactory> jmlparserFactoryKey = new Context.Key<JmlFactory>();

    /**
     * The constructor for the factory.
     * 
     * @param context
     *            the Context for which this is the factory
     */
    protected JmlFactory(Context context) {
        super(context);
        this.context = context;
    }

    /** The context in which this factory works */
    protected Context context;

    /**
     * A static call that registers an instance of JmlFactory as the factory
     * to use for this context.
     * 
     * @param context
     *            the context in which to register the factory
     */
    public static void preRegister(final Context context) {
        context.put(parserFactoryKey, new Context.Factory<ParserFactory>() {
            public ParserFactory make() {
                return new JmlFactory(context);
            }
        });
    }

    /**
     * Creates a new parser from the factory, given a lexer and flags as to
     * whether to keep javadoc comments and whether to generate end position
     * information.
     */
    // @ requires S != null;
    // @ ensures this.S != null && this.context != null;
    // @ ensures this.names != null && this.jmlF != null;
    @Override
    public Parser newParser(CharSequence input, boolean keepDocComments,
            boolean genEndPos, boolean keepLineMap) {
        return newParser(input, keepDocComments, genEndPos, keepLineMap,
                false); // The last argument says that the parser begins
                        // not within a JML comment
    }

    public JmlParser newParser(CharSequence input, boolean keepDocComments,
            boolean genEndPos, boolean keepLineMap, boolean enableJml) {
        JmlScanner lexer = (JmlScanner) scannerFactory.newScanner(input);
        lexer.setJml(enableJml);
        JmlParser p = new JmlParser(this, lexer, keepDocComments || true); 
            // FIXME - really just want the doc comments when doing jmldoc
        p.names = Names.instance(context);
        p.context = context;
        p.utils = Utils.instance(context);
        return p;
    }

}