boolean shootButton(int x, int y, int dia) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if (sqrt(sq(disX) + sq(disY)) < dia/2) {
    return true;
  } else {
    return false;
  }
};
