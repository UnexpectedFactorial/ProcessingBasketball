/* autogenerated by Processing revision 1277 on 2022-04-08 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import processing.sound.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class TermProject_ChengKC extends PApplet {



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

int test;
int buttonX, buttonY;
int buttonSize = 90;
int targetX, targetY, targetWidth, targetHeight;
boolean overShoot = false;

int gameState, soundCheck, prevGameState;




 public void setup() {
  gameState = 1;

  /* size commented out by preprocessor */;
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

    int chance = PApplet.parseInt(random(1, 10));
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

 public void draw() {
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
        bgMusic.amp(0.2f);
        bgMusic.loop();
      };
      soundCheck++;
      break;
    case 2://free throw
      bgMusic.stop();
      bgMusic.removeFromCache();
      ambient.amp(0.2f);
      ambient.loop();
      soundCheck++;
      break;
    case 3://two player
      bgMusic.stop();
      bgMusic.removeFromCache();
      ambient.amp(0.2f);
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
 public void mousePressed() {

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

 public void exit() {
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
class Cloud{
  float x = random(-width,width*2);
  float y = random(0,100);
  float spread = random(15,45);
  float cloudSpeed = 1;
  float cloudWidth = 65;
  float cloudHeight = 40;
   public void windyMovement(){
    x = x+cloudSpeed;
    
    if (x >width+cloudWidth){
      x = 0-cloudWidth;
    };
  };
   public void cloudShape(){
    fill(255);
    ellipse(x,y,cloudWidth,cloudHeight);
    ellipse(x+spread*0.3f,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.6f,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.2f,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.7f,y+spread,cloudWidth,cloudHeight);
    ellipse(x+spread*0.5f,y-spread,cloudWidth,cloudHeight);
  };
  
};

class StormCloud extends Cloud{
  
   public void cloudShape(){
    fill(150);
    ellipse(x,y,cloudWidth,cloudHeight);
    ellipse(x+spread*0.3f,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.6f,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.2f,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.7f,y+spread,cloudWidth,cloudHeight);
    ellipse(x+spread*0.5f,y-spread,cloudWidth,cloudHeight);
  };
};
class Human{
  float tempx;
  float tempy;
  int skin = color(243, 221, 162);
  int shirt = color(216, 38, 24);
  int pants = color(25, 99, 231);
  
  float headWidth = 45;
  float headHeight = 45;
  float bodyHeight = 75;
  float bodyWidth = 45;
  float legHeight = 70;
  float legWidth = 15;
  float armWidth = 15;
  float armHeight = 60;
  
   public void movement(int x, int y){
    tempx = x+random(-1,1);
    tempy = y+random(-4,4);
  };
  
   public void updateColor(int x){
    switch(x){
      case 1:
      shirt = color(216, 38, 24);
      break;
      case 2:
      shirt = color(144, 9, 150);
      break;
      case 3:
      shirt = color(38, 148, 44);
      break;
      
    };
  };
  
};

class Spectator extends Human{
    float r = 90;
    int limit = 160;
    float rotationalSpeed = 0.9f;
  
  
   public void display(){
    fill(skin);
    ellipse(tempx,tempy,headWidth,headHeight);
    rectMode(CENTER);
    fill(shirt);
    rect(tempx,tempy+headHeight+10,bodyWidth,bodyHeight);
    fill(pants);
    rect(tempx-legWidth,tempy+headHeight+10+bodyHeight,legWidth,legHeight);
    rect(tempx+legWidth,tempy+headHeight+10+bodyHeight,legWidth,legHeight);
    
    fill(shirt); //rotating arms
    
    r+= rotationalSpeed;
    if(r > limit || r < 90){
      rotationalSpeed = -rotationalSpeed;
      r +=rotationalSpeed;
    };
    pushMatrix();
      translate(tempx-armWidth-10,tempy+headHeight-15);
      rotate(radians(r));
      rect(0,0,armWidth,armHeight+15);
    popMatrix();
    
    pushMatrix();
      translate(tempx+bodyWidth-armWidth-10,tempy+headHeight-15);
      rotate(radians(-r));
      rect(0,0,armWidth,armHeight+15);
    popMatrix();
    
    rectMode(CORNER);
  };
};


class Ref extends Spectator{
  int shirt = color(255);
  int pants = color(0);
  
   public void display(){
    fill(skin);
    ellipse(tempx,tempy,headWidth,headHeight);
    rectMode(CENTER);
    fill(shirt);
    rect(tempx,tempy+headHeight+10,bodyWidth,bodyHeight);
    rect(tempx-armWidth-17,tempy+headHeight+5,armWidth,armHeight);
    rect(tempx+bodyWidth-armWidth+2,tempy+headHeight+5,armWidth,armHeight);
    fill(pants);
    rect(tempx-legWidth,tempy+headHeight+10+bodyHeight,legWidth,legHeight);
    rect(tempx+legWidth,tempy+headHeight+10+bodyHeight,legWidth,legHeight);
    
    rectMode(CORNER);
  };
};
int playerShirt = color(245, 235, 90);

class Player extends Spectator{
  int xPos;
  int yPos;
   public void setPos(int x, int y){
    xPos = x;
    yPos = y;
  };
   public void makeBody(){
    ellipseMode(CENTER);
    rectMode(CENTER);
    fill(skin);
    ellipse(xPos,yPos,45,45);
    fill(playerShirt);
    rect(xPos,yPos+bodyHeight-15,bodyWidth,bodyHeight);
    fill(pants);
    rect(xPos,yPos+bodyHeight+60,bodyWidth-10,bodyHeight+20);
    rectMode(CORNER);
    
  };
   public void changeColor(int choice){
    switch(choice){
      case 1:
        playerShirt = color(245, 235, 90);
        break;
      case 2:
        playerShirt = color(23, 138, 48);
        break;
      case 3:
        playerShirt = color(238, 66, 66);
        break;
      case 4:
        playerShirt = color(66, 105, 238);
        break;
    };
    
  };
  
};
 public boolean checkRectButton(int x, int y, int width, int height) {
  if (mouseX >= x && mouseX <= x+width && mouseY>= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
};
int currentPlayer = 1;
float xVec1,xVec2,yVec1,yVec2;
int oneScore,twoScore = 0;
 public void draw2p() {
  rectMode(CORNER);
  textFont(titleFont);
  background(124, 170, 240);

  fill(7, 90, 19);
  noStroke();
  rect(0, 500, width, height);
  fill(130, 130, 130);
  rect(100, 550, 1000, 70);
  fill(255);
  rectMode(CENTER);
  rect(targetX, targetY, targetWidth, targetHeight);
  rectMode(CORNER);



  for (int i = 0; i < c.size(); i++) {
    Cloud cGen = c.get(i);
    cGen.cloudShape();
    cGen.windyMovement();
  };

  for (int i = 1; i < s.length; i++) {
    s[i].movement(i*250, 350);
    s[i].display();
    //s[i].updateColor(2);
  };

  //ball physics
  if (ballX > width + 35 || ballY > height + 35) { //reset if it falls out of bounds
    if(currentPlayer == 2){
        targetY = 200;
      };
    switch(currentPlayer){

       case 1:
        twoOutput.println("PLAYER 1: FAIL | XPOWER: "+ markerX +" | YPOWER: "+markerY);
        twoOutput.flush();
        currentPlayer = 2;
        break;
       case 2:
         twoOutput.println("PLAYER 2: FAIL | XPOWER: "+ markerX +" | YPOWER: "+markerY);
         twoOutput.flush();
         currentPlayer = 1;
         break;
    };
    ballX = 120;
    ballY = 440;
    xVec = xVecDef;
    markerX = xVecDef;
    yVec = yVecDef;
    markerY = yVecDef;
    grav = 1;
    shot = false;
    falling = false;
    //score = 0;
    boo.amp(0.2f);
    boo.play();

    //oneOutput.close();
  };
  if (xVec > 35) {//x speed cap
    xVec = 35;
    markerX = 35;
  };
  if (yVec>35) {//y speed cap
    yVec=35;
    markerY = 35;
  };
  if (shot == true || falling == true) {
    falling = true;
    ballX += xVec;
    yVec -= grav;
    ballY -= yVec;
  };
  if (ballY >= 585 && ballX <= 1100) {
    yVec = -yVec;
    ballBounce.amp(0.4f);
    ballBounce.play();
  };
  //collision
  if (ballX > targetX - targetWidth/2 && ballX < targetX+targetWidth/2 && ballY > targetY - targetHeight/2 && ballY < targetY + targetHeight/2) { //score
    if(currentPlayer == 2){
      targetY = PApplet.parseInt(random(150, 400));
    };
    
    switch(currentPlayer){
       case 1:
        twoOutput.println("PLAYER 1: SUCCESS | XPOWER: "+ markerX +" | YPOWER: "+markerY);
        twoOutput.flush();
        currentPlayer = 2;
        oneScore++;
        break;
       case 2:
         twoOutput.println("PLAYER 2: SUCCESS | XPOWER: "+ markerX +" | YPOWER: "+markerY);
         twoOutput.flush();
         currentPlayer = 1;
         twoScore++;
         break;
    };
    ballX = 120;
    ballY = 440;
    xVec = random(1, 35);
    markerX = xVec;
    yVec = random(1, 35);
    markerY = yVec;
    grav = 1;
    shot = false;
    falling = false;
    cheer.amp(0.2f);
    cheer.play();
    
    
  };



  fill(239, 48, 48);
  ellipseMode(CENTER);
  ellipse(buttonX, buttonY, buttonSize, buttonSize);

  p.setPos(100, 400);
  if(currentPlayer == 1){
    p.updateColor(3);
  } 
  else if(currentPlayer == 2){
    p.updateColor(4);
  };
  p.makeBody();

  stroke(0);
  strokeWeight(15);
  line(120, 440, 120+markerX*2, 440-markerY*2);
  noStroke();

  fill(247, 96, 16);
  ellipse(ballX, ballY, 35, 35);

  fill(151, 9, 9);
  rect (1100, 40, 150, 50);
  fill(255);
  textAlign(LEFT);
  textSize(45);
  text("menu", 1120, 75);

  //keyboard interaction
  if (keyPressed) {
    if (key == 'r'|| key == 'R') {
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
    };
    if (shot == false) { //prevent player from using the force on the ball
      if (key == CODED) {
        if (keyCode == UP) {
          yVec++;
          markerY++;
        };
        if (keyCode == DOWN) {
          if (yVec>0) {
            yVec--;
            markerY--;
          };
        };
        if (keyCode == LEFT) {
          if (xVec>0) {
            xVec--;
            markerX--;
          };
        };
        if (keyCode == RIGHT) {
          xVec++;
          markerX++;
        };
      };
    };
  };
  
  //ui
  fill(130);
  rectMode(CENTER);
  rect(width/2,0,850,100);
  fill(241, 239, 173);
  rect(width/2,0,840,90);
  textAlign(CENTER);
  fill(0);
  textFont(scoreFont);
  textSize(20);
  text("Player 1: "+oneScore+" | Player 2: " + twoScore+" | Current:"+currentPlayer,width/2,40);
  
  
};
boolean pause = false;

 public void drawGame() {
  background(124, 170, 240);


  fill(7, 90, 19);
  noStroke();
  rect(0, 500, width, height);
  fill(130, 130, 130);
  rect(100, 550, 1000, 70);
  fill(255);
  rectMode(CENTER);
  rect(targetX, targetY, targetWidth, targetHeight);
  rectMode(CORNER);



  for (int i = 0; i < c.size(); i++) {
    Cloud cGen = c.get(i);
    cGen.cloudShape();
    cGen.windyMovement();
  };

  for (int i = 1; i < s.length; i++) {
    s[i].movement(i*250, 350);
    s[i].display();
    //s[i].updateColor(2);
  };

  //ball physics
  if (ballX > width + 35 || ballY > height + 35) { //reset if it falls out of bounds
    oneOutput.println("FAIL | XPOWER: "+ markerX +" | YPOWER: "+markerY);
    oneOutput.flush();
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
    boo.amp(0.2f);
    boo.play();
    
    //oneOutput.close();
  };
  if (xVec > 35) {//x speed cap
    xVec = 35;
    markerX = 35;
  };
  if (yVec>35) {//y speed cap
    yVec=35;
    markerY = 35;
  };
  if (shot == true || falling == true) {
    falling = true;
    ballX += xVec;
    yVec -= grav;
    ballY -= yVec;
  };
  if (ballY >= 585 && ballX <= 1100) {
    yVec = -yVec;
    ballBounce.amp(0.4f);
    ballBounce.play();
  };
  //collision
  if (ballX > targetX - targetWidth/2 && ballX < targetX+targetWidth/2 && ballY > targetY - targetHeight/2 && ballY < targetY + targetHeight/2) { //score
    oneOutput.println("SUCCESS | XPOWER: "+ markerX +" | YPOWER: "+markerY);
    oneOutput.flush();
    score++;
    ballX = 120;
    ballY = 440;
    xVec = random(1, 35);
    markerX = xVec;
    yVec = random(1, 35);
    markerY = yVec;
    grav = 1;
    shot = false;
    falling = false;
    targetY = PApplet.parseInt(random(150, 400));
    cheer.amp(0.2f);
    cheer.play();
  };



  fill(239, 48, 48);
  ellipseMode(CENTER);
  ellipse(buttonX, buttonY, buttonSize, buttonSize);

  p.setPos(100, 400);
  p.makeBody();

  stroke(0);
  strokeWeight(15);
  line(120, 440, 120+markerX*2, 440-markerY*2);
  noStroke();

  fill(247, 96, 16);
  ellipse(ballX, ballY, 35, 35);
  
  fill(151, 9, 9);
  rect (1100,40,150,50);
  fill(255);
  textAlign(LEFT);
  textSize(45);
  text("menu",1120,75);

  //keyboard interaction
  if (keyPressed) {
    if (key == 'r'|| key == 'R') {
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
    };
    if (shot == false) { //prevent player from using the force on the ball
      if (key == CODED) {
        if (keyCode == UP) {
          yVec++;
          markerY++;
        };
        if (keyCode == DOWN) {
          if (yVec>0) {
            yVec--;
            markerY--;
          };
        };
        if (keyCode == LEFT) {
          if (xVec>0) {
            xVec--;
            markerX--;
          };
        };
        if (keyCode == RIGHT) {
          xVec++;
          markerX++;
        };
        
      };
    };
  };
};
boolean instReturn = false;

 public void drawInstruct(){ //state 4
  textFont(titleFont);
  background(menuBG);
  
  rectMode(CENTER);
  textAlign(CENTER);
  
  fill(55);
  rect(width/2,height/2,650,700);
  fill(150);
  rect(width/2,height/2,630,680);
  
  fill(55);
  textSize(55);
  text("Instructions",width/2,160);
  textSize(25);
  text("Press up and down to adjust vertical power",width/2,260);
  text("Press left and right to adjust horizontal power",width/2,360);
  text("Press r if you manage to get the ball stuck",width/2,460);
  
  fill(55);
  rect(width/2,height/2+200,300,150);
  fill(255);
  textSize(55);
  text("Return",width/2,height/2+200);
  
};
int textX = -900;

boolean select1p = false;
boolean select2p = false;
boolean selectInst = false;
 public void drawMenu(){
 
  background(menuBG);
  fill(255);
  textSize(50);
  textAlign(CORNER);
  textFont(titleFont);
  text("Voxel Revolution - Kevin Macleod",textX,850);
  
  textAlign(CENTER);
  textSize(80);
  text("Pro(cessing) Basketball 2",width/2,100);
  textX+=2;
  if (textX > width+30){
    textX = -900;
  };
  
  fill(50);
  rectMode(CENTER);
  rect(width/2,height/2,400,650);
  fill(150);
  rect(width/2,height/2,370,620);
  
  fill(50);
  rect(width/2,height/2 - 170,300,150);
  rect(width/2,height/2,300,150);
  rect(width/2,height/2 + 170,300,150);
  
  textSize(45);
  fill(255);
  text("Instructions",width/2,height/2+170);
  text("Free Throw",width/2,height/2 - 170);
  text("Versus Mode",width/2,height/2);
};
boolean returnTo = false;
boolean gotoMenu = false;
 public void pause(){
  textFont(titleFont);
  background(menuBG);
  
  rectMode(CENTER);
  textAlign(CENTER);
  
  fill(55);
  rect(width/2,height/2,850,700);
  fill(150);
  rect(width/2,height/2,830,680);
  
  fill(55);
  textSize(55);
  text("Game Paused",width/2,160);
  fill(55);
  rect(width/2,height/2+200,500,150);
  fill(255);
  textSize(55);
  text("Return to Menu",width/2,height/2+200);
  
  fill(55);
  rect(width/2,height/2,500,150);
  fill(255);
  textSize(55);
  text("Resume",width/2,height/2);
};
 public boolean shootButton(int x, int y, int dia) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if (sqrt(sq(disX) + sq(disY)) < dia/2) {
    return true;
  } else {
    return false;
  }
};
 public void update(int x, int y) {
  switch(gameState) {
  case 1:
    if (checkRectButton(width/2-150, height/2-245, 300, 150)) {
      select1p = true;
    } else if (checkRectButton(width/2-150, height/2-75, 300, 150)) {
      select2p = true;
    } else if (checkRectButton(width/2-150, height/2+95, 300, 150)) {
      selectInst = true;
    } else {
      select1p = false;
      select2p = false;
      selectInst = false;
    };

  case 2:
    if (shootButton(buttonX, buttonY, buttonSize)) {
      overShoot = true;
    } else if (checkRectButton(1100, 40, 150, 50)) {
      pause = true;
    } else {
      pause = false;
      overShoot = false;
    };
  case 3:
    if (shootButton(buttonX, buttonY, buttonSize)) {
      overShoot = true;
    } else if (checkRectButton(1100, 40, 150, 50)) {
      pause = true;
    } else {
      pause = false;
      overShoot = false;
    };

    break;

  case 4:
    if (checkRectButton(width/2-150, height/2+125, 300, 150)) {
      instReturn = true;
    } else {
      instReturn = false;
    };

    break;
  case 5:
    if (checkRectButton(width/2-250, height/2-75, 500, 150)) {
      returnTo = true;
    } else if (checkRectButton(width/2-250, height/2+125, 500, 150)) {
      gotoMenu = true;
    } else {
      returnTo = false;
      gotoMenu = false;
    };

    break;
  };
};


  public void settings() { size(1280, 900); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TermProject_ChengKC" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
