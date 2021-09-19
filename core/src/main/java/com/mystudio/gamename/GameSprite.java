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

import org.mini2Dx.core.graphics.Animation;
import org.mini2Dx.core.graphics.SpriteSheet;
import org.mini2Dx.core.graphics.Texture;

public class GameSprite {
    private Animation anim = new Animation();
    private Texture texture;
    private SpriteSheet spriteSheet;
    private int frameWidth, frameHeight;

    public GameSprite(String file, int frameWidth, int frameHeight) {
        texture = Utility.textureImportFromFile(file);
        spriteSheet = new SpriteSheet(texture, frameWidth, frameHeight);
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        anim.addFrame(spriteSheet.getSprite(0), 1);
    }

    public GameSprite(String file, int frameWidth, int frameHeight, float frameDuration, int numberOfFrames) {
        texture = Utility.textureImportFromFile(file);
        spriteSheet = new SpriteSheet(texture, frameWidth, frameHeight);
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;

        for (int i = 0; i < numberOfFrames; i++) {
            anim.addFrame(spriteSheet.getSprite(i), frameDuration);
        }
    }

    public Animation getAnimation() {
        return anim;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }


}
