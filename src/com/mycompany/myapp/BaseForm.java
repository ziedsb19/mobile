package com.mycompany.myapp;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.mycompany.myapp.evenements.views.AddEvent;
import com.mycompany.myapp.evenements.views.Evenement;

public class BaseForm extends Form {
    
    private Toolbar tb;
    
    public void addToolBar(){
        tb = getToolbar();
        tb.addMaterialCommandToSideMenu("Evenements", FontImage.MATERIAL_HOME, (e)->{new Evenement().show();});
        tb.addMaterialCommandToSideMenu("Actualites", FontImage.MATERIAL_HOME, (e)->{});
        tb.addMaterialCommandToSideMenu("FabLab", FontImage.MATERIAL_HOME, (e)->{});
        tb.addMaterialCommandToSideMenu("Forum", FontImage.MATERIAL_HOME, (e)->{});
    }
}
