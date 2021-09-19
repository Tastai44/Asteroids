/*******************************************************************************
 * Copyright 2020 Viridian Software Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.mystudio.gamename;

import org.mini2Dx.core.geom.Positionable;
import org.mini2Dx.gdx.math.Vector2;

public class Physics {
    private static final float GRAVITY = 9.81f;
    // reference to object position
    private Positionable position;
    private Vector2 velocity;
    private float gravityScale;

    /**
     * Default will always assign velocity to Vector2.Zero and gravity scale to 1
     *
     * @param position an object reference required for physics to apply it's calculations
     */
    Physics(Positionable position) {
        velocity = new Vector2();
        this.position = position;
        gravityScale = 1;
    }

    /**
     * @param position     an object reference required for physics to apply it's calculations
     * @param gravityScale the scalar value of the force of gravity. 1 = 9.81g
     */
    Physics(Positionable position, float gravityScale) {
        velocity = new Vector2();
        this.position = position;
        this.gravityScale = gravityScale;
    }

    public void update(float delta) {
        // Apply Gravity
        velocity.y += gravityScale * GRAVITY * delta;
        // Assign Changes in velocity to object position
        updatePositionWithVelocity();
    }

    public void addForce(Vector2 force) {
        velocity.add(force);
    }

    public void addForceReverse(Vector2 force) {
        velocity.sub(force);
    }

    private void updatePositionWithVelocity() {
        position.setXY(velocity.x + position.getX(), velocity.y + position.getY());
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }
}
