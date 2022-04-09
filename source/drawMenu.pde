int textX = -900;

boolean select1p = false;
boolean select2p = false;
boolean selectInst = false;
void drawMenu(){
 
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
