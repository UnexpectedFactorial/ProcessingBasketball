class Cloud{
  float x = random(-width,width*2);
  float y = random(0,100);
  float spread = random(15,45);
  float cloudSpeed = 1;
  float cloudWidth = 65;
  float cloudHeight = 40;
  void windyMovement(){
    x = x+cloudSpeed;
    
    if (x >width+cloudWidth){
      x = 0-cloudWidth;
    };
  };
  void cloudShape(){
    fill(255);
    ellipse(x,y,cloudWidth,cloudHeight);
    ellipse(x+spread*0.3,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.6,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.2,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.7,y+spread,cloudWidth,cloudHeight);
    ellipse(x+spread*0.5,y-spread,cloudWidth,cloudHeight);
  };
  
};

class StormCloud extends Cloud{
  
  void cloudShape(){
    fill(150);
    ellipse(x,y,cloudWidth,cloudHeight);
    ellipse(x+spread*0.3,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.6,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.2,y,cloudWidth,cloudHeight);
    ellipse(x-spread*0.7,y+spread,cloudWidth,cloudHeight);
    ellipse(x+spread*0.5,y-spread,cloudWidth,cloudHeight);
  };
};
