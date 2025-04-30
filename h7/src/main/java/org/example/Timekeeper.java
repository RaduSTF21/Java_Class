package org.example;

public class Timekeeper implements Runnable {
    private final int timeLimit;
    private final GameController gameController;

    public Timekeeper(int timeLimit, GameController gameController) {
        this.timeLimit = timeLimit;
        this.gameController = gameController;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (timeLimit * 1000);

        while (gameController.isGameRunning() && System.currentTimeMillis() < endTime) {
            long currentTime = System.currentTimeMillis();
            long elapsedSeconds = (currentTime - startTime) / 1000;

            if (elapsedSeconds % 10 == 0) {
                System.out.println("Game time: " + elapsedSeconds + " seconds");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        if (System.currentTimeMillis() >= endTime) {
            System.out.println("Time limit reached! The game will now end.");
            gameController.stopGame();
        }
    }
}