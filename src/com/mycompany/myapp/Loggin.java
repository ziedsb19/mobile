package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.evenements.views.BaseEvent;
import com.mycompany.myapp.evenements.views.EvenementsView;


public class Loggin extends BaseEvent{
    
    private Form form;
    
    public Loggin() {
        form = new Form("Loggin", new BorderLayout());
        form.setUIID("Loggin");
        form.add(BorderLayout.NORTH, new Label(VarGlobales.getTheme().getImage("Logo.png"), "LogoLabel"));
        
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        username.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");
        
        Container content = BoxLayout.encloseY(
                username,
                password,
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp)
        );
        content.setScrollableY(true);
        form.add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        signIn.addActionListener(e -> new EvenementsView().getForm().show());
    }
    
    public Form getForm(){
        return form;
    }
    
}
