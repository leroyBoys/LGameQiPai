package com.game.manager;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/10.
 */
public abstract class UpdateData implements Runnable {
    protected volatile boolean isUpdate = false;
    private final String updateKey;
    public UpdateData(String updateKey){
        this.updateKey = updateKey+this.getClass().getSimpleName();
    }

    public abstract void update();
    @Override
    public void run() {

    }

    protected void updateIng(){
        this.isUpdate = true;
    }

    protected void hasUpdated(){
        this.isUpdate = false;
    }

    public String getUpdateKey() {
        return updateKey;
    }
}
