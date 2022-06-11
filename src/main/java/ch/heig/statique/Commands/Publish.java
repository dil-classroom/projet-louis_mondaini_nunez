package ch.heig.statique.Commands;

import ch.heig.statique.Utils.Utils;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;
import picocli.CommandLine;

/** Permet de compiler un projet en site statique Le site généré se trouve dans le dossier build/ */
@CommandLine.Command(
        name = "publish",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Publish the site folder to a remote git repository")
public class Publish implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The directory containing the site")
    private File file;

    @CommandLine.Parameters(
            index = "1",
            description = "The repository URI where the site must be written")
    private String repository;

    @CommandLine.Parameters(index = "2", description = "The username for the GitHub repository")
    private String username;

    @CommandLine.Parameters(index = "3", description = "The password for the GitHub repository")
    private String password;

    @Override
    public Integer call() throws IOException, GitAPIException {
        try {

            // Creating local repository
            Git git = Git.init().setDirectory(file).call();

            // Adding all files of the repository
            git.add().addFilepattern(".").call();

            // Commit all files
            git.commit()
                    .setAll(true)
                    .setMessage("publishing the site to a GitHub repository")
                    .call();

            // Adding files to remote
            git.remoteAdd().setName("main").setUri(new URIish(repository)).call();

            // Pushing files to remote repository
            PushCommand pushCommand =
                    git.push()
                            .setCredentialsProvider(
                                    new UsernamePasswordCredentialsProvider(username, password));
            SshSessionFactory sshSessionFactory =
                    new JschConfigSessionFactory() {
                        @Override
                        protected void configure(OpenSshConfig.Host host, Session session) {
                            // do nothing
                        }

                        @Override
                        protected JSch createDefaultJSch(FS fs) throws JSchException {
                            JSch defaultJSch = super.createDefaultJSch(fs);
                            defaultJSch.addIdentity(
                                    "C:"
                                            + Utils.SEPARATOR
                                            + "Users"
                                            + Utils.SEPARATOR
                                            + "FNGUSER004"
                                            + Utils.SEPARATOR
                                            + ".ssh"
                                            + Utils.SEPARATOR
                                            + "id_ed25519");

                            // if key is protected with passphrase
                            defaultJSch.addIdentity(
                                    "C:"
                                            + Utils.SEPARATOR
                                            + "Users"
                                            + Utils.SEPARATOR
                                            + "FNGUSER004"
                                            + Utils.SEPARATOR
                                            + ".ssh"
                                            + Utils.SEPARATOR
                                            + "id_ed25519",
                                    password);

                            return defaultJSch;
                        }
                    };
            pushCommand.setTransportConfigCallback(
                    transport -> {
                        SshTransport sshTransport = (SshTransport) transport;
                        sshTransport.setSshSessionFactory(sshSessionFactory);
                    });
            pushCommand.add("master");
            pushCommand.setRemote("main");
            pushCommand.call();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }
        return 0;
    }
}
