package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.evenements.views.BaseEvent;
import com.mycompany.myapp.evenements.views.EvenementsView;
import java.io.IOException;
import java.util.Map;
 
public class Loggin extends BaseEvent{
 
    //FIXME: text inputs colors
    
    private Form form;
    
    public Loggin() {
        form = new Form("Loggin", new BorderLayout());
        form.setUIID("Loggin");
        form.add(BorderLayout.NORTH, new Label(VarGlobales.getTheme().getImage("Logo.png"), "LogoLabel"));
        
        TextField username = new TextField(null, "username");
        TextField password = new TextField(null, "Password");
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
        signIn.addActionListener(e -> {
            if (username.getText() == null ||  username.getText().isEmpty() || password.getText() == null || password.getText().isEmpty()){
                Dialog.show("loggin", "veuillez remplir tous les champs ", "ok", null);
            }
            else {
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                loggin(username.getText().trim(), password.getText().trim());
            }
        });
    }
    
    public Form getForm(){
        return form;
    }

    private void loggin(String username, String password) {
        ConnectionRequest cr = new ConnectionRequest(""
                + "http://localhost/pi/tech_events/web/app_dev.php/evenement/mobile/loggin?username="+username+"&password="+password, false);
        NetworkManager.getInstance().addToQueueAndWait(cr);
        if (!new String(cr.getResponseData()).equals("no")){
            JSONParser json = new JSONParser();
            try {
                Map <String, Object> userMap = json.parseJSON(new CharArrayReader(new String(cr.getResponseData()).toCharArray()));
                Utilisateur user = new Utilisateur();
                user.setId((int)((double)userMap.get("id")));
                user.setUsername((String)userMap.get("username"));
                user.setNom_prenom((String)userMap.get("nomPrenom"));
                user.setAdresse((String)userMap.get("adresse"));
                user.setEmail((String)userMap.get("email"));
                user.setNumero_telephone(Integer.parseInt(userMap.get("numeroTel").toString()));
                VarGlobales.setUtilisateur(user);
                new EvenementsView().getForm().show();
            } catch (IOException ex) {
            }
        }
        else
            Dialog.show("loggin", "mauvais coordonnées", "ok", null);
    }
    
}
