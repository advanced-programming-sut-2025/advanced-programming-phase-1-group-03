package com.ap.rmi;

import com.ap.asset.SoundAsset;
import com.ap.managers.GameUIManager;
import com.ap.ui.widget.DecisionDialog;
import com.badlogic.gdx.Gdx;

import java.util.concurrent.CountDownLatch;

public class AskImpl implements Ask {
    DecisionDialog eatDialog;

    @Override
    public boolean ask(String name) {
        final CountDownLatch latch = new CountDownLatch(1);
        final boolean[] decision = new boolean[1];

        Gdx.app.postRunnable(() -> {
            eatDialog = GameUIManager.instance.showDecisionDialog(name,
                    () -> {
                        eatDialog.remove();
                        decision[0] = true;
                        latch.countDown();
                    }, () -> {
                        eatDialog.remove();
                        decision[0] = false;
                        latch.countDown();
                    });
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            return false;
        }
        return decision[0];
    }
}
