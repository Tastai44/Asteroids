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
import java.util.concurrent.TimeUnit;
import java.util.Date;

import org.mini2Dx.core.Graphics;
import org.mini2Dx.core.game.BasicGame;
import java.util.ArrayList;

public class Game extends BasicGame{
    public static final String GAME_IDENTIFIER = "com.mystudio.gamename";


    // Sprite file locations
    private static int MAX_BULLET = 1; // initialize number of bullets
    private static int MAX_LARGE = 8; // initialize number of Big asteroids
    private static int MAX_MEDIUM = 14; // initialize number of Medium asteroids
    private static int MAX_SMALL = 25; // initialize number of Small asteroids
    private BackGround bg;
    private Player player;
    private Bullet[] bullets = new Bullet[MAX_BULLET];
    // Used to pool our asteroids
    private Asteroid[] asteroids = new Asteroid[MAX_LARGE + MAX_MEDIUM + MAX_SMALL];
    // any active ones we'll add here to loop through
    private ArrayList<Asteroid> activeAsteroids;
    private Score score = new Score();
    private Lives lives = new Lives();
    private UI ui;
    private UI ui2;
    private Sounds myGameSounds;
    public Bullet speed; // speed of bullet
    public Player rotationSpeed; // speed of spaceship
    int s; // keep score
    int level=1; // number of level
    int c = 1;
    String intruction ;
//    Thread.sleep(7000);

    @Override
    public void initialise() {
        bg = new BackGround(ImageFilePaths.BACKGROUND); // use for declare background
        ui = new UI(intruction);



        ui = new UI(lives.getRemainingLives(), score.getScore(), level);
        player = new Player();
        initialiseAsteroids();
        initialiseBullets();
        myGameSounds = new Sounds();

        myGameSounds.back.play(); // background sound
    }

    private void initialiseBullets() {
        for (int i = 0; i < MAX_BULLET; i++) {
            bullets[i] = new Bullet(ImageFilePaths.LASER_BLUE); // Bullet's picture
        }
    }

    private void initialiseAsteroids() {
        activeAsteroids = new ArrayList<Asteroid>();

        int large = 0;
        for (; large < MAX_LARGE; large++) {
            asteroids[large] = new Asteroid(Asteroid.SIZE_LARGE_ASTEROID, true, 44);
            activeAsteroids.add(asteroids[large]);
        }

        int medium = 0;
        for (; medium < MAX_MEDIUM; medium++) {
            asteroids[large + medium] = new Asteroid(Asteroid.SIZE_MEDIUM_ASTEROID, false, 22);
        }

        int small = 0;
        for (; small < MAX_SMALL; small++) {
            asteroids[large + medium + small] =
                    new Asteroid(Asteroid.SIZE_SMALL_ASTEROID, false, 11);
        }
    }

    @Override
    public void update(float delta) {
        player.update(delta);
        activateBullet();

        for (Asteroid asteroid : activeAsteroids) {
            asteroid.update(delta);
        }

        for (int i = 0; i < MAX_BULLET; i++) {
            if (bullets[i].isActive()) {
                bullets[i].update(delta);
            }
        }

        if (player.isAlive()) {
            checkCollisions();
        } else {
            // flash
            for (Asteroid asteroid : activeAsteroids) {
                asteroid.setRandomPosition();
            }
            player.init();
        }
    }

    public void activateBullet() {
        if (!Utility.shootButtonPressed()) {
            return;
        }
        for (int i = 0; i < MAX_BULLET; i++) {
            if (bullets[i].isActive()) {
                continue;
            }
            bullets[i].init(player.getPosition(),
                    player.getFacingDirection(),
                    player.getAngle()
            );
            myGameSounds.shoot.play();
            break;
        }
    }

    public void checkCollisions() {
        checkBulletAsteroidCollisions();
        checkAsteroidPlayerCollisions();
    }

