package controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;


public class BombControl extends Component {
    private int radius;

    public BombControl(int radius) {
        this.radius = radius;
    }

    @Override
    public void onUpdate( double tpf){ }

    public void explode() {
        BoundingBoxComponent bbox = getEntity().getBoundingBoxComponent();

    }

}
