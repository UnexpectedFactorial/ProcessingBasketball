color playerShirt = color(245, 235, 90);

class Player extends Spectator{
  int xPos;
  int yPos;
  void setPos(int x, int y){
    xPos = x;
    yPos = y;
  };
  void makeBody(){
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
  void changeColor(int choice){
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
