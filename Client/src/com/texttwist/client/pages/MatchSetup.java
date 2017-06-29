package com.texttwist.client.pages;

import com.texttwist.client.controllers.MatchSetupController;
import constants.Palette;
import com.texttwist.client.ui.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;

/**
 * Created by loke on 14/06/2017.
 */
public class MatchSetup extends Page{

    public TTContainer matchSetupContainer;
    public MatchSetupController matchSetupController;

    MatchSetup(JFrame window) throws Exception {
        super(window);
        matchSetupController = new MatchSetupController();
        createUIComponents();
        window.setVisible(true);
    }

    @Override
    public void createUIComponents() throws Exception {
        addLogo(root);

        matchSetupContainer = new TTContainer(
                null,
                new Dimension(1150,220),
                Palette.root_backgroundColor,
                -1,
                root);

        TTLabel title = new TTLabel(
                new Point(170,0),
                new Dimension(350,50),
                "Invite players",
                new Font(Palette.inputBox_font.getFontName(), Font.ITALIC, 38),
                null,
                matchSetupContainer);

       TTSearchBar searchUserBar = new TTSearchBar(
               new Point(20, 80),
               new Dimension(250, 40),
               "Username",
               matchSetupContainer);

        addFooter(root);
        addNext(footer,
                new Font(Palette.inputBox_font.getFontName(), Font.BOLD, 40),
                null,
                "Play!",
                new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        //If server response ok, start play, else error
                       return matchSetupController.play(searchUserBar.list);
                    }
                });

        addBack(footer,
                new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        return new Menu(Page.window);
                    }
                });

    }
}
