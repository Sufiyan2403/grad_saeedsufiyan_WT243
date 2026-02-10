import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

class BikerResult {
    String name;
    long duration;
    LocalTime startTime;
    LocalTime endTime;

    BikerResult(String name, long duration,
                LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}


class BikerTask implements Callable<BikerResult> {

    private final String bikerName;
    private final int totalDistance;
    private final CountDownLatch startLatch;

    BikerTask(String bikerName, int distanceKm, CountDownLatch startLatch) {
        this.bikerName = bikerName;
        this.totalDistance = distanceKm * 1000;
        this.startLatch = startLatch;
    }

    public BikerResult call() throws Exception {

        startLatch.await();

        int covered = 0;
        Random random = new Random();

        long startMillis = System.currentTimeMillis();
        LocalTime beginTime = LocalTime.now();

        while (covered < totalDistance) {
            int speed = 100 + random.nextInt(400);
            Thread.sleep(speed);

            covered += 100;
            if (covered > totalDistance) covered = totalDistance;

            System.out.println(bikerName + " has covered " + covered + " m");
        }

        long endMillis = System.currentTimeMillis();
        LocalTime finishTime = LocalTime.now();

        return new BikerResult(
                bikerName,
                (endMillis - startMillis) / 1000,
                beginTime,
                finishTime
        );
    }
}

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);

        System.out.print("Enter race distance (km): ");
        int distance = sc.nextInt();

        System.out.println("Enter names of 10 bikers:");

        CountDownLatch startLatch = new CountDownLatch(10);
        List<Future<BikerResult>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String name = sc.next();
            futures.add(
                executor.submit(new BikerTask(name, distance, startLatch))
            );
        }

        System.out.println("Countdown");
        for (int i = 10; i >= 0; i--) {
            System.out.println(i);
            startLatch.countDown();
            Thread.sleep(500);
        }

        

        List<BikerResult> results = new ArrayList<>();
        for (Future<BikerResult> f : futures) {
            results.add(f.get());
        }

        executor.shutdown();

        results.sort(Comparator.comparingLong(r -> r.duration));

        System.out.println("\n--- Final Dashboard ---");
        int rank = 1;
        for (BikerResult r : results) {
            System.out.println(
                "Rank " + rank++ + " : " + r.name +
                " | Start: " + r.startTime +
                " | End: " + r.endTime +
                " | Time = " + r.duration + " s"
            );
        }

        sc.close();
    }
}
