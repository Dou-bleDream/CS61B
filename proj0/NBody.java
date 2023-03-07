
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;


public class NBody {
    public static double readRadius(String file) {
        double R = 0.0;
        try {
            File inputfile = new File(file);
            Scanner scanner = new Scanner(inputfile);
//              将该文件对象传递给Scanner类的构造函数来创建一个用于读取该文件的Scanner对象。
//              Scanner类是Java I/O库中的另一个类，用于扫描输入源（如文件、标准输入等）并将
//              其转换为不同类型的数据（如整数、双精度等）
            int Nrows = scanner.nextInt();
            R = scanner.nextDouble();

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return R;
    }

    public static Body[] readBodies(String file) {
        Body[] bodies = null;
//        提前定义值为null做为默认，后面就只需return一次
        try {
            File inputfile = new File(file);
            Scanner scanner = new Scanner(inputfile);

            int Nrows = scanner.nextInt();
            scanner.nextDouble();
//            跳过前俩数据
            bodies = new Body[Nrows];
            for (int i = 0; i < Nrows; i++) {
                scanner.nextLine();
//            移动到下一行
                if (scanner.hasNextLine()) {
                    bodies[i] = new Body(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(),
                            scanner.nextDouble(), scanner.nextDouble(), scanner.next());
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bodies;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];
        double radius = readRadius(fileName);
        Body[] bodies = readBodies(fileName);

        
        StdDraw.enableDoubleBuffering();
          StdDraw.setScale(-radius, radius);
//        StdDraw.setXscale(-radius*10, radius*10);
//        StdDraw.setYscale(-radius*10, radius*10);
//        在调用StdDraw.setScale(-0.05, 1.05)方法之前，画布的默认坐标范围为[0, 1]，而在调用这个方法之后，
//        画布的坐标范围就变成了[-0.05, 1.05]，也就是说，我们在这个范围内绘制的任何图形都会被绘制出来。
//        然后，通过将整个范围缩放到[0, 1]，我们可以确保整个绘图区域都包含在新的坐标系中，并且不会超出范围

       double Timecurrent = 0.0;
        Double[] xForces = new Double[bodies.length];
        Double[] yForces = new Double[bodies.length];
        StdAudio.play("audio/2001.mid");
        while (Timecurrent < T){
            for(int i = 0; i < bodies.length; i++){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            for(int i = 0; i < bodies.length; i++){
                bodies[i].update(dt,xForces[i],xForces[i]);
            }
            StdDraw.clear();

            String background = "images/starfield.jpg";
            StdDraw.picture(0, 0, background);
            for(Body body: bodies){
                body.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            Timecurrent += dt;
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }

    }

}
