package com.ap.ui.view;

import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.input.UIEvent;
import com.ap.ui.model.ViewModel;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class AbstractView<T extends ViewModel>  extends Table implements EventListener {

    protected final Stage stage;
    protected final Skin skin;
    protected final T viewModel;

    protected AbstractView(Stage stage, Skin skin, T viewModel) {
        // Instantiate table with the given skin
        super(skin);

        this.stage = stage;
        this.skin = skin;
        this.viewModel = viewModel;

        // Now we can receive events from stage
        stage.addListener(this);

    }

    abstract protected void setupUI();

    // The subclasses can implement these methods, these methods can handle UI
    public void onRight() {
    }
    public void onLeft() {
    }
    public void onUp() {
    }
    public void onDown() {
    }
    public void onSelect() {
    }
    public void onCancel() {
    }

    @Override
    public boolean handle(Event event) {
        if(event instanceof UIEvent uiEvent) {
            switch (uiEvent.getCommand()) {
                case Right -> onRight();
                case Left -> onLeft();
                case Up -> onUp();
                case Down -> onDown();
                case Select -> onSelect();
                case Cancel -> onCancel();
            }
        }
        return false;
    }

    public static void OnClick(Actor actor, OnEventConsumer consumer) {
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                consumer.onEvent();
            }
        });
    }
    public static <T extends Actor> void OnEnter(T actor, OnActorEvent<T> consumer) {
        actor.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                consumer.onEvent(actor);
            }
        });
    }
    public static <T extends Actor> void OnExit(T actor, OnActorEvent<T> consumer) {
        actor.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                consumer.onEvent(actor);
            }
        });
    }
    @FunctionalInterface
    public interface OnActorEvent<T extends Actor> {
        void onEvent(T actor);
    }

    public static void AddHoverScale(Actor button, float from, float to, float duration) {
        button.setOrigin(button.getWidth() / 2, button.getHeight() / 2);
        OnEnter(button, (Actor btn) -> {
            btn.addAction(Actions.scaleTo(to, to, duration));
        });
        OnExit(button, (Actor btn) -> {
            btn.addAction(Actions.scaleTo(from, from, duration));
        });
    }
    public static void AddHoverScale(Actor button, AudioService audioService) {
        OnEnter(button, (Actor btn) -> {
            audioService.playSound(SoundAsset.HoverButton);
            btn.addAction(Actions.scaleTo(2f, 2f, 0.3f));
        });
        OnExit(button, (Actor btn) -> {
            btn.addAction(Actions.scaleTo(1f, 1f, 0.3f));
        });
    }
    @FunctionalInterface
    public interface OnEventConsumer {
        void onEvent();
    }
}
