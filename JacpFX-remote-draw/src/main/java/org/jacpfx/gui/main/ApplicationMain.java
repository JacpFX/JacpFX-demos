package org.jacpfx.gui.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jacpfx.gui.workbench.DrawingWorkbench;
import org.jacpfx.minimal.launcher.JacpFXApplicationLauncher;
import org.jacpfx.rcp.workbench.FXWorkbench;

/**
 * Created by amo on 13.12.13.
 *
 * @author <a href="mailto:amo.ahcp@gmail.com"> Andy Moncsek</a>
 */
public class ApplicationMain extends JacpFXApplicationLauncher {

    public ApplicationMain() {

    }


    /**
     * @param args aa  ddd
     */
    public static void main(final String[] args) {
        Application.launch(args);


    }

    @Override
    protected Class<? extends FXWorkbench> getWorkbenchClass() {
        return DrawingWorkbench.class;
    }

    @Override
    protected String[] getBasePackages() {
        return new String[]{"org.jacpfx.gui", "org.jacpfx.component"};
    }

    @Override
    protected void postInit(Stage stage) {
        Scene scene = stage.getScene();
        // add style sheet
        scene.getStylesheets().addAll(
                ApplicationMain.class.getResource("/styles/quickstart.css")
                        .toExternalForm()
        );
        stage.getIcons().add(new Image("images/icons/JACP_512_512.png"));
    }
}
