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

import org.mini2Dx.core.collision.CollisionBox;
import org.mini2Dx.core.geom.Positionable;
import org.mini2Dx.gdx.math.Vector2;
import org.mini2Dx.core.Graphics;

public class Bullet {
    private static final float MAX_TIME_TO_LIVE = 1;
    public static float SPEED = 10;

    private Physics physics;
    private CollisionBox collisionBox;
    private GameSprite bulletSprite;
    private boolean active = false;
    float timeToLive = 0;

    /**
     * @param file location of sprite/texture image used for bullet
     */
    public Bullet(String file) {
        collisionBox = new CollisionBox();
        bulletSprite = new GameSprite(file, GameSpritesFrameSizeInformation.BULLET_FRAME_WIDTH,
                GameSpritesFrameSizeInformation.BULLET_FRAME_HEIGHT);

        collisionBox.setHeight(bulletSprite.getFrameHeight());
        collisionBox.setWidth(bulletSprite.getFrameWidth());
        collisionBox.setCenter(collisionBox.getCenterX(), collisionBox.getCenterY());

        physics = new Physics(collisionBox, 0);
    }

    public void init(Positionable position, Vector2 direction, float angle) {
        collisionBox.setRotation(angle + Utility.OFFSET_SPRITE_ROTATION);
        bulletSprite.getAnimation().setRotation(collisionBox.getRotation());
        collisionBox.forceTo(position.getX(), position.getY());
        physics.setVelocity(direction.x * SPEED, direction.y * SPEED);
        timeToLive = MAX_TIME_TO_LIVE;
        active = true;
    }

    public void update(float delta) {
        physics.update(delta);
        Utility.wrapAroundMap(collisionBox);
        checkIfTimeToLiveExpired(delta);
    }

    private void checkIfTimeToLiveExpired(float delta) {
        if (timeToLive > 0) {
            timeToLive -= 1 * delta;
        } else {
            active = false;
        }
    }

    public void render(Graphics g) {

        // We wish to centre our texture with the collider
        bulletSprite.getAnimation().draw(g,
                collisionBox.getRenderX() - bulletSprite.getFrameWidth() / 2,
                collisionBox.getRenderY() - bulletSprite.getFrameHeight() / 2);

        if (Utility.DEBUG) {
            collisionBox.draw(g);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
