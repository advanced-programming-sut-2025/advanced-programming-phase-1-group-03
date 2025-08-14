package com.ap.ui.widget;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.screen.GameScreen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;

public class ProcessingMachineWindow extends Window {

    private final Skin skin;
    private final Stage stage;
    private final AssetService assetService;

    private ProgressBar progressBar;
    private TextButton startButton;
    private TextButton cancelButton;
    private TextButton collectButton;
    private Label statusLabel;
    private GameScreen gameScreen;

    private ProcessingRecipe currentRecipe;
    private float progressTime;
    private boolean isProcessing = false;

    public ProcessingMachineWindow(Skin skin, Stage stage, AssetService assetService, GameScreen gameScreen) {
        super("Processing Machine", skin);
        this.skin = skin;
        this.stage = stage;
        this.assetService = assetService;
        this.gameScreen = gameScreen;

        defaults().pad(10).fillX();
        setMovable(true);
        setResizable(false);
        setScale(1.2f);

        // نمونه دستور العمل
        currentRecipe = new ProcessingRecipe(
                "Cheese",
                assetService.get(AtlasAsset.Crafting).findRegion("Cheese_Press"),
                new String[]{"Milk x1"},
                5f // ثانیه
        );

        // لیبل محصول
        Label productLabel = new Label("Product: " + currentRecipe.productName, skin);
        productLabel.setAlignment(Align.center);
        productLabel.setWrap(true);
        add(productLabel).colspan(2).width(250).center().row();

        // لیبل عنوان مواد اولیه
        Label ingTitle = new Label("Ingredients:", skin);
        ingTitle.setAlignment(Align.center);
        ingTitle.setWrap(true);
        add(ingTitle).colspan(2).width(250).center().row();

        // لیست مواد اولیه
        for (String ing : currentRecipe.ingredients) {
            Label ingLabel = new Label("- " + ing, skin);
            ingLabel.setAlignment(Align.center);
            ingLabel.setWrap(true);
            add(ingLabel).colspan(2).width(250).center().row();
        }

        // لیبل وضعیت
        statusLabel = new Label("Idle", skin);
        statusLabel.setAlignment(Align.center);
        statusLabel.setWrap(true);
        add(statusLabel).colspan(2).width(250).center().padTop(5).row();

        // نوار پیشرفت
        progressBar = new ProgressBar(0, currentRecipe.processingTime, 0.1f, false, skin);
        progressBar.setValue(0);
        progressBar.setAnimateDuration(0.1f);
        add(progressBar).width(200).colspan(2).padTop(5).center().row();

        // دکمه‌ها
        startButton = new TextButton("Start", skin);
        cancelButton = new TextButton("Cancel", skin);
        collectButton = new TextButton("Collect", skin);
        collectButton.setDisabled(true);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isProcessing) {
                    isProcessing = true;
                    progressTime = 0;
                    statusLabel.setText("Processing...");
                    collectButton.setDisabled(true);
                }
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isProcessing) {
                    isProcessing = false;
                    progressTime = 0;
                    progressBar.setValue(0);
                    statusLabel.setText("Cancelled");
                }
            }
        });

        collectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statusLabel.setText("Collected: " + currentRecipe.productName);
                collectButton.setDisabled(true);

            }
        });

        add(startButton).padTop(10);
        add(cancelButton).padTop(10).row();
        add(collectButton).colspan(2).padTop(5).center().row();

        pack();
        centerWindow();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isProcessing) {
            progressTime += delta;
            progressBar.setValue(progressTime);
            if (progressTime >= currentRecipe.processingTime) {
                isProcessing = false;
                statusLabel.setText("Done! Ready to collect.");
                collectButton.setDisabled(false);
            }
        }
    }

    private void centerWindow() {
        float x = (stage.getViewport().getWorldWidth() - getWidth()) / 2;
        float y = (stage.getViewport().getWorldHeight() - getHeight()) / 2;
        setPosition(x, y);
    }

    public static void show(Stage stage, Skin skin, AssetService assetService, GameScreen gameScreen) {
        ProcessingMachineWindow window = new ProcessingMachineWindow(skin, stage, assetService, gameScreen);
        window.getColor().a = 0f;
        window.addAction(Actions.fadeIn(0.3f));
        stage.addActor(window);
    }

    public static class ProcessingRecipe {
        public final String productName;
        public final TextureRegion icon;
        public final String[] ingredients;
        public final float processingTime;

        public ProcessingRecipe(String productName, TextureRegion icon, String[] ingredients, float processingTime) {
            this.productName = productName;
            this.icon = icon;
            this.ingredients = ingredients;
            this.processingTime = processingTime;
        }
    }
}
