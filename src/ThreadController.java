public class ThreadController {

    public static void main (String args[]){
        method();
        method2();
        method3();
        method4();
        method5();
        method6();
        method7();

        UI ui = new UI();
        ui.setName("UI thread");
        ui.start();



        memoryManager manager = new memoryManager();
        manager.setName("Memory Manager thread");
        manager.start();





    }

    public static void method(){
        Sprite sprite = new Sprite(true,new floatingPoint(40,600),30,15,1);
        sprite.newForce(.3,-.6,9500);

        Sprite sprite2 = new Sprite(true,new floatingPoint(440 ,30),10,15,1);
        sprite2.newForce(-2,0,250);

//        Sprite sprite3 = new Sprite(true,new floatingPoint(550 ,125),10,15);
//        sprite3.newForce(0,0,1000);
    }

    public static void method2(){
        Sprite sprite = new Sprite(true,new floatingPoint(200,100),10,15,1);
        sprite.newForce(1,0,250);

        Sprite sprite2 = new Sprite(true,new floatingPoint(400 ,75),10,15,1);
        sprite2.newForce(-2,0,250);
    }

    public static void method3(){
        Sprite sprite = new Sprite(true,new floatingPoint(300,100),10,15,1);
        sprite.newForce(1,0,250);

        Sprite sprite2 = new Sprite(true,new floatingPoint(400 ,100),10,15,1);
        sprite2.newForce(-2,0,250);

        Sprite sprite3 = new Sprite(true,new floatingPoint(600 ,100),20,15,1);
        sprite3.newForce(-4,0,250);
    }

    public static void method4(){
        Sprite sprite = new Sprite(true, new floatingPoint(1000,100),10,15,1);
        Sprite sprite2 = new Sprite(true, new floatingPoint(400,100),20,15,1);
        sprite.newForce(-5,0,300);

    }

    public static void method5(){
        Sprite sprite = new Sprite(true, new floatingPoint(1000,300),10,15,1);
        Sprite sprite2 = new Sprite(true, new floatingPoint(400,300),20,15,1);
        sprite2.newForce(10,0,300);

    }

    public static void method6(){
        Sprite sprite = new Sprite(true, new floatingPoint(1000,300),20,15,1);
        Sprite sprite2 = new Sprite(true, new floatingPoint(400,300),10,15,1);
        sprite2.newForce(10,0,300);

    }

    public static void method7(){
        Sprite sprite = new Sprite(true, new floatingPoint(0,0),20,15,1);

    }
}
