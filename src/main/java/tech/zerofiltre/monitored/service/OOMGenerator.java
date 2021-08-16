package tech.zerofiltre.monitored.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OOMGenerator {


  private boolean shouldContinue = true;

  public void startOOMFor(long time) {
    new Thread(new Producer(), "producer").start();
    new Thread(new Consumer(), "consumer").start();
    new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(time);
        shouldContinue = false;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "oomStopper").start();

  }

  public void stopOOM() {
    shouldContinue = false;
  }

  BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();


  class Producer implements Runnable {

    @Override
    public void run() {
      while (shouldContinue) {
        queue.offer(new byte[3 * 1024 * 1024]);
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      log.info("Stopping Producer");
    }
  }

  class Consumer implements Runnable {

    @Override
    public void run() {
      while (shouldContinue) {
        try {
          queue.take();
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      log.info("Stopping Consumer");
    }
  }

}
