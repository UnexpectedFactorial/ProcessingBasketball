void update(int x, int y) {
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
