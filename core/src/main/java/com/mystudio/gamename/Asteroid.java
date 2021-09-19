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

import org.mini2Dx.core.Graphics;
import org.mini2Dx.core.collision.CollisionCircle;
import org.mini2Dx.gdx.math.MathUtils;

public class Asteroid {
    public static final int SIZE_LARGE_ASTEROID = 10;
    public static final int SIZE_MEDIUM_ASTEROID  = 5;
    public static final int SIZE_SMALL_ASTEROID  = 3;

    private CollisionCircle asteroidCollisionCircle;
    private GameSprite sprite;
    private Physics physics;
    private boolean active = false;
    private int size;
    private float spinSpeed = 25f;

    public Asteroid(int size, boolean active, float radius) {
        this.size = size;
        this.active = active;
        setTexture();
        asteroidCollisionCircle = new CollisionCircle(radius);
        // We set gravity scale to 0 as we are out in space
        physics = new Physics(asteroidCollisionCircle, 0);
        setRandomPosition();
        physics.setVelocity(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f));
    }

    private void setTexture() {
        if (size == SIZE_LARGE_ASTEROID) {
            try {
                sprite = new GameSprite(ImageFilePaths.BIG_ASTEROID_SPRITE,
                        GameSpritesFrameSizeInformation.ASTEROID_LARGE_FRAME_WIDTH,
                        GameSpritesFrameSizeInformation.ASTEROID_LARGE_FRAME_HEIGHT );
                spinSpeed = MathUtils.random(-50, 50);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (size == SIZE_MEDIUM_ASTEROID) {
            try {
                sprite = new GameSprite(ImageFilePaths.MEDIUM_ASTEROID_SPRITE,
                        GameSpritesFrameSizeInformation.ASTEROID_MEDIUM_FRAME_WIDTH,
                        GameSpritesFrameSizeInformation.ASTEROID_MEDIUM_FRAME_HEIGHT );

                spinSpeed = MathUtils.random(-150, 150);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (size == SIZE_SMALL_ASTEROID) {
            try {
                sprite = new GameSprite(ImageFilePaths.SMALL_ASTEROID_SPRITE,
                        GameSpritesFrameSizeInformation.ASTEROID_SMALL_FRAME_WIDTH,
                        GameSpritesFrameSizeInformation.ASTEROID_SMALL_FRAME_HEIGHT );
                spinSpeed = MathUtils.random(-250, 250);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setRandomPosition() {
        float min = 100, max = 500;
        float x = 300;
        float y = 300;

        while (!(x > max || x < min) && !(y > max || y < min)) {
            x = MathUtils.random(0f, 600.0f);
            y = MathUtils.random(0.0f, 600f);
        }
        asteroidCollisionCircle.setXY(x, y);
    }

    public void update(float delta) {
        physics.update(delta);
        sprite.getAnimation().rotate(spinSpeed * delta);
        Utility.wrapAroundMap(asteroidCollisionCircle);
    }

    public void render(Graphics g) {
        sprite.getAnimation().draw(g,
                asteroidCollisionCircle.getRenderX() - sprite.getFrameWidth() / 2,
                asteroidCollisionCircle.getRenderY() - sprite.getFrameHeight() / 2);

        if (Utility.DEBUG) {
            asteroidCollisionCircle.draw(g);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSize() {
        return size;
    }

    public void setPositionImmediately(float posX, float posY) {
        asteroidCollisionCircle.forceTo(posX, posY);
    }

    public CollisionCircle getCollisionCircle() {
        return asteroidCollisionCircle;
    }

}
