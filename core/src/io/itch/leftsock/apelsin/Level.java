package io.itch.leftsock.apelsin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.text.Format;
import java.util.List;

public class Level implements AScreen {
    Texture visCell;
    Texture orangeCell;
    Texture hiddenCell;
    Texture voidCell;
    SpriteBatch batch;
    Tribe userTribe;

    private List<Cell> cells;
    private List<Unit> units;
    private List<Tribe> tribes;
    private Texture[] unitTextures;
    private Stage stage;
    Skin skin;
    Label orangesCountLabel;
    Label resultLabel;

    public void create () {
        batch = new SpriteBatch();
        visCell = new Texture("cell.png");
        orangeCell = new Texture("orangeCell.png");
        hiddenCell = new Texture("hiddenCell.png");
        voidCell = new Texture("void.png");

        unitTextures = new Texture[]{
                new Texture("unit0.png"),
                new Texture("unit1.png"),
                new Texture("unit2.png"),
        };

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label orangesLabel = new Label("Oranges: ", skin);
        orangesCountLabel = new Label("", skin);
        resultLabel = new Label("", skin);

        Table table = new Table();
        stage.addActor(table);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setPosition(0, 0);
        table.align(Align.top | Align.left);
        table.add(orangesLabel);
        table.add(orangesCountLabel);
        table.add(resultLabel);
    }

    public void render () {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
        orangesCountLabel.setText(userTribe.getOranges());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.setProjectionMatrix(Apelsin.viewport.getCamera().combined);
        batch.begin();
        for (Cell cell:
             cells) {
            batch.draw(cell.isVisible() ? (cell.isOrange()? orangeCell : (cell.isVoid()? voidCell : visCell)) : hiddenCell,cell.getX(),cell.getY());
        }
        for (Tribe tribe:
             tribes) {
            Unit[] units =  tribe.getUnits().toArray(new Unit[]{});
            for (Unit unit :
                   units) {
                if (unit.getState() == State.MOVING || unit.getState() == State.AVOIDING)
                    unit.continueMove();

                unit.update(Gdx.graphics.getDeltaTime());

                batch.draw(unitTextures[unit.getTribe().getId()], unit.getX(), unit.getY());
            }
            if (tribe.getUnits().isEmpty()){
                if (tribe.equals(userTribe)){
                    lose();
                } else {
                    boolean bots = false;
                    for (Tribe otherTribe:
                            tribes)
                        if (!otherTribe.equals(tribe)&&!tribe.equals(userTribe)&&!tribe.getUnits().isEmpty())
                            bots = true;
                    if (!bots)
                        win();
                }
            }
            if (tribe.equals(userTribe)) continue;
            Bot.move(tribe);
        }
        batch.end();
    }

    private void lose() {
        resultLabel.setText("Lose!");
    }

    private void win() {
        resultLabel.setText("Win!");
    }


    public boolean touchUp (int worldX, int worldY, int pointer, int button) {
        if (!resultLabel.textEquals("")) Apelsin.stop();
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        for (Unit unit:
            userTribe.getUnits()) {
            unit.moveTo(worldX, worldY);
        }
        return true;
    }

    public void dispose () {
        batch.dispose();
        visCell.dispose();
        orangeCell.dispose();
        hiddenCell.dispose();
        voidCell.dispose();
        stage.dispose();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public void setTribes(List<Tribe> tribes) {
        this.tribes = tribes;
    }

    public List<Tribe> getTribes() {
        return tribes;
    }
}
