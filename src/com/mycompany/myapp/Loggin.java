package com.mycompany.myapp;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import static com.codename1.ui.TextArea.PASSWORD;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.evenements.views.Evenement;


public class Loggin extends BaseForm {
    
    public Loggin (Resources theme){
        setUIID("SignIn");
        setTitle("Loggin");
        setLayout(new BorderLayout());
        Image icon = theme.getImage("Logo.png");
        add(BorderLayout.NORTH,new Label(icon, "CenterLabel"));
        TextField username = new TextField(null, "entrer votre loggin");
        TextField password = new TextField (null, "******");
        password.setConstraint(PASSWORD);
        Button loggin = new Button("Loggin");
        loggin.addActionListener((e)->{new Evenement().show();});
        Button signUp = new Button("Sign Up");
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");
        Container signUpCnt = new Container(new FlowLayout(CENTER));
        signUpCnt.addAll(signUp, doneHaveAnAccount);
        Container bottom = new Container(BoxLayout.y());
        bottom.addAll(username, password,loggin,signUpCnt);
        add(BorderLayout.SOUTH,bottom);
    }
}