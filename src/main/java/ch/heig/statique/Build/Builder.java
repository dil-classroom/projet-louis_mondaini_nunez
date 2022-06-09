package ch.heig.statique.Build;

import ch.heig.statique.Parser.PageParser;
import ch.heig.statique.Site.Page;
import ch.heig.statique.Utils.Utils;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.yaml.snakeyaml.Yaml;

/**
 * Builder class allowing to generate a website from a given directory
 * containing the sources and pages as markdown files and a configuration file.
 * It uses the Handlebars library to generate the HTML files.
 */
public class Builder {

    /**
     * Builds the website from the given directory. The directory must contain
     * the website sources and the configuration file. The method iterates over
     * the files in the directory and generates the corresponding HTML files and
     * copy the other files in a new directory called "build".
     * @param siteFolder the directory containing the website sources
     * @return the File to the build directory
     * @throws IOException if an error occurs while reading or writing files
     */
    public static File buildSite(File siteFolder) throws IOException {
        // Verify the absolute path
        if (siteFolder.isAbsolute()) {
            siteFolder = new File(siteFolder.toString() + Utils.SEPARATOR + "site");
        } else {
            throw new RuntimeException("Please use an absolute path");
        }

        // Verify the configuration file
        File config = new File(siteFolder + Utils.SEPARATOR + "config.yaml");
        if (!FileUtils.directoryContains(siteFolder, config)) {
            throw new RuntimeException("Could not find any config.yaml file");
        }

        // Create new build directory and delete it if it already exists
        File buildFolder = new File(siteFolder + Utils.SEPARATOR + "build");
        FileUtils.deleteDirectory(buildFolder);

        // Copy all files needed to build the website
        FileUtils.copyDirectory(
                siteFolder,
                buildFolder,
                pathname -> {
                    if (pathname.getName().equals("build") && pathname.isDirectory()
                            || pathname.getName().equals("config.yaml")
                            || pathname.getName().equals("template")) {
                        return false;
                    }

                    return true;
                });

        // Get iterator on all markdown files
        var it = FileUtils.iterateFiles(buildFolder, new String[] {"md"}, true);

        // Set up Handlebars and yaml parser
        TemplateLoader loader =
                new FileTemplateLoader(new File(siteFolder + Utils.SEPARATOR + "template"));
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("layout");

        Yaml yaml = new Yaml();

        // Iterate over all markdown files and convert them to HTML files
        while (it.hasNext()) {
            File md = it.next();
            Page page = PageParser.parseFile(md);

            Map<String, Object> siteConfig =
                    yaml.load(FileUtils.readFileToString(config, StandardCharsets.UTF_8));

            Context context =
                    Context.newBuilder(new Object())
                            .combine("site", siteConfig)
                            .combine("page", page.getMetadata())
                            .combine("content", page.getContent())
                            .resolver(MapValueResolver.INSTANCE, JavaBeanValueResolver.INSTANCE)
                            .build();

            FileUtils.writeStringToFile(
                    md,
                    PageParser.convertMdToHtml(template.apply(context)),
                    StandardCharsets.UTF_8,
                    false);

            // Rename the file to the correct extension
            if (!md.renameTo(
                    new File(FilenameUtils.removeExtension(md.getAbsolutePath()) + ".html"))) {
                throw new RuntimeException("Cannot rename the md files to html files");
            }
        }

        return buildFolder;
    }
}
