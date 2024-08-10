package io.itch.leftsock.apelsin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class MainScreen implements AScreen, InputProcessor {
    private Stage stage;
    Skin skin;
    private TextButton play;
    private TextButton help;
    private TextField seed;
    private Dialog dialog;
    private Table table;

    @Override
    public void create() {
        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label name = new Label("Apelsin", skin);
        name.setAlignment(Align.center);
        play = new TextButton(">",skin);
        seed = new TextField("",skin);
        help = new TextButton("Help",skin);
        play.add(seed);
        table = new Table();
        stage.addActor(table);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setPosition(0, 0);
        table.align(Align.center);
        table.add(name);
        table.row();
        table.add(play);
        table.row();
        table.add(help);

        dialog = new Dialog("Help",skin);
        dialog.text(new Label("Hello there! Apelsin is a real-time strategy. \n" +
                "You play as a head of a tribe. You have to move your tribe \n" +
                "to orange cells (by click). If a unit is on the orange cell, it \n" +
                "will collect oranges. Sometimes your units wanna eat, so if you have \n" +
                "no oranges, they will die of hunger. Also there are some more tribes. \n" +
                "They are ruled by bots and their goal is the same. Units always avoid  \n" +
                "units from another tribe. The last tribe wins! \n\n" +
                "To play write a seed right to the '>' button, then \n" +
                "press the '>' button",skin));

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.8f,0.37f,0.28f,1f);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return stage.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return stage.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return stage.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        dialog.hide();
        if(table.isVisible()) {
            if (Apelsin.viewport.getCamera().unproject(new Vector3(screenX, screenY, 0)).sub(help.getX() + help.getWidth() / 2, help.getY() + help.getHeight() / 2, 0).x < 1 && Math.abs(Apelsin.viewport.getCamera().unproject(new Vector3(screenX, screenY, 0)).sub(help.getX() + help.getWidth() / 2, help.getY() + help.getHeight() / 2, 0).y) < 10) {
                dialog.show(stage);
                table.setVisible(false);
            } else if (Apelsin.viewport.getCamera().unproject(new Vector3(screenX, screenY, 0)).sub(play.getX(), play.getY() + play.getHeight() / 2, 0).x < 1 && Math.abs(Apelsin.viewport.getCamera().unproject(new Vector3(screenX, screenY, 0)).sub(play.getX(), play.getY() + play.getHeight() / 2, 0).y) < 10)
                Apelsin.play(Integer.parseInt(seed.getText()));
        } else
            table.setVisible(true);

        return stage.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
