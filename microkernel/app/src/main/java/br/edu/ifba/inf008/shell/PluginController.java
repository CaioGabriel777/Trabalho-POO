package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.App;
import br.edu.ifba.inf008.interfaces.IPluginController;
import br.edu.ifba.inf008.interfaces.IPlugin;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginController implements IPluginController {
    public boolean init() {
        try {

            String[] possiblePaths = {
                    "./plugins",
                    "../plugins",
                    "microkernel/plugins",
                    System.getProperty("user.dir") + "/plugins"
            };

            File currentDir = null;
            for (String path : possiblePaths) {
                File testDir = new File(path);
                if (testDir.exists() && testDir.isDirectory()) {
                    currentDir = testDir;
                    System.out.println("Plugins directory found: " + testDir.getAbsolutePath());
                    break;
                }
            }

            if (currentDir == null || !currentDir.exists()) {
                System.out.println("Warning: plugins directory not found. No plugins will be loaded.");
                return true;
            }

            FilenameFilter jarFilter = new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".jar");
                }
                
            };

            String[] plugins = currentDir.list(jarFilter);

            if (plugins == null || plugins.length == 0) {
                System.out.println("No plugins found in " + currentDir.getAbsolutePath());
                return true;
            }

            System.out.println("Found " + plugins.length + " plugins");

            URL[] jars = new URL[plugins.length];
            for (int i = 0; i < plugins.length; i++) {
                jars[i] = new File(currentDir, plugins[i]).toURI().toURL();
                System.out.println("Loading plugin: " + plugins[i]);
            }

            URLClassLoader ulc = new URLClassLoader(jars, App.class.getClassLoader());

            for (int i = 0; i < plugins.length; i++) {
                String pluginName = plugins[i].split("\\.")[0];
                try {
                    IPlugin plugin = (IPlugin) Class.forName("br.edu.ifba.inf008.plugins." + pluginName, true, ulc)
                            .getDeclaredConstructor().newInstance();
                    plugin.init();
                    System.out.println("Plugin initialized: " + pluginName);
                } catch (Exception e) {
                    System.out.println("Failed to load plugin " + pluginName + ": " + e.getMessage());
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