    private void checkBulletAsteroidCollisions() {
        for (int i = 0; i < MAX_BULLET; i++) {
            if (!bullets[i].isActive()) {
                continue;
            }

            for (Asteroid asteroid : activeAsteroids) {
                if (!bullets[i].getCollisionBox().intersects(asteroid.getCollisionCircle())) {
                    continue;
                }
                bullets[i].setActive(false);
                myGameSounds.explode.play();
                splitAsteroid(asteroid);
                break;
            }
        } // end for loop
    } // end checkBulletAsteroidCollisions

    private void checkAsteroidPlayerCollisions() {
        for (Asteroid asteroid : activeAsteroids) {
            if (!asteroid.getCollisionCircle().intersects(player.getPlayerCollisionCircle())) {
                continue;
            }
            player.setActive(false);
            lives.removeLife();
            speed.SPEED -= 1; // decrease speed of bullet.
            rotationSpeed.rotationSpeed -= 10.0f; // decrease speed of rotationSpeed spaceship.
            score.changeScoreByAmount(-10); // Minus 10 points
            // Add Game over
            if(lives.getRemainingLives() == 0){
                System.out.println("Game Over!");
                // Game over picture
                bg = new BackGround(ImageFilePaths.GAMEOVER);
                myGameSounds.back.stop();
                myGameSounds.lose.play();
            }
            myGameSounds.con.play();
//            myGameSounds.death.play();
            ui.set(lives.getRemainingLives(),score.getScore(),level);
        }
    }

    private void splitAsteroid(Asteroid asteroid) {
        if (asteroid.getSize() == Asteroid.SIZE_LARGE_ASTEROID) {
            placeAsteroid(MAX_LARGE, MAX_MEDIUM + MAX_LARGE,
                    asteroid.getCollisionCircle().getX(),
                    asteroid.getCollisionCircle().getY()
            );
            score.changeScoreByAmount(10); // Big size
        } else if (asteroid.getSize() == Asteroid.SIZE_MEDIUM_ASTEROID) {
            placeAsteroid(MAX_MEDIUM + MAX_LARGE,
                    MAX_SMALL + MAX_MEDIUM + MAX_LARGE,
                    asteroid.getCollisionCircle().getX(),
                    asteroid.getCollisionCircle().getY()
            );
            score.changeScoreByAmount(20); // Medium size
        } else {
            score.changeScoreByAmount(80); // Small size
        }
        activeAsteroids.remove(asteroid);

        // If score more than 300 points, then life and level are increase 1,
        // and score will reset to 0 point.
        // Player can win when hit level 5.
        if(score.getScore() > 300){
            lives.increaseLife();
            score.setScore(0);
            level++;
            speed.SPEED += 10; // increase speed of bullet.
            rotationSpeed.rotationSpeed += 20.0f; // increase speed of rotationSpeed spaceship.
            if(level == 5) {

                System.out.println("YOU WIN!");
                // Game Win picture
                myGameSounds.back.stop();  // stop background sound
                bg = new BackGround(ImageFilePaths.WIN);
            }
        }

        ui.set(lives.getRemainingLives(),score.getScore(),level);
        asteroid.setActive(false);

    }

    public void placeAsteroid(int from, int to, float posX, float posY) {
        int numberOfAsteroidsSpawned = 0;
        for (int i = from; i < to; i++) {
            if (asteroids[i].isActive()) {
                continue;
            }
            asteroids[i].setPositionImmediately(posX, posY);
            activeAsteroids.add(asteroids[i]);
            asteroids[i].setActive(true);
            numberOfAsteroidsSpawned++;

            if (numberOfAsteroidsSpawned > 1) {
                break;
            }

        }
    }

    @Override
    public void render(Graphics g) {

        bg.render(g);
        if(c<301){

            ui.ins("Instructions \n" +
                    "Aunt Parina is 10 points.\n" +
                    "Uncle Tu is 20 points.\n" +
                    "Big Pawit is 80 points.");
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            c ++;
        }else{
            c=303; // for stop counting
            ui.ins("");
        }

        for (Asteroid asteroid : activeAsteroids) {
            asteroid.render(g);
        }
        for (int i = 0; i < MAX_BULLET; i++) {
            if (!bullets[i].isActive()) {
                continue;
            }
            bullets[i].render(g);
        }
        player.render(g);

        ui.render(g);

    }
}
