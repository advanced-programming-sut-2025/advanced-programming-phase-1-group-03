package com.ap.ui.widget;


import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.model.StoreProduct;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.function.BiConsumer;

public class StoreBuyDialog extends Dialog {

    private int quantity = 1;
    private final Label quantityLabel;

    public StoreBuyDialog(Skin skin, Stage stage,
                          StoreProduct product,
                          AudioService audioService,
                          BiConsumer<StoreProduct, Integer> buyHandler) {

        super("Buy Item", skin);

        setModal(true);
        setMovable(false);
        setResizable(false);
        setSize(400, 400); // اندازه مناسب برای محتوای کامل
        setPosition(
                (stage.getWidth() - getWidth()) / 2f,
                (stage.getHeight() - getHeight()) / 2f
        );

        setTouchable(Touchable.enabled);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true; // جلوگیری از عبور کلیک به لایه‌های پایین
            }
        });


        Table content = new Table(skin);
        content.pad(20);
        content.defaults().space(10);


        Image productImage = new Image(product.texture);
        content.add(productImage).size(64, 64).colspan(2).center().row();

        Label nameLabel = new Label(product.name, skin);
        content.add(nameLabel).colspan(2).center().row();

        Label priceLabel = new Label("Price: " + product.sellPrice, skin);
        content.add(priceLabel).colspan(2).center().row();


        TextButton minusButton = new TextButton("-", skin);
        TextButton plusButton = new TextButton("+", skin);
        quantityLabel = new Label(String.valueOf(quantity), skin);

        Table quantityTable = new Table();
        quantityTable.add(minusButton).size(40, 40);
        quantityTable.add(quantityLabel).width(10).center().pad(10);
        quantityTable.add(plusButton).size(40, 40);

        content.add(quantityTable).colspan(2).center().row();


        TextButton buyButton = new TextButton("Buy", skin);
        TextButton cancelButton = new TextButton("Cancel", skin);

        Table buttonTable = new Table();
        buttonTable.add(buyButton).width(120).height(40).padRight(10);
        buttonTable.add(cancelButton).width(120).height(40);

        content.add(buttonTable).colspan(2).padTop(20);

        getContentTable().add(content);

        // رویدادها
        plusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quantity++;
                quantityLabel.setText(String.valueOf(quantity));
                audioService.playSound(SoundAsset.HoverButton);
            }
        });

        minusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (quantity > 1) {
                    quantity--;
                    quantityLabel.setText(String.valueOf(quantity));
                    audioService.playSound(SoundAsset.HoverButton);
                }
            }
        });

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyHandler.accept(product, quantity);
                remove();
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audioService.playSound(SoundAsset.HoverButton);
                remove();
            }
        });

        stage.addActor(this);
    }
}
