boolean returnTo = false;
boolean gotoMenu = false;
void pause(){
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
