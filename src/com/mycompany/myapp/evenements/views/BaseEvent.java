package com.mycompany.myapp.evenements.views;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.Style;

public class BaseEvent {
    
    private Form form;
    
    public void addToolBar(Toolbar tb){
        tb.addMaterialCommandToOverflowMenu("evenements", FontImage.MATERIAL_HOME, (e)->{
            EvenementsView ev = new EvenementsView();
            ev.getForm().show();
        });
        tb.addMaterialCommandToOverflowMenu("Ma bibiliotheque", FontImage.MATERIAL_LIBRARY_BOOKS, (e)->{
            new Library().getForm().show();
        });
        tb.addMaterialCommandToOverflowMenu("ajouter votre evenemement", FontImage.MATERIAL_ADD_BOX, (e)->{
            AddEvent addEvent = new AddEvent();
            addEvent.getForm().show();
        });    
    }
    
}
