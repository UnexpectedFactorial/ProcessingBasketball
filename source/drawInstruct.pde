boolean instReturn = false;

void drawInstruct(){ //state 4
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
