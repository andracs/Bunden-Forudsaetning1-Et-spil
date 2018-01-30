package com.myname;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Control;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 *
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class BasicGameApp4 extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
    }

    private Entity player;

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getControl(DudeControl.class).moveRight();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getControl(DudeControl.class).moveLeft();
            }
        }, KeyCode.A);
    }

    @Override
    protected void initGame() {
        player = Entities.builder()
                .at(200, 200)
                .with(new DudeControl())
                .buildAndAttach();
    }

    class DudeControl extends Control {

        private int speed = 0;

        private AnimatedTexture texture;
        private AnimationChannel animIdle, animWalk;

        public DudeControl() {
            animIdle = new AnimationChannel("newdude.png", 4, 32, 42, Duration.seconds(1), 1, 1);
            animWalk = new AnimationChannel("newdude.png", 4, 32, 42, Duration.seconds(1), 0, 3);

            texture = new AnimatedTexture(animIdle);
        }

        @Override
        public void onAdded(Entity entity) {
            entity.setView(texture);
        }

        @Override
        public void onUpdate(Entity entity, double tpf) {
            entity.translateX(speed * tpf);

            if (speed == 0) {
                texture.setAnimationChannel(animIdle);
            } else {
                texture.setAnimationChannel(animWalk);

                speed = (int) (speed * 0.9);

                if (FXGLMath.abs(speed) < 1) {
                    speed = 0;
                }
            }
        }

        public void moveRight() {
            speed = 150;

            getEntity().setScaleX(1);
        }

        public void moveLeft() {
            speed = -150;

            getEntity().setScaleX(-1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}