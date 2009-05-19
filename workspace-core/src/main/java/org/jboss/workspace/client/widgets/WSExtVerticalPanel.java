package org.jboss.workspace.client.widgets;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.gwt.mosaic.core.client.DOM;
import org.jboss.workspace.client.layout.WorkspaceLayout;

public class WSExtVerticalPanel extends VerticalPanel {

    private boolean armed;
    private WorkspaceLayout workspaceLayout;

    Timer t = new Timer() {
        public void run() {
            if (armed) workspaceLayout.openNavPanel();
        }
    };

    int range = -1;
    
    public WSExtVerticalPanel(WorkspaceLayout layout) {
        super();

        sinkEvents(Event.MOUSEEVENTS);

        this.workspaceLayout = layout;
    }

    @Override
    public void onBrowserEvent(Event event) {
        if (!armed) return;

        switch (event.getTypeInt()) {
            case Event.ONMOUSEOVER:
                break;
            case Event.ONMOUSEUP:
                if (range == -1) {
                    range = getAbsoluteTop() + 20;
                }

                if (event.getClientY() > range) {
                    t.cancel();
                    workspaceLayout.openNavPanel();
                }
                break;

            case Event.ONCLICK:
                break;

            case Event.ONMOUSEMOVE:

                if (range == -1) {
                    range = getAbsoluteTop() + 20;
                }

                if (event.getClientY() > range) {
                    getElement().setClassName("workspace-LeftNavArea-MouseOver");
                    t.schedule(200);
                }

                break;


            case Event.ONMOUSEOUT:
                Element to = DOM.eventGetToElement(event);
                if (to == null || !DOM.isOrHasChild(this.getElement(), to)) {
                    getElement().setClassName("workspace-LeftNavArea");
                    t.cancel();
                    workspaceLayout.closeNavPanel();
                }
                break;
        }
    }


    public boolean isArmed() {
        return armed;
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
    }
}
