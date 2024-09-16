package exercises04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SemaphoreImpTest {

  @Test
  public void semaphoreMustNotHaveNegativeState() throws InterruptedException {
    var semaphore = new SemaphoreImp(1);
    semaphore.release();
    semaphore.release();
    semaphore.acquire();
    assertEquals(0, semaphore.getState());
  }
}
