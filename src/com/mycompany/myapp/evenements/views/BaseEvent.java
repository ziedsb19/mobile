package com.mycompany.myapp.evenements.views;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.mycompany.myapp.Loggin;

public class BaseEvent {
    
    private Form form;
    
    public void addToolBar(Toolbar tb){
        tb.addMaterialCommandToOverflowMenu("evenements", FontImage.MATERIAL_HOME, (e)->{
            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
            EvenementsView ev = new EvenementsView(); 
            ev.getForm().show();
        });
        tb.addMaterialCommandToOverflowMenu("Ma bibiliotheque", FontImage.MATERIAL_LIBRARY_BOOKS, (e)->{
            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
            new Library().getForm().show();
        });
        tb.addMaterialCommandToOverflowMenu("ajouter votre evenemement", FontImage.MATERIAL_ADD_BOX, (e)->{
            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
            AddEvent addEvent = new AddEvent();
            addEvent.getForm().show();
        });
        tb.addMaterialCommandToOverflowMenu("deconnexion", FontImage.MATERIAL_LOCK, (e)->{
            new Loggin().getForm().show();
        });
    }
    
}
