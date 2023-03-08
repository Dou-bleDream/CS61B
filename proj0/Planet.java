

    public class Planet{
        private final double G = 6.67e-11;
        public double xxPos;
        public double yyPos;
        public double xxVel;
        public double yyVel;
        public double mass;
        public String imgFileName;
        public Planet(double xP, double yP, double xV,
                    double yV, double m, String img){
            xxPos = xP;
            yyPos = yP;
            xxVel = xV;
            yyVel = yV;
            mass =  m;
            imgFileName = img;
        }
        public Planet(Planet b){
            this.xxPos = b.xxPos;
            this.yyPos = b.yyPos;
            this.xxVel = b.xxVel;
            this.yyVel = b.yyVel;
            this.mass = b.mass;
            this.imgFileName = b.imgFileName;
        }
        public double calcDistance(Planet b){
            double r;
            double dx = b.xxPos - this.xxPos;
            double dy = b.yyPos - this.yyPos;
            r = Math.pow(Math.pow(dx,2)+Math.pow(dy,2),0.5);
            return r;
        }
        public  double calcForceExertedBy(Planet b){

            double F = G * this.mass * b.mass / Math.pow(calcDistance(b),2);
            return F;
        }
        public  double calcForceExertedByX(Planet b){
            double Fx = calcForceExertedBy(b) * (b.xxPos - this.xxPos) / calcDistance(b);
            return Fx;
        }

        public  double calcForceExertedByY(Planet b){
            double Fy = calcForceExertedBy(b) * (b.yyPos - this.yyPos) / calcDistance(b);
            return Fy;
        }
        public double calcNetForceExertedByX(Planet[] allBodys) {
            double netForceX = 0;
            for (Planet body : allBodys) {
                if (this.equals(body)) {
                    continue;
                }
                netForceX += calcForceExertedByX(body);
            }
            return netForceX;
        }

        public double calcNetForceExertedByY(Planet[] allBodys) {
            double netForceY = 0;
            for (Planet body : allBodys) {
                if (this.equals(body)) {
                    continue;
                }
                netForceY += calcForceExertedByY(body);
            }
            return netForceY;
        }
        public void update(double dt,double fX,double fY){

            double ax = fX / this.mass;   //x-a
            double ay = fY / this.mass;
            this.xxVel += dt * ax;    //x-vel
            this.yyVel += dt * ay;
            this.xxPos += dt * this.xxVel;    
            this.yyPos += dt * this.yyVel;


        }
        public void draw(){
            StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
        }

    }