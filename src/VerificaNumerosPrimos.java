import java.util.concurrent.CountDownLatch;

public class VerificaNumerosPrimos {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java VerificaNumerosPrimos <lista-de-numeros>");
            return;
        }

        int numThreads = args.length;
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (String arg : args) {
            int number = Integer.parseInt(arg);
            Thread thread = new Thread(new PrimeRunnable(number, latch));
            thread.start();
        }

        try {
            latch.await(); // Aguarda até todas as threads terminarem
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PrimeRunnable implements Runnable {
    private final int number;
    private final CountDownLatch latch;

    public PrimeRunnable(int number, CountDownLatch latch) {
        this.number = number;
        this.latch = latch;
    }

    @Override
    public void run() {
        if (isPrime(number)) {
            System.out.println("O numero " + number + " e primo.");
        } else {
            System.out.println("O numero " + number + " nao e primo.");
        }
        latch.countDown(); // Marca a conclusão da thread
    }

    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        if (num <= 3) {
            return true;
        }
        if (num % 2 == 0 || num % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}
