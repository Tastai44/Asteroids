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

import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.collision.CollisionArea;
import org.mini2Dx.core.graphics.Texture;
import org.mini2Dx.gdx.Input;

/**
 * This is made because I don't want to have to repeatedly type out
 * Mdx.graphics.newTexture(Mdx.files.external(file)
 * and possibly some other useful functions
 * PaulJame5 ;)
 */
public class Utility {
    public static final float OFFSET_SPRITE_ROTATION = 90.0f;
    public static final boolean DEBUG = false;

    public static Texture textureImportFromFile(String file) {
        return Mdx.graphics.newTexture(Mdx.files.external(file));
    }

    public static boolean leftKeyPressed() {
        return (Mdx.input.isKeyDown(Input.Keys.A) || Mdx.input.isKeyDown(Input.Keys.LEFT));
    }

    public static boolean rightKeyPressed() {
        return (Mdx.input.isKeyDown(Input.Keys.D) || Mdx.input.isKeyDown(Input.Keys.RIGHT));
    }

    public static boolean upKeyPressed() {
        return (Mdx.input.isKeyDown(Input.Keys.W) || Mdx.input.isKeyDown(Input.Keys.UP));
    }

    public static boolean downKeyPressed() {
        return (Mdx.input.isKeyDown(Input.Keys.S) || Mdx.input.isKeyDown(Input.Keys.DOWN));
    }

    public static boolean shootButtonPressed() {
        return Mdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }

    /**
     * This checks that objects positions are withing the boundaries of our screen and then
     * if they go out of range they will
     * arrive from the opposite side. Literally wrapping around the screen.
     * <p>
     * x a reference of Position class for checking our x and y values
     * y see x
     *
     * @return passed in position values and any changes if they were made
     */
    public static void wrapAroundMap(CollisionArea pos) {
        if (pos.getX() < -90) {
            pos.forceTo(580, pos.getY());
        } else if (pos.getX() > 700) {
            pos.forceTo(-40, pos.getY());
        } else if (pos.getY() < -80) {
            pos.forceTo(pos.getX(), 520);
        } else if (pos.getY() > 550) {
            pos.forceTo(pos.getX(), -30);
        }
    }
}
