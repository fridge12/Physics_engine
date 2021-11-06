public class memoryManager extends Thread {

    static long maxMemory = 40;
    static long maxMemoryPercent = 90;

    @Override
    public void run() {

        while (true) {

            memoryUsage(maxMemoryPercent);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

        private static void memoryUsage(long percent){

            //to convert memory to mb i divide it by 1,000,000 but to get percent i need to multiply by 100 so i just divide it by 10,000
            long memoryPercent =((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/10000)/maxMemory;
            System.out.println("memory usage ="+memoryPercent);

            if(memoryPercent>=percent){
                System.gc();
                System.out.println("called gc");
            }

        }


}
