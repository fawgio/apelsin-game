package io.itch.leftsock.apelsin;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Apelsin extends ApplicationAdapter implements InputProcessor {

	static Level level;
	static MainScreen mainScreen = new MainScreen();
	public final static float VP_WIDTH = 620;
	public final static float VP_HEIGHT = 480;
	static private OrthographicCamera camera;
	public static Viewport viewport;
	private static boolean playing;

	@Override public void create () {
		camera = new OrthographicCamera(VP_WIDTH,VP_HEIGHT);
		viewport = new FitViewport(VP_WIDTH, VP_HEIGHT, camera);
		viewport.apply();
		camera.translate(VP_WIDTH/2,VP_HEIGHT/2);
		mainScreen.create();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		if (playing)
			level.render();
		else
			mainScreen.render();
	}

	@Override public void resize (int width, int height) {
		viewport.update(width, height);
	}
	
	@Override
	public void dispose () {
		mainScreen.dispose();
		level.dispose();
	}

	public static void play(int seed){
		playing = true;
		level = Randomizer.generate(seed);
		mainScreen.dispose();
	}

	public static void stop() {
		playing = false;
		level.dispose();
		mainScreen.create();
	}

	@Override
	public boolean keyDown(int keycode) {
		return !playing && mainScreen.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return !playing && mainScreen.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		return !playing && mainScreen.keyTyped(character);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button){
		Vector3 mousePos = new Vector3(0,0,0);
		mousePos.set(Gdx.input.getX(), Gdx.input.getY(),0);
		camera.unproject(mousePos);
		return playing ? level.touchUp((int) mousePos.x, (int) mousePos.y, pointer, button) : mainScreen.touchUp((int) mousePos.x, (int) mousePos.y, pointer, button);
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		return ! playing && mainScreen.touchDown(screenX, screenY, pointer, button);
	}

	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
		return true;
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
