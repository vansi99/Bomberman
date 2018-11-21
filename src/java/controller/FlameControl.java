package controller;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class FlameControl extends Component {
    @Override
    public void onUpdate( double tpf){ }

    public void burn(){
        getEntity().removeFromWorld();
    }
}
