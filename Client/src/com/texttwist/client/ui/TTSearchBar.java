package com.texttwist.client.ui;
import com.texttwist.client.constants.Palette;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Callable;


/**
 * Created by loke on 14/06/2017.
 */
public class TTSearchBar extends TTInputField{

    private DefaultListModel matchedUsers = new DefaultListModel();
    public TTSearchBar(Point position,
                       Dimension dimension,
                       String placeholer,
                       DefaultListModel listModel,
                       Callable<Object> clickHandler,
                       TTContainer parent){

        super(position, dimension, placeholer, parent);
        setBackground(Palette.scrollPanel_backgroundColor);
        setFont(Palette.inputBox_font);
        setBounds(position.x, position.y, dimension.width, dimension.height);
        setPreferredSize(dimension);
        setForeground(Palette.fontColor);

        TTScrollList userList = new TTScrollList(
                new Point(20,120),
                new Dimension(250,95),
                matchedUsers,
                parent
        );

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                //Every time i press a key, execute a search of users
            }
        });

        parent.add(this);
    }
}