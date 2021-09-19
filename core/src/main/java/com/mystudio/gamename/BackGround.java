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
import org.mini2Dx.core.graphics.Texture;

public class BackGround {
    private Texture bgITexture;
    private float scaleAMount = 4;

    public BackGround(String file) {
        bgITexture = Utility.textureImportFromFile(file);
    }

    public void render(Graphics g) {
        g.scale(scaleAMount, scaleAMount);
        g.drawTexture(bgITexture, 0, 0);
        g.scale(1 / scaleAMount, 1 / scaleAMount);
    }
}
