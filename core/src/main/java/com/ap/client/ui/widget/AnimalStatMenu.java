package com.ap.client.ui.widget;

import com.ap.client.Constraints;
import com.ap.client.asset.AssetService;
import com.ap.client.asset.AtlasAsset;
import com.ap.client.asset.SoundAsset;
import com.ap.client.audio.AudioService;
import com.ap.client.items.Animals.Animal;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimalStatMenu extends Group {
    
    //one is manager on is instance, manager will create instance in show
    private Animal animal;
    private AnimalStatMenu instance;
    private AnimalStatMenu manager;
    private Stage stage;
    private Skin skin;
    private AssetService assetService;
    private AudioService audioService;

    private boolean isShowing = false;

    //for manager pass animal null
    public AnimalStatMenu(Animal animal, Stage stage, Skin skin, AssetService assetService, AudioService audioService) {
        this.animal = animal;
        this.stage = stage;
        this.skin = skin;
        this.assetService = assetService;
        this.audioService = audioService;
    }


    private TextField titleWindow;

    private void setupUI() {
        float screenWidth = Constraints.WORLD_WIDTH_RESOLUTION;
        float screenHeight = Constraints.WORLD_HEIGHT_RESOLUTION;

        titleWindow = new TextField(animal.getName(), skin);
        titleWindow.setSize(250, 50);


        Window body = new Window("", skin);
        body.getTitleLabel().setColor(Color.RED);
        body.getTitleLabel().setFontScale(1.5f);

        Label type = new Label(animal.getType().getUiName(), skin);
        Label friendship = new Label("Friendship: " + animal.getFriendship(), skin);
        Label age = new Label("Age: " + animal.getAge() + "months", skin);
        Label Health = new Label("Health: " + animal.getHealth() + "/" + Animal.maxHealth, skin);

        body.add(type).left().row();
        body.add(friendship).left().row();
        body.add(age).left().row();
        body.add(Health).left();

        body.setSize(250, 200);

        titleWindow.setPosition((screenWidth - body.getWidth()) / 2, (screenHeight + body.getHeight()) / 2 + 10);
        body.setPosition((screenWidth - body.getWidth()) / 2, (screenHeight - body.getHeight()) / 2);

        addActor(titleWindow);
        addActor(body);

        Image closeButton = new Image();
        closeButton.setDrawable(new TextureRegionDrawable(assetService.get(AtlasAsset.UIElements).findRegion("cell/cross_icon")));
        closeButton.setSize(30, 30);
        closeButton.setPosition((screenWidth + body.getWidth()) / 2 + 5, (screenHeight - body.getHeight()) / 2);
        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (!titleWindow.getText().isEmpty()) animal.setName(titleWindow.getText());
                exitMenu();
            }
        });
        closeButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                closeButton.addAction(Actions.scaleTo(1.2f, 1.2f, 0.15f));
                audioService.playSound(SoundAsset.HoverButton);

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                closeButton.addAction(Actions.scaleTo(1f, 1f, 0.1f));
            }
        });
        addActor(closeButton);
//        addActor(nameField);
    }

    public void show(Animal animal) {
        instance = new AnimalStatMenu(animal, stage, skin, assetService, audioService);
        stage.addActor(instance);
        instance.setupUI();
        isShowing = true;
        instance.setManager(this);
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public void setManager(AnimalStatMenu manager) {
        this.manager = manager;
    }

    public void exitMenu() {
        stage.unfocus(titleWindow);
        stage.getActors().removeValue(this, true);
        manager.setShowing(false);
    }
    
}
