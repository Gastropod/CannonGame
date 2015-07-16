package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.Grass;
import com.kilobolt.gameobjects.Pipe;
import com.kilobolt.gameobjects.Portal;
import com.kilobolt.gameobjects.Cannon;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbhelpers.AssetLoader;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;

	private int midPointY;
	private int gameHeight;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	private Portal portal1;
	private Cannon startCannon;
	
	private Rectangle portalUp, portalDown;

	// Game Assets
	private TextureRegion bg, cannon, grass;
	private Animation birdAnimation, portalAnimationDown, portalAnimationUp;
	private TextureRegion birdMid;
	private TextureRegion skullUp, skullDown, bar;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		myWorld = world;

		this.gameHeight = gameHeight;
		this.midPointY = midPointY;

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
	}

	private void initGameObjects() {
		bird = myWorld.getBird();
		scroller = myWorld.getScroller();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
		startCannon = scroller.getStartCannon();
		pipe1 = scroller.getPipe1();
		pipe2 = scroller.getPipe2();
		pipe3 = scroller.getPipe3();
		portal1 = scroller.getPortal1();
	}

	private void initAssets() {
		bg = AssetLoader.bg;
		cannon = AssetLoader.cannon;
		grass = AssetLoader.grass;
		birdAnimation = AssetLoader.birdAnimation;
		birdMid = AssetLoader.bird;
		portalAnimationDown = AssetLoader.portalAnimationDown;
		portalAnimationUp = AssetLoader.portalAnimationUp;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		bar = AssetLoader.bar;
	}

	private void drawGrass() {
		// Draw the grass
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawCannon() {

		batcher.draw(cannon, startCannon.getX(), startCannon.getY(), startCannon.getWidth(), startCannon.getHeight());
	}

	private void drawSkulls() {
		// Temporary code! Sorry about the mess :)
		// We will fix this when we finish the Pipe class.

		batcher.draw(skullUp, pipe1.getX() - 1, pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe1.getX() - 1, pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

		batcher.draw(skullUp, pipe2.getX() - 1, pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe2.getX() - 1, pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

		batcher.draw(skullUp, pipe3.getX() - 1, pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe3.getX() - 1, pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
	}

	private void drawPipes() {
		// Temporary code! Sorry about the mess :)
		// We will fix this when we finish the Pipe class.
		batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(), pipe1.getHeight());
		batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45, pipe1.getWidth(),
				midPointY + 66 - (pipe1.getHeight() + 45));

		batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(), pipe2.getHeight());
		batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45, pipe2.getWidth(),
				midPointY + 66 - (pipe2.getHeight() + 45));

		batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(), pipe3.getHeight());
		batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45, pipe3.getWidth(),
				midPointY + 66 - (pipe3.getHeight() + 45));
	}

	public void render(float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background color
		shapeRenderer.setColor(153 / 255.0f, 217 / 255.0f, 234 / 255.0f, 1);
		shapeRenderer.rect(0, 0, 136, midPointY + 66);

		// Draw Grass
		shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 66, 136, 11);

		// Draw Dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 77, 136, 52);
		
		portalDown = portal1.getPortalDown();
		portalUp = portal1.getPortalUp();
		
		shapeRenderer.setColor(147 / 255.0f, 1 / 255.0f, 1 / 255.0f, 1);
		shapeRenderer.rect(portalDown.getX(), portalDown.getY(), portalDown.getWidth(), portalDown.getHeight());
		shapeRenderer.rect(portalUp.getX(), portalUp.getY(), portalUp.getWidth(), portalUp.getHeight());
		
		shapeRenderer.end();

		batcher.begin();
		batcher.disableBlending();
		batcher.draw(bg, 0, 0, 136, 138);
		// batcher.draw(bg, 0, midPointY + 23, 136, 43);

		// 1. Draw Grass
		drawGrass();

		// 2. Draw Pipes
		drawPipes();
		batcher.enableBlending();

		// 3. Draw Skulls (requires transparency)
		drawSkulls();

		// 4 Draw Cannon
		drawCannon();

		batcher.draw(portalAnimationDown.getKeyFrame(runTime), portal1.getX(), portal1.getY(), portal1.getWidth(),
				portal1.getHeight());
		batcher.draw(portalAnimationUp.getKeyFrame(runTime), portal1.getX(), portal1.getY()-150, portal1.getWidth(),
				portal1.getHeight());
		

		if (myWorld.isFire()) {

		} else {

			if (bird.shouldntFlap()) {
				batcher.draw(birdMid, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
						bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

			} else {

				batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
			}
		}

		// TEMPORARY CODE! We will fix this section later:

		if (myWorld.isReady()) {
			// Draw shadow first
			AssetLoader.shadow.draw(batcher, "Ready?", (136 / 2) - (42), 76);
			// Draw text
			AssetLoader.font.draw(batcher, "Ready?", (136 / 2) - (42 - 1), 75);
		} else {

			if (myWorld.isGameOver() || myWorld.isHighScore()) {

				if (myWorld.isGameOver()) {
					AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
					AssetLoader.font.draw(batcher, "Game Over", 24, 55);

					AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
					AssetLoader.font.draw(batcher, "High Score:", 22, 105);

					String highScore = AssetLoader.getHighScore() + "";

					// Draw shadow first
					AssetLoader.shadow.draw(batcher, highScore, (136 / 2) - (3 * highScore.length()), 128);
					// Draw text
					AssetLoader.font.draw(batcher, highScore, (136 / 2) - (3 * highScore.length() - 1), 127);
				} else {
					AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
					AssetLoader.font.draw(batcher, "High Score!", 18, 55);
				}

				AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
				AssetLoader.font.draw(batcher, "Try again?", 24, 75);

				// Convert integer into String
				String score = myWorld.getScore() + "";

				// Draw shadow first
				AssetLoader.shadow.draw(batcher, score, (136 / 2) - (3 * score.length()), 12);
				// Draw text
				AssetLoader.font.draw(batcher, score, (136 / 2) - (3 * score.length() - 1), 11);

			}

			// Convert integer into String
			String score = myWorld.getScore() + "";

			// Draw shadow first
			AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2) - (3 * score.length()), 12);
			// Draw text
			AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2) - (3 * score.length() - 1), 11);

		}

		batcher.end();

	}
}