package com.mystudio.gamename.desktop;

import com.badlogic.gdx.backends.lwjgl.DesktopMini2DxGame;

import com.mystudio.gamename.Game;
import org.mini2Dx.libgdx.desktop.DesktopMini2DxConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		DesktopMini2DxConfig config = new DesktopMini2DxConfig(Game.GAME_IDENTIFIER);
		config.vSyncEnabled = true;
		new DesktopMini2DxGame(new Game(), config);
	}
}
