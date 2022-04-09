int currentPlayer = 1;
float xVec1,xVec2,yVec1,yVec2;
int oneScore,twoScore = 0;
void draw2p() {
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
    boo.amp(0.2);
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
    ballBounce.amp(0.4);
    ballBounce.play();
  };
  //collision
  if (ballX > targetX - targetWidth/2 && ballX < targetX+targetWidth/2 && ballY > targetY - targetHeight/2 && ballY < targetY + targetHeight/2) { //score
    if(currentPlayer == 2){
      targetY = int(random(150, 400));
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
    cheer.amp(0.2);
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
