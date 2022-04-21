// Flocking
// Daniel Shiffman
// https://thecodingtrain.com
// https://thecodingtrain.com/CodingChallenges/124-flocking-boids.html
// https://youtu.be/mhjuuHl6qHM
// https://editor.p5js.org/codingtrain/sketches/ry4XZ8OkN

// Edited By Justin Holder - 4/20/22

const flock = [];

let alignSlider, cohesionSlider, separationSlider, boidQuadTee;

function setup() {
  createCanvas(window.innerWidth- window.innerWidth/90, window.innerHeight - window.innerHeight/10);
  frameRate(100);
  alignSlider = createSlider(0, 2, 1, 0.1);
  cohesionSlider = createSlider(0, 2, 1, 0.1);
  separationSlider = createSlider(0, 2, 1, 0.1);
  for (let i = 0; i < 100; i++) {
    let newBoid = new Boid();
    flock.push(newBoid);
  }
}

let count = 101;
let renderTime = 0;
function draw() {
  let start = performance.now();
  background(37, 37, 37);
  for (let boid of flock) {
    boid.edges();
    boid.flock(flock);
    boid.update();
    boid.show();
  }
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
