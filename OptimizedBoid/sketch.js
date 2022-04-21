// Flocking
// Daniel Shiffman
// https://thecodingtrain.com
// https://thecodingtrain.com/CodingChallenges/124-flocking-boids.html
// https://youtu.be/mhjuuHl6qHM
// https://editor.p5js.org/codingtrain/sketches/ry4XZ8OkN

// Edited By Justin Holder - 4/20/22

const flock = [];
let showQuad = false;

let alignSlider, cohesionSlider, separationSlider, checkBox;

function setup() {
  createCanvas(window.innerWidth- window.innerWidth/90, window.innerHeight - window.innerHeight/10);
  frameRate(100);
  alignSlider = createSlider(0, 2, 1, 0.1);
  cohesionSlider = createSlider(0, 2, 1, 0.1);
  separationSlider = createSlider(0, 2, 1, 0.1);
  checkBox = createCheckbox("Show Quads", false);
  checkBox.changed(checked);
  for (let i = 0; i < 500; i++) {
    let newBoid = new Boid();
    flock.push(newBoid);
  }
}

function checked() {
  console.log("Changed");
  if (checkBox.checked()) {
    showQuad = true;
  } else {
    showQuad = false;
  }
}

let count = 101;
let renderTime = 0;
function draw() {
  let start = performance.now();
  background(37, 37, 37);

  let boundary = new Rectangle(width, height, width, height);
  boidQuadTee = new QuadTree(boundary, 2);

  for (let boid of flock) {
    let point = new Point(boid.position.x, boid.position.y, boid);
    boidQuadTee.insert(point);
    boid.edges();
    boid.flock(boidQuadTee);
    boid.update();
    if(!showQuad) { boid.show(); };
  }

  if(showQuad) { boidQuadTee.show(); };

  if (count > 30) {
    renderTime = (performance.now() - start) / 1000;
    count = 0;
  } else {
    count++;
  }
    stroke("white");
    fill("white");
    textFont('sans-serif', 15);
    text( 'RenderTime: ' + (renderTime), 20, 30);
}
