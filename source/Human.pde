class Human{
  float tempx;
  float tempy;
  color skin = color(243, 221, 162);
  color shirt = color(216, 38, 24);
  color pants = color(25, 99, 231);
  
  float headWidth = 45;
  float headHeight = 45;
  float bodyHeight = 75;
  float bodyWidth = 45;
  float legHeight = 70;
  float legWidth = 15;
  float armWidth = 15;
  float armHeight = 60;
  
  void movement(int x, int y){
    tempx = x+random(-1,1);
    tempy = y+random(-4,4);
  };
  
  void updateColor(int x){
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
    float rotationalSpeed = 0.9;
  
  
  void display(){
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
  color shirt = color(255);
  color pants = color(0);
  
  void display(){
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
