/*
 * This file is part of the OpenJML project.
 * Copyright (c) 2006-2013 David R. Cok
 * @author David R. Cok
 */
package org.jmlspecs.openjml.eclipse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.smtlib.IPos;
import org.smtlib.SMT;

/**
 * The activator class controls the plug-in life cycle. The plug-in is a
 * singleton- there is just one OpenJML Eclipse plug-in in a process.
 */
public class Activator extends AbstractUIPlugin {

	/** The plug-in ID, which must match the content of plugin.xml in several places */
	public static final String PLUGIN_ID = "org.jmlspecs.OpenJMLUI"; //$NON-NLS-1$

	/** The plug-in ID of the Specs project plugin (containing specifications
	 * of Java library classes).  This must match the ID specified in the 
	 * plugin.xml file of the Specs plugin.  The Specs plugin is the
	 * source of all the Java library specifications.
	 */
	public static final String SPECS_PLUGIN_ID = "org.jmlspecs.Specs"; //$NON-NLS-1$

	/** The single shared instance */
	private static Activator plugin;

	/** A general utility instance used by the plugin */
	protected Utils utils;

	/**
	 * The constructor, called by Eclipse, not by user code
	 */
	public Activator() {
		//Log.log("UI Plugin constructor executed");
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		Log.log.setListener(new ConsoleLogger(Messages.OpenJMLUI_Activator_JmlConsoleTitle));
		//Log.log("JML UI plugin started");
		
		// Various initialization: instances of options and utils, cached fields
		utils = new Utils();
		utils.initializeProperties();
		
	    /** The logic file finder for the plug-in looks for a logic file with the given name:
	     * (a) if no logic directory path is set, then it looks within the plugin itself for any built-in files
	     * (b) if a logic directory path is set, it looks there.
	     * <P>
	     * I would have thought that the non-plugin functionality of (a) exporting
	     * logics directory and (b) finding the logic files on the classpath
	     * would work - but I have not been able to make that function. Thus
	     * we need one mechanism for finding resources inside a plug-in (this 
	     * one - with reference to the plug-in bundle) and another mechanism
	     * (looking on the classpath) when one is not inside a plug-in.
	     */
	    SMT.logicFinder = new SMT.ILogicFinder() {
	    	@Override
	    	public InputStream find(SMT.Configuration smtConfig, String name, IPos pos) throws java.io.IOException {
	    		if (smtConfig == null || smtConfig.logicPath == null || smtConfig.logicPath.trim().length() ==0) {
	    			// This logic depends on the fact that the SMT logic files
	    			// reside at the root of the jSMTLIB.jar file, and that the
	    			// .jar file is part of the plugin.
	    			URL url = Platform.getBundle(PLUGIN_ID).getResource(name + org.smtlib.Utils.SUFFIX);
	    			if (url != null) {
	    				InputStream stream =  url.openStream();
	    				if (stream != null) return stream;
	    			}
	    		} else {
	    			String fname = smtConfig.logicPath + File.separator + name + org.smtlib.Utils.SUFFIX;
	    			File f = new File(fname);
	    			if (f.isFile()) return new FileInputStream(f);
	    		}
    			utils.showMessageInUI(null,"OpenJML - SMT",
						"No logic file found for " + name);
				return null;
	    	}
	    };

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		utils = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance. 'Default' is an odd name, but it is the
	 * typical name used in Eclipse for this purpose.
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
}
