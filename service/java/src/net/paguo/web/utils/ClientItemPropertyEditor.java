package net.paguo.web.utils;

import net.paguo.controller.ClientItemController;
import net.paguo.domain.clients.ClientItem;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

/**Property editor for ClientItem bean
 * @version $Id $
 */
public class ClientItemPropertyEditor extends PropertyEditorSupport {
    private ClientItemController controller;


    public ClientItemController getController() {
        return controller;
    }

    public void setController(ClientItemController controller) {
        this.controller = controller;
    }

    public ClientItemPropertyEditor(ClientItemController controller) {
        this.controller = controller;
    }

    @Override
    public void setAsText(String text){
        if (StringUtils.isBlank(text)){
            setValue(null);
        }else{
            try{
                Integer id = Integer.decode(text);
                ClientItem ci = getController().getClientDao().read(id);
                setValue(ci);
            }catch(NumberFormatException e){
                setValue(null);
            }
        }
    }

    @Override
    public String getAsText(){
       ClientItem ci = (ClientItem) getValue();
       return ci == null ? "" : ci.getId().toString();
    }
}
