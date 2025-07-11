package com.ap.ui.model;

import com.ap.GdxGame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ViewModel {
    protected final GdxGame game;

    // We use this to implement observer design pattern easily
    protected final PropertyChangeSupport support;

    public ViewModel(GdxGame game) {
        this.game = game;
        support = new PropertyChangeSupport(this);
    }

    /**
     * This method add listener to PropertyChangeSupport
     * @param propertyName name Of property
     * @param clazz type Of property
     * @param listener implementation of onPropertyChange interface
     * @param <T> type of property
     */
    public <T> void onPropertyChange(String propertyName, Class<T> clazz, onPropertyChange<T> listener) {
        support.addPropertyChangeListener(propertyName, event -> {
            listener.onChange(clazz.cast(event.getOldValue()), clazz.cast(event.getNewValue()));
        });
    }

    public void clearPropertyListeners() {
        for(PropertyChangeListener listener : support.getPropertyChangeListeners()) {
            support.removePropertyChangeListener(listener);
        }
    }
    @FunctionalInterface
    public interface onPropertyChange<T> {
        void onChange(T oldValue, T newValue);
    }
}
