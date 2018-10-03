package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.controllers.JoystickControl;
import com.mygdx.game.stages.PlayerHud;

import box2dLight.RayHandler;

import static com.mygdx.game.DarkAndDarkness.PPM;
import static com.mygdx.game.DarkAndDarkness.V_HEIGHT;
import static com.mygdx.game.DarkAndDarkness.V_WIDTH;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Game game;
    private World world;
    private PlayerHud hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Box2DDebugRenderer box2DDebugRenderer;
    private RayHandler rayHandler;
    private JoystickControl moveJoystick;
    private JoystickControl actionJoystick;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        box2DDebugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        gameCam = new OrthographicCamera();
        gamePort = new FillViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gameCam);
        world = new World(new Vector2(0, -10), true);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(Color.BLACK);
        rayHandler.setAmbientLight(0.8f);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(3);
        hud = new PlayerHud(batch);
        initJoysticksControlls();
        Gdx.input.setInputProcessor(hud.getStage());


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level_1-1.tmx");
        map = new TiledMap();
/*
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("some_layer_name");
        Cell cell = new Cell();
        TiledMapTileSet tileSet = map.getTileSets().getTileSet("tileset_name");
        cell.setTile(tileSet.getTile(42)); *//* or some other id, identifying the tile *//*
        layer.setCell(32, 64, cell); // 32 and 64 being x and y coordinates*/
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);


    }

    private void initJoysticksControlls() {
        moveJoystick = new JoystickControl(50, 50);
        actionJoystick = new JoystickControl(Gdx.app.getGraphics().getWidth() - JoystickControl.JOYSTICK_WIDTH - 50, 50);
        moveJoystick.addTouchPad(hud.getStage());
        actionJoystick.addTouchPad(hud.getStage());
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        box2DDebugRenderer.render(world, gameCam.combined);

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        batch.end();

        rayHandler.setCombinedMatrix(gameCam);
        rayHandler.render();

        batch.setProjectionMatrix(hud.getStage().getCamera().combined);


        moveJoystick.checkTouchPad();

        hud.getStage().act(delta);
        hud.getStage().draw();
    }

    private void update(float delta) {

       /* player.update(dt);
        light.setPosition(player.body.getPosition().x, player.body.getPosition().y + 0.2f);
        light1.setPosition(player.body.getPosition().x - 0.2f, player.body.getPosition().y);
        light2.setPosition(player.body.getPosition().x + 0.2f, player.body.getPosition().y);*/
        rayHandler.update();
      /*  if (jumpedFromRamp && !worldContactListener.isOnRamp()) {
            jumpedFromRamp = false;
        }*/

        world.step(1 / 60f, 6, 2);
        //   sweepDeadBodies();
        // if (player.body.getPosition().x >= gamePort.getWorldWidth() / 2) {
        //      gameCam.position.x = player.body.getPosition().x;
        //  }

        gameCam.update();


        renderer.setView(gameCam);
    }

    @Override
    public void resize(int width, int height) {
        if(gamePort != null) {
            gamePort.update(width, height);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        hud.getStage().dispose();
    }
}
