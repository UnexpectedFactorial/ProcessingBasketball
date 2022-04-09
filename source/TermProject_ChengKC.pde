import processing.sound.*;

PImage menuBG;

PrintWriter oneOutput, twoOutput;

SoundFile bgMusic, ambient, buttonSelect, ballBounce, cheer, boo;

//fonts
PFont titleFont, scoreFont;


Spectator[] s = new Spectator[4];
ArrayList<Cloud> c = new ArrayList<Cloud>();
Player p;
boolean falling;
boolean shot;
float ballX, ballY, xVec, yVec, grav, xVecDef, yVecDef, markerX, markerY;
int score;

color test;
int buttonX, buttonY;
int buttonSize = 90;
int targetX, targetY, targetWidth, targetHeight;
boolean overShoot = false;

int gameState, soundCheck, prevGameState;




void setup() {
  gameState = 1;

  size(1280, 900);
  frameRate(60);

  buttonX = 1200;
  buttonY = 770;

  targetX = 1050;
  targetY = 200;
  targetWidth = 15;
  targetHeight = 100;

  p = new Player();

  //var for the ball
  boolean shot = false;
  ballX = 120;
  ballY = 440;
  xVecDef = 25;
  markerX = 25;
  yVecDef = 25;
  markerY = 25;
  xVec = 25;
  yVec = 25;
  grav = 1;
  falling = false;
  shot = false;
  score = 0;


  //classes
  for (int i = 1; i < s.length; i++) {
    if (i == 2) {
      s[i]= new Ref();
    } else {
      s[i] = new Spectator();
    };
  };

  for (int i = 0; i < 25; i++) {

    int chance = int(random(1, 10));
    if (chance < 8) {
      Cloud cloudGener = new Cloud();
      c.add(cloudGener);
    } else {
      Cloud cloudGener = new StormCloud();
      c.add(cloudGener);
    };
  };
  //sounds
  bgMusic = new SoundFile(this, "VoxelRevolution.mp3");
  ambient = new SoundFile(this, "ambiance.wav");
  buttonSelect = new SoundFile(this, "button04b.mp3");
  ballBounce = new SoundFile(this, "bounce.wav");
  cheer = new SoundFile(this, "yay.wav");
  boo = new SoundFile(this, "aww.wav");

  //sceneBackgrounds
  menuBG = loadImage("background.jpg");
  menuBG.resize(1280, 900);

  //fonts
  titleFont = createFont("PermanentMarker-Regular.ttf", 64);
  scoreFont = createFont("PressStart2P-Regular.ttf", 64);

  //file i/o
  oneOutput = createWriter("1pLogs.txt");
  twoOutput = createWriter("2pLogs.txt");
};

void draw() {
  update(mouseX, mouseY);

  switch(gameState) {
  case 1: //main menu
    drawMenu();
    break;

  case 2: //1p
    drawGame();
    break;

  case 3://2p
    draw2p();
    break;
  case 4://instructions
    drawInstruct();
    break;
  case 5://pause menu
    pause();
    break;
  };
  if (soundCheck == 0) {
    switch(gameState) {
    case 1: //menu
      ambient.stop();
      ambient.removeFromCache();
      if (!bgMusic.isPlaying()) {
        bgMusic.amp(0.2);
        bgMusic.loop();
      };
      soundCheck++;
      break;
    case 2://free throw
      bgMusic.stop();
      bgMusic.removeFromCache();
      ambient.amp(0.2);
      ambient.loop();
      soundCheck++;
      break;
    case 3://two player
      bgMusic.stop();
      bgMusic.removeFromCache();
      ambient.amp(0.2);
      ambient.loop();
      soundCheck++;
      break;
    case 4://instructions

      break;
    case 5://pause
      bgMusic.stop();
      bgMusic.removeFromCache();
      ambient.stop();
      ambient.removeFromCache();
      soundCheck++;
      break;
    };
  };
};
void mousePressed() {

  switch(gameState) {
  case 1:
    if (select1p) {
      buttonSelect.play();
      gameState = 2;
      soundCheck = 0;
    } else if (select2p) {
      buttonSelect.play();
      gameState = 3;
      soundCheck = 0;
    } else if (selectInst) {
      buttonSelect.play();
      gameState = 4;
      soundCheck = 0;
    };
    break;
  case 2:
    if (overShoot) {
      shot = true;
    };
    if (pause) {
      prevGameState = 2;
      buttonSelect.play();
      gameState = 5;
      soundCheck = 0;
      select1p = false;
    };
    break;
  case 3:
    if (overShoot) {
      shot = true;
    };
    if (pause) {
      prevGameState = 3;
      buttonSelect.play();
      gameState = 5;
      soundCheck = 0;
      select2p = false;
    };
    break;
  case 4:
    if (instReturn) {
      buttonSelect.play();
      gameState = 1;
      soundCheck = 0;
    };
    break;
  case 5:
    if (gotoMenu) {
      buttonSelect.play();
      gameState = 1;
      soundCheck = 0;
      //reset game when player leaves
      ballX = 120;
      ballY = 440;
      xVec = xVecDef;
      markerX = xVecDef;
      yVec = yVecDef;
      markerY = yVecDef;
      grav = 1;
      shot = false;
      falling = false;
      score = 0;
      targetY = 200;
      currentPlayer = 1;
      xVec1 = xVecDef;
      xVec2 = xVecDef;
      yVec1 = yVecDef;
      yVec2 = yVecDef;
      oneScore=0;
      twoScore = 0;
    } else if (returnTo) {
      buttonSelect.play();
      gameState = prevGameState;
      soundCheck = 0;
    };
    break;
  };
};

void exit() {
  oneOutput.close();
  twoOutput.close();
  ambient.removeFromCache();
  bgMusic.removeFromCache();
  buttonSelect.removeFromCache();
  ballBounce.removeFromCache();
  boo.removeFromCache();
  cheer.removeFromCache();
  super.exit();
}
