package com.ap.client.component;

import com.badlogic.ashley.core.Component;

public class CameraFollow implements Component {
    // One and only one entity should use this component
    // the camera will follow the first entity with the CameraFollow Component
}
